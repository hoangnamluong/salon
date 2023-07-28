package ou.lhn.salon.db.service.Stylist_db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;

import ou.lhn.salon.db.DatabaseConstant;
import ou.lhn.salon.db.DatabaseHelper;
import ou.lhn.salon.db.model.Stylist;

public class StylistServiceImpl implements StylistService{
    private static StylistServiceImpl INSTANCE;
    private final DatabaseHelper databaseHelper;

    private StylistServiceImpl(Context context) {
        databaseHelper = DatabaseHelper.getInstance(context);
    }

    public static StylistServiceImpl getInstance(Context context) {
        if(INSTANCE == null) {
            synchronized (StylistServiceImpl.class) {
                if(INSTANCE == null)
                    INSTANCE = new StylistServiceImpl(context);
            }
        }

        return INSTANCE;
    }

    @Override
    public ArrayList<Stylist> getAllStylist() {
        SQLiteDatabase read = databaseHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseConstant.TABLE_STYLIST;

        ArrayList<Stylist> returnList = new ArrayList<>();
        Cursor cursor = read.rawQuery(query, null);

        if(cursor == null || cursor.getCount() == 0) {
            return null;
        }

        cursor.moveToFirst();

        do {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int customerPerDay = cursor.getInt(2);
            boolean active = cursor.getInt(3) == 1;
            byte[] avatar = cursor.getBlob(4);

            returnList.add(new Stylist(id, name, customerPerDay, active, null, avatar));
        } while(cursor.moveToNext());

        return returnList;
    }

    @Override
    public boolean addStylist(Stylist stylist) {
        SQLiteDatabase write = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseConstant.STYLIST_NAME, stylist.getName());
        contentValues.put(DatabaseConstant.STYLIST_CUSTOMER_PER_DAY, stylist.getCustomerPerDay());
        contentValues.put(DatabaseConstant.STYLIST_ACTIVE, stylist.isActive());
        contentValues.put(DatabaseConstant.STYLIST_AVATAR, stylist.getImage());
        contentValues.put(DatabaseConstant.FK_STYLIST_SALON, stylist.getSalon().getId());

        long result = write.insert(DatabaseConstant.TABLE_STYLIST, null, contentValues);

        return result != -1;
    }

    @Override
    public boolean updateStylist(Stylist stylist) {
        return false;
    }

    @Override
    public boolean deleteStylist(int stylistId) {
        return false;
    }

    @Override
    public int countCustomerToday(int stylistId) {
        SQLiteDatabase read = databaseHelper.getReadableDatabase();
        String query = "SELECT COUNT(*) " +
                "FROM " + DatabaseConstant.TABLE_APPOINTMENT + " a," +
                DatabaseConstant.TABLE_STYLIST + " s " +
                "WHERE a." + DatabaseConstant.FK_APPOINTMENT_STYLIST + " = s." + DatabaseConstant.STYLIST_ID  +
                " AND s." + DatabaseConstant.STYLIST_ID + " = " + stylistId +
                " AND strftime('%Y%m%d', 'now') - strftime('%Y%m%d', " + DatabaseConstant.APPOINTMENT_DATE + ") = 0";

        Cursor cursor = read.rawQuery(query, null);

        if(cursor == null || cursor.getCount() == 0) {
            return -1;
        }

        cursor.moveToFirst();

        int result = cursor.getInt(0);

        return result;
    }
}
