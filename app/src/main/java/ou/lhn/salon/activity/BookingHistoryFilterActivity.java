package ou.lhn.salon.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import ou.lhn.salon.R;
import ou.lhn.salon.data.Constant;
import ou.lhn.salon.db.model.Salon;
import ou.lhn.salon.db.service.Salon_db.SalonSerivce;
import ou.lhn.salon.db.service.Salon_db.SalonServiceImpl;
import ou.lhn.salon.util.DateTimeFormat;

public class BookingHistoryFilterActivity extends AppCompatActivity {
    private SalonSerivce salonSerivce = SalonServiceImpl.getInstance(this);

    private Spinner bookingFilterSpDate, bookingFilterSpMonth, bookingFilterSpYear;
    private AppCompatButton bookingFilterBtnConfirm;

    private ArrayAdapter<String> dateAdapter;
    private ArrayAdapter<String> monthAdapter;
    private ArrayAdapter<String> yearAdapter;
    private ArrayList<String> dateList;
    private ArrayList<String> yearList;

    private int selectedYearPos, selectedMonthPos, selectedDatePos;
    private Salon salon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history_filter);

        initView();
        initData();
        initSpinner();
        initListener();
    }

    private void initView() {
        bookingFilterSpDate = findViewById(R.id.bookingFilterSpDate);
        bookingFilterSpMonth = findViewById(R.id.bookingFilterSpMonth);
        bookingFilterSpYear = findViewById(R.id.bookingFilterSpYear);
        bookingFilterBtnConfirm = findViewById(R.id.bookingFilterBtnConfirm);

        bookingFilterSpDate.setEnabled(false);
        bookingFilterSpMonth.setEnabled(false);
    }

    private void initData() {
        salon = salonSerivce.getSalonById(1);
    }

    private void initSpinner() {
        Calendar calendar = DateTimeFormat.convertDateToCalendar(salon.getCreatedAt());
        Calendar today = Calendar.getInstance();

        yearList = new ArrayList<>();
        yearList.add("Select Year");
        yearList.add("2024");

        dateList = new ArrayList<>(Arrays.asList(Constant.DAY_31));

        dateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dateList);
        bookingFilterSpDate.setAdapter(dateAdapter);

        monthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Constant.Months);
        bookingFilterSpMonth.setAdapter(monthAdapter);

        for(int i = today.get(Calendar.YEAR) ; i >= calendar.get(Calendar.YEAR); i--) {
            yearList.add(String.format("%d", i));
        }

        yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, yearList);
        bookingFilterSpYear.setAdapter(yearAdapter);
    }

    private void initListener() {
        bookingFilterSpDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               selectDate(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        bookingFilterSpMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectMonth(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        bookingFilterSpYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectYear(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        bookingFilterBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmFilter();
            }
        });
    }

    private void selectYear(int position) {
        if(position != 0) {
            bookingFilterSpMonth.setEnabled(true);

            if(selectedMonthPos == 0) {
                bookingFilterSpDate.setEnabled(false);
            } else {
                bookingFilterSpDate.setEnabled(true);
            }

        } else {
            bookingFilterSpMonth.setEnabled(false);
            bookingFilterSpDate.setEnabled(false);
        }

        selectedYearPos = position;
    }

    private void selectMonth(int position) {
        selectedMonthPos = position;

        if(position != 0) {
            bookingFilterSpDate.setEnabled(true);
        } else {
            bookingFilterSpDate.setEnabled(false);
        }

        if(position == 2) {
            if(isLeapYear(Integer.parseInt(yearList.get(selectedYearPos)))) {
                dateList.clear();
                dateList.addAll(new ArrayList<>(Arrays.asList(Constant.DAY_29)));
            } else {
                dateList.clear();
                dateList.addAll(new ArrayList<>(Arrays.asList(Constant.DAY_28)));
            }

            return;
        }

        if((position % 2 != 0 && position <= 7)
                || (position % 2 == 0 && position > 7)) {
            dateList.clear();
            dateList.addAll(new ArrayList<>(Arrays.asList(Constant.DAY_31)));
        } else {
            dateList.clear();
            dateList.addAll(new ArrayList<>(Arrays.asList(Constant.DAY_30)));
        }

        dateAdapter.notifyDataSetChanged();
    }

    private void selectDate(int position) {
        selectedDatePos = position;
    }

    private void confirmFilter() {
        Intent intent = new Intent();
        setResult(RESULT_OK);

        if(selectedYearPos == 0)
            return;

        intent.putExtra("year", Integer.parseInt(yearList.get(selectedYearPos)));

        if(selectedMonthPos != 0) {
            intent.putExtra("month", Integer.parseInt(Constant.Months[selectedMonthPos]));

            if(selectedDatePos != 0) {
                intent.putExtra("date", Integer.parseInt(dateList.get(selectedDatePos)));
            }
        }

        finish();
    }

    private boolean isLeapYear(int i) {
        if(i % 4 == 0) {
            if(i % 100 != 0)
                return true;
            else if(i % 400 == 0)
                return true;
        }

        return false;
    }
}