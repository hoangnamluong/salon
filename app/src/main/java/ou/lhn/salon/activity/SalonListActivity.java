package ou.lhn.salon.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import ou.lhn.salon.R;
import ou.lhn.salon.recView.adapter.SalonAdapter;
import ou.lhn.salon.db.model.Salon;
import ou.lhn.salon.recView.impl.RecyclerViewInterface;

public class SalonListActivity extends AppCompatActivity implements RecyclerViewInterface {
    private ArrayList<Salon> salonModels = new ArrayList<>();

    private RecyclerView salonRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon_list);

        initView();

        setUpSalonModels();

        SalonAdapter adapter = new SalonAdapter(this, salonModels, this);

        setUpSalonRecView(adapter);
    }


    private void initView() {
        salonRecyclerView = findViewById(R.id.salonListRecViewSalon);
    }

    private void setUpSalonModels() {
        String[] salonName = getResources().getStringArray(R.array.salon_name);
        String[] salonAddress = getResources().getStringArray(R.array.salon_address);
    }

    private void setUpSalonRecView(SalonAdapter adapter) {
        salonRecyclerView.setAdapter(adapter);
        salonRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemClick(int pos) {
        Toast.makeText(this, salonModels.get(pos).toString(), Toast.LENGTH_SHORT).show();
    }
}