package ou.lhn.salon.db.service.Voucher_db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        return null;
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
        return false;
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
