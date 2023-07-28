package ou.lhn.salon.db.service.Appointment_db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ou.lhn.salon.db.DatabaseConstant;
import ou.lhn.salon.db.DatabaseHelper;
import ou.lhn.salon.db.model.Appointment;

public class AppointmentServiceImpl implements AppointmentService{
    private static AppointmentServiceImpl INSTANCE;
    private DatabaseHelper databaseHelper;

    private AppointmentServiceImpl(Context context) {
        databaseHelper = DatabaseHelper.getInstance(context);
    }

    public static AppointmentServiceImpl getInstance(Context context) {
        if(INSTANCE == null) {
            synchronized (AppointmentServiceImpl.class) {
                if(INSTANCE == null)
                    INSTANCE = new AppointmentServiceImpl(context);
            }
        }

        return INSTANCE;
    }

    @Override
    public ArrayList<Appointment> getAppointmentsByStaff(int staffId) {
        SQLiteDatabase read = databaseHelper.getReadableDatabase();
        String query = "SELECT * " +
                "FROM " + DatabaseConstant.TABLE_APPOINTMENT + " a," + DatabaseConstant.TABLE_SALON + " s," + DatabaseConstant.TABLE_USER + " u" +
                "WHERE u." + DatabaseConstant.FK_USER_SALON + " = s." + DatabaseConstant.SALON_ID +
                "AND a." + DatabaseConstant.FK_APPOINTMENT_SALON + " = s." + DatabaseConstant.SALON_ID +
                "AND u." + DatabaseConstant.USER_ID + " = " + staffId;

        ArrayList<Appointment> returnList = new ArrayList<>();

        Cursor cursor = read.rawQuery(query, null);

        if(cursor == null || cursor.getCount() == 0)
            return null;

        cursor.moveToFirst();

        do{

        } while(cursor.moveToNext());

        return returnList;
    }

    @Override
    public boolean addAppointment(Appointment appointment) {
        SQLiteDatabase write = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseConstant.APPOINTMENT_DATE, appointment.getAppointmentDate().getTime());
        contentValues.put(DatabaseConstant.APPOINTMENT_STATUS, appointment.getStatus());
        contentValues.put(DatabaseConstant.APPOINTMENT_ACTIVE, appointment.isActive());
        contentValues.put(DatabaseConstant.FK_APPOINTMENT_SERVICE, appointment.getService().getId());
        contentValues.put(DatabaseConstant.FK_APPOINTMENT_SALON, appointment.getSalon().getId());
        contentValues.put(DatabaseConstant.FK_APPOINTMENT_CUSTOMER, appointment.getCustomer().getId());
        contentValues.put(DatabaseConstant.FK_APPOINTMENT_STYLIST, appointment.getStylist().getId());

        if(appointment.getVoucher() != null) {
            contentValues.put(DatabaseConstant.FK_APPOINTMENT_VOUCHER, appointment.getVoucher().getId());
        }

        long result = write.insert(DatabaseConstant.TABLE_APPOINTMENT, null, contentValues);

        if(result == -1)
            return false;
        else
            return true;
    }

    @Override
    public boolean updateAppointment(Appointment appointment) {
        SQLiteDatabase write = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String query = DatabaseConstant.APPOINTMENT_ID + " = " + appointment.getId();

        contentValues.put(DatabaseConstant.APPOINTMENT_DATE, appointment.getAppointmentDate().toString());
        contentValues.put(DatabaseConstant.APPOINTMENT_STATUS, appointment.getStatus());
        contentValues.put(DatabaseConstant.FK_APPOINTMENT_SERVICE, appointment.getService().getId());
        contentValues.put(DatabaseConstant.FK_APPOINTMENT_SALON, appointment.getSalon().getId());
        contentValues.put(DatabaseConstant.FK_APPOINTMENT_CUSTOMER, appointment.getCustomer().getId());
        contentValues.put(DatabaseConstant.FK_APPOINTMENT_STYLIST, appointment.getStylist().getId());

        if(appointment.getVoucher() != null) {
            contentValues.put(DatabaseConstant.FK_APPOINTMENT_VOUCHER, appointment.getVoucher().getId());
        }

        int result = write.update(DatabaseConstant.TABLE_APPOINTMENT, contentValues, query, null);

        if(result == 0)
            return false;
        else
            return true;
    }

    @Override
    public boolean deleteAppointment(int appointmentId) {
        SQLiteDatabase write = databaseHelper.getWritableDatabase();
        String query = DatabaseConstant.APPOINTMENT_ID + " = " + appointmentId;

        int result = write.delete(DatabaseConstant.TABLE_APPOINTMENT, query, null);

        if(result == 0)
            return false;
        else
            return true;
    }
}
