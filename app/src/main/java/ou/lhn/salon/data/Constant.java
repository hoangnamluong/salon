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
}
