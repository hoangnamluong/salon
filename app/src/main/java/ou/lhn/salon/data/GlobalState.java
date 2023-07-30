package ou.lhn.salon.data;

import android.app.Application;

import ou.lhn.salon.db.model.User;

public class GlobalState extends Application {
    //User Authentication
    private static User LoggedIn;

    public static User getLoggedIn() {
        return LoggedIn;
    }

    public static void setLoggedIn(User loggedIn) {
        LoggedIn = loggedIn;
    }
}
