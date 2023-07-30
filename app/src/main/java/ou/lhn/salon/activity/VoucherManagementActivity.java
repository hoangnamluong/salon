package ou.lhn.salon.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ou.lhn.salon.R;
import ou.lhn.salon.db.model.User;
import ou.lhn.salon.db.model.Voucher;
import ou.lhn.salon.db.service.Voucher_db.VoucherService;
import ou.lhn.salon.db.service.Voucher_db.VoucherServiceImpl;
import ou.lhn.salon.view.adapter.AdminVoucherAdapter;

public class VoucherManagementActivity extends AppCompatActivity implements View.OnClickListener{

    ImageButton btnBack, btnAdd;
    Button btnSearch;
    EditText edtDate;
    ListView lvVoucher;
    Calendar calendar;
    ArrayList<Voucher>  voucherArrayList;
    AdminVoucherAdapter adminVoucherAdapter;
    private VoucherService voucherService = VoucherServiceImpl.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_management);
        initView();
        voucherArrayList = new ArrayList<>();
        adminVoucherAdapter = new AdminVoucherAdapter(VoucherManagementActivity.this, R.layout.voucher_list_layout, voucherArrayList);
        lvVoucher.setAdapter(adminVoucherAdapter);
        getVoucherList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getVoucherList();
    }

    private void initView() {
        btnBack = findViewById(R.id.admVoucherMnBtnBack);
        btnAdd = findViewById(R.id.admImgBtnAdd);
        lvVoucher = findViewById(R.id.admVoucherMnLvVoucher);
        edtDate = (EditText) findViewById(R.id.edtCondition);
        btnSearch = (Button) findViewById(R.id.btnSearch);

        btnAdd.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        edtDate.setOnClickListener(this);
    }

    private void getVoucherList(){
        ArrayList<Voucher> allVouchers = voucherService.getAllVoucher();

        if (allVouchers != null && !allVouchers.isEmpty()) {
            voucherArrayList.clear();
            voucherArrayList.addAll(allVouchers);
            adminVoucherAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "No vouchers found.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.admVoucherMnBtnBack) {
            finish();
        } else if (id == R.id.admImgBtnAdd) {
            goToActivity(AddVoucherActivity.class);
        } else if (id == R.id.edtCondition) {
            ChooseDate();
        } else if (id == R.id.btnSearch) {
            String expireDay = edtDate.getText().toString().trim();
            getVoucherListByExpireDate(expireDay);
        }
    }

    private void getVoucherListByExpireDate(String expireDate) {
        ArrayList<Voucher> allVouchers = voucherService.getVoucherListByExpireDate(expireDate);

        if (allVouchers != null && !allVouchers.isEmpty()) {
            voucherArrayList.clear();
            voucherArrayList.addAll(allVouchers);
            adminVoucherAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "No vouchers found.", Toast.LENGTH_SHORT).show();
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
                edtDate.setText(dateString);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void goToActivity(Class<?> targetActivityClass) {
        Intent intent = new Intent(VoucherManagementActivity.this, targetActivityClass);
        startActivity(intent);
    }
}