package ou.lhn.salon.db.service.Appointment_db;

import java.util.ArrayList;

import ou.lhn.salon.db.model.Appointment;

public interface AppointmentService {
    ArrayList<Appointment> getAppointmentsByStaff(int staffId);
    boolean addAppointment(Appointment appointment);
    boolean updateAppointment(Appointment appointment);
    boolean deleteAppointment(int appointmentId);
}
