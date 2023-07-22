package ou.lhn.salon.db.model;

public class UserVoucher {
    private User user;
    private Voucher voucher;
    private boolean active;

    public UserVoucher() {
    }

    public UserVoucher(User user, Voucher voucher, boolean active) {
        this.user = user;
        this.voucher = voucher;
        this.active = active;
    }

    @Override
    public String toString() {
        return "UserVoucher{" +
                "user=" + user +
                ", voucher=" + voucher +
                ", active=" + active +
                '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
