package ou.lhn.salon.db.service.Auth_db;

public class AuthConstant {
    private static final String ALGORITHM = "AES";
    private static final String MODE = "AES/GCM/NoPadding";
    private static final String IV = "PBb21Te4HkyYqYeK";
    private static final String SECRET_KEY = "EHTak9tRdod25qvSiL7m8y8SiMhsBfnT";

    public static String getALGORITHM() {
        return ALGORITHM;
    }

    public static String getMODE() {
        return MODE;
    }

    public static String getIV() {
        return IV;
    }

    public static String getSecretKey() {
        return SECRET_KEY;
    }
}
