package ou.lhn.salon.db.model;

import java.util.Date;

public class Appointment {
    private int id;
    private Date appointmentDate;
    private int cost;
    private String status;
    private boolean active;
    private Voucher voucher;
    private User customer;
    private Stylist stylist;
    private Service service;
    private Salon salon;

    public Appointment() {
    }

    public Appointment(int id, Date appointmentDate, int cost, String status, boolean active, Voucher voucher, User customer, Stylist stylist, Service service, Salon salon) {
        this.id = id;
        this.appointmentDate = appointmentDate;
        this.cost = cost;
        this.status = status;
        this.active = active;
        this.voucher = voucher;
        this.customer = customer;
        this.stylist = stylist;
        this.service = service;
        this.salon = salon;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", appointmentDate=" + appointmentDate +
                ", cost=" + cost +
                ", status='" + status + '\'' +
                ", active=" + active +
                ", voucher=" + voucher +
                ", customer=" + customer +
                ", stylist=" + stylist +
                ", service=" + service +
                ", salon=" + salon +
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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
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

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Stylist getStylist() {
        return stylist;
    }

    public void setStylist(Stylist stylist) {
        this.stylist = stylist;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Salon getSalon() {
        return salon;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }
}
