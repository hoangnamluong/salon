package ou.lhn.salon.db.service.Stats;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ou.lhn.salon.db.DatabaseConstant;
import ou.lhn.salon.db.DatabaseHelper;
import ou.lhn.salon.db.model.RevenueStats;
import ou.lhn.salon.db.service.Service_db.ServiceServiceImpl;

public class StatServiceImpl implements StatService {
    private static StatServiceImpl INSTANCE;
    private final DatabaseHelper databaseHelper;

    private StatServiceImpl(Context context) {
        databaseHelper = DatabaseHelper.getInstance(context);
    }

    public static StatServiceImpl getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ServiceServiceImpl.class) {
                if (INSTANCE == null)
                    INSTANCE = new StatServiceImpl(context);
            }
        }

        return INSTANCE;
    }


    @Override
    public ArrayList<RevenueStats> getTop5SalonRevenue(int month, int year) {
        ArrayList<RevenueStats> revenueStatsList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        // Truy vấn cơ sở dữ liệu để tính toán doanh thu của các salon trong tháng và năm cụ thể
        String query = "SELECT " +
                "salon." + DatabaseConstant.SALON_NAME + ", " +
                "COUNT(appointment." + DatabaseConstant.APPOINTMENT_ID + ") AS " + DatabaseConstant.APPOINTMENT_COUNT + ", " +
                "SUM(service." + DatabaseConstant.SERVICE_PRICE + ") AS " + DatabaseConstant.TOTAL_REVENUE + " " +
                "FROM " + DatabaseConstant.TABLE_SALON + " AS salon " +
                "JOIN " + DatabaseConstant.TABLE_APPOINTMENT + " AS appointment ON salon." + DatabaseConstant.SALON_ID + " = appointment." + DatabaseConstant.FK_APPOINTMENT_SALON + " " +
                "JOIN " + DatabaseConstant.TABLE_SERVICE + " AS service ON appointment." + DatabaseConstant.FK_APPOINTMENT_SERVICE + " = service." + DatabaseConstant.SERVICE_ID + " " +
                "WHERE strftime('%m', appointment." + DatabaseConstant.APPOINTMENT_DATE + ") = ? " +
                "AND strftime('%Y', appointment." + DatabaseConstant.APPOINTMENT_DATE + ") = ? " +
                "GROUP BY salon." + DatabaseConstant.SALON_ID + " " +
                "ORDER BY " + DatabaseConstant.TOTAL_REVENUE + " DESC " +
                "LIMIT 5";

        Cursor cursor = db.rawQuery(query, new String[]{String.format("%02d", month), String.valueOf(year)});

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int nameColumnIndex = cursor.getColumnIndex(DatabaseConstant.SALON_NAME);
                int countColumnIndex = cursor.getColumnIndex(DatabaseConstant.APPOINTMENT_COUNT);
                int revenueColumnIndex = cursor.getColumnIndex(DatabaseConstant.TOTAL_REVENUE);

                if (nameColumnIndex != -1 && countColumnIndex != -1 && revenueColumnIndex != -1) {
                    String salonName = cursor.getString(nameColumnIndex);
                    int countAppointment = cursor.getInt(countColumnIndex);
                    long totalRevenue = cursor.getLong(revenueColumnIndex);

                    revenueStatsList.add(new RevenueStats(salonName, countAppointment, totalRevenue));
                }
            }

            cursor.close();
        }

        return revenueStatsList;
    }

    @Override
    public int getAmountUser() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String query = "SELECT COUNT(*) FROM " + DatabaseConstant.TABLE_USER;
        Cursor cursor = db.rawQuery(query, null);

        int userCount = 0;
        if (cursor != null) {
            cursor.moveToFirst();
            userCount = cursor.getInt(0);
            cursor.close();
        }

        return userCount;
    }

    @Override
    public int getAmountSalon() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String query = "SELECT COUNT(*) FROM " + DatabaseConstant.TABLE_SALON;
        Cursor cursor = db.rawQuery(query, null);

        int salonCount = 0;
        if (cursor != null) {
            cursor.moveToFirst();
            salonCount = cursor.getInt(0);
            cursor.close();
        }

        return salonCount;
    }

    @Override
    public int getAmountOrder() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String query = "SELECT COUNT(*) FROM " + DatabaseConstant.TABLE_APPOINTMENT;
        Cursor cursor = db.rawQuery(query, null);

        int orderCount = 0;
        if (cursor != null) {
            cursor.moveToFirst();
            orderCount = cursor.getInt(0);
            cursor.close();
        }

        return orderCount;
    }

}
