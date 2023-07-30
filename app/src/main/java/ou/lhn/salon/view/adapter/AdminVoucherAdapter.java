package ou.lhn.salon.view.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import ou.lhn.salon.R;
import ou.lhn.salon.activity.EditUserActivity;
import ou.lhn.salon.activity.EditVoucherActivity;
import ou.lhn.salon.db.model.Voucher;
import ou.lhn.salon.db.service.Voucher_db.VoucherService;
import ou.lhn.salon.db.service.Voucher_db.VoucherServiceImpl;

public class AdminVoucherAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Voucher> vouchersList;
    private VoucherService voucherService;

    public AdminVoucherAdapter(Context context, int layout, ArrayList<Voucher> vouchersList) {
        this.context = context;
        this.layout = layout;
        this.vouchersList = vouchersList;
        this.voucherService = VoucherServiceImpl.getInstance(context);
    }

    @Override
    public int getCount() {
        return vouchersList.size();
    }

    @Override
    public Object getItem(int i) {
        return vouchersList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    static class ViewHolder {
        TextView txtId, txtCode, txtPer, txtExpireDay;
        ImageView imgActive;
        ImageButton btnDelete, btnEdit;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            // Ánh xạ các thành phần trong ViewHolder
            holder.txtId = view.findViewById(R.id.voucherTxtID);
            holder.txtCode = view.findViewById(R.id.voucherTxtCode);
            holder.txtPer = view.findViewById(R.id.voucherTxtPer);
            holder.txtExpireDay = view.findViewById(R.id.voucherTxtExpireDay);
            holder.imgActive = view.findViewById(R.id.voucherImgActive);
            holder.btnDelete = view.findViewById(R.id.voucherBtnDelete);
            holder.btnEdit = view.findViewById(R.id.voucherBtnEdit);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // Lấy thông tin từ danh sách dựa vào vị trí i
        Voucher voucher = vouchersList.get(i);

        // Thiết lập dữ liệu cho các thành phần trong ViewHolder
        holder.txtId.setText(String.valueOf(voucher.getId()));
        holder.txtCode.setText(voucher.getCode());
        holder.txtPer.setText(voucher.getPercentage() + "");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String expireDateFormatted = sdf.format(voucher.getExpiredDate());
        holder.txtExpireDay.setText(expireDateFormatted);
        holder.imgActive.setImageResource(voucher.isActive() ? R.drawable.ic_active_management_20 : R.drawable.ic_cross_management_20);

        // Đặt xử lý sự kiện cho nút Edit và Delete
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditVoucherActivity.class);
                intent.putExtra("voucherId", voucher.getId());
                context.startActivity(intent);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setMessage("Bạn có muốn xóa voucher " + voucher.getCode() + " không?");
                alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                            // Xóa voucher từ database
                            voucherService.deleteVoucher(voucher.getId());

                            // Xóa salon khỏi danh sách trong adapter
                            vouchersList.remove(voucher);

                            // Cập nhật lại giao diện
                            notifyDataSetChanged();
                    }
                });

                alert.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                alert.show();
            }
        });

        return view;
    }

}
