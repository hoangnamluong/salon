package ou.lhn.salon.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import ou.lhn.salon.R;
import ou.lhn.salon.db.model.Voucher;
import ou.lhn.salon.db.service.Voucher_db.VoucherService;
import ou.lhn.salon.db.service.Voucher_db.VoucherServiceImpl;

public class VoucherActivity extends AppCompatActivity {

    private ListView lvVoucher;
    private VoucherService voucherService = VoucherServiceImpl.getInstance(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_user);

        initView();
    }

    private void initView(){
        lvVoucher = (ListView) findViewById(R.id.lvVoucher);
        ArrayAdapter arr;
        arr = new ArrayAdapter(this, androidx.transition.R.layout.support_simple_spinner_dropdown_item
                , voucherService.getAllVoucher());
        lvVoucher.setAdapter(arr);
    }
}
