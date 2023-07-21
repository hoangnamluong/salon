package ou.lhn.salon.db.model;

public class Rating {
    private int id;
    private int rating;
    private boolean active;
    private int customerId;
    private int stylistId;

    public Rating() {
    }

    public Rating(int id, int rating, boolean active, int customerId, int stylistId) {
        this.id = id;
        this.rating = rating;
        this.active = active;
        this.customerId = customerId;
        this.stylistId = stylistId;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", rating=" + rating +
                ", active=" + active +
                ", customerId=" + customerId +
                ", stylistId=" + stylistId +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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
}
