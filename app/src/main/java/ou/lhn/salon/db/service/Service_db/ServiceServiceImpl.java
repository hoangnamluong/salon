package ou.lhn.salon.db.service.Service_db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ou.lhn.salon.db.DatabaseConstant;
import ou.lhn.salon.db.DatabaseHelper;
import ou.lhn.salon.db.model.Service;

public class ServiceServiceImpl implements ServiceService{
    private static ServiceServiceImpl INSTANCE;
    private final DatabaseHelper databaseHelper;

    private ServiceServiceImpl(Context context) {
        databaseHelper = DatabaseHelper.getInstance(context);
    }

    public static ServiceServiceImpl getInstance(Context context) {
        if(INSTANCE == null) {
            synchronized (ServiceServiceImpl.class) {
                if(INSTANCE == null)
                    INSTANCE = new ServiceServiceImpl(context);
            }
        }

        return INSTANCE;
    }

    @Override
    public ArrayList<Service> getAllServices() {
        SQLiteDatabase read = databaseHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseConstant.TABLE_SERVICE;

        Cursor cursor = read.rawQuery(query, null);
        ArrayList<Service> returnList = new ArrayList<>();

        if(cursor == null || cursor.getCount() == 0) {
            return null;
        }

        cursor.moveToFirst();

        do {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String description = cursor.getString(2);
            int price = cursor.getInt(3);
            int salon_id = cursor.getInt(4);

            returnList.add(new Service(id, name, description, price, null));
        } while(cursor.moveToNext());

        return returnList;
    }

    @Override
    public boolean addService(Service service) {
        SQLiteDatabase write = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseConstant.SERVICE_NAME, service.getName());
        contentValues.put(DatabaseConstant.SERVICE_DESCRIPTION, service.getDescription());
        contentValues.put(DatabaseConstant.SERVICE_PRICE, service.getPrice());
        contentValues.put(DatabaseConstant.FK_SERVICE_SALON, service.getSalon().getId());

        long result = write.insert(DatabaseConstant.TABLE_SERVICE, null, contentValues);

        if(result == -1)
            return false;
        else
            return true;
    }

    @Override
    public boolean updateService(Service service) {
        return false;
    }

    @Override
    public boolean deleteService(int serviceId) {
        return false;
    }
}
