package ou.lhn.salon.db.model;

public class Service {
    private int id;
    private String name;
    private String description;
    private int price;
    private Salon salon;

    public Service() {
    }

    public Service(int id, String name, String description, int price, Salon salon) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.salon = salon;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
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

    public Salon getSalon() {
        return salon;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }
}
