package ou.lhn.salon.db.service.User_db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ou.lhn.salon.db.DatabaseConstant;
import ou.lhn.salon.db.DatabaseHelper;
import ou.lhn.salon.db.model.Salon;
import ou.lhn.salon.db.model.User;
import ou.lhn.salon.db.service.Salon_db.SalonSerivce;
import ou.lhn.salon.db.service.Salon_db.SalonServiceImpl;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl INSTANCE;
    private final DatabaseHelper databaseHelper;

    private UserServiceImpl(Context context) {
        databaseHelper = DatabaseHelper.getInstance(context);
    }

    public static UserServiceImpl getInstance(Context context) {
        if(INSTANCE == null) {
            synchronized (UserServiceImpl.class) {
                if(INSTANCE == null)
                    INSTANCE = new UserServiceImpl(context);
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

    @Override
    public User isUserAMember(String phone) {
        SQLiteDatabase read = databaseHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseConstant.TABLE_USER + " WHERE " + DatabaseConstant.USER_PHONE + " = '" + phone + "';";

        Cursor cursor = read.rawQuery(query, null);

        if(cursor == null || cursor.getCount() == 0) {
            return null;
        }

        cursor.moveToFirst();

        int id = cursor.getInt(0);
        String fullName = cursor.getString(1);
        String username1 = cursor.getString(2);
        String password = cursor.getString(3);
        String email = cursor.getString(4);
        String phone1 = cursor.getString(5);
        boolean active = cursor.getInt(6) == 1;
        int role = cursor.getInt(7);
        byte[] avatar = cursor.getBlob(8);

        return new User(id, fullName, username1, password, email, phone1, active, role, avatar, null);
    }
}
