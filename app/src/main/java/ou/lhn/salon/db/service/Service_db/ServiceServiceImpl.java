package ou.lhn.salon.db.service.Service_db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ou.lhn.salon.db.DatabaseConstant;
import ou.lhn.salon.db.DatabaseHelper;
import ou.lhn.salon.db.model.Salon;
import ou.lhn.salon.db.model.Service;
import ou.lhn.salon.db.model.Stylist;

public class ServiceServiceImpl implements ServiceService {
    private static ServiceServiceImpl INSTANCE;
    private final DatabaseHelper databaseHelper;

    private ServiceServiceImpl(Context context) {
        databaseHelper = DatabaseHelper.getInstance(context);
    }

    public static ServiceServiceImpl getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ServiceServiceImpl.class) {
                if (INSTANCE == null)
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

        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }

        cursor.moveToFirst();

        do {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String description = cursor.getString(2);
            long price = cursor.getLong(3);
            int salon_id = cursor.getInt(4);

            returnList.add(new Service(id, name, description, price, null));
        } while (cursor.moveToNext());

        return returnList;
    }

    @Override
    public ArrayList<Service> getAllServicesBySalonId(int salonId) {
        SQLiteDatabase read = databaseHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseConstant.TABLE_SERVICE + " WHERE " + DatabaseConstant.FK_SERVICE_SALON + " = " + salonId;

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
            long price = cursor.getLong(3);
            int salon_id = cursor.getInt(4);

            returnList.add(new Service(id, name, description, price, null));
        } while (cursor.moveToNext());

        // Đảm bảo giải phóng Cursor sau khi sử dụng.
        if (cursor != null) {
            cursor.close();
        }

        return returnList;
    }

    @Override
    public ArrayList<Service> getServiceListByName(String name){
        ArrayList<Service> serviceList = new ArrayList<>();
        SQLiteDatabase read = databaseHelper.getReadableDatabase();

        String query = "SELECT * FROM " + DatabaseConstant.TABLE_SERVICE +
                " WHERE " + DatabaseConstant.SERVICE_NAME + " LIKE ?";
        String[] selectionArgs = new String[]{"%" + name + "%"};

        Cursor cursor = read.rawQuery(query, selectionArgs);

        if (cursor == null || cursor.getCount() == 0) {
            return serviceList;
        }

        cursor.moveToFirst();

        do {
            int id = cursor.getInt(0);
            String name1 = cursor.getString(1);
            String description = cursor.getString(2);
            long price = cursor.getLong(3);
            int salon_id = cursor.getInt(4);

            Salon salon = new Salon();
            salon.setId(salon_id);



            serviceList.add(new Service(id, name1, description, price, salon));
        } while (cursor.moveToNext());

        // Đảm bảo giải phóng Cursor sau khi sử dụng.
        if (cursor != null) {
            cursor.close();
        }

        return serviceList;
    }


    @Override
    public ArrayList<Service> getServiceByAppointmentId(int appointmentId) {
        SQLiteDatabase read = databaseHelper.getReadableDatabase();
        String query = "SELECT s.*" +
                " FROM " + DatabaseConstant.TABLE_SERVICE + " s, " + DatabaseConstant.TABLE_APPOINTMENT + " a" +
                " WHERE " + DatabaseConstant.FK_APPOINTMENT_SERVICE + " = " + DatabaseConstant.SERVICE_ID +
                " AND " + DatabaseConstant.APPOINTMENT_ID + " = " + appointmentId;

        Cursor cursor = read.rawQuery(query, null);
        ArrayList<Service> returnList = new ArrayList<>();

        if(cursor == null || cursor.getCount() == 0) {
            return null;
        }

        cursor.moveToFirst();
        do {
            int id = cursor.getInt(0);
            String serviceName = cursor.getString(1);
            String description = cursor.getString(2);
            long price = cursor.getLong(3);
            int salonId = cursor.getInt(4);

            Salon salon = new Salon();
            salon.setId(salonId);

            Service service = new Service(id, serviceName, description, price, salon);
            returnList.add(service);
        } while (cursor.moveToNext());

        cursor.close();
        return returnList;
    }


    @Override
    public Service getServiceById(int serviceId) {
        SQLiteDatabase read = databaseHelper.getReadableDatabase();
        String query = "SELECT * " +
                "FROM " + DatabaseConstant.TABLE_SERVICE +
                " WHERE " + DatabaseConstant.SERVICE_ID + " = " + serviceId;

        Cursor cursor = read.rawQuery(query, null);

        if (cursor == null || cursor.getCount() == 0)
            return null;

        cursor.moveToFirst();

        int id = cursor.getInt(0);
        String name = cursor.getString(1);
        String description = cursor.getString(2);
        long price = cursor.getInt(3);
        int salon_id = cursor.getInt(4);
        Salon salon = new Salon();
        salon.setId(salon_id);
        return new Service(id, name, description, price, salon);
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
  
        if (result == -1)
            return false;
        else
            return true;
    }

    @Override
    public boolean updateService(Service service) {
        SQLiteDatabase write = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseConstant.SERVICE_NAME, service.getName());
        contentValues.put(DatabaseConstant.SERVICE_DESCRIPTION, service.getDescription());
        contentValues.put(DatabaseConstant.SERVICE_PRICE, service.getPrice());
        contentValues.put(DatabaseConstant.FK_SERVICE_SALON, service.getSalon().getId());

        int result = write.update(DatabaseConstant.TABLE_SERVICE, contentValues, DatabaseConstant.SERVICE_ID + " = " + service.getId(), null);


        return result > 0;
    }


    @Override
    public boolean deleteService(int serviceId) {
        SQLiteDatabase write = databaseHelper.getWritableDatabase();
        String[] whereArgs = {String.valueOf(serviceId)};
        int rowsAffected = write.delete(DatabaseConstant.TABLE_SERVICE, DatabaseConstant.SERVICE_ID + " = ?", whereArgs);
        return rowsAffected > 0;
    }
}
