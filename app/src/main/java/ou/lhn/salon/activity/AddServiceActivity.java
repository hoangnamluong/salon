package ou.lhn.salon.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ou.lhn.salon.R;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import ou.lhn.salon.R;
import ou.lhn.salon.db.model.Salon;
import ou.lhn.salon.db.model.Service;
import ou.lhn.salon.db.model.Stylist;
import ou.lhn.salon.db.service.Service_db.ServiceService;
import ou.lhn.salon.db.service.Service_db.ServiceServiceImpl;

public class AddServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnBack;
    private Button btnSave;
    private EditText edtName, edtDescription, edtPrice, edtSalonID;
    private ServiceService serviceService = ServiceServiceImpl.getInstance(this);
    int salonId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        int id = getIntent().getIntExtra("salonId", -1);
        if (id != -1) {
            salonId = id;
        }

        // Ánh xạ các thành phần trong giao diện
        btnBack = findViewById(R.id.admAddServiceBtnBack);
        btnSave = findViewById(R.id.admAddServiceBtnSave);
        edtName = findViewById(R.id.admAddServiceEdtName);
        edtDescription = findViewById(R.id.admAddServiceEdtDescription);
        edtPrice = findViewById(R.id.admAddServiceEdtPrice);
        edtSalonID = findViewById(R.id.admAddServiceEdtSalonID);

        edtSalonID.setText(salonId + "");
        // Đặt sự kiện cho nút "Back" và "Lưu"
        btnBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.admAddServiceBtnBack) {
            finish();
        } else if (id == R.id.admAddServiceBtnSave) {
            // Xử lý lưu thông tin dịch vụ
            saveServiceInfo();
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
            Service service = new Service(1, name, description, price, salon);
            if (!CheckValidateService(service)) return;
            if (serviceService.addService(service)) {
                Toast.makeText(this, "Đã thêm", Toast.LENGTH_SHORT).show();
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
