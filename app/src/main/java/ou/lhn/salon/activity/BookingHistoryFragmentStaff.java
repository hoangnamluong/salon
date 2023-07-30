package ou.lhn.salon.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

import ou.lhn.salon.R;
import ou.lhn.salon.databinding.ActivityBookingHistoryFilterBinding;
import ou.lhn.salon.db.model.Appointment;
import ou.lhn.salon.db.service.Appointment_db.AppointmentService;
import ou.lhn.salon.db.service.Appointment_db.AppointmentServiceImpl;
import ou.lhn.salon.view.adapter.BookingHistoryAdapter;

public class BookingHistoryFragmentStaff extends Fragment {
    private AppointmentService appointmentService;

    private EditText bhsEdtSearch;
    private ImageButton bhsBtnFilter;
    private ListView bhsLvBookingHistory;
    private BottomNavigationView bhsBnvStatus;
    private AlertDialog alertDialog;

    private ArrayList<Appointment> appointments;
    private BookingHistoryAdapter adapter;

    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() != Activity.RESULT_OK) return;

                    Intent intent = result.getData();

                    if(intent == null) return;

                    int year = intent.getIntExtra("year", -1);
                    int month = intent.getIntExtra("month", -1);
                    int date = intent.getIntExtra("date", -1);

                    refetchData(year, month, date);
                }
            }
    );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_history_staff, container, false);
        appointmentService = AppointmentServiceImpl.getInstance(getContext());

        initView(view);
        initListView();
        initListener();

        return view;
    }

    private void initView(View view) {
        bhsEdtSearch = view.findViewById(R.id.bhsEdtSearch);
        bhsBnvStatus = view.findViewById(R.id.bhsBnvStatus);
        bhsLvBookingHistory = view.findViewById(R.id.bhsLvBookingHistory);
        bhsBtnFilter = view.findViewById(R.id.bhsBtnFilter);

        alertDialog = new AlertDialog.Builder(getContext()).create();

        bhsEdtSearch.clearFocus();
    }

    private void initListView() {
        appointments = appointmentService.getPendingAppointments(1) ;
        adapter = new BookingHistoryAdapter(getContext(), R.layout.list_view_booking_history_item, appointments);
        bhsLvBookingHistory.setAdapter(adapter);
    }

    private void initListener() {
        bhsBnvStatus.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return switchStatus(item);
            }
        });

        bhsBtnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BookingHistoryFilterActivity.class);
                activityResultLauncher.launch(intent);
            }
        });

        bhsLvBookingHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), BookingHistoryDetailActivityStaff.class);
                intent.putExtra("appointment", appointments.get(position));
                startActivity(intent);
            }
        });
    }

    private boolean switchStatus(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.bhmPending) {
            appointments.clear();
            appointments.addAll(appointmentService.getPendingAppointments(1));
            adapter.notifyDataSetChanged();

            return true;
        } else if (itemId == R.id.bhmCompleted) {
            appointments.clear();
            appointments.addAll(appointmentService.getCompletedAppointments(1));
            adapter.notifyDataSetChanged();

            return true;
        }

        return false;
    }

    private void refetchData(int year, int month, int date) {
        appointments.clear();
        appointments.addAll(appointmentService.getAppointmentsByDate(year, month, date, 1, getContext()));

        adapter.notifyDataSetChanged();
    }
}