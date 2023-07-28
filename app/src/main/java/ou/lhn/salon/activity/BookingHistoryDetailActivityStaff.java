package ou.lhn.salon.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.FrameLayout;

import ou.lhn.salon.R;
import ou.lhn.salon.util.HeaderFragmentUtil;

public class BookingHistoryDetailActivityStaff extends AppCompatActivity {
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private FrameLayout bhdHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history_detail_staff);

        initView();
        inflateHeader();
    }

    private void initView() {
        bhdHeader = findViewById(R.id.bhdHeader);
    }

    private void inflateHeader() {
        HeaderFragmentUtil.createHeaderFragment(fragmentManager,"Booking Detail", bhdHeader.getId(), new HeaderFragment());
    }
}