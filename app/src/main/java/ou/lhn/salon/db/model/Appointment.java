package ou.lhn.salon.db.model;

import java.util.Date;

public class Appointment {
    private int id;
    private Date appointmentDate;
    private String appointmentTime;
    private String status;
    private boolean active;
    private int voucherId;
    private int customerId;
    private int stylistId;
    private int serviceId;

    public Appointment() {
    }

    public Appointment(int id, Date appointmentDate, String appointmentTime, String status, boolean active, int voucherId, int customerId, int stylistId, int serviceId) {
        this.id = id;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = status;
        this.active = active;
        this.voucherId = voucherId;
        this.customerId = customerId;
        this.stylistId = stylistId;
        this.serviceId = serviceId;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", appointmentDate=" + appointmentDate +
                ", appointmentTime='" + appointmentTime + '\'' +
                ", status='" + status + '\'' +
                ", active=" + active +
                ", voucherId=" + voucherId +
                ", customerId=" + customerId +
                ", stylistId=" + stylistId +
                ", serviceId=" + serviceId +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getStylistId() {
        return stylistId;
    }

    public void setStylistId(int stylistId) {
        this.stylistId = stylistId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }
}
