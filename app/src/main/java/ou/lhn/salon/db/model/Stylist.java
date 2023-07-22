package ou.lhn.salon.db.model;

public class Stylist {
    private int id;
    private String name;
    private int customerPerDay;
    private boolean active;
    private Salon salon;

    public Stylist() {
    }

    public Stylist(int id, String name, int customerPerDay, boolean active, Salon salon) {
        this.id = id;
        this.name = name;
        this.customerPerDay = customerPerDay;
        this.active = active;
        this.salon = salon;
    }

    @Override
    public String toString() {
        return "Stylist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", customerPerDay=" + customerPerDay +
                ", active=" + active +
                ", salon=" + salon +
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

    public Salon getSalon() {
        return salon;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }
}
