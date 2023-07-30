package ou.lhn.salon.db.service.Voucher_db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import ou.lhn.salon.db.DatabaseConstant;
import ou.lhn.salon.db.DatabaseHelper;
import ou.lhn.salon.db.model.Voucher;

public class VoucherServiceImpl implements VoucherService{
    private static VoucherServiceImpl INSTANCE;
    private final DatabaseHelper databaseHelper;

    private VoucherServiceImpl(Context context) {
        databaseHelper = DatabaseHelper.getInstance(context);
    }

    public static VoucherServiceImpl getInstance(Context context) {
        if(INSTANCE == null) {
            synchronized (VoucherServiceImpl.class) {
                if(INSTANCE == null)
                    INSTANCE = new VoucherServiceImpl(context);
            }
        }

        return INSTANCE;
    }

    @Override
    public ArrayList<Voucher> getAllVoucher() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cs = db.rawQuery("select * from "+DatabaseConstant.TABLE_VOUCHER, null);
        cs.moveToFirst();
        ArrayList<Voucher> arr = new ArrayList<>();
        if (cs == null || cs.getCount()<=0){
            return null;
        }
        do {
            arr.add(new Voucher(cs.getInt(0), cs.getString(1), cs.getInt(2), new Date(cs.getLong(3)), cs.getInt(4)==1));
        } while (cs.moveToNext());
        return arr;

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

        int id = cursor.getInt(0);
        String code1 = cursor.getString(1);
        int percentage = cursor.getInt(2);
        boolean active = cursor.getInt(4) == 1;

        Date expiredDate;
        try {
            expiredDate = SimpleDateFormat.getDateTimeInstance().parse(cursor.getString(3));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return new Voucher(id, code1, percentage, expiredDate, active);
    }

    @Override
    public boolean addVoucher(Voucher voucher) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseConstant.VOUCHER_CODE, voucher.getCode());
        values.put(DatabaseConstant.VOUCHER_PERCENTAGE, voucher.getPercentage());
        values.put(DatabaseConstant.VOUCHER_ACTIVE, voucher.isActive());
        values.put(DatabaseConstant.VOUCHER_EXPIRED_DATE, voucher.getExpiredDate().toString());
        return db.insert(DatabaseConstant.TABLE_VOUCHER, null, values) != -1;
    }

    @Override
    public boolean updateVoucher(Voucher voucher) {
        return false;
    }

    @Override
    public boolean deleteVoucher(int voucherId) {
        return false;
    }
}
