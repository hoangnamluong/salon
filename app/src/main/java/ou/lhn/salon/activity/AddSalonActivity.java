package ou.lhn.salon.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import ou.lhn.salon.R;
import ou.lhn.salon.db.model.Salon;
import ou.lhn.salon.db.model.User;
import ou.lhn.salon.db.service.Salon_db.SalonSerivce;
import ou.lhn.salon.db.service.Salon_db.SalonServiceImpl;

public class AddSalonActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgSalon;
    private ImageButton btnBack;
    private Button btnChooseImage, btnSave;
    private EditText edtNameSalon, edtDescription, edtAddress;

    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CODE_FOLDER = 456;

    SalonSerivce salonSerivce = SalonServiceImpl.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_salon);

        // Ánh xạ các thành phần trong giao diện
        imgSalon = findViewById(R.id.admAddSalonImgSalon);
        btnChooseImage = findViewById(R.id.admAddSalonBtnChooseImage);
        btnSave = findViewById(R.id.admAddSalonBtnSave);
        btnBack = findViewById(R.id.admAddSalonBtnBack);
        edtNameSalon = findViewById(R.id.admAddSalonEdtNameSalon);
        edtDescription = findViewById(R.id.admAddSalonEdtDescription);
        edtAddress = findViewById(R.id.admAddSalonEdtAddress);

        // Đặt sự kiện cho nút "Chọn ảnh" và "Lưu"
        btnChooseImage.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.admAddSalonBtnBack){
            GoToActivity(SalonManagementActivity.class);
        }
        else if (id == R.id.admAddSalonBtnChooseImage) {
            // Xử lý chọn ảnh
            PopupMenu popupMenu = new PopupMenu(AddSalonActivity.this, view);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    int id = menuItem.getItemId();
                    if (id == R.id.itemCamera){
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, REQUEST_CODE_CAMERA);
                        return true;
                    }
                    else if (id == R.id.itemFile){
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent,REQUEST_CODE_FOLDER);
                        return true;
                    }
                    return false;
                }
            });

            popupMenu.inflate(R.menu.popup_menu_choose_image);
            popupMenu.show();
        } else if (id == R.id.admAddSalonBtnSave) {
            // Xử lý lưu thông tin salon
            saveSalonInfo();
        }
    }

    private void saveSalonInfo() {
        // Lấy thông tin từ các thành phần trong giao diện
        String nameSalon = edtNameSalon.getText().toString().trim();
        String description = edtDescription.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();

        BitmapDrawable bitmapDrawable = (BitmapDrawable) imgSalon.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArr = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArr);
        byte[] image = byteArr.toByteArray();

        try {
            Salon salon = new Salon(1, nameSalon,address, description, true, image);
            if (!CheckValidateSalon(salon)) return;
            if(salonSerivce.addSalon(salon)){
                Toast.makeText(this, "Đã thêm", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        catch (Exception e){
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
        if(requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgSalon.setImageBitmap(bitmap);
        }
        if(requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null){
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
        Intent intent = new Intent(AddSalonActivity.this, targetActivityClass);
        startActivity(intent);
    }
}
