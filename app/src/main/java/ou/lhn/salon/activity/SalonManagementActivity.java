package ou.lhn.salon.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ou.lhn.salon.R;
import ou.lhn.salon.db.model.Salon;
import ou.lhn.salon.db.model.Stylist;
import ou.lhn.salon.db.service.Salon_db.SalonSerivce;
import ou.lhn.salon.db.service.Salon_db.SalonServiceImpl;
import ou.lhn.salon.view.adapter.AdminSalonAdapter;

public class SalonManagementActivity extends AppCompatActivity implements View.OnClickListener{

    ImageButton btnBack, btnAdd;
    Button btnSearch;
    ListView lvSalon;
    EditText edtName;
    ArrayList<Salon> salonList;
    AdminSalonAdapter adminSalonAdapter;
    SalonSerivce salonSerivce = SalonServiceImpl.getInstance(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon_management);
        InitView();
        salonList = new ArrayList<Salon>();
        adminSalonAdapter = new AdminSalonAdapter(SalonManagementActivity.this, R.layout.salon_list_layout, salonList);
        lvSalon.setAdapter(adminSalonAdapter);
        getSalonList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSalonList();
    }

    private void InitView() {
        btnBack = (ImageButton) findViewById(R.id.admSalonMnBtnBack);
        btnAdd = (ImageButton) findViewById(R.id.admImgBtnAdd);
        lvSalon = (ListView) findViewById(R.id.admSalonMnLvSalon);
        edtName = (EditText) findViewById(R.id.edtCondition);
        btnSearch = (Button) findViewById(R.id.btnSearch);

        btnAdd.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
    }

    private void getSalonList(){
        ArrayList<Salon> allSalons = salonSerivce.getAllSalons();

        if (allSalons != null && !allSalons.isEmpty()) {
            salonList.clear();
            salonList.addAll(allSalons);
            adminSalonAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "No salon found.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.admSalonMnBtnBack){
            GoToActivity(AdminHomeActivity.class);
        }
        else if (id == R.id.admImgBtnAdd){
            GoToActivity(AddSalonActivity.class);
        } else if (id == R.id.btnSearch) {
            String salonName = edtName.getText().toString().trim();
            getSalonListByName(salonName);
        }
    }

    private void getSalonListByName(String salonName) {
        ArrayList<Salon> allSalons = salonSerivce.getListSalonByName(salonName);

        if (allSalons != null && !allSalons.isEmpty()) {
            salonList.clear();
            salonList.addAll(allSalons);
            adminSalonAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "No salon found.", Toast.LENGTH_SHORT).show();
        }
    }

    private void GoToActivity(Class<?> targetActivityClass) {
        Intent intent = new Intent(SalonManagementActivity.this, targetActivityClass);
        startActivity(intent);
    }
}