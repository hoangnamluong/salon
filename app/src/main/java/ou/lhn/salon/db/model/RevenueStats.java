package ou.lhn.salon.db.model;

public class RevenueStats {
    private String SalonName;
    private int countAppointment;
    private long total;

    public RevenueStats(String salonName, int countAppointment, long total) {
        SalonName = salonName;
        this.countAppointment = countAppointment;
        this.total = total;
    }
    public RevenueStats(){};

    @Override
    public String toString() {
        return "RevenueStats{" +
                "SalonName='" + SalonName + '\'' +
                ", countAppointment=" + countAppointment +
                ", total=" + total +
                '}';
    }

    public String getSalonName() {
        return SalonName;
    }

    public void setSalonName(String salonName) {
        SalonName = salonName;
    }

    public int getCountAppointment() {
        return countAppointment;
    }

    public void setCountAppointment(int countAppointment) {
        this.countAppointment = countAppointment;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
