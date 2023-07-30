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
import android.widget.TextView;

import java.util.ArrayList;

import ou.lhn.salon.R;
import ou.lhn.salon.activity.EditServiceActivity;
import ou.lhn.salon.activity.EditStylistActivity;
import ou.lhn.salon.db.model.Service;
import ou.lhn.salon.db.service.Service_db.ServiceService;
import ou.lhn.salon.db.service.Service_db.ServiceServiceImpl;

public class AdminServiceAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ServiceService serviceService;
    private ArrayList<Service> servicesList;

    public AdminServiceAdapter(Context context, int layout, ArrayList<Service> servicesList) {
        this.context = context;
        this.layout = layout;
        this.servicesList = servicesList;
        this.serviceService = ServiceServiceImpl.getInstance(context);
    }


    @Override
    public int getCount() {
        return servicesList.size();
    }

    @Override
    public Object getItem(int i) {
        return servicesList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    static class ViewHolder {
        TextView txtId, txtName, txtPrice;
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
            holder.txtId = view.findViewById(R.id.serviceTxtID);
            holder.txtName = view.findViewById(R.id.serviceTxtName);
            holder.txtPrice = view.findViewById(R.id.serviceTxtPrice);
            holder.btnDelete = view.findViewById(R.id.serviceBtnDelete);
            holder.btnEdit = view.findViewById(R.id.serviceBtnEdit);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // Lấy thông tin từ danh sách dựa vào vị trí i
        Service service = servicesList.get(i);

        // Thiết lập dữ liệu cho các thành phần trong ViewHolder
        holder.txtId.setText(String.valueOf(service.getId()));
        holder.txtName.setText(service.getName());
        holder.txtPrice.setText(String.valueOf(service.getPrice()));

        // Đặt xử lý sự kiện cho nút Edit và Delete nếu cần
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditServiceActivity.class);
                intent.putExtra("serviceId", service.getId());
                context.startActivity(intent);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert  =new AlertDialog.Builder(context);
                alert.setMessage("Bạn có muốn xóa service " + service.getName() +" không?");
                alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Xóa service từ database
                        serviceService.deleteService(service.getId());

                        // Xóa service khỏi danh sách trong adapter
                        servicesList.remove(servicesList.remove(service));

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
