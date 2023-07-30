package ou.lhn.salon.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ou.lhn.salon.R;
import ou.lhn.salon.data.Constant;
import ou.lhn.salon.data.GlobalState;
import ou.lhn.salon.db.model.Appointment;
import ou.lhn.salon.db.model.Salon;
import ou.lhn.salon.db.model.Service;
import ou.lhn.salon.db.model.Stylist;
import ou.lhn.salon.db.model.User;
import ou.lhn.salon.db.model.Voucher;
import ou.lhn.salon.db.service.Appointment_db.AppointmentService;
import ou.lhn.salon.db.service.Appointment_db.AppointmentServiceImpl;
import ou.lhn.salon.db.service.Salon_db.SalonSerivce;
import ou.lhn.salon.db.service.Salon_db.SalonServiceImpl;
import ou.lhn.salon.util.HeaderFragmentUtil;

public class BookingConfirmActivity extends AppCompatActivity {
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private final AppointmentService appointmentService = AppointmentServiceImpl.getInstance(this);
    private final SalonSerivce salonSerivce = SalonServiceImpl.getInstance(this);

    private TextView bookingConfirmTxtInfoAppointTime,
                bookingConfirmTxtInfoServiceName,
                bookingConfirmTxtInfoServicePrice,
                bookingConfirmTxtInfoVoucherPrice,
                bookingConfirmTxtInfoTotalCost,
                bookingConfirmTxtInfoCustomerName,
                bookingConfirmTxtInfoCustomerPhone,
                bookingConfirmTxtInfoCustomerEmail,
                bookingConfirmTxtInfoStylistName;
    private AppCompatButton bookingConfirmBtnConfirm;
    private AlertDialog alertDialog;

    private User customer;
    private Stylist stylist;
    private Service service;
    private Calendar calendar;
    private Voucher voucher;
    private Salon salon;
    private long totalCost;
    private boolean isMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirm);

        initView();
        inflateHeader();
        receiveData();
        initData();
        initListener();
    }

    private void inflateHeader() {
        HeaderFragmentUtil.createHeaderFragment(fragmentManager, "Confirm Appointment", R.id.bookingConfirmHeader, new HeaderFragment());
    }

    private void initView() {
        bookingConfirmTxtInfoAppointTime = findViewById(R.id.bookingConfirmTxtInfoAppointTime);
        bookingConfirmTxtInfoServiceName = findViewById(R.id.bookingConfirmTxtInfoServiceName);
        bookingConfirmTxtInfoServicePrice = findViewById(R.id.bookingConfirmTxtInfoServicePrice);
        bookingConfirmTxtInfoVoucherPrice = findViewById(R.id.bookingConfirmTxtInfoVoucherPrice);
        bookingConfirmTxtInfoTotalCost = findViewById(R.id.bookingConfirmTxtInfoTotalCost);
        bookingConfirmTxtInfoCustomerName = findViewById(R.id.bookingConfirmTxtInfoCustomerName);
        bookingConfirmTxtInfoCustomerPhone = findViewById(R.id.bookingConfirmTxtInfoCustomerPhone);
        bookingConfirmTxtInfoCustomerEmail = findViewById(R.id.bookingConfirmTxtInfoCustomerEmail);
        bookingConfirmTxtInfoStylistName = findViewById(R.id.bookingConfirmTxtInfoStylistName);
        bookingConfirmBtnConfirm = findViewById(R.id.bookingConfirmBtnConfirm);

        alertDialog = new AlertDialog.Builder(BookingConfirmActivity.this).create();
    }

    private void receiveData() {
        Intent intent = getIntent();

        customer = (User) intent.getSerializableExtra("customer");
        stylist = (Stylist) intent.getSerializableExtra("stylist");
        service = (Service) intent.getSerializableExtra("service");
        calendar = (Calendar) intent.getSerializableExtra("appointmentDate");
        isMember = intent.getBooleanExtra("isMember", false);
        voucher = (Voucher) intent.getSerializableExtra("voucher");
    }

    @SuppressLint("DefaultLocale")
    private void initData() {
        bookingConfirmTxtInfoAppointTime.setText(new SimpleDateFormat("EEEE dd/MM/yyyy HH:mm", Locale.getDefault()).format(calendar.getTime()));
        bookingConfirmTxtInfoServiceName.setText(service.getName().toString());
        bookingConfirmTxtInfoServicePrice.setText(String.format("%d", service.getPrice()));
        bookingConfirmTxtInfoCustomerName.setText(customer.getFullName());
        bookingConfirmTxtInfoCustomerPhone.setText(customer.getPhone());
        bookingConfirmTxtInfoStylistName.setText(stylist.getName().toString());

        if(!isMember) {
            bookingConfirmTxtInfoCustomerEmail.setText("New Customer");
        } else {
            bookingConfirmTxtInfoCustomerEmail.setText(customer.getEmail());
        }

        if (voucher != null) {
            long voucherPrice = (voucher.getPercentage() / Constant.PERCENT_100) * service.getPrice();
            totalCost = service.getPrice() - voucherPrice;

            bookingConfirmTxtInfoVoucherPrice.setText(String.format("%d", voucherPrice));
            bookingConfirmTxtInfoTotalCost.setText(String.format("%d", totalCost));
        } else {
            totalCost = service.getPrice();

            bookingConfirmTxtInfoVoucherPrice.setText("");
            bookingConfirmTxtInfoTotalCost.setText(String.format("%d", service.getPrice()));
        }

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bookingConfirmBtnConfirm.setEnabled(false);
                    }
                });

                salon = salonSerivce.getSalonByStaffId(GlobalState.getLoggedIn().getId());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bookingConfirmBtnConfirm.setEnabled(true);

                        if(salon == null) {
                            Toast.makeText(BookingConfirmActivity.this, "Can not get Salon. Please restart app.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();*/
    }

    private void initListener() {
        bookingConfirmBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmAppointment();
            }
        });
    }

    private void confirmAppointment() {
        alertDialog.setTitle("Booking Appointment");
        alertDialog.setView(getLayoutInflater().inflate(R.layout.progress_bar, null));

        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alertDialog.show();
                    }
                });

                Appointment appointment = new Appointment(1, calendar.getTime(), true, totalCost, Constant.Status.PENDING.value, customer, service, stylist, voucher, salon);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alertDialog.dismiss();
                    }
                });

                /*if(appointmentService.addAppointment(appointment)) {
                    Intent intent = new Intent(BookingConfirmActivity.this, StaffMainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }*/

                Intent intent = new Intent(BookingConfirmActivity.this, StaffMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }).start();
    }
}