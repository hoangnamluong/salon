package ou.lhn.salon.db.service.Auth;

import android.util.Base64;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import ou.lhn.salon.db.model.User;
import ou.lhn.salon.db.repository.Auth.AuthRepository;
import ou.lhn.salon.db.repository.Auth.AuthRepositoryImpl;

public class AuthServiceImpl implements AuthService {
    private static AuthServiceImpl INSTANCE;
    private final AuthRepository authRepository = AuthRepositoryImpl.getInstance();

    private AuthServiceImpl() {
    }

    public static AuthServiceImpl getInstance() {
        if(INSTANCE == null) {
            synchronized (AuthServiceImpl.class) {
                if(INSTANCE == null)
                    INSTANCE = new AuthServiceImpl();
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
        return false;
    }

    public String encrypt(String value) {
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

    public String decrypt(String value) {
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
