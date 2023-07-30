package ou.lhn.salon.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import ou.lhn.salon.R;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ou.lhn.salon.R;
import ou.lhn.salon.db.model.Salon;
import ou.lhn.salon.db.model.Voucher;
import ou.lhn.salon.db.service.Voucher_db.VoucherService;
import ou.lhn.salon.db.service.Voucher_db.VoucherServiceImpl;

public class EditVoucherActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnBack;
    private Button btnSave;
    private EditText edtCode, edtPercentage, edtExpiredDate;
    private RadioGroup rbGroup;
    private RadioButton rbTrue, rbFalse;
    private int voucherId = -1;
    private VoucherService voucherService = VoucherServiceImpl.getInstance(this);
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_voucher);
        int id = getIntent().getIntExtra("voucherId", -1);
        if (id != -1) {
            voucherId = id;
        }
        // Ánh xạ các thành phần trong giao diện
        btnBack = findViewById(R.id.admEditVoucherBtnBack);
        btnSave = findViewById(R.id.admEditVoucherBtnSave);
        edtCode = findViewById(R.id.admEditVoucherEdtCode);
        edtPercentage = findViewById(R.id.admEditVoucherEdtPercentage);
        edtExpiredDate = findViewById(R.id.admEditVoucherEdtExpiredDate);
        rbGroup = findViewById(R.id.admEditVoucherRbgActive);
        rbTrue = findViewById(R.id.admEditVoucherRbTrue);
        rbFalse = findViewById(R.id.admEditVoucherRbFalse);

        // Đặt sự kiện cho nút "Back" và "Lưu"
        btnBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        edtExpiredDate.setOnClickListener(this);

        displayVoucherInfo();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.admEditVoucherBtnBack) {
            finish(); // Khi bấm nút "Back", thoát khỏi EditVoucherActivity
        } else if (id == R.id.admEditVoucherEdtExpiredDate) {
            ChooseDate();
        } else if (id == R.id.admEditVoucherBtnSave) {
            // Xử lý lưu thông tin voucher
            saveVoucherInfo();
        }
    }

    private void ChooseDate() {
        calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String dateString = sdf.format(calendar.getTime());
                edtExpiredDate.setText(dateString);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void displayVoucherInfo() {
        Voucher voucher = voucherService.getVoucherById(voucherId);
        if (voucher != null) {
            edtCode.setText(voucher.getCode());
            edtPercentage.setText(voucher.getPercentage() + "");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String expireDateFormatted = sdf.format(voucher.getExpiredDate());
            edtExpiredDate.setText(expireDateFormatted);
            if (voucher.isActive()) {
                rbTrue.setChecked(true);
            } else {
                rbFalse.setChecked(true);
            }
        }
    }

    private void saveVoucherInfo() {
        // Lấy thông tin từ các thành phần trong giao diện
        try {
            String code = edtCode.getText().toString().trim();
            int percentage = Integer.parseInt(edtPercentage.getText().toString().trim());
            boolean active = rbTrue.isChecked() == true;
            String expiredDate = edtExpiredDate.getText().toString().trim();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = sdf.parse(expiredDate);
            Voucher voucher = new Voucher(voucherId, code, percentage, date, active);
            if (!CheckValidateVoucher(voucher)) return;
            if (voucherService.updateVoucher(voucher)) {
                Toast.makeText(this, "Đã sửa", Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(this, "Vui lòng nhập % giảm giá!", Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Vui lòng chọn ngày!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean CheckValidateVoucher(Voucher voucher) {
        if (voucher.getCode().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập mã code!", Toast.LENGTH_SHORT).show();
            return false;
        }
        int per = voucher.getPercentage();
        if (per <= 0 || per > 100) {
            Toast.makeText(this, "Nhập giá trị giảm giá (%) từ 0 đến 100!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
