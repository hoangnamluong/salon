package ou.lhn.salon.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.Calendar;

import ou.lhn.salon.R;
import ou.lhn.salon.data.Constant;
import ou.lhn.salon.db.DatabaseHelper;
import ou.lhn.salon.db.model.Appointment;
import ou.lhn.salon.db.model.Salon;
import ou.lhn.salon.db.model.Service;
import ou.lhn.salon.db.model.Stylist;
import ou.lhn.salon.db.model.User;
import ou.lhn.salon.db.model.Voucher;
import ou.lhn.salon.db.service.Appointment_db.AppointmentService;
import ou.lhn.salon.db.service.Appointment_db.AppointmentServiceImpl;
import ou.lhn.salon.db.service.Service_db.ServiceServiceImpl;
import ou.lhn.salon.db.service.Stats.StatService;
import ou.lhn.salon.db.service.Stats.StatServiceImpl;
import ou.lhn.salon.db.service.User_db.UserService;
import ou.lhn.salon.db.service.User_db.UserServiceImpl;

public class AdminHomeActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton admBtnUserMgm, admBtnSalonMgm, admBtnVoucherMgm, admBtnStatRevenue;
    ImageView admImgAvatar;
    TextView admTxtFullName, admTxtTotalUser, admTxtTotalSalon, admTxtTotalOrder;
    UserService userService = UserServiceImpl.getInstance(this);
    StatService statService = StatServiceImpl.getInstance(this);
    int userId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        InitView();
        int id = getIntent().getIntExtra("userId", -1);
        if (id != -1) {
            userId = id;
            displayAdminInfo(userId);
        }
        displayHeaderInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayHeaderInfo();
    }

    private void displayAdminInfo(int userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            admTxtFullName.setText(user.getFullName());
            Bitmap bitmap = BitmapFactory.decodeByteArray(user.getAvatar(), 0, user.getAvatar().length);
            admImgAvatar.setImageBitmap(bitmap);
        }
    }

    private void displayHeaderInfo() {
        admTxtTotalUser.setText(statService.getAmountUser() + "");
        admTxtTotalSalon.setText(statService.getAmountSalon() + "");
        admTxtTotalOrder.setText(statService.getAmountOrder() + "");
    }

    private void InitView() {
        admBtnUserMgm = (ImageButton) findViewById(R.id.admBtnManageUser);
        admBtnSalonMgm = (ImageButton) findViewById(R.id.admBtnManageSalon);
        admBtnVoucherMgm = (ImageButton) findViewById(R.id.admBtnManageVoucher);
        admBtnStatRevenue = (ImageButton) findViewById(R.id.admBtnStatistic);
        admImgAvatar = (ImageView) findViewById(R.id.admImgAvatar);
        admTxtFullName = (TextView) findViewById(R.id.admTxtFullName);
        admTxtTotalUser = (TextView) findViewById(R.id.admTxtTotalUser);
        admTxtTotalSalon = (TextView) findViewById(R.id.admTxtTotalSalon);
        admTxtTotalOrder = (TextView) findViewById(R.id.admTxtTotalOrder);

        admBtnUserMgm.setOnClickListener(this);
        admBtnSalonMgm.setOnClickListener(this);
        admBtnVoucherMgm.setOnClickListener(this);
        admBtnStatRevenue.setOnClickListener(this);
        admImgAvatar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.admBtnManageUser) {
            GoToActivity(UserManagementActivity.class);
        } else if (id == R.id.admBtnManageSalon) {
            GoToActivity(SalonManagementActivity.class);
        } else if (id == R.id.admBtnManageVoucher) {
            GoToActivity(VoucherManagementActivity.class);
        } else if (id == R.id.admBtnStatistic) {
            GoToActivity(RevenueStatisticsActivity.class);
        } else if (id == R.id.admImgAvatar) {
            PopupMenu popupMenu = new PopupMenu(AdminHomeActivity.this, view);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    int id = menuItem.getItemId();
                    if (id == R.id.itemLogout) {
                        GoToActivity(LoginActivity.class);
                        return true;
                    }
                    return false;
                }
            });
            popupMenu.inflate(R.menu.popup_menu_click_avatar_admin);
            popupMenu.show();
        }
    }

    private void GoToActivity(Class<?> targetActivityClass) {
        Intent intent = new Intent(AdminHomeActivity.this, targetActivityClass);
        startActivity(intent);
    }
}