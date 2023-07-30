package ou.lhn.salon.db.model;

import java.io.Serializable;
import java.util.Date;

public class Appointment implements Serializable {
    private int id;
    private Date appointmentDate;
    private boolean active;
    private long cost;
    private String status;
    private User customer;
    private Service service;
    private Stylist stylist;
    private Voucher voucher;
    private Salon salon;

    public Appointment() {
    }

    public Appointment(int id, Date appointmentDate, boolean active, long cost, String status, User customer, Service service, Stylist stylist, Voucher voucher, Salon salon) {
        this.id = id;
        this.appointmentDate = appointmentDate;
        this.active = active;
        this.cost = cost;
        this.status = status;
        this.customer = customer;
        this.service = service;
        this.stylist = stylist;
        this.voucher = voucher;
        this.salon = salon;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Stylist getStylist() {
        return stylist;
    }

    public void setStylist(Stylist stylist) {
        this.stylist = stylist;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public Salon getSalon() {
        return salon;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }
}
