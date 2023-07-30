package ou.lhn.salon.util;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ReplaceFragment {
    public static void replaceFragment(FragmentManager fragmentManager ,Fragment fragment, int viewId) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(viewId, fragment);
        fragmentTransaction.commit();
    }
}
