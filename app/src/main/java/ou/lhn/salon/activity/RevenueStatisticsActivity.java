package ou.lhn.salon.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ou.lhn.salon.R;
import ou.lhn.salon.db.model.RevenueStats;
import ou.lhn.salon.db.service.Stats.StatService;
import ou.lhn.salon.db.service.Stats.StatServiceImpl;

public class RevenueStatisticsActivity extends AppCompatActivity {

    private EditText edtMonth, edtYear;
    private Button btnStat;
    private ImageButton btnBack;
    private TableLayout statTableLayout;
    ArrayList<RevenueStats> revenueStatsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue_statistics);

        edtMonth = findViewById(R.id.edtMonth);
        edtYear = findViewById(R.id.edtYear);
        btnStat = findViewById(R.id.btnStat);
        btnBack = findViewById(R.id.statBtnBack);
        statTableLayout = findViewById(R.id.statTableLayout);

        btnStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int month = Integer.parseInt(edtMonth.getText().toString());
                    int year = Integer.parseInt(edtYear.getText().toString());
                    if (month < 1 || month > 12) {
                        Toast.makeText(RevenueStatisticsActivity.this, "Tháng không hợp lệ!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (year < 1945 || year > 2099) {
                        Toast.makeText(RevenueStatisticsActivity.this, "Mời nhập lại năm!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    revenueStatsList = doRevenueStats(month, year);
                    displayStats(revenueStatsList);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    Toast.makeText(RevenueStatisticsActivity.this, "Không được để trống tháng hoặc năm khi thống kê!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private ArrayList<RevenueStats> doRevenueStats(int month, int year) {
        StatService statService = StatServiceImpl.getInstance(this);
        return statService.getTop5SalonRevenue(month, year);
    }

    // Hiển thị kết quả thống kê lên TableLayout
    private void displayStats(ArrayList<RevenueStats> revenueStatsList) {
        // Xóa dữ liệu hiện tại của bảng trước khi hiển thị kết quả mới
        statTableLayout.removeAllViews();

        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                1.0f
        );

        // Tạo hàng tiêu đề cho bảng
        TableRow headerRow = new TableRow(this);
        TextView tvIndex = new TextView(this); // TextView cho cột số thứ tự
        TextView tvSalonName = new TextView(this);
        TextView tvAppointmentCount = new TextView(this);
        TextView tvTotalRevenue = new TextView(this);

        tvIndex.setText("STT");
        tvSalonName.setText("Tên Salon");
        tvAppointmentCount.setText("Số lượng đơn hàng");
        tvTotalRevenue.setText("Doanh thu");

        tvIndex.setLayoutParams(params);
        tvSalonName.setLayoutParams(params);
        tvAppointmentCount.setLayoutParams(params);
        tvTotalRevenue.setLayoutParams(params);

        headerRow.addView(tvIndex);
        headerRow.addView(tvSalonName);
        headerRow.addView(tvAppointmentCount);
        headerRow.addView(tvTotalRevenue);

        statTableLayout.addView(headerRow);

        // Duyệt qua danh sách thông tin doanh thu và hiển thị lên bảng
        int index = 1;
        for (RevenueStats stats : revenueStatsList) {
            TableRow dataRow = new TableRow(this);
            TextView tvIndexData = new TextView(this);
            TextView tvName = new TextView(this);
            TextView tvCount = new TextView(this);
            TextView tvRevenue = new TextView(this);

            tvIndexData.setText(String.valueOf(index));
            tvName.setText(stats.getSalonName());
            tvCount.setText(String.valueOf(stats.getCountAppointment()));
            tvRevenue.setText(String.valueOf(stats.getTotal()));

            tvIndexData.setLayoutParams(params);
            tvName.setLayoutParams(params);
            tvCount.setLayoutParams(params);
            tvRevenue.setLayoutParams(params);

            dataRow.addView(tvIndexData);
            dataRow.addView(tvName);
            dataRow.addView(tvCount);
            dataRow.addView(tvRevenue);

            statTableLayout.addView(dataRow);

            index++;
        }
    }

}
