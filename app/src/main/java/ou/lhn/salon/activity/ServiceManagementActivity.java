package ou.lhn.salon.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ou.lhn.salon.R;
import ou.lhn.salon.db.model.Service;
import ou.lhn.salon.db.model.Stylist;
import ou.lhn.salon.db.service.Service_db.ServiceService;
import ou.lhn.salon.db.service.Service_db.ServiceServiceImpl;
import ou.lhn.salon.db.service.Stylist_db.StylistService;
import ou.lhn.salon.db.service.Stylist_db.StylistServiceImpl;
import ou.lhn.salon.view.adapter.AdminServiceAdapter;
import ou.lhn.salon.view.adapter.AdminStylistAdapter;

public class ServiceManagementActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnBack, btnAdd;
    Button btnSearch;
    EditText edtName;
    ListView lvService;
    AdminServiceAdapter adminServiceAdapter;
    ArrayList<Service> serviceArrayList;
    ServiceService serviceService = ServiceServiceImpl.getInstance(this);

    int salonId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_management);
        initView();
        int id = getIntent().getIntExtra("salonId", -1);
        if (id != -1){
            salonId = id;
        }
        serviceArrayList = new ArrayList<>();
        adminServiceAdapter = new AdminServiceAdapter(ServiceManagementActivity.this, R.layout.service_list_layout, serviceArrayList);
        lvService.setAdapter(adminServiceAdapter);
        getServiceList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getServiceList();
    }

    private void initView() {
        btnBack = findViewById(R.id.admServiceMnBtnBack);
        btnAdd = findViewById(R.id.admImgBtnAdd);
        lvService = findViewById(R.id.admServiceMnLvService);
        edtName = (EditText) findViewById(R.id.edtCondition);
        btnSearch = (Button) findViewById(R.id.btnSearch);

        btnAdd.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnSearch.setOnClickListener(this);

    }
    private void getServiceList() {
        ArrayList<Service> allServices = serviceService.getAllServicesBySalonId(salonId);

        if (allServices != null && !allServices.isEmpty()) {
            serviceArrayList.clear();
            serviceArrayList.addAll(allServices);
            adminServiceAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "No services found.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.admServiceMnBtnBack) {
            finish();
        } else if (id == R.id.admImgBtnAdd) {
            goToActivity(AddServiceActivity.class, salonId);
        } else if (id == R.id.btnSearch) {
            String serviceName = edtName.getText().toString().trim();
            getServiceListByName(serviceName);
        }
    }

    private void getServiceListByName(String serviceName) {
        ArrayList<Service> allServices = serviceService.getServiceListByName(serviceName);

        if (allServices != null && !allServices.isEmpty()) {
            serviceArrayList.clear();
            serviceArrayList.addAll(allServices);
            adminServiceAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "No services found.", Toast.LENGTH_SHORT).show();
        }
    }

    private void goToActivity(Class<?> targetActivityClass, int salonId) {
        Intent intent = new Intent(ServiceManagementActivity.this, targetActivityClass);
        intent.putExtra("salonId", salonId);
        startActivity(intent);
    }
}