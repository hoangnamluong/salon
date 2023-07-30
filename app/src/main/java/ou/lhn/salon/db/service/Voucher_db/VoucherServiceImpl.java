package ou.lhn.salon.db.service.Voucher_db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.time.LocalDate;
import java.util.Date;

import ou.lhn.salon.db.DatabaseConstant;
import ou.lhn.salon.db.DatabaseHelper;
import ou.lhn.salon.db.model.Voucher;
import ou.lhn.salon.util.DateTimeFormat;

public class VoucherServiceImpl implements VoucherService{
    private static VoucherServiceImpl INSTANCE;
    private final DatabaseHelper databaseHelper;

    private VoucherServiceImpl(Context context) {
        databaseHelper = DatabaseHelper.getInstance(context);
    }

    public static VoucherServiceImpl getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (VoucherServiceImpl.class) {
                if (INSTANCE == null)
                    INSTANCE = new VoucherServiceImpl(context);
            }
        }

        return INSTANCE;
    }

    @Override
    public ArrayList<Voucher> getAllVoucher() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        ArrayList<Voucher> voucherList = new ArrayList<>();

        String query = "SELECT * FROM " + DatabaseConstant.TABLE_VOUCHER;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String code = cursor.getString(1);
                int percentage = cursor.getInt(2);
                boolean active = cursor.getInt(4) == 1;

                Date expiredDate;
                try {
                    expiredDate = SimpleDateFormat.getDateTimeInstance().parse(cursor.getString(3));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                voucherList.add(new Voucher(id, code, percentage, expiredDate, active));
            } while (cursor.moveToNext());

            cursor.close();
        }

        return voucherList;
    }

    @Override
    public ArrayList<Voucher> getVoucherListByExpireDate(String expireDateStr) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        ArrayList<Voucher> voucherList = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date expireDate;

        try {
            expireDate = dateFormat.parse(expireDateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        String query = "SELECT * FROM " + DatabaseConstant.TABLE_VOUCHER +
                " WHERE " + DatabaseConstant.VOUCHER_EXPIRED_DATE + " <= ?";
        String[] selectionArgs = new String[]{dateFormat.format(expireDate)};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String code = cursor.getString(1);
                int percentage = cursor.getInt(2);
                boolean active = cursor.getInt(4) == 1;

                Date expiredDate;
                try {
                    expiredDate = dateFormat.parse(cursor.getString(3));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                voucherList.add(new Voucher(id, code, percentage, expiredDate, active));
            } while (cursor.moveToNext());

            cursor.close();
        }

        return voucherList;
    }

    @Override
    public Voucher getVoucherByCode(String code) {
        SQLiteDatabase read = databaseHelper.getReadableDatabase();
        String query = "SELECT * " +
                "FROM " + DatabaseConstant.TABLE_VOUCHER +
                " WHERE " + DatabaseConstant.VOUCHER_CODE + " = '" + code + "' " +
                " AND " + DatabaseConstant.VOUCHER_ACTIVE + " = 1" +
                " AND strftime('%Y%m%d', 'now') - strftime('%Y%m%d', " + DatabaseConstant.VOUCHER_EXPIRED_DATE + ") = 0";

        Cursor cursor = read.rawQuery(query, null);

        if (cursor == null || cursor.getCount() == 0)
            return null;

        cursor.moveToFirst();
        int id = cursor.getInt(0);
        String code1 = cursor.getString(1);
        int percentage = cursor.getInt(2);
        boolean active = cursor.getInt(4) == 1;

        Date expiredDate;
        try {
            expiredDate = DateTimeFormat.convertSqliteDateToDate(cursor.getString(3));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        cursor.close();
      
        return new Voucher(id, code1, percentage, expiredDate, active);
    }

    @Override
    public Voucher getVoucherById(int voucherId) {
        SQLiteDatabase read = databaseHelper.getReadableDatabase();
        String query = "SELECT * " +
                "FROM " + DatabaseConstant.TABLE_VOUCHER +
                " WHERE " + DatabaseConstant.VOUCHER_ID + " = " + voucherId;

        Cursor cursor = read.rawQuery(query, null);

        if (cursor == null || cursor.getCount() == 0)
            return null;

        int id = cursor.getInt(0);
        String code = cursor.getString(1);
        int percentage = cursor.getInt(2);
        boolean active = cursor.getInt(4) == 1;
        Date expiredDate;
        try {
            expiredDate = DateTimeFormat.convertSqliteDateToDate(cursor.getString(3));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return new Voucher(id, code, percentage, expiredDate, active);
    }

    @Override
    public boolean addVoucher(Voucher voucher) {
        SQLiteDatabase write = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseConstant.VOUCHER_CODE, voucher.getCode());
        contentValues.put(DatabaseConstant.VOUCHER_PERCENTAGE, voucher.getPercentage());
        contentValues.put(DatabaseConstant.VOUCHER_EXPIRED_DATE, SimpleDateFormat.getDateTimeInstance().format(voucher.getExpiredDate()));
        contentValues.put(DatabaseConstant.VOUCHER_ACTIVE, voucher.isActive());

        long result = write.insert(DatabaseConstant.TABLE_VOUCHER, null, contentValues);

        return result != -1;
    }


    @Override
    public boolean updateVoucher(Voucher voucher) {
        SQLiteDatabase write = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseConstant.VOUCHER_CODE, voucher.getCode());
        contentValues.put(DatabaseConstant.VOUCHER_PERCENTAGE, voucher.getPercentage());
        contentValues.put(DatabaseConstant.VOUCHER_EXPIRED_DATE, SimpleDateFormat.getDateTimeInstance().format(voucher.getExpiredDate()));
        contentValues.put(DatabaseConstant.VOUCHER_ACTIVE, voucher.isActive());

        String whereClause = DatabaseConstant.VOUCHER_ID + " = ?";
        String[] whereArgs = {String.valueOf(voucher.getId())};

        int rowsAffected = write.update(DatabaseConstant.TABLE_VOUCHER, contentValues, whereClause, whereArgs);

        return rowsAffected > 0;
    }


    @Override
    public boolean deleteVoucher(int voucherId) {
        SQLiteDatabase write = databaseHelper.getWritableDatabase();

        try {
            // Thử xóa dòng dữ liệu
            String[] whereArgs = {String.valueOf(voucherId)};
            int rowsAffected = write.delete(DatabaseConstant.TABLE_VOUCHER, DatabaseConstant.VOUCHER_ID + " = ?", whereArgs);

            return rowsAffected > 0;
        } catch (Exception e) {
            // Nếu xảy ra lỗi, thực hiện cập nhật cột active thành false
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseConstant.VOUCHER_ACTIVE, false);

            String[] whereArgs = {String.valueOf(voucherId)};
            int rowsAffected = write.update(DatabaseConstant.TABLE_VOUCHER, contentValues, DatabaseConstant.VOUCHER_ID + " = ?", whereArgs);

            return rowsAffected > 0;
        }
    }
}
