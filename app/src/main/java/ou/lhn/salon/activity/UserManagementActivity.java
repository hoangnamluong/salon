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
import ou.lhn.salon.db.model.Salon;
import ou.lhn.salon.db.model.User;
import ou.lhn.salon.db.service.Service_db.ServiceServiceImpl;
import ou.lhn.salon.db.service.User_db.UserService;
import ou.lhn.salon.db.service.User_db.UserServiceImpl;
import ou.lhn.salon.view.adapter.AdminUserAdapter;

public class UserManagementActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnBack, btnAdd;
    Button btnSearch;
    EditText edtName;
    ListView lvUser;
    private ArrayList<User> userArrayList;
    private UserService userService = UserServiceImpl.getInstance(this);
    private AdminUserAdapter adminUserAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);
        InitView();
        userArrayList = new ArrayList<>();
        adminUserAdapter = new AdminUserAdapter(UserManagementActivity.this, R.layout.user_list_layout, userArrayList);
        lvUser.setAdapter(adminUserAdapter);
        getUserList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserList();
    }

    private void InitView() {
        btnBack = (ImageButton) findViewById(R.id.admUserMnBtnBack);
        btnAdd = (ImageButton) findViewById(R.id.admImgBtnAdd);
        lvUser = (ListView) findViewById(R.id.admUserMnLvUser);
        edtName = (EditText) findViewById(R.id.edtCondition);
        btnSearch = (Button) findViewById(R.id.btnSearch);

        btnAdd.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnSearch.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.admUserMnBtnBack){
            finish();
        }
        else if (id == R.id.admImgBtnAdd){
            GoToActivity(AddUserActivity.class);
        } else if (id == R.id.btnSearch) {
            String userFullName = edtName.getText().toString().trim();
            getUserListByName(userFullName);
        }
    }

    private void getUserListByName(String userFullName) {
        ArrayList<User> allUsers = userService.getUserListByName(userFullName);

        if (allUsers != null && !allUsers.isEmpty()) {
            userArrayList.clear();
            userArrayList.addAll(allUsers);
            adminUserAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "No stylists found.", Toast.LENGTH_SHORT).show();
        }
    }

    private void getUserList(){
        ArrayList<User> allUsers = userService.getAllUsers();

        if (allUsers != null && !allUsers.isEmpty()) {
            userArrayList.clear();
            userArrayList.addAll(allUsers);
            adminUserAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "No stylists found.", Toast.LENGTH_SHORT).show();
        }
    }
    private void GoToActivity(Class<?> targetActivityClass) {
        Intent intent = new Intent(UserManagementActivity.this, targetActivityClass);
        startActivity(intent);
    }
}