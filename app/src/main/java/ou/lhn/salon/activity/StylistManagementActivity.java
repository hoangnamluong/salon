package ou.lhn.salon.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ou.lhn.salon.R;
import ou.lhn.salon.db.model.Stylist;
import ou.lhn.salon.db.service.Stylist_db.StylistService;
import ou.lhn.salon.db.service.Stylist_db.StylistServiceImpl;
import ou.lhn.salon.view.adapter.AdminStylistAdapter;

public class StylistManagementActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnBack, btnAdd;
    ListView lvStylist;
    Button btnSearch;
    EditText edtName;
    AdminStylistAdapter adminStylistAdapter;

    ArrayList<Stylist> stylistArrayList;
    StylistService stylistService = StylistServiceImpl.getInstance(this);

    int salonId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stylist_management);
        initView();
        int id = getIntent().getIntExtra("salonId", -1);
        if (id != -1){
            salonId = id;
        }
        stylistArrayList = new ArrayList<>();
        adminStylistAdapter = new AdminStylistAdapter(StylistManagementActivity.this, R.layout.stylist_list_layout, stylistArrayList);
        lvStylist.setAdapter(adminStylistAdapter);
        Toast.makeText(this, salonId +"", Toast.LENGTH_SHORT).show();
        getListStylist();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getListStylist();
    }

    private void initView() {
        btnBack = findViewById(R.id.admStylistMnBtnBack);
        btnAdd = findViewById(R.id.admImgBtnAdd);
        lvStylist = findViewById(R.id.admStylistMnLvStylist);
        edtName = (EditText) findViewById(R.id.edtCondition);
        btnSearch = (Button) findViewById(R.id.btnSearch);

        btnAdd.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnSearch.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.admStylistMnBtnBack) {
            finish();
        } else if (id == R.id.admImgBtnAdd) {
            Intent intent = new Intent(StylistManagementActivity.this, AddStylistActivity.class);
            intent.putExtra("salonId", salonId);
            startActivity(intent);
        } else if (id == R.id.btnSearch) {
            String stylistName = edtName.getText().toString().trim();
            getServiceListByName(stylistName);
        }
    }

    private void getServiceListByName(String stylistName) {
        ArrayList<Stylist> allStylists = stylistService.getListStylistByName(stylistName);

        if (allStylists != null && !allStylists.isEmpty()) {
            stylistArrayList.clear();
            stylistArrayList.addAll(allStylists);
            adminStylistAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "No stylists found.", Toast.LENGTH_SHORT).show();
        }
    }

    private void getListStylist() {
        ArrayList<Stylist> allStylists = stylistService.getAllStylistBySalonId(salonId);

        if (allStylists != null && !allStylists.isEmpty()) {
            stylistArrayList.clear();
            stylistArrayList.addAll(allStylists);
            adminStylistAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "No stylists found.", Toast.LENGTH_SHORT).show();
        }
    }

    private void goToActivity(Class<?> targetActivityClass) {
        Intent intent = new Intent(StylistManagementActivity.this, targetActivityClass);
        startActivity(intent);
    }
}