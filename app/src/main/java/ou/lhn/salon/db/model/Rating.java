package ou.lhn.salon.db.model;

import java.io.Serializable;

public class Rating implements Serializable {
    private int id;
    private int rating;
    private boolean active;
    private User customer;
    private Stylist stylist;

    public Rating() {
    }

    public Rating(int id, int rating, boolean active, User customer, Stylist stylist) {
        this.id = id;
        this.rating = rating;
        this.active = active;
        this.customer = customer;
        this.stylist = stylist;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", rating=" + rating +
                ", active=" + active +
                ", customer=" + customer +
                ", stylist=" + stylist +
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
}
