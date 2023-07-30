package ou.lhn.salon.db.service.Salon_db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ou.lhn.salon.db.DatabaseConstant;
import ou.lhn.salon.db.DatabaseHelper;
import ou.lhn.salon.db.model.Salon;
import ou.lhn.salon.util.DateTimeFormat;

public class SalonServiceImpl implements SalonSerivce {
    private static SalonServiceImpl INSTANCE;
    private final DatabaseHelper databaseHelper;

    private SalonServiceImpl(Context context) {
        databaseHelper = DatabaseHelper.getInstance(context);
    }

    public static SalonServiceImpl getInstance(Context context) {
        if(INSTANCE == null) {
            synchronized (SalonServiceImpl.class) {
                if(INSTANCE == null)
                    INSTANCE = new SalonServiceImpl(context);
            }
        }

        return INSTANCE;
    }

    @Override
    public ArrayList<Salon> getAllSalons() {
        return null;
    }

    @Override
    public Salon getSalonById(int salonId) {
        SQLiteDatabase read = databaseHelper.getReadableDatabase();
        String query = "SELECT * " +
                "FROM " + DatabaseConstant.TABLE_SALON +
                " WHERE " + DatabaseConstant.SALON_ID + " = " + salonId;

        Cursor cursor = read.rawQuery(query, null);

        if (cursor == null || cursor.getCount() == 0)
            return null;

        cursor.moveToFirst();

        int id = cursor.getInt(0);
        String name = cursor.getString(1);
        String address = cursor.getString(2);
        String description = cursor.getString(3);
        boolean active = cursor.getInt(4) == 1;
        Date createdAt;
        Date updatedAt;
        try {
            createdAt = DateTimeFormat.convertSqliteDateToDate(cursor.getString(5));
            updatedAt = DateTimeFormat.convertSqliteDateToDate(cursor.getString(6));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        byte[] avatar = cursor.getBlob(7);

        return new Salon(id, name, address, description, active, createdAt, updatedAt, avatar);
    }

    @Override
    public Salon getSalonByStaffId(int staffId) {
        SQLiteDatabase read = databaseHelper.getReadableDatabase();
        String query = "SELECT s.*" +
                " FROM " + DatabaseConstant.TABLE_SALON + " s, " + DatabaseConstant.TABLE_USER + " st" +
                " WHERE s." + DatabaseConstant.SALON_ID + " = st." + DatabaseConstant.FK_USER_SALON +
                " AND st." + DatabaseConstant.USER_ID + " = " + staffId;

        Cursor cursor = read.rawQuery(query, null);

        if(cursor == null || cursor.getCount() == 0)
            return null;

        return null;
    }

    @Override
    public boolean addSalon(Salon salon) {
        SQLiteDatabase write = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseConstant.SALON_NAME, salon.getName());
        contentValues.put(DatabaseConstant.SALON_ADDRESS, salon.getAddress());
        contentValues.put(DatabaseConstant.SALON_DESCRIPTION, salon.getDescription());
        contentValues.put(DatabaseConstant.SALON_ACTIVE, salon.isActive());
        contentValues.put(DatabaseConstant.SALON_IMAGE, salon.getImage());

        long result = write.insert(DatabaseConstant.TABLE_SALON, null, contentValues);

        if(result == -1)
            return false;
        else
            return true;
    }

    @Override
    public boolean updateSalon(Salon salon) {
        return false;
    }

    @Override
    public boolean deleteSalon(int id) {
        return false;
    }

    @Override
    public boolean softDeleteSalon(int id) {
        return false;
    }
}
