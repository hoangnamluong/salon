package ou.lhn.salon.data;

public class Constant {
    public enum ROLE {
        USER(1),
        STAFF(2),
        MANAGER(3),
        ADMIN(0);

        public final int value;

        ROLE(int value) { this.value = value; }
    }

    public enum DayInWeek {
        MONDAY("MONDAY"),
        TUESDAY("TUESDAY"),
        WEDNESDAY("WEDNESDAY"),
        THURSDAY("THURSDAY"),
        FRIDAY("FRIDAY"),
        SATURDAY("SATURDAY"),
        SUNDAY("SUNDAY");

        public final String value;

        DayInWeek(String value) {
            this.value = value;
        }
    }

    public enum Status {
        PENDING("PENDING"),
        COMPLETED("COMPLETED");

        public final String value;

        Status(String value) { this.value = value; }
    }

    public static final int PERCENT_100 = 100;
    public static final String[] DAY_31 = new String[] {"Select Date","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
    public static final String[] DAY_30 = new String[] {"Select Date","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30"};
    public static final String[] DAY_29 = new String[] {"Select Date","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29"};
    public static final String[] DAY_28 = new String[] {"Select Date","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28"};
    public static final String[] Months = new String[] {"Select Month", "January", "February",
            "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December" };
}
