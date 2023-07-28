package ou.lhn.salon.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import ou.lhn.salon.R;

public class HeaderFragment extends Fragment {
    private TextView headerTxt;
    private ImageButton headerBtnReturn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_header, container, false);

        headerTxt = view.findViewById(R.id.headerTitle);
        headerBtnReturn = view.findViewById(R.id.headerBtnReturn);

        Bundle bundle = getArguments();
        String title = bundle.getString("titleMessage");

        headerTxt.setText(title);

        headerBtnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return view;
    }
}