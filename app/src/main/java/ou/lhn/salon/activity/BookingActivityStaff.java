package ou.lhn.salon.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import ou.lhn.salon.R;
import ou.lhn.salon.data.Constant;
import ou.lhn.salon.db.model.Service;
import ou.lhn.salon.db.model.Stylist;
import ou.lhn.salon.db.model.User;
import ou.lhn.salon.db.model.Voucher;
import ou.lhn.salon.db.service.Service_db.ServiceService;
import ou.lhn.salon.db.service.Service_db.ServiceServiceImpl;
import ou.lhn.salon.db.service.User_db.UserService;
import ou.lhn.salon.db.service.User_db.UserServiceImpl;
import ou.lhn.salon.db.service.Voucher_db.VoucherService;
import ou.lhn.salon.db.service.Voucher_db.VoucherServiceImpl;
import ou.lhn.salon.util.HeaderFragmentUtil;
import ou.lhn.salon.view.adapter.BookingServiceAdapter;

public class BookingActivityStaff extends AppCompatActivity {
    private static final int REQUEST_CODE = 1925;

    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private final UserService userService = UserServiceImpl.getInstance(this);
    private final ServiceService serviceService = ServiceServiceImpl.getInstance(this);
    private final VoucherService voucherService = VoucherServiceImpl.getInstance(this);

    private ArrayList<Service> services;
    private BookingServiceAdapter adapter;

    private FrameLayout bookingHeader;
    private TextView bookingBtnPickStylist, bookingBtnPickDate, bookingBtnPickTime;
    private AppCompatButton bookingBtnConfirm;
    private EditText bookingEdtVoucher, bookingMemberInfoEdtFullName, bookingMemberInfoEdtPhone;
    private Spinner bookingSpService;

    private Calendar calendar = Calendar.getInstance();
    private Stylist stylist;
    private Service service;

    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();

                        if(intent == null) return;

                        stylist = (Stylist) intent.getSerializableExtra("stylist");

                        bookingBtnPickStylist.setText(stylist.getName().toString());
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_staff);

        initView();
        inflateHeader();
        initService();
        initListener();
    }

    private void initView() {
        bookingHeader = findViewById(R.id.bookingHeader);
        bookingBtnPickStylist = findViewById(R.id.bookingBtnPickStylist);
        bookingBtnPickDate = findViewById(R.id.bookingBtnPickDate);
        bookingBtnPickTime = findViewById(R.id.bookingBtnPickTime);
        bookingBtnConfirm = findViewById(R.id.bookingBtnConfirm);
        bookingMemberInfoEdtFullName = findViewById(R.id.bookingMemberInfoEdtFullName);
        bookingMemberInfoEdtPhone = findViewById(R.id.bookingMemberInfoEdtPhone);
        bookingEdtVoucher = findViewById(R.id.bookingEdtVoucher);
        bookingSpService = findViewById(R.id.bookingSpService);
    }

    private void inflateHeader() {
        HeaderFragmentUtil.createHeaderFragment(fragmentManager,"Booking An Appointment", bookingHeader.getId(), new HeaderFragment());
    }

    private void initListener() {
        bookingBtnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPickedDate();
            }
        });

        bookingBtnPickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getPickedTime();
            }
        });

        bookingBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmAppointment();
            }
        });

        bookingBtnPickStylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookingActivityStaff.this, StylistSelectActivity.class);
                activityResultLauncher.launch(intent);
            }
        });

        bookingSpService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                service = services.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });
    }

    private void initService() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(BookingActivityStaff.this, "Getting Salon Services", Toast.LENGTH_SHORT).show();
                    }
                });

                services = serviceService.getAllServices();

                if(services == null || services.size() == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(BookingActivityStaff.this, "Getting Services Failed Or Salon Has No Service", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }

                adapter = new BookingServiceAdapter(BookingActivityStaff.this, R.layout.spinner_service_item, services);
                bookingSpService.setAdapter(adapter);
            }
        }).start();
    }

    private void getPickedDate() {
        CalendarConstraints constraints = new CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now()).build();

        MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Pick Appointment Date").setCalendarConstraints(constraints).build();

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                calendar.setTimeInMillis((Long)selection);
                calendar.add(Calendar.MONTH, 1);

                String pickedDate = String.format(Locale.getDefault() ,
                        "%02d/%02d/%d",
                        calendar.get(Calendar.DAY_OF_MONTH),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.YEAR));

                bookingBtnPickDate.setText(pickedDate);
            }
        });

        materialDatePicker.show(fragmentManager, null);
    }

    private void getPickedTime() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(BookingActivityStaff.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                String pickedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                bookingBtnPickTime.setText(pickedTime);
            }
        }, 12, 0, true);

        timePickerDialog.show();
    }

    private void confirmAppointment() {
        if(!validateData()) {
            Toast.makeText(this, "Please fill all the information and check your personal information", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(BookingActivityStaff.this, BookingConfirmActivity.class);

        User user = userService.isUserAMember(bookingMemberInfoEdtPhone.getText().toString());
        Voucher voucher = checkVoucher();

        intent.putExtra("appointmentDate", calendar);
        intent.putExtra("stylist", stylist);
        intent.putExtra("service", service);

        if(user != null) {
            intent.putExtra("customer" , user);
            intent.putExtra("isMember", true);
        }
        else {
            intent.putExtra("isMember", false);
            intent.putExtra("customer", new User(0,
                    bookingMemberInfoEdtFullName.getText().toString(),
                    null,
                    null,
                    null,
                    bookingMemberInfoEdtPhone.getText().toString(),
                    true,
                    Constant.ROLE.USER.value,
                    null,
                    null
                    ));
        }

        if(voucher != null) {
            intent.putExtra("voucher", voucher);
        }

        startActivity(intent);
    }

    private boolean validateData() {
        if(bookingMemberInfoEdtFullName.getText().toString().equals("")
                || bookingMemberInfoEdtPhone.getText().toString().equals("")
                || stylist == null
                || service == null
                || bookingBtnPickDate.getText().toString().equals("Select Date")
                || bookingBtnPickTime.getText().toString().equals("Select Time")
        )
            return false;

        return true;
    }

    private Voucher checkVoucher() {
        return voucherService.getVoucherByCode(bookingEdtVoucher.getText().toString());
    }
}