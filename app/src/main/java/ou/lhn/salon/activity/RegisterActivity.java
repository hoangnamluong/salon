package ou.lhn.salon.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

import ou.lhn.salon.R;
import ou.lhn.salon.db.model.User;
import ou.lhn.salon.db.service.Auth_db.AuthService;
import ou.lhn.salon.db.service.Auth_db.AuthServiceImpl;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText registerEditTxtUsername, registerEditTxtEmail, registerEditTxtPassword, registerEditTxtConfirmPassword, registerEditTxtPhone, registerEditTxtFullName;
    private AppCompatButton registerBtnRegister;
    private TextView registerTxtLogin, registerTxtError;
    private ProgressBar registerLoadingBar;
    private AuthService authService = AuthServiceImpl.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        initListener();
    }

    private void initView() {
        registerEditTxtUsername = findViewById(R.id.registerEditTxtUsername);
        registerEditTxtEmail = findViewById(R.id.registerEditTxtEmail);
        registerEditTxtPhone = findViewById(R.id.registerEditTxtPhone);
        registerEditTxtFullName = findViewById(R.id.registerEditTxtFullname);
        registerEditTxtPassword = findViewById(R.id.registerEditTxtPassword);
        registerEditTxtConfirmPassword = findViewById(R.id.registerEditTxtConfirmPassword);
        registerBtnRegister = findViewById(R.id.registerBtnRegister);
        registerTxtLogin = findViewById(R.id.registerTxtLogin);
        registerTxtError = findViewById(R.id.registerTxtError);
        registerLoadingBar = findViewById(R.id.registerLoadingBar);
    }

    private void initListener() {
        registerBtnRegister.setOnClickListener(this);
        registerTxtLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.registerBtnRegister) {
             if(validateData())
                 registerFunc();
        } else if (id == R.id.registerTxtLogin) {
            loginSwitchActivity();
        }
    }

    private boolean validateData() {
        if(
            registerEditTxtUsername.getText().toString().equals("") ||
            registerEditTxtEmail.getText().toString().equals("") ||
            registerEditTxtFullName.getText().toString().equals("") ||
            registerEditTxtPhone.getText().toString().equals("") ||
            registerEditTxtPassword.getText().toString().equals("") ||
            registerEditTxtConfirmPassword.getText().toString().equals("")
        ) {
            registerTxtError.setText(R.string.please_fill_all_the_information);
            registerTxtError.setVisibility(View.VISIBLE);
            return false;
        }

        return true;
    }

    private void registerFunc() {
        String username = registerEditTxtUsername.getText().toString();
        String password = registerEditTxtPassword.getText().toString();

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(registerEditTxtEmail.getText().toString());
        user.setPhone(registerEditTxtPhone.getText().toString());
        user.setFullName(registerEditTxtFullName.getText().toString());

        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        registerLoadingBar.setVisibility(View.VISIBLE);
                        registerBtnRegister.setVisibility(View.GONE);
                    }
                });
                long success = authService.registerUser(user);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(success);
                        if(success == -2) {
                            Toast.makeText(RegisterActivity.this, "UserName already taken", Toast.LENGTH_LONG).show();
                        }else if (success != -1) {
                            loginSwitchActivity();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Register Failed!", Toast.LENGTH_SHORT).show();
                        }
                        registerLoadingBar.setVisibility(View.GONE);
                        registerBtnRegister.setVisibility(View.VISIBLE);
                    }
                });
            }
        }).start();
    }

    private void loginSwitchActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}