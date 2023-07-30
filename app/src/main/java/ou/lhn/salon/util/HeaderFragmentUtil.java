package ou.lhn.salon.util;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import ou.lhn.salon.activity.HeaderFragment;

public class HeaderFragmentUtil {
    public static void createHeaderFragment(FragmentManager fragmentManager, String message, int activityHeaderId, HeaderFragment headerFragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();

        bundle.putString("titleMessage", message);

        headerFragment.setArguments(bundle);

        fragmentTransaction.replace(activityHeaderId, headerFragment).commit();
    }

    public static void removeOldFragment(FragmentManager fragmentManager, HeaderFragment headerFragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(headerFragment);

        fragmentTransaction.commit();
    }
}
