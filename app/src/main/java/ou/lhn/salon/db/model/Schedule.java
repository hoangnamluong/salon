package ou.lhn.salon.db.model;

public class Schedule {
    private int id;
    private String dayInWeek;
    private String fromTime;
    private String toTime;
    private int salonId;

    public Schedule() {
    }

    public Schedule(int id, String dayInWeek, String fromTime, String toTime, int salonId) {
        this.id = id;
        this.dayInWeek = dayInWeek;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.salonId = salonId;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", dayInWeek='" + dayInWeek + '\'' +
                ", fromTime='" + fromTime + '\'' +
                ", toTime='" + toTime + '\'' +
                ", salonId=" + salonId +
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

    public int getSalonId() {
        return salonId;
    }

    public void setSalonId(int salonId) {
        this.salonId = salonId;
    }
}
