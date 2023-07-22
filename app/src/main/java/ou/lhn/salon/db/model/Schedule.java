package ou.lhn.salon.db.model;

public class Schedule {
    private int id;
    private String dayInWeek;
    private String fromTime;
    private String toTime;
    private Salon salon;

    public Schedule() {
    }

    public Schedule(int id, String dayInWeek, String fromTime, String toTime, Salon salon) {
        this.id = id;
        this.dayInWeek = dayInWeek;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.salon = salon;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", dayInWeek='" + dayInWeek + '\'' +
                ", fromTime='" + fromTime + '\'' +
                ", toTime='" + toTime + '\'' +
                ", salon=" + salon +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDayInWeek() {
        return dayInWeek;
    }

    public void setDayInWeek(String dayInWeek) {
        this.dayInWeek = dayInWeek;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public Salon getSalon() {
        return salon;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }
}
