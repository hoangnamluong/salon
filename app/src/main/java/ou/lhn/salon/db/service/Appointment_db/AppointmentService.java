package ou.lhn.salon.db.service.Appointment_db;

import android.content.Context;

import java.util.ArrayList;

import ou.lhn.salon.db.model.Appointment;

public interface AppointmentService {
    ArrayList<Appointment> getAppointmentsByStaff(int staffId);
    ArrayList<Appointment> getAppointmentsByDate(int year, int month, int date, int salonId, Context context);
    ArrayList<Appointment> getPendingAppointments(int salonId);
    ArrayList<Appointment> getCompletedAppointments(int salonId);
    boolean addAppointment(Appointment appointment);
    boolean updateAppointment(Appointment appointment);
    boolean deleteAppointment(int appointmentId);
}
