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
    public static final String SALON_USER_ID_FK = "manager_id";

    //USER TABLE
    public static final String TABLE_USER = "user.db";
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
    public static final String USER_ROLE_ID_FK = "role_id";
    public static final String USER_AVATAR = "avatar";

    //BRANCH TABLE
    public static final String TABLE_BRANCH = "branch.db";
    public static final String BRANCH_ID = "id";
    public static final String BRANCH_ADDRESS = "address";
    public static final String BRANCH_SALON_ID_FK = "salon_id";

    //EMPLOYEE TABLE
    public static final String TABLE_EMPLOYEE = "employee.db";
    public static final String EMPLOYEE_USER_ID_FK = "usFKer_id";
    public static final String EMPLOYEE_BRANCH_ID_FK = "branch_id";
    public static final String EMPLOYEE_IDENTITY_CARD_NUMBER = "identity_card_number";

    //ROLE TABLE
    public static final String TABLE_ROLE = "role.db";
    public static final String ROLE_ID = "id";
    public static final String ROLE_NAME = "name";


    //SALON QUERY
    public static final String SALON_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_SALON + " (" + SALON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            SALON_NAME + " NVARCHAR(255) NOT NULL," +
            SALON_ADDRESS + " NVARCHAR(255)," +
            SALON_DESCRIPTION + " NVARCHAR(255)," +
            SALON_ACTIVE + " BOOLEAN NOT NULL DEFAULT 1," +
            SALON_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
            SALON_UPDATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
            SALON_IMAGE + " VARCHAR(255)" +
            SALON_USER_ID_FK + "INTEGER NOT NULL UNIQUE, " +
            "CONSTRAINT fk_user_salon FOREIGN KEY (" + SALON_USER_ID_FK + ") " +
                "REFERENCES " + TABLE_USER + "(" + USER_ID + ") ON UPDATE CASCADE ON DELETE CASCADE" +
            ");";
    public static final String SALON_DROP = "DROP TABLE IF EXISTS " + TABLE_SALON;

    //USER QUERY
    public static final String USER_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + " (" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
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
            USER_ROLE_ID_FK + " INTEGER NOT NULL," +
            USER_AVATAR + " VARCHAR(255)" +
            "UNIQUE(" + USER_USERNAME + "," + USER_EMAIL + "," + USER_PHONE + ")," +
            "CONSTRAINT chk_age CHECK(strftime(\"%Y\", CURRENT_TIMESTAMP) - strftime(\"%Y\", " + USER_BIRTH + ") >= 18)" +
            "CONSTRAINT fk_role_user FOREIGN KEY (" + USER_ROLE_ID_FK + ") " +
                "REFERENCES " + TABLE_ROLE + "(" + ROLE_ID + ") ON UPDATE CASCADE ON DELETE CASCADE" +
            ")";
    public static final String USER_DROP = "DROP TABLE IF EXISTS " + TABLE_USER;

    //BRANCH QUERY
    public static final String BRANCH_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_BRANCH + " (" + BRANCH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            BRANCH_ADDRESS + " NVARCHAR(255) NOT NULL," +
            BRANCH_SALON_ID_FK + " INTEGER NOT NULL UNIQUE," +
            "CONSTRAINT fk_branch_salon FOREIGN KEY (" + BRANCH_SALON_ID_FK + ") " +
                "REFERENCES " + TABLE_SALON + "(" + SALON_ID + ") ON UPDATE CASCADE ON DELETE CASCADE" +
            ")";
    public static final String BRANCH_DROP = "DROP TABLE IF EXISTS " + TABLE_BRANCH;

    //EMPLOYEE QUERY
    public static final String EMPLOYEE_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_EMPLOYEE + " (" + EMPLOYEE_USER_ID_FK + " INTEGER NOT NULL UNIQUE," +
            EMPLOYEE_BRANCH_ID_FK + " INTEGER NOT NULL UNIQUE," +
            EMPLOYEE_IDENTITY_CARD_NUMBER + " VARCHAR(20) NOT NULL UNIQUE," +
            "CONSTRAINT fk_user_employee FOREIGN KEY (" + EMPLOYEE_USER_ID_FK + ") " +
                "REFERENCES " + TABLE_USER + "(" + USER_ID + ") ON UPDATE CASCADE ON DELETE CASCADE," +
            "CONSTRAINT fk_branch_employee FOREIGN KEY (" + EMPLOYEE_BRANCH_ID_FK + ") " +
                "REFERENCES " + TABLE_BRANCH + "(" + BRANCH_ID + ") ON UPDATE CASCADE ON DELETE CASCADE" +
            ");";
    public static final String EMPLOYEE_DROP = "DROP TABLE IF EXISTS " + TABLE_EMPLOYEE;

    //ROLE QUERY
    public static final String ROLE_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_ROLE + " (" +
            ROLE_ID + " INTEGER PRIMARY KEY AUTO_INCREMENT," +
            ROLE_NAME + " VARCHAR(15) NOT NULL UNIQUE" +
            ");";
    public static final String ROLE_DROP = "DROP TABLE IF EXISTS " + TABLE_ROLE;
}
