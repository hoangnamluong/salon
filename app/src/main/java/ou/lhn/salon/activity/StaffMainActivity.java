package ou.lhn.salon.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import ou.lhn.salon.R;
import ou.lhn.salon.util.ReplaceFragment;

public class StaffMainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager = getSupportFragmentManager();

    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_main);

        initView();
        ReplaceFragment.replaceFragment(fragmentManager, new HomeFragmentStaff(), R.id.staffMainFrame);
        initListener();
    }

    private void initView() {
        bottomNavigationView = findViewById(R.id.bottomAppMenu);
    }

    private void initListener() {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_home) {
                    ReplaceFragment.replaceFragment(fragmentManager, new HomeFragmentStaff(), R.id.staffMainFrame);
                    return true;
                } else if (itemId == R.id.navigation_history) {
                    ReplaceFragment.replaceFragment(fragmentManager, new BookingHistoryFragmentStaff(), R.id.staffMainFrame);
                    return true;
                } else if (itemId == R.id.navigation_profile) {
                    ReplaceFragment.replaceFragment(fragmentManager, new ProfileFragmentStaff(), R.id.staffMainFrame);
                    return true;
                }

                return false;
            }
        });
    }
}