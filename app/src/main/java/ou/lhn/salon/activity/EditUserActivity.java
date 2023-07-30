package ou.lhn.salon.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import ou.lhn.salon.R;
import ou.lhn.salon.db.model.Salon;
import ou.lhn.salon.db.model.User;
import ou.lhn.salon.db.service.User_db.UserService;
import ou.lhn.salon.db.service.User_db.UserServiceImpl;

public class EditUserActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imgAvatar;
    ImageButton imgBtnBack;
    Button btnChooseAvatar, btnSave;
    EditText edtUserName, edtFullname, edtEmail, edtPhone, edtSalonId;
    Spinner spRole;
    RadioGroup rgActive;
    RadioButton rbTrue, rbFalse;
    LinearLayout lnSalonIdLayout;
    int userId = -1;
    private ArrayAdapter<String> rolesAdapter;
    private List<String> rolesList;
    private int[] roleValues;

    int roleSelected = 1;
    int salonIdDefault = 1;
    boolean active = true;

    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CODE_FOLDER = 456;

    private UserService userService = UserServiceImpl.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        int id = getIntent().getIntExtra("userId", -1);
        if (id != -1) {
            userId = id;
        }
        InitView();
        displaySalonData();
    }

    private void InitView() {
        imgAvatar = (ImageView) findViewById(R.id.admEditUserImgAvatar);
        imgBtnBack = (ImageButton) findViewById(R.id.admEditUserBtnBack);
        btnSave = (Button) findViewById(R.id.admEditUserBtnSave);
        btnChooseAvatar = (Button) findViewById(R.id.admEditUserBtnChooseAvatar);
        edtUserName = (EditText) findViewById(R.id.admEditUserEdtUserName);
        edtFullname = (EditText) findViewById(R.id.admEditUserEdtFullName);
        edtEmail = (EditText) findViewById(R.id.admEditUserEdtEmail);
        edtPhone = (EditText) findViewById(R.id.admEditUserEdtPhone);
        edtSalonId = (EditText) findViewById(R.id.admEditUserEdtSalonId);
        lnSalonIdLayout = (LinearLayout) findViewById(R.id.admEditUserSalonIdLayout);
        spRole = (Spinner) findViewById(R.id.admEditUserSpRole);
        rgActive = (RadioGroup) findViewById(R.id.admEditUserRbgActive);
        rbTrue = (RadioButton) findViewById(R.id.admEditUserRbTrue);
        rbFalse = (RadioButton) findViewById(R.id.admEditUserRbFalse);


//        Gán sự kiện onclick cho button
        imgBtnBack.setOnClickListener(this);
        btnChooseAvatar.setOnClickListener(this);
        btnSave.setOnClickListener(this);

//        Nhận giá trị cho các list từ resource
        rolesList = Arrays.asList(getResources().getStringArray(R.array.roles));
        roleValues = getResources().getIntArray(R.array.roleValues);

//        Khởi tạo và gán Adapter cho spiner
        rolesAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, rolesList);
        rolesAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spRole.setAdapter(rolesAdapter);

//        Lắng nghe sự kiện khi người dùng chọn một role
        spRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                roleSelected = roleValues[i];
                Toast.makeText(EditUserActivity.this, roleSelected + "", Toast.LENGTH_SHORT).show();
                if (roleSelected != 0 && roleSelected != 1) {
                    lnSalonIdLayout.setVisibility(View.VISIBLE);
                } else lnSalonIdLayout.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.admEditUserBtnBack) {
            finish();
        }
        //        Gán sự kiện onclick cho button chọn ảnh
        else if (id == R.id.admEditUserBtnChooseAvatar) {
            PopupMenu popupMenu = new PopupMenu(EditUserActivity.this, view);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    int id = menuItem.getItemId();
                    if (id == R.id.itemCamera) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, REQUEST_CODE_CAMERA);
                        return true;
                    } else if (id == R.id.itemFile) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, REQUEST_CODE_FOLDER);
                        return true;
                    }
                    return false;
                }
            });
            popupMenu.inflate(R.menu.popup_menu_choose_image);
            popupMenu.show();
        } else if (id == R.id.admEditUserBtnSave) {
            // Xử lý lưu thông tin người dùng
            saveUserInfo();
        }
    }

    private void displaySalonData() {
        User user = userService.getUserById(userId);
        if (user != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(user.getAvatar(), 0, user.getAvatar().length);
            imgAvatar.setImageBitmap(bitmap);
            edtUserName.setText(user.getUsername());
            edtFullname.setText(user.getFullName());
            edtEmail.setText(user.getEmail());
            edtPhone.setText(user.getPhone());
            if (user.isActive()) {
                rbTrue.setChecked(true);
            } else {
                rbFalse.setChecked(true);
            }
            roleSelected = user.getRole();
            spRole.setSelection(roleSelected);
            edtSalonId.setText(user.getSalon().getId() + "");
        }
    }

    private void saveUserInfo() {
        String username = edtUserName.getText().toString().trim();
        String fullname = edtFullname.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        int role = roleSelected;
        int salonId = salonIdDefault;
        if (role != 0 && role != 1) {
            salonId = Integer.parseInt(edtSalonId.getText().toString());
        }
        Salon salon = new Salon();
        salon.setId(salonId);
        boolean active = rbTrue.isChecked() == true;
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imgAvatar.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArr = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArr);
        byte[] avatar = byteArr.toByteArray();

        try {
            User user = new User(userId, fullname, username, "NoUpdate", email, phone, active, role, avatar, salon);
            if (!CheckValidateUser(user)) return;
            if (userService.updateUser(user)) {
                Toast.makeText(this, "Đã sửa", Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean CheckValidateUser(User user) {
        if (user.getUsername().isEmpty() || user.getFullName().isEmpty() || user.getEmail().isEmpty() || user.getPhone().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(user.getEmail()).matches()) {
            Toast.makeText(this, "Địa chỉ email không hợp lệ.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean isUsernameUnique(String username) {
        User existingUser = userService.getUserByUsername(username);
        return existingUser == null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgAvatar.setImageBitmap(bitmap);
        }
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgAvatar.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void GoToActivity(Class<?> targetActivityClass) {
        Intent intent = new Intent(EditUserActivity.this, targetActivityClass);
        startActivity(intent);
    }
}