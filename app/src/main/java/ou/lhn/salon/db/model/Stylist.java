package ou.lhn.salon.db.model;

public class Stylist {
    private int id;
    private String name;
    private int customerPerDay;
    private boolean active;
    private int salonId;

    public Stylist() {
    }

    public Stylist(int id, String name, int customerPerDay, boolean active, int salonId) {
        this.id = id;
        this.name = name;
        this.customerPerDay = customerPerDay;
        this.active = active;
        this.salonId = salonId;
    }

    @Override
    public String toString() {
        return "Stylist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", customerPerDay=" + customerPerDay +
                ", active=" + active +
                ", salonId=" + salonId +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCustomerPerDay() {
        return customerPerDay;
    }

    public void setCustomerPerDay(int customerPerDay) {
        this.customerPerDay = customerPerDay;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getSalonId() {
        return salonId;
    }

    public void setSalonId(int salonId) {
        this.salonId = salonId;
    }
}
