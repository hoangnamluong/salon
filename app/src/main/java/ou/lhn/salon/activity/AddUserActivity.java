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
import ou.lhn.salon.db.service.Auth_db.AuthService;
import ou.lhn.salon.db.service.Auth_db.AuthServiceImpl;
import ou.lhn.salon.db.service.User_db.UserService;
import ou.lhn.salon.db.service.User_db.UserServiceImpl;

public class AddUserActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imgAvatar;
    ImageButton imgBtnBack;
    Button btnChooseAvatar, btnSave;
    EditText edtUserName, edtPassword, edtFullname, edtEmail, edtPhone, edtSalonId;
    Spinner spRole;

    LinearLayout lnSalonIdLayout;

    private ArrayAdapter<String> rolesAdapter;
    private List<String> rolesList;
    private int[] roleValues;

    int roleSelected = 1;
    int salonIdDefault = 1;

    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CODE_FOLDER = 456;

    private UserService userService = UserServiceImpl.getInstance(this);
    private AuthService authService = AuthServiceImpl.getInstance(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        InitView();
    }

    private void InitView() {
        imgAvatar = (ImageView) findViewById(R.id.admAddUserImgAvatar);
        imgBtnBack = (ImageButton) findViewById(R.id.admAddUserBtnBack);
        btnChooseAvatar = (Button) findViewById(R.id.admAddUserBtnChooseAvatar);
        btnSave = (Button) findViewById(R.id.admAddUserBtnSave);
        edtUserName = (EditText) findViewById(R.id.admAddUserEdtUserName);
        edtPassword = (EditText) findViewById(R.id.admAddUserEdtPassword);
        edtFullname = (EditText) findViewById(R.id.admAddUserEdtFullName);
        edtEmail = (EditText) findViewById(R.id.admAddUserEdtEmail);
        edtPhone = (EditText) findViewById(R.id.admAddUserEdtPhone);
        edtSalonId = (EditText) findViewById(R.id.admAddUserEdtSalonId);
        lnSalonIdLayout = (LinearLayout) findViewById(R.id.admAddUserSalonIdLayout);
        spRole = (Spinner) findViewById(R.id.admAddUserSpRole);

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

        spRole.setSelection(1);

//        Lắng nghe sự kiện khi người dùng chọn một role
        spRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                roleSelected = roleValues[i];
                Toast.makeText(AddUserActivity.this, roleSelected + "", Toast.LENGTH_SHORT).show();
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
//        Gán sự kiện onclick cho button back
        if (id == R.id.admAddUserBtnBack) {
            finish();
        }
//        Gán sự kiện onclick cho button chọn ảnh
        else if (id == R.id.admAddUserBtnChooseAvatar) {
            PopupMenu popupMenu = new PopupMenu(AddUserActivity.this, view);
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
        } else if (id == R.id.admAddUserBtnSave) {
            saveUserInfo();
        }
    }

    private void saveUserInfo() {
        String username = edtUserName.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
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
        String passworkEncrypt = authService.encrypt(password);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imgAvatar.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArr = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArr);
        byte[] avatar = byteArr.toByteArray();

        try {
            User user = new User(1, fullname, username, passworkEncrypt, email, phone, true, role, avatar, salon);
            if (!CheckValidateUser(user)) return;
            if (userService.addUser(user)) {
                Toast.makeText(this, "Đã thêm", Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean CheckValidateUser(User user) {
        if (user.getUsername().isEmpty() || user.getPassword().isEmpty() || user.getFullName().isEmpty() || user.getEmail().isEmpty() || user.getPhone().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(user.getEmail()).matches()) {
            Toast.makeText(this, "Địa chỉ email không hợp lệ.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!isUsernameUnique(user.getUsername())) {
            Toast.makeText(this, "Username đã tồn tại.", Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(AddUserActivity.this, targetActivityClass);
        startActivity(intent);
    }
}