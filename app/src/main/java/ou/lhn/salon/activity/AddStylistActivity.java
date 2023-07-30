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
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import ou.lhn.salon.R;
import ou.lhn.salon.db.model.Salon;
import ou.lhn.salon.db.model.Stylist;
import ou.lhn.salon.db.service.Service_db.ServiceServiceImpl;
import ou.lhn.salon.db.service.Stylist_db.StylistService;
import ou.lhn.salon.db.service.Stylist_db.StylistServiceImpl;

public class AddStylistActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgAvatar;
    private ImageButton btnBack;
    private Button btnChooseImage, btnSave;
    private EditText edtName, edtCusPerDay, edtSalonID;

    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CODE_FOLDER = 456;
    int salonId = -1;

    private StylistService stylistService = StylistServiceImpl.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stylist);
        int id = getIntent().getIntExtra("salonId", -1);
        if (id != -1) {
            salonId = id;
        }

        // Ánh xạ các thành phần trong giao diện
        imgAvatar = findViewById(R.id.admAddStylistImgAvatar);
        btnChooseImage = findViewById(R.id.admAddStylistBtnChooseImage);
        btnSave = findViewById(R.id.admAddStylistBtnSave);
        btnBack = findViewById(R.id.admAddStylistBtnBack);
        edtName = findViewById(R.id.admAddStylistEdtName);
        edtCusPerDay = findViewById(R.id.admAddStylistEdtCusPerday);
        edtSalonID = findViewById(R.id.admAddStylistEdtSalonID);

        // Đặt sự kiện cho nút "Chọn ảnh" và "Lưu"
        btnChooseImage.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        edtSalonID.setText(salonId + "");
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.admAddStylistBtnBack) {
            finish(); // Khi bấm nút "Back", thoát khỏi AddStylistActivity
        } else if (id == R.id.admAddStylistBtnChooseImage) {
            // Xử lý chọn ảnh
            PopupMenu popupMenu = new PopupMenu(AddStylistActivity.this, view);
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
        } else if (id == R.id.admAddStylistBtnSave) {
            // Xử lý lưu thông tin thợ cắt tóc
            saveStylistInfo();
        }
    }

    private void saveStylistInfo() {
        // Lấy thông tin từ các thành phần trong giao diện
        try {
            String name = edtName.getText().toString().trim();
            int cusPerDay = Integer.parseInt(edtCusPerDay.getText().toString().trim());

            BitmapDrawable bitmapDrawable = (BitmapDrawable) imgAvatar.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();
            ByteArrayOutputStream byteArr = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArr);
            byte[] avatar = byteArr.toByteArray();

            Salon salon = new Salon();
            salon.setId(salonId);
            Stylist stylist = new Stylist(1, name, cusPerDay, true, salon, avatar);
            if (!CheckValidateStylist(stylist)) return;
            if (stylistService.addStylist(stylist)) {
                Toast.makeText(this, "Đã thêm", Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(this, "Mời nhập số lượng khách tối đa trên ngày!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean CheckValidateStylist(Stylist stylist) {
        if (stylist.getName().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên stylist!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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
        Intent intent = new Intent(AddStylistActivity.this, targetActivityClass);
        startActivity(intent);
    }
}
