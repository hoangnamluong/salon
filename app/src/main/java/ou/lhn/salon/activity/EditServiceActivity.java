package ou.lhn.salon.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import ou.lhn.salon.R;
import ou.lhn.salon.db.model.Salon;
import ou.lhn.salon.db.model.Service;
import ou.lhn.salon.db.model.Stylist;
import ou.lhn.salon.db.service.Service_db.ServiceService;
import ou.lhn.salon.db.service.Service_db.ServiceServiceImpl;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class EditServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnBack;
    private Button btnSave;
    private EditText edtName, edtDescription, edtPrice, edtSalonID;
    private ServiceService serviceService = ServiceServiceImpl.getInstance(this);
    int serviceId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_service);

        // Ánh xạ các thành phần trong giao diện
        btnBack = findViewById(R.id.admEditServiceBtnBack);
        btnSave = findViewById(R.id.admEditServiceBtnSave);
        edtName = findViewById(R.id.admEditServiceEdtName);
        edtDescription = findViewById(R.id.admEditServiceEdtDescription);
        edtPrice = findViewById(R.id.admEditServiceEdtPrice);
        edtSalonID = findViewById(R.id.admEditServiceEdtSalonID);

        // Đặt sự kiện cho nút "Back" và "Lưu"
        btnBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        int id = getIntent().getIntExtra("serviceId", -1);
        if (id != -1) {
            serviceId = id;
        }

        // Lấy thông tin dịch vụ từ Intent (hoặc từ cơ sở dữ liệu) và hiển thị lên giao diện
        displayServiceInfo();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.admEditServiceBtnBack) {
            finish(); // Khi bấm nút "Back", thoát khỏi EditServiceActivity
        } else if (id == R.id.admEditServiceBtnSave) {
            // Xử lý lưu thông tin dịch vụ
            saveServiceInfo();
        }
    }

    private void displayServiceInfo() {
        Service service = serviceService.getServiceById(serviceId);
        if (service != null) {
            edtName.setText(service.getName());
            edtDescription.setText(service.getDescription());
            edtPrice.setText(service.getPrice() + "");
            edtSalonID.setText(service.getSalon().getId() + "");
        }
    }

    private void saveServiceInfo() {
        // Lấy thông tin từ các thành phần trong giao diện
        try {
            String name = edtName.getText().toString().trim();
            String description = edtDescription.getText().toString().trim();
            long price = Long.parseLong(edtPrice.getText().toString().trim());
            int salonId = Integer.parseInt(edtSalonID.getText().toString().trim());

            Salon salon = new Salon();
            salon.setId(salonId);
            Service service = new Service(serviceId, name, description, price, salon);
            if (!CheckValidateService(service)) return;
            if (serviceService.updateService(service)) {
                Toast.makeText(this, "Đã sửa", Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Vui lòng nhập giá!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean CheckValidateService(Service service) {
        if (service.getName().isEmpty() || service.getDescription().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
