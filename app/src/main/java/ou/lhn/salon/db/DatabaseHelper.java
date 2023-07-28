package ou.lhn.salon.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "salon.db";
    private static final int DB_VERSION = 1;

    private static DatabaseHelper INSTANCE;

    public static DatabaseHelper getInstance(Context context) {
        if(INSTANCE == null) {
            synchronized (DatabaseHelper.class) {
                if(INSTANCE == null) {
                    INSTANCE = new DatabaseHelper(context);
                }
            }
        }

        return INSTANCE;
    }

    protected DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseConstant.SALON_QUERY);
        db.execSQL(DatabaseConstant.USER_QUERY);
        db.execSQL(DatabaseConstant.SCHEDULE_QUERY);
        db.execSQL(DatabaseConstant.SERVICE_QUERY);
        db.execSQL(DatabaseConstant.STYLIST_QUERY);
        db.execSQL(DatabaseConstant.VOUCHER_QUERY);
        db.execSQL(DatabaseConstant.RATING_QUERY);
        db.execSQL(DatabaseConstant.APPOINTMENT_QUERY);
        db.execSQL(DatabaseConstant.USER_VOUCHER_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
