package ou.lhn.salon.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import ou.lhn.salon.R;
import ou.lhn.salon.data.Constant;
import ou.lhn.salon.data.GlobalState;
import ou.lhn.salon.db.model.Appointment;
import ou.lhn.salon.db.model.Salon;
import ou.lhn.salon.db.model.Service;
import ou.lhn.salon.db.model.Stylist;
import ou.lhn.salon.db.model.User;
import ou.lhn.salon.db.service.Appointment_db.AppointmentService;
import ou.lhn.salon.db.service.Appointment_db.AppointmentServiceImpl;
import ou.lhn.salon.db.service.Auth_db.AuthConstant;
import ou.lhn.salon.db.service.User_db.UserService;
import ou.lhn.salon.db.service.User_db.UserServiceImpl;
import ou.lhn.salon.util.InitData;
import ou.lhn.salon.util.ReplaceFragment;

public class StaffMainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager = getSupportFragmentManager();

    private UserService userService = UserServiceImpl.getInstance(this);

    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton mStaffFloatingBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_main);

        initView();
        ReplaceFragment.replaceFragment(fragmentManager, new HomeFragmentStaff(), R.id.staffMainFrame);
        initListener();

        GlobalState.setLoggedIn(userService.getUserById(2));
    }

    private void initView() {
        bottomNavigationView = findViewById(R.id.bottomAppMenu);
        mStaffFloatingBtn = findViewById(R.id.mStaffFloatingBtn);
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

        mStaffFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffMainActivity.this, BookingActivityStaff.class);
                startActivity(intent);
            }
        });
    }

    private String encrypt(String value) {
        try{
            SecretKeySpec secretKeySpec = new SecretKeySpec(AuthConstant.getSecretKey().getBytes(), AuthConstant.getALGORITHM());

            Cipher cipher = Cipher.getInstance(AuthConstant.getMODE());

            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(AuthConstant.getIV().getBytes()));

            byte[] encryptedData = cipher.doFinal(value.getBytes());

            return Base64.encodeToString(encryptedData, Base64.DEFAULT);
        } catch(Exception ex) {
            ex.printStackTrace();
            return "Encrypt Error!";
        }
    }
}