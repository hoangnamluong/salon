package ou.lhn.salon.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import ou.lhn.salon.R;

public class BookingHistoryFragmentStaff extends Fragment {
    private EditText bhsEdtSearch;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_history_staff, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        bhsEdtSearch = view.findViewById(R.id.bhsEdtSearch);
        bhsEdtSearch.clearFocus();
    }
}