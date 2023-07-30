package ou.lhn.salon.view.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ou.lhn.salon.R;
import ou.lhn.salon.activity.EditSalonActivity;
import ou.lhn.salon.activity.EditStylistActivity;
import ou.lhn.salon.db.model.Stylist;
import ou.lhn.salon.db.service.Stylist_db.StylistService;
import ou.lhn.salon.db.service.Stylist_db.StylistServiceImpl;

public class AdminStylistAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Stylist> listStylist;
    private StylistService stylistService;

    public AdminStylistAdapter(Context context, int layout, ArrayList<Stylist> listStylist) {
        this.context = context;
        this.layout = layout;
        this.listStylist = listStylist;
        this.stylistService = StylistServiceImpl.getInstance(context);
    }

    @Override
    public int getCount() {
        return listStylist.size();
    }

    @Override
    public Object getItem(int i) {
        return listStylist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    static class ViewHolder {
        ImageView imgImage, imgActive;
        ImageButton btnDelete, btnEdit;
        TextView txtStylistId, txtStylistName, txtStylistCusPerDay;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            // Ánh xạ các thành phần trong ViewHolder
            holder.imgImage = view.findViewById(R.id.stylistImgImage);
            holder.imgActive = view.findViewById(R.id.stylistImgActive);
            holder.btnDelete = view.findViewById(R.id.stylistBtnDelete);
            holder.btnEdit = view.findViewById(R.id.stylistBtnEdit);
            holder.txtStylistId = view.findViewById(R.id.stylistTxtID);
            holder.txtStylistName = view.findViewById(R.id.stylistTxtName);
            holder.txtStylistCusPerDay = view.findViewById(R.id.stylistTxtCusPerDay);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // Lấy thông tin từ danh sách dựa vào vị trí i
        Stylist stylist = listStylist.get(i);

        // Thiết lập dữ liệu cho các thành phần trong ViewHolder
        holder.imgActive.setImageResource(stylist.isActive() ? R.drawable.ic_active_management_20 : R.drawable.ic_cross_management_20);
        holder.txtStylistId.setText(String.valueOf(stylist.getId()));
        holder.txtStylistName.setText(stylist.getName());
        holder.txtStylistCusPerDay.setText(String.valueOf(stylist.getCustomerPerDay()));

        byte[] image = stylist.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        holder.imgImage.setImageBitmap(bitmap);

        // Đặt xử lý sự kiện cho nút Edit và Delete nếu cần
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditStylistActivity.class);
                intent.putExtra("stylistId", stylist.getId());
                context.startActivity(intent);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert  =new AlertDialog.Builder(context);
                alert.setMessage("Bạn có muốn xóa stylist " + stylist.getName() +" không?");
                alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                            // Xóa salon từ database
                            stylistService.deleteStylist(stylist.getId());

                            // Xóa salon khỏi danh sách trong adapter
                            listStylist.remove(stylist);

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
