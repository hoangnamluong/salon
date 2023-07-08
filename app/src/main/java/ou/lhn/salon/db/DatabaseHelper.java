package ou.lhn.salon.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import ou.lhn.salon.util.GetAppContext;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "salon.db";
    private static final int DB_VERSION = 1;

    private static DatabaseHelper INSTANCE;

    public static DatabaseHelper getInstance() {
        if(INSTANCE == null) {
            synchronized (DatabaseHelper.class) {
                if(INSTANCE == null) {
                    INSTANCE = new DatabaseHelper(GetAppContext.getContext());
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
        db.execSQL(DatabaseConstant.BRANCH_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabaseConstant.SALON_DROP);
        db.execSQL(DatabaseConstant.USER_DROP);
        db.execSQL(DatabaseConstant.BRANCH_DROP);
    }
}
