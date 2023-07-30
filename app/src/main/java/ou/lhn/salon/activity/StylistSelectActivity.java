package ou.lhn.salon.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ou.lhn.salon.R;
import ou.lhn.salon.db.model.Stylist;
import ou.lhn.salon.db.service.Stylist_db.StylistService;
import ou.lhn.salon.db.service.Stylist_db.StylistServiceImpl;
import ou.lhn.salon.view.adapter.StylistSelectAdapter;

public class StylistSelectActivity extends AppCompatActivity {
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private final StylistService stylistService = StylistServiceImpl.getInstance(this);

    private AppCompatButton stylistSelectBtnConfirm, stylistSelectBtnCancel;
    private ListView stylistSelectLv;

    private ArrayList<Stylist> stylists;
    private StylistSelectAdapter adapter;
    private Stylist stylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stylist_select);

        initView();
        initListener();
        initListView();
    }

    private void initView() {
        stylistSelectBtnConfirm = findViewById(R.id.stylistSelectBtnConfirm);
        stylistSelectBtnCancel = findViewById(R.id.stylistSelectBtnCancel);
        stylistSelectLv = findViewById(R.id.stylistSelectLv);
    }

    private void initListener() {
        stylistSelectBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        stylistSelectBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmClicked();
            }
        });

        stylistSelectLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                stylist = stylists.get(position);
            }
        });
    }

    private void initListView() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                stylists = stylistService.getAllStylist();

                if(stylists == null || stylists.size() == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(StylistSelectActivity.this, "Getting Stylists Failed Or Salon Has No ", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }

                adapter = new StylistSelectAdapter(StylistSelectActivity.this, R.layout.list_view_stylist_item, stylists);
                stylistSelectLv.setAdapter(adapter);
            }
        }).start();
    }

    private void confirmClicked() {
        if(stylist == null) {
            Toast.makeText(this, "You have not choose any stylist", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra("stylist", stylist);
        setResult(RESULT_OK, intent);
        finish();
    }
}