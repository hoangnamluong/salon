package ou.lhn.salon.db;

public class DatabaseConstant {
    //SALON TABLE
    public static final String TABLE_SALON = "salon";
    public static final String SALON_ID = "id";
    public static final String SALON_NAME = "name";
    public static final String SALON_ADDRESS = "address";
    public static final String SALON_DESCRIPTION = "description";
    public static final String SALON_ACTIVE = "active";
    public static final String SALON_CREATED_AT = "created_at";
    public static final String SALON_UPDATED_AT = "updated_at";
    public static final String SALON_IMAGE = "image";
    public static final String FK_SALON_MANAGER = "manager_id";

    //USER TABLE
    public static final String TABLE_USER = "user";
    public static final String USER_ID = "id";
    public static final String USER_FIRST_NAME = "firstName";
    public static final String USER_LAST_NAME = "lastName";
    public static final String USER_USERNAME = "username";
    public static final String USER_PASSWORD = "password";
    public static final String USER_EMAIL = "email";
    public static final String USER_PHONE = "phone";
    public static final String USER_BIRTH = "birth";
    public static final String USER_GENDER = "gender";
    public static final String USER_ACTIVE = "active";
    public static final String USER_CREATED_AT = "createdAt";
    public static final String USER_UPDATED_AT = "updatedAt";
    public static final String FK_USER_ROLE = "role_id";
    public static final String USER_AVATAR = "avatar";

    //APPOINTMENT TABLE
    public static final String TABLE_APPOINTMENT = "appointment";
    public static final String APPOINTMENT_ID = "id";
    public static final String APPOINTMENT_DATE = "appointment_date";
    public static final String APPOINTMENT_TIME = "appointment_time";
    public static final String APPOINTMENT_STATUS = "status";
    public static final String APPOINTMENT_ACTIVE = "active";
    public static final String FK_APPOINTMENT_VOUCHER = "voucher_id";
    public static final String FK_APPOINTMENT_CUSTOMER = "customer_id";
    public static final String FK_APPOINTMENT_STYLIST = "stylist_id";
    public static final String FK_APPOINTMENT_SERVICE = "service_id";

    //RATING TABLE
    public static final String TABLE_RATING = "rating";
    public static final String RATING_ID = "id";
    public static final String RATING_RATING = "rating";
    public static final String RATING_ACTIVE = "active";
    public static final String FK_RATING_CUSTOMER = "user_id";
    public static final String FK_RATING_STYLIST = "stylist_id";

    //STYLIST TABLE
    public static final String TABLE_STYLIST = "stylist";
    public static final String STYLIST_ID = "id";
    public static final String STYLIST_NAME = "name";
    public static final String STYLIST_CUSTOMER_PER_DAY = "customer_per_day";
    public static final String STYLIST_ACTIVE = "active";
    public static final String FK_STYLIST_SALON = "salon_id";

    //SERVICE TABLE
    public static final String TABLE_SERVICE = "service";
    public static final String SERVICE_ID = "id";
    public static final String SERVICE_NAME = "name";
    public static final String SERVICE_DESCRIPTION = "description";
    public static final String SERVICE_PRICE = "price";
    public static final String FK_SERVICE_SALON = "salon_id";

    //SCHEDULE TABLE
    public static final String TABLE_SCHEDULE = "schedule";
    public static final String SCHEDULE_ID = "id";
    public static final String SCHEDULE_DAY_IN_WEEK = "day_in_week";
    public static final String SCHEDULE_FROM_TIME = "from_time";
    public static final String SCHEDULE_TO_TIME = "to_time";
    public static final String FK_SCHEDULE_SALON = "salon_id";

    //VOUCHER TABLE
    public static final String TABLE_VOUCHER = "voucher";
    public static final String VOUCHER_ID = "id";
    public static final String VOUCHER_CODE = "code";
    public static final String VOUCHER_PERCENTAGE = "percentage";
    public static final String VOUCHER_EXPIRED_DATE = "expired_date";
    public static final String VOUCHER_ACTIVE = "active";

    //USER_VOUCHER TABLE
    public static final String TABLE_USER_VOUCHER = "user_voucher";
    public static final String UV_USER = "user_id";
    public static final String UV_VOUCHER = "voucher_id";
    public static final String UV_ACTIVE = "active";

    //ROLE TABLE
    public static final String TABLE_ROLE = "role";
    public static final String ROLE_ID = "id";
    public static final String ROLE_NAME = "name";


    //SALON QUERY
    public static final String SALON_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_SALON + " (" +
            SALON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            SALON_NAME + " NVARCHAR(255) NOT NULL," +
            SALON_ADDRESS + " NVARCHAR(255)," +
            SALON_DESCRIPTION + " NVARCHAR(255)," +
            SALON_ACTIVE + " BOOLEAN NOT NULL DEFAULT 1," +
            SALON_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
            SALON_UPDATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
            SALON_IMAGE + " VARCHAR(255)" +
            FK_SALON_MANAGER + "INTEGER NOT NULL UNIQUE, " +
            "CONSTRAINT fk_user_salon FOREIGN KEY (" + FK_SALON_MANAGER + ") " +
                "REFERENCES " + TABLE_USER + "(" + USER_ID + ") ON UPDATE CASCADE ON DELETE CASCADE" +
            ");";
    public static final String SALON_DROP = "DROP TABLE IF EXISTS " + TABLE_SALON;

    //USER QUERY
    public static final String USER_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + " (" +
            USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            USER_FIRST_NAME + " NVARCHAR(30) NOT NULL," +
            USER_LAST_NAME + " NVARCHAR(15) NOT NULL," +
            USER_USERNAME + " NVARCHAR(15) NOT NULL," +
            USER_PASSWORD + " VARCHAR(255) NOT NULL," +
            USER_EMAIL + " VARCHAR(100) NOT NULL," +
            USER_PHONE + " VARCHAR(13) NOT NULL," +
            USER_BIRTH + " DATETIME NOT NULL," +
            USER_GENDER + " BOOLEAN DEFAULT 0," +
            USER_ACTIVE + " BOOLEAN DEFAULT 1," +
            USER_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
            USER_UPDATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
            FK_USER_ROLE + " INTEGER NOT NULL," +
            USER_AVATAR + " VARCHAR(255)" +
            "UNIQUE(" + USER_USERNAME + "," + USER_EMAIL + "," + USER_PHONE + ")," +
            "CONSTRAINT chk_age CHECK(strftime(\"%Y\", CURRENT_TIMESTAMP) - strftime(\"%Y\", " + USER_BIRTH + ") >= 18)" +
            "CONSTRAINT fk_role_user FOREIGN KEY (" + FK_USER_ROLE + ") " +
                "REFERENCES " + TABLE_ROLE + "(" + ROLE_ID + ") ON UPDATE CASCADE ON DELETE CASCADE" +
            ")";
    public static final String USER_DROP = "DROP TABLE IF EXISTS " + TABLE_USER;

    //ROLE QUERY
    public static final String ROLE_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_ROLE + " (" +
            ROLE_ID + " INTEGER PRIMARY KEY AUTO_INCREMENT," +
            ROLE_NAME + " VARCHAR(15) NOT NULL UNIQUE" +
            ");";
    public static final String ROLE_DROP = "DROP TABLE IF EXISTS " + TABLE_ROLE;

    //APPOINTMENT QUERY
    public static final String APPOINTMENT_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_APPOINTMENT + " (" +
            APPOINTMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            APPOINTMENT_DATE + " NVARCHAR(30) NOT NULL DEFAULT CURRENT_TIMESTAMP," +
            APPOINTMENT_TIME + " NVARCHAR(30) NOT NULL DEFAULT CURRENT_TIMESTAMP," +
            APPOINTMENT_ACTIVE + " BOOLEAN NOT NULL DEFAULT 1," +
            APPOINTMENT_STATUS + " VARCHAR(20) NOT NULL DEFAULT 'PENDING'," +
            FK_APPOINTMENT_CUSTOMER + " INTEGER NOT NULL UNIQUE," +
            FK_APPOINTMENT_SERVICE + "INTEGER NOT NULL UNIQUE," +
            FK_APPOINTMENT_STYLIST + "INTEGER NOT NULL UNIQUE," +
            FK_APPOINTMENT_VOUCHER + "INTEGER NOT NULL UNIQUE," +
            "CONSTRAINT fk_appointment_customer FOREIGN KEY (" + FK_APPOINTMENT_CUSTOMER + ") " +
                "REFERENCES " + TABLE_USER + "(" + USER_ID + ") ON UPDATE CASCADE ON DELETE CASCADE" +
            "CONSTRAINT fk_appointment_service FOREIGN KEY (" + FK_APPOINTMENT_SERVICE + ") " +
                "REFERENCES " + TABLE_SERVICE + "(" + SERVICE_ID + ") ON UPDATE CASCADE ON DELETE CASCADE" +
            "CONSTRAINT fk_appointment_stylist FOREIGN KEY (" + FK_APPOINTMENT_STYLIST + ") " +
                "REFERENCES " + TABLE_STYLIST + "(" + STYLIST_ID + ") ON UPDATE CASCADE ON DELETE CASCADE" +
            "CONSTRAINT fk_appointment_voucher FOREIGN KEY (" + FK_APPOINTMENT_VOUCHER + ") " +
                "REFERENCES " + TABLE_VOUCHER + "(" + VOUCHER_ID + ") ON UPDATE CASCADE ON DELETE CASCADE" +
            ")";
    public static final String APPOINTMENT_DROP = "DROP TABLE IF EXISTS " + TABLE_APPOINTMENT;

    //RATING QUERY
    public static final String RATING_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_RATING + " (" +
            RATING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            RATING_RATING + " INTEGER NOT NULL DEFAULT 1," +
            RATING_ACTIVE + " BOOLEAN NOT NULL DEFAULT 1," +
            FK_RATING_CUSTOMER + " INTEGER NOT NULL UNIQUE," +
            FK_RATING_STYLIST + " INTEGER NOT NULL UNIQUE," +
            "CONSTRAINT fk_rating_customer FOREIGN KEY (" + FK_RATING_CUSTOMER + ") " +
                "REFERENCES " + TABLE_USER + "(" + USER_ID + ") ON UPDATE CASCADE ON DELETE CASCADE" +
            "CONSTRAINT fk_rating_stylist FOREIGN KEY (" + FK_RATING_STYLIST + ") " +
                "REFERENCES " + TABLE_STYLIST + "(" + STYLIST_ID + ") ON UPDATE CASCADE ON DELETE CASCADE" +
            ")";
    public static final String RATING_DROP = "DROP TABLE IF EXISTS " + TABLE_RATING;

    //SCHEDULE QUERY
    public static final String SCHEDULE_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_SCHEDULE + " (" +
            SCHEDULE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            SCHEDULE_DAY_IN_WEEK + " VARCHAR(25) NOT NULL DEFAULT 'Monday'," +
            SCHEDULE_FROM_TIME + " VARCHAR(25) NOT NULL," +
            SCHEDULE_TO_TIME + " VARCHAR(25) NOT NULL," +
            FK_SCHEDULE_SALON + " INTEGER NOT NULL UNIQUE," +
            "CONSTRAINT fk_schedule_salon FOREIGN KEY (" + FK_SCHEDULE_SALON + ") " +
                "REFERENCES " + TABLE_SALON + "(" + SALON_ID + ") ON UPDATE CASCADE ON DELETE CASCADE" +
            ")";
    public static final String SCHEDULE_DROP = "DROP TABLE IF EXISTS " + TABLE_SCHEDULE;

    //SERVICE QUERY
    public static final String SERVICE_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_SERVICE + " (" +
            SERVICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            SERVICE_NAME + " NVARCHAR(50) NOT NULL DEFAULT 'Hair Cut'," +
            SERVICE_DESCRIPTION + " NVARCHAR(100)," +
            SERVICE_PRICE + " INTEGER NOT NULL DEFAULT 50000," +
            FK_SERVICE_SALON + " INTEGER NOT NULL UNIQUE," +
            "CONSTRAINT fk_service_salon FOREIGN KEY (" + FK_SERVICE_SALON + ") " +
                "REFERENCES " + TABLE_SALON + "(" + SALON_ID + ") ON UPDATE CASCADE ON DELETE CASCADE" +
            ")";
    public static final String SERVICE_DROP = "DROP TABLE IF EXISTS " + TABLE_SERVICE;

    //STYLIST QUERY
    public static final String STYLIST_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_STYLIST + " (" +
            STYLIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            STYLIST_NAME + " NVARCHAR(50) NOT NULL DEFAULT 'Hip Soong Che'," +
            STYLIST_CUSTOMER_PER_DAY + " INTEGER NOT NULL DEFAULT 2," +
            STYLIST_ACTIVE + " BOOLEAN NOT NULL DEFAULT 1," +
            FK_STYLIST_SALON + " INTEGER NOT NULL UNIQUE," +
            "CONSTRAINT fk_stylist_salon FOREIGN KEY (" + FK_STYLIST_SALON + ") " +
                "REFERENCES " + TABLE_SALON + "(" + SALON_ID + ") ON UPDATE CASCADE ON DELETE CASCADE" +
            ")";
    public static final String STYLIST_DROP = "DROP TABLE IF EXISTS " + TABLE_STYLIST;

    //VOUCHER QUERY
    public static final String VOUCHER_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_VOUCHER + " (" +
            VOUCHER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            VOUCHER_CODE + " VARCHAR(50) NOT NULL DEFAULT 'SD3FDQ211VG234'," +
            VOUCHER_PERCENTAGE + " INTEGER NOT NULL DEFAULT 20," +
            VOUCHER_EXPIRED_DATE + " DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP," +
            VOUCHER_ACTIVE + " BOOLEAN NOT NULL DEFAULT 1," +
            ")";
    public static final String VOUCHER_DROP = "DROP TABLE IF EXISTS " + TABLE_VOUCHER;

    //USER_VOUCHER QUERY
    public static final String USER_VOUCHER_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_USER_VOUCHER + " (" +
            UV_USER + " INTEGER NOT NULL UNIQUE," +
            UV_VOUCHER + " INTEGER NOT NULL UNIQUE," +
            UV_ACTIVE + " BOOLEAN NOT NULL DEFAULT 1," +
            "CONSTRAINT fk_uv_user FOREIGN KEY (" + UV_USER + ") " +
                "REFERENCES " + TABLE_USER + "(" + USER_ID + ") ON UPDATE CASCADE ON DELETE CASCADE" +
            "CONSTRAINT fk_uv_voucher FOREIGN KEY (" + UV_VOUCHER + ") " +
                "REFERENCES " + TABLE_VOUCHER + "(" + VOUCHER_ID + ") ON UPDATE CASCADE ON DELETE CASCADE" +
            ")";
    public static final String USER_VOUCHER_DROP = "DROP TABLE IF EXISTS " + TABLE_USER_VOUCHER;
}
