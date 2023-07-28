package ou.lhn.salon.db.service.Auth_db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Base64;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

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
    public boolean registerUser(User user) {
        return false;
    }

    @Override
    public boolean loginUser(String username, String password) {
        if(password.toLowerCase().trim().equals("1 OR 1".toLowerCase().trim()) || username.toLowerCase().trim().equals("1 OR 1".toLowerCase().trim())) {
            return false;
        }

        SQLiteDatabase read = databaseHelper.getReadableDatabase();
        String query = "SELECT username, password FROM user WHERE username = '" + username + "';";
        String usernameFromDb, passwordFromDb;
        Cursor cursor = read.rawQuery(query, null);

        if(cursor == null || cursor.getCount() == 0) {
            return false;
        }

        cursor.moveToFirst();

        do {
            usernameFromDb = cursor.getString(0);
            passwordFromDb = cursor.getString(1);
        } while(cursor.moveToNext());

        if(password.equals(decrypt(passwordFromDb)))
            return true;
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
