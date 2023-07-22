package ou.lhn.salon.db.model;

import androidx.annotation.Nullable;

import java.util.Date;

public class Salon {
    private int id;
    private String name;
    private String address;
    private String description;
    private boolean active;
    private Date createdAt = new Date();
    private Date updatedAt;
    private String image;
    private User manager;

    public Salon() {
    }

    public Salon(int id, String name, String address, String description, boolean active, Date createdAt, Date updatedAt, String image, User manager) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.image = image;
        this.manager = manager;
    }

    @Override
    public String toString() {
        return "Salon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", active=" + active +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", image='" + image + '\'' +
                ", manager=" + manager +
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }
}
