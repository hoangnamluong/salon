package ou.lhn.salon.db.model;

import java.util.Date;

public class Voucher {
    private int id;
    private String code;
    private int percentage;
    private Date expiredDate;
    private boolean active;

    public Voucher() {
    }

    public Voucher(int id, String code, int percentage, Date expiredDate, boolean active) {
        this.id = id;
        this.code = code;
        this.percentage = percentage;
        this.expiredDate = expiredDate;
        this.active = active;
    }

    @Override
    public String toString() {
        return "Voucher{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", percentage=" + percentage +
                ", expiredDate=" + expiredDate +
                ", active=" + active +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
