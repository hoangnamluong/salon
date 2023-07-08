package ou.lhn.salon.db.service.User;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ou.lhn.salon.db.DatabaseHelper;
import ou.lhn.salon.db.model.User;
import ou.lhn.salon.db.repository.User.UserRepository;
import ou.lhn.salon.db.repository.User.UserRepositoryImpl;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl INSTANCE;
    private final UserRepository userRepository = UserRepositoryImpl.getInstance();

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        if(INSTANCE == null) {
            synchronized (UserServiceImpl.class) {
                if(INSTANCE == null)
                    INSTANCE = new UserServiceImpl();
            }
        }

        return INSTANCE;
    }

    public ArrayList<User> getAllUsers() { return null; }

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
