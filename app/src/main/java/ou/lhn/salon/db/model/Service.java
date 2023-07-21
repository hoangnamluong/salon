package ou.lhn.salon.db.model;

public class Service {
    private int id;
    private String name;
    private String description;
    private int price;
    private int salonId;

    public Service() {
    }

    public Service(int id, String name, String description, int price, int salonId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.salonId = salonId;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSalonId() {
        return salonId;
    }

    public void setSalonId(int salonId) {
        this.salonId = salonId;
    }
}
