package ou.lhn.salon.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

import ou.lhn.salon.R;
import ou.lhn.salon.db.model.Salon;

public class SalonListActivity extends AppCompatActivity {
    private ArrayList<Salon> salonModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon_list);

        initView();

        setUpSalonModels();

        setUpSalonListView();
    }


    private void initView() {

    }

    private void setUpSalonModels() {
        String[] salonName = getResources().getStringArray(R.array.salon_name);
        String[] salonAddress = getResources().getStringArray(R.array.salon_address);
    }

    private void setUpSalonListView() {
    }
}