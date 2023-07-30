package ou.lhn.salon.db.service.Auth_db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import ou.lhn.salon.R;
import ou.lhn.salon.activity.UserMainActivity;
import ou.lhn.salon.data.Constant;
import ou.lhn.salon.data.GlobalState;
import ou.lhn.salon.db.DatabaseConstant;
import ou.lhn.salon.db.DatabaseHelper;
import ou.lhn.salon.db.model.User;

public class AuthServiceImpl implements AuthService {
    private static AuthServiceImpl INSTANCE;
    private final DatabaseHelper databaseHelper;

    private AuthServiceImpl(Context context) {
        databaseHelper = DatabaseHelper.getInstance(context);
    }

    public static AuthServiceImpl getInstance(Context context) {
        if(INSTANCE == null) {
            synchronized (AuthServiceImpl.class) {
                if(INSTANCE == null)
                    INSTANCE = new AuthServiceImpl(context);
            }
        }

        return INSTANCE;
    }

    @Override
    public long registerUser(User user) {
        SQLiteDatabase write = databaseHelper.getWritableDatabase();
        String query = "Select * " +
                "from "+ DatabaseConstant.TABLE_USER+" " +
                "where "+DatabaseConstant.USER_USERNAME+" = '"+user.getUsername()+"';";
        Cursor cursor = write.rawQuery(query, null);
        if (cursor.getCount()>0){
            return -2;
        }
        ContentValues values = new ContentValues();
        values.put(DatabaseConstant.USER_USERNAME, user.getUsername());
        values.put(DatabaseConstant.USER_PHONE, user.getPhone());
        values.put(DatabaseConstant.USER_FULL_NAME, user.getFullName());
        values.put(DatabaseConstant.USER_PASSWORD, encrypt(user.getUsername()));
        values.put(DatabaseConstant.USER_EMAIL, user.getEmail());
        values.put(DatabaseConstant.USER_ROLE, Constant.ROLE.USER.value);
        values.put(DatabaseConstant.USER_ACTIVE, Boolean.TRUE);
//        Bitmap bitmap = BitmapFactory.decodeResource(Resources.getSystem() ,R.drawable.baseline_person_24_white);
//        System.out.println(bitmap);
//        ByteArrayOutputStream byteArr = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArr);
//        byte[] avatar = byteArr.toByteArray();
//        System.out.println(avatar);
//        values.put(DatabaseConstant.USER_AVATAR, avatar);
        return write.insert(DatabaseConstant.TABLE_USER, null, values);
    }

    @SuppressLint("Range")
    @Override
    public boolean loginUser(String username, String password) {
        if(password.toLowerCase().trim().equals("1 OR 1".toLowerCase().trim()) || username.toLowerCase().trim().equals("1 OR 1".toLowerCase().trim())) {
            return false;
        }

        SQLiteDatabase read = databaseHelper.getReadableDatabase();
        String query = "SELECT * FROM user WHERE username = '" + username + "';";
        String usernameFromDb, passwordFromDb;
        Cursor cursor = read.rawQuery(query, null);

        if(cursor == null || cursor.getCount() == 0) {
            return false;
        }

        cursor.moveToFirst();

        do {
            usernameFromDb = cursor.getString(2);
            passwordFromDb = cursor.getString(3);
        } while(cursor.moveToNext());


        if(password.equals(decrypt(passwordFromDb))){
            cursor.moveToPrevious();
            User user = new User();
            user.setId(cursor.getInt(0));
            user.setFullName(cursor.getString(1));
            user.setUsername(cursor.getString(2));
            user.setEmail(cursor.getString(4));
            user.setPhone(cursor.getString(5));
            user.setActive(cursor.getInt(6)==1);
            user.setRole(cursor.getInt(7));
            GlobalState.setLoggedIn(user);
            return true;
        }

        else
            return false;
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

    private String decrypt(String value) {
        try{
            byte[] decodedData = Base64.decode(value, Base64.DEFAULT);

            SecretKeySpec secretKeySpec = new SecretKeySpec(AuthConstant.getSecretKey().getBytes(), AuthConstant.getALGORITHM());

            Cipher cipher = Cipher.getInstance(AuthConstant.getMODE());

            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(AuthConstant.getIV().getBytes()));

            byte[] decryptedData = cipher.doFinal(decodedData);

            return new String(decryptedData, StandardCharsets.UTF_8);
        } catch(Exception ex) {
            ex.printStackTrace();
            return "Decrypt Error!";
        }
    }
}
