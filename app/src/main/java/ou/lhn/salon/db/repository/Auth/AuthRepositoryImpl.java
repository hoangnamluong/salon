package ou.lhn.salon.db.repository.Auth;

import ou.lhn.salon.db.DatabaseHelper;
import ou.lhn.salon.db.model.User;

public class AuthRepositoryImpl implements AuthRepository {
    private static AuthRepositoryImpl INSTANCE;
    private final DatabaseHelper databaseHelper = DatabaseHelper.getInstance();

    private AuthRepositoryImpl() {
    }

    public static AuthRepositoryImpl getInstance() {
        if(INSTANCE == null) {
            synchronized (AuthRepositoryImpl.class) {
                if(INSTANCE == null)
                    INSTANCE = new AuthRepositoryImpl();
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
}
