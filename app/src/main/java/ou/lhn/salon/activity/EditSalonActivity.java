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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import ou.lhn.salon.R;
import ou.lhn.salon.db.model.Salon;
import ou.lhn.salon.db.service.Salon_db.SalonSerivce;
import ou.lhn.salon.db.service.Salon_db.SalonServiceImpl;

public class EditSalonActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgSalon;
    private ImageButton btnBack;
    private Button btnChooseImage, btnSave;
    private EditText edtNameSalon, edtDescription, edtAddress;
    private RadioGroup rbgActive;
    private RadioButton rbTrue, rbFalse;

    int salonId = -1;
    SalonSerivce salonSerivce = SalonServiceImpl.getInstance(this);
    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CODE_FOLDER = 456;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_salon);

        int id = getIntent().getIntExtra("salonId", -1);
        if (id != -1) {
            salonId = id;
        }
        Toast.makeText(this, salonId + "", Toast.LENGTH_SHORT).show();
        // Ánh xạ các thành phần trong giao diện
        imgSalon = findViewById(R.id.admEditSalonImgSalon);
        btnChooseImage = findViewById(R.id.admEditSalonBtnChooseImage);
        btnSave = findViewById(R.id.admEditSalonBtnSave);
        btnBack = findViewById(R.id.admEditSalonBtnBack);
        edtNameSalon = findViewById(R.id.admEditSalonEdtNameSalon);
        edtDescription = findViewById(R.id.admEditSalonEdtDescription);
        edtAddress = findViewById(R.id.admEditSalonEdtAddress);
        rbgActive = findViewById(R.id.admEditSalonRbgActive);
        rbTrue = findViewById(R.id.admEditSalonRbTrue);
        rbFalse = findViewById(R.id.admEditSalonRbFalse);


        // Đặt sự kiện cho nút "Chọn ảnh" và "Lưu"
        btnChooseImage.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        // Xử lý hiển thị dữ liệu salon cần chỉnh sửa
        displaySalonData();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.admEditSalonBtnBack) {
            finish(); // Khi bấm nút "Back", thoát khỏi EditSalonActivity
        } else if (id == R.id.admEditSalonBtnChooseImage) {
            // Xử lý chọn ảnh
            PopupMenu popupMenu = new PopupMenu(EditSalonActivity.this, view);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    int id = menuItem.getItemId();
                    if (id == R.id.itemCamera) {
                        // Mở camera để chụp ảnh
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, REQUEST_CODE_CAMERA);
                        return true;
                    } else if (id == R.id.itemFile) {
                        // Mở thư mục để chọn ảnh từ bộ sưu tập
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
        } else if (id == R.id.admEditSalonBtnSave) {
            // Xử lý lưu thông tin salon
            saveSalonInfo();
        }
    }

    private void displaySalonData() {
        Salon salon = salonSerivce.getSalonById(salonId);
        if (salon != null) {
            if(salon.getImage() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(salon.getImage(), 0, salon.getImage().length);
                imgSalon.setImageBitmap(bitmap);
            }

            edtNameSalon.setText(salon.getName());
            edtDescription.setText(salon.getDescription());
            edtAddress.setText(salon.getAddress());
            if (salon.isActive()) {
                rbTrue.setChecked(true);
            } else {
                rbFalse.setChecked(true);
            }
        }
    }

    private void saveSalonInfo() {
        // Lấy thông tin từ các thành phần trong giao diện
        String nameSalon = edtNameSalon.getText().toString().trim();
        String description = edtDescription.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();
        boolean active = rbTrue.isChecked() == true;

        BitmapDrawable bitmapDrawable = (BitmapDrawable) imgSalon.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArr = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArr);
        byte[] image = byteArr.toByteArray();

        try {
            Salon salon = new Salon(salonId, nameSalon, address, description, active, image);
            if (!CheckValidateSalon(salon)) return;
            if (salonSerivce.updateSalon(salon)) {
                Toast.makeText(this, "Đã sửa", Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean CheckValidateSalon(Salon salon) {
        if (salon.getName().isEmpty() || salon.getAddress().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgSalon.setImageBitmap(bitmap);
        }
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgSalon.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void GoToActivity(Class<?> targetActivityClass) {
        Intent intent = new Intent(EditSalonActivity.this, targetActivityClass);
        startActivity(intent);
    }
}
