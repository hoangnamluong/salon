package ou.lhn.salon.db.repository.User;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ou.lhn.salon.db.DatabaseHelper;
import ou.lhn.salon.db.model.User;

public class UserRepositoryImpl implements UserRepository {
    private static UserRepositoryImpl INSTANCE;
    private final DatabaseHelper databaseHelper = DatabaseHelper.getInstance();

    private UserRepositoryImpl() {
    }

    public static UserRepositoryImpl getInstance() {
        if(INSTANCE == null) {
            synchronized (UserRepositoryImpl.class) {
                if(INSTANCE == null)
                    INSTANCE = new UserRepositoryImpl();
            }
        }

        return INSTANCE;
    }

    public ArrayList<User> getAllUsers() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        return new ArrayList<>();
    }

    public User getUserById(int id) {
        return null;
    }

    public boolean addUser(User user) {
        return false;
    }

    public boolean updateUser(User user) {
        return false;
    }

    public boolean deleteUser(int id) {
        return false;
    }

    @Override
    public boolean softDeleteUser(int id) {
        return false;
    }
}
