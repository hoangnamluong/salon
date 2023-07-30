package ou.lhn.salon.db.service.User_db;

import android.content.ContentValues;
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
        ArrayList<User> userList = new ArrayList<>();

        String query = "SELECT * FROM " + DatabaseConstant.TABLE_USER;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String fullName = cursor.getString(1);
                String username = cursor.getString(2);
                String password = cursor.getString(3);
                String email = cursor.getString(4);
                String phone = cursor.getString(5);
                boolean active = cursor.getInt(6) == 1;
                int role = cursor.getInt(7);
                byte[] avatar = cursor.getBlob(8);

                userList.add(new User(id, fullName, username, password, email, phone, active, role, avatar, null));
            } while (cursor.moveToNext());

            cursor.close();
        }

        return userList;
    }

    public ArrayList<User> getUserListByName(String name) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        ArrayList<User> userList = new ArrayList<>();

        String query = "SELECT * FROM " + DatabaseConstant.TABLE_USER +
                " WHERE " + DatabaseConstant.USER_FULL_NAME + " LIKE ?";
        String[] selectionArgs = new String[]{"%" + name + "%"};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String fullName = cursor.getString(1);
                String username = cursor.getString(2);
                String password = cursor.getString(3);
                String email = cursor.getString(4);
                String phone = cursor.getString(5);
                boolean active = cursor.getInt(6) == 1;
                int role = cursor.getInt(7);
                byte[] avatar = cursor.getBlob(8);

                userList.add(new User(id, fullName, username, password, email, phone, active, role, avatar, null));
            } while (cursor.moveToNext());

            cursor.close();
        }

        return userList;
    }

    public User getUserById(int userId) {
        SQLiteDatabase read = databaseHelper.getReadableDatabase();
        String query = "SELECT *" +
                " FROM " + DatabaseConstant.TABLE_USER +
                " WHERE " + DatabaseConstant.USER_ID + " = " + userId;

        Cursor cursor = read.rawQuery(query, null);

        if(cursor == null || cursor.getCount() == 0)
            return null;

        cursor.moveToFirst();

        int id = cursor.getInt(0);
        String fullName = cursor.getString(1);
        String username = cursor.getString(2);
        String email = cursor.getString(4);
        String phone = cursor.getString(5);
        boolean active = cursor.getInt(6) == 1;
        int role = cursor.getInt(7);
        byte[] avatar = cursor.getBlob(8);

        Salon salon = new Salon();
        salon.setId(cursor.getInt(9));

        return new User(id, fullName, username, "", email, phone, active, role, avatar, salon);
    public User getUserById(int id) {
        SQLiteDatabase read = databaseHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseConstant.TABLE_USER + " WHERE " + DatabaseConstant.USER_ID + " = " + id;

        Cursor cursor = read.rawQuery(query, null);

        if (cursor == null || cursor.getCount() == 0)
            return null;

        cursor.moveToFirst();

        String fullName = cursor.getString(1);
        String username = cursor.getString(2);
        String password = cursor.getString(3);
        String email = cursor.getString(4);
        String phone = cursor.getString(5);
        boolean active = cursor.getInt(6) == 1;
        int role = cursor.getInt(7);
        byte[] avatar = cursor.getBlob(8);
        Salon salon = new Salon();
        salon.setId(cursor.getInt(9));

        return new User(id, fullName, username, password, email, phone, active, role, avatar, salon);
    }

    @Override
    public User getUserByUsername(String username) {
        SQLiteDatabase read = databaseHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseConstant.TABLE_USER +
                " WHERE " + DatabaseConstant.USER_USERNAME + " = ?";
        String[] selectionArgs = new String[]{username};

        Cursor cursor = read.rawQuery(query, selectionArgs);

        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }

        cursor.moveToFirst();

        int id = cursor.getInt(0);
        String fullName = cursor.getString(1);
        String username1 = cursor.getString(2);
        String password = cursor.getString(3);
        String email = cursor.getString(4);
        String phone = cursor.getString(5);
        boolean active = cursor.getInt(6) == 1;
        int role = cursor.getInt(7);
        byte[] avatar = cursor.getBlob(8);
        Salon salon = new Salon();
        salon.setId(cursor.getInt(9));

        return new User(id, fullName, username1, password, email, phone, active, role, avatar, salon);
    }

    public boolean addUser(User user) {
        SQLiteDatabase write = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseConstant.USER_FULL_NAME, user.getFullName());
        contentValues.put(DatabaseConstant.USER_USERNAME, user.getUsername());
        contentValues.put(DatabaseConstant.USER_PASSWORD, user.getPassword());
        contentValues.put(DatabaseConstant.USER_EMAIL, user.getEmail());
        contentValues.put(DatabaseConstant.USER_PHONE, user.getPhone());
        contentValues.put(DatabaseConstant.USER_ACTIVE, user.isActive());
        contentValues.put(DatabaseConstant.USER_ROLE, user.getRole());
        contentValues.put(DatabaseConstant.USER_AVATAR, user.getAvatar());
        if(user.getSalon() != null) {
            contentValues.put(DatabaseConstant.FK_USER_SALON, user.getSalon().getId());
        }

        long result = write.insert(DatabaseConstant.TABLE_USER, null, contentValues);

        return result != -1;
    }

    public boolean updateUser(User user) {
        SQLiteDatabase write = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseConstant.USER_FULL_NAME, user.getFullName());
        contentValues.put(DatabaseConstant.USER_USERNAME, user.getUsername());
        contentValues.put(DatabaseConstant.USER_EMAIL, user.getEmail());
        contentValues.put(DatabaseConstant.USER_PHONE, user.getPhone());
        contentValues.put(DatabaseConstant.USER_ACTIVE, user.isActive());
        contentValues.put(DatabaseConstant.USER_ROLE, user.getRole());
        contentValues.put(DatabaseConstant.USER_AVATAR, user.getAvatar());
        contentValues.put(DatabaseConstant.FK_USER_SALON, user.getSalon().getId());

        String whereClause = DatabaseConstant.USER_ID + " = ?";
        String[] whereArgs = {String.valueOf(user.getId())};

        int rowsAffected = write.update(DatabaseConstant.TABLE_USER, contentValues, whereClause, whereArgs);

        return rowsAffected > 0;
    }

    public boolean deleteUser(int id) {
        SQLiteDatabase write = databaseHelper.getWritableDatabase();

        String whereClause = DatabaseConstant.USER_ID + " = ?";
        String[] whereArgs = {String.valueOf(id)};

        int rowsAffected = write.delete(DatabaseConstant.TABLE_USER, whereClause, whereArgs);

        return rowsAffected > 0;
    }

    @Override
    public boolean softDeleteUser(int id) {
        SQLiteDatabase write = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseConstant.USER_ACTIVE, false);

        String whereClause = DatabaseConstant.USER_ID + " = ?";
        String[] whereArgs = {String.valueOf(id)};

        int rowsAffected = write.update(DatabaseConstant.TABLE_USER, contentValues, whereClause, whereArgs);

        return rowsAffected > 0;
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
