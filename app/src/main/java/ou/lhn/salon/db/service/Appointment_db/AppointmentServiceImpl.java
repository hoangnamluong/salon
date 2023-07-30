package ou.lhn.salon.db.service.Appointment_db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ou.lhn.salon.db.DatabaseConstant;
import ou.lhn.salon.db.DatabaseHelper;
import ou.lhn.salon.db.model.Appointment;
import ou.lhn.salon.data.Constant;
import ou.lhn.salon.db.model.Salon;
import ou.lhn.salon.db.model.Service;
import ou.lhn.salon.db.model.Stylist;
import ou.lhn.salon.db.model.User;
import ou.lhn.salon.db.model.Voucher;
import ou.lhn.salon.db.service.User_db.UserService;
import ou.lhn.salon.db.service.User_db.UserServiceImpl;
import ou.lhn.salon.util.DateTimeFormat;

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
    public ArrayList<Appointment> getAppointmentsByDate(int year, int month, int date, int salonId, Context context) {
        UserService userService = UserServiceImpl.getInstance(context);

        SQLiteDatabase read = databaseHelper.getReadableDatabase();
        String query = "SELECT a.*" +
                " FROM " + DatabaseConstant.TABLE_SALON + " s, " +
                DatabaseConstant.TABLE_USER + " u, " +
                DatabaseConstant.TABLE_APPOINTMENT + " a" +
                " WHERE u." + DatabaseConstant.FK_USER_SALON + " = s." + DatabaseConstant.SALON_ID +
                " AND a." + DatabaseConstant.FK_APPOINTMENT_SALON + " = s." + DatabaseConstant.SALON_ID +
                " AND s." + DatabaseConstant.SALON_ID + " = " + salonId + " " ;

        if(year != -1) {
            query += "AND strftime('%Y', " + DatabaseConstant.APPOINTMENT_DATE + ")" + " = " + year + " ";
        }

        if(month != -1) {
            query += "AND strftime('%m', " + DatabaseConstant.APPOINTMENT_DATE + ")" + " = " + month + " ";
        }

        if (date != -1) {
            query += "AND strftime('%d', " + DatabaseConstant.APPOINTMENT_DATE + ")" + " = " + date + " ";
        }

        ArrayList<Appointment> returnList = new ArrayList<>();

        Cursor cursor = read.rawQuery(query, null);

        if(cursor == null || cursor.getCount() == 0)
            return null;

        cursor.moveToFirst();

        do{
            try {
                int id = cursor.getInt(0);
                Date date1 = DateTimeFormat.convertSqliteDateToDate(cursor.getString(1));
                boolean active = cursor.getInt(2) == 1;
                int cost = cursor.getInt(3);
                String status = cursor.getString(4);

                User user = userService.getUserById(cursor.getInt(5));

                returnList.add(new Appointment(id, date1, active, cost, status, user, null ,null ,null, null));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } while(cursor.moveToNext());

        return returnList;
    }

    @Override
    public ArrayList<Appointment> getPendingAppointments(int salonId) {
        SQLiteDatabase read = databaseHelper.getReadableDatabase();
        String query = "SELECT a.*, u.*" +
                " FROM " + DatabaseConstant.TABLE_SALON + " s, " +
                        DatabaseConstant.TABLE_USER + " u, " +
                        DatabaseConstant.TABLE_APPOINTMENT + " a" +
                " WHERE a." + DatabaseConstant.FK_APPOINTMENT_CUSTOMER + " = u." + DatabaseConstant.USER_ID  +
                " AND a." + DatabaseConstant.FK_APPOINTMENT_SALON + " = s." + DatabaseConstant.SALON_ID +
                " AND s." + DatabaseConstant.SALON_ID + " = " + salonId +
                " AND a." + DatabaseConstant.APPOINTMENT_STATUS + " = '" + Constant.Status.PENDING.value + "'";

        ArrayList<Appointment> returnList = new ArrayList<>();

        Cursor cursor = read.rawQuery(query, null);

        if(cursor == null || cursor.getCount() == 0)
            return returnList;

        cursor.moveToFirst();

        do{
            try {
                int id = cursor.getInt(0);
                Date date = DateTimeFormat.convertSqliteDateToDate(cursor.getString(1));
                boolean active = cursor.getInt(2) == 1;
                int cost = cursor.getInt(3);
                String status = cursor.getString(4);

                User user = new User();
                Service service = new Service();
                Stylist stylist = new Stylist();
                Voucher voucher = new Voucher();
                Salon salon = new Salon();

                service.setId(cursor.getInt(6));
                stylist.setId(cursor.getInt(7));
                voucher.setId(cursor.getInt(8));
                salon.setId(cursor.getInt(9));

                user.setId(cursor.getInt(10));
                user.setFullName(cursor.getString(11));
                user.setUsername(cursor.getString(12));
                user.setEmail(cursor.getString(14));
                user.setPhone(cursor.getString(15));
                user.setActive(cursor.getInt(16) == 1);
                user.setRole(cursor.getInt(17));
                user.setAvatar(cursor.getBlob(18));

                returnList.add(new Appointment(id, date, active, cost, status, user, service, stylist, voucher, salon));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } while(cursor.moveToNext());

        return returnList;
    }

    @Override
    public ArrayList<Appointment> getCompletedAppointments(int salonId) {
        SQLiteDatabase read = databaseHelper.getReadableDatabase();
        String query = "SELECT a.*, u.*" +
                " FROM " + DatabaseConstant.TABLE_SALON + " s, " +
                DatabaseConstant.TABLE_USER + " u, " +
                DatabaseConstant.TABLE_APPOINTMENT + " a" +
                " WHERE a." + DatabaseConstant.FK_APPOINTMENT_CUSTOMER + " = u." + DatabaseConstant.USER_ID  +
                " AND a." + DatabaseConstant.FK_APPOINTMENT_SALON + " = s." + DatabaseConstant.SALON_ID +
                " AND s." + DatabaseConstant.SALON_ID + " = " + salonId +
                " AND a." + DatabaseConstant.APPOINTMENT_STATUS + " = '" + Constant.Status.COMPLETED.value + "'";

        ArrayList<Appointment> returnList = new ArrayList<>();

        Cursor cursor = read.rawQuery(query, null);

        if(cursor == null || cursor.getCount() == 0)
            return returnList;

        cursor.moveToFirst();

        do{
            try {
                int id = cursor.getInt(0);
                Date date = DateTimeFormat.convertSqliteDateToDate(cursor.getString(1));
                boolean active = cursor.getInt(2) == 1;
                int cost = cursor.getInt(3);
                String status = cursor.getString(4);

                User user = new User();
                Service service = new Service();
                Stylist stylist = new Stylist();
                Voucher voucher = new Voucher();
                Salon salon = new Salon();

                service.setId(cursor.getInt(6));
                stylist.setId(cursor.getInt(7));
                voucher.setId(cursor.getInt(8));
                salon.setId(cursor.getInt(9));

                user.setId(cursor.getInt(10));
                user.setFullName(cursor.getString(11));
                user.setUsername(cursor.getString(12));
                user.setEmail(cursor.getString(14));
                user.setPhone(cursor.getString(15));
                user.setActive(cursor.getInt(16) == 1);
                user.setRole(cursor.getInt(17));
                user.setAvatar(cursor.getBlob(18));

                returnList.add(new Appointment(id, date, active, cost, status, user, service, stylist, voucher, salon));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } while(cursor.moveToNext());

        return returnList;
    }

    @Override
    public boolean addAppointment(Appointment appointment) {
        SQLiteDatabase write = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        try {
            contentValues.put(DatabaseConstant.APPOINTMENT_DATE, DateTimeFormat.convertDateToSqliteDate(appointment.getAppointmentDate()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        contentValues.put(DatabaseConstant.APPOINTMENT_STATUS, appointment.getStatus());
        contentValues.put(DatabaseConstant.APPOINTMENT_ACTIVE, appointment.isActive());
        contentValues.put(DatabaseConstant.APPOINTMENT_COST, appointment.getCost());
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
