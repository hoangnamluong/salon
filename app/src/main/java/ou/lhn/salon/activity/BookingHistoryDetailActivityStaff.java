package ou.lhn.salon.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.text.ParseException;

import ou.lhn.salon.R;
import ou.lhn.salon.data.Constant;
import ou.lhn.salon.db.model.Appointment;
import ou.lhn.salon.db.model.Salon;
import ou.lhn.salon.db.model.Service;
import ou.lhn.salon.db.model.Stylist;
import ou.lhn.salon.db.service.Salon_db.SalonSerivce;
import ou.lhn.salon.db.service.Salon_db.SalonServiceImpl;
import ou.lhn.salon.db.service.Service_db.ServiceService;
import ou.lhn.salon.db.service.Service_db.ServiceServiceImpl;
import ou.lhn.salon.db.service.Stylist_db.StylistService;
import ou.lhn.salon.db.service.Stylist_db.StylistServiceImpl;
import ou.lhn.salon.db.service.Voucher_db.VoucherService;
import ou.lhn.salon.db.service.Voucher_db.VoucherServiceImpl;
import ou.lhn.salon.util.DateTimeFormat;
import ou.lhn.salon.util.HeaderFragmentUtil;

public class BookingHistoryDetailActivityStaff extends AppCompatActivity {
    private final FragmentManager fragmentManager = getSupportFragmentManager();

    private final ServiceService serviceService = ServiceServiceImpl.getInstance(this);
    private final VoucherService voucherService = VoucherServiceImpl.getInstance(this);
    private final StylistService stylistService = StylistServiceImpl.getInstance(this);
    private final SalonSerivce salonSerivce = SalonServiceImpl.getInstance(this);

    private FrameLayout bhdHeader;
    private TextView bhdTxtSalonName,
            bhdTxtCustomerName,
            bhdTxtInfoAppointTime,
            bhdTxtInfoServiceName,
            bhdTxtInfoServicePrice,
            bhdTxtInfoVoucherPrice,
            bhdTxtInfoTotalCost,
            bhdTxtInfoCustomerName,
            bhdTxtInfoCustomerPhone,
            bhdTxtInfoCustomerEmail,
            bhdTxtInfoStylistName;

    private Appointment appointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history_detail_staff);

        initView();
        inflateHeader();
        receiveData();
        initData();
        attachDataToView();
    }

    private void initView() {
        bhdHeader = findViewById(R.id.bhdHeader);
        bhdTxtSalonName = findViewById(R.id.bhdTxtSalonName);
        bhdTxtCustomerName = findViewById(R.id.bhdTxtCustomerName);
        bhdTxtInfoAppointTime = findViewById(R.id.bhdTxtInfoAppointTime);
        bhdTxtInfoServiceName = findViewById(R.id.bhdTxtInfoServiceName);
        bhdTxtInfoServicePrice = findViewById(R.id.bhdTxtInfoServicePrice);
        bhdTxtInfoVoucherPrice = findViewById(R.id.bhdTxtInfoVoucherPrice);
        bhdTxtInfoTotalCost = findViewById(R.id.bhdTxtInfoTotalCost);
        bhdTxtInfoCustomerName = findViewById(R.id.bhdTxtCustomerName);
        bhdTxtInfoCustomerPhone = findViewById(R.id.bhdTxtInfoCustomerPhone);
        bhdTxtInfoCustomerEmail = findViewById(R.id.bookingConfirmTxtInfoCustomerEmail);
        bhdTxtInfoStylistName = findViewById(R.id.bhdTxtInfoStylistName);
    }

    private void inflateHeader() {
        HeaderFragmentUtil.createHeaderFragment(fragmentManager,"Booking Detail", bhdHeader.getId(), new HeaderFragment());
    }

    private void receiveData() {
        Intent intent = getIntent();
        appointment = (Appointment) intent.getSerializableExtra("appointment");
    }

    private void initData() {
        int salonId = appointment.getSalon().getId();
        int serviceId = appointment.getService().getId();
        int stylistId = appointment.getStylist().getId();
        int voucherId = appointment.getVoucher().getId();

        appointment.setSalon(salonSerivce.getSalonById(salonId));
        appointment.setService(serviceService.getServiceById(serviceId));
        appointment.setStylist(stylistService.getStylistById(stylistId));
        appointment.setVoucher(voucherService.getVoucherById(voucherId));
    }

    private void attachDataToView() {
        long voucherPrice = (appointment.getVoucher().getPercentage() / Constant.PERCENT_100) * appointment.getService().getPrice();

        bhdTxtSalonName.setText(appointment.getSalon().getName().toString());
        bhdTxtCustomerName.setText(appointment.getCustomer().getFullName().toString());
        try {
            bhdTxtInfoAppointTime.setText(DateTimeFormat.convertDateToSqliteDate(appointment.getAppointmentDate()).toString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        bhdTxtInfoServiceName.setText(appointment.getService().getName().toString());
        bhdTxtInfoServicePrice.setText(String.format("%d", appointment.getService().getPrice()));
        bhdTxtInfoVoucherPrice.setText(String.format("%d", voucherPrice));
        bhdTxtInfoTotalCost.setText(String.format("%d", appointment.getCost()));
        bhdTxtInfoCustomerName.setText(appointment.getCustomer().getFullName().toString());
        bhdTxtInfoCustomerPhone.setText(appointment.getCustomer().getPhone());
        bhdTxtInfoCustomerEmail.setText(appointment.getCustomer().getEmail().toString());
        bhdTxtInfoStylistName.setText(appointment.getStylist().getName().toString());
    }
}