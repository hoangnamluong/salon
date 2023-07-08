package ou.lhn.salon.util;

import android.app.Application;
import android.content.Context;

public class GetAppContext extends Application {
    private static Context context = null;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
