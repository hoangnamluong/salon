package ou.lhn.salon.view.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ou.lhn.salon.R;
import ou.lhn.salon.activity.AddUserActivity;
import ou.lhn.salon.activity.EditSalonActivity;
import ou.lhn.salon.activity.SalonManagementActivity;
import ou.lhn.salon.activity.ServiceManagementActivity;
import ou.lhn.salon.activity.StylistManagementActivity;
import ou.lhn.salon.db.model.Salon;
import ou.lhn.salon.db.service.Salon_db.SalonSerivce;
import ou.lhn.salon.db.service.Salon_db.SalonServiceImpl;

public class AdminSalonAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Salon> listSalon;

    private SalonSerivce salonSerivce;

    public AdminSalonAdapter(Context context, int layout, ArrayList<Salon> listSalon) {
        this.context = context;
        this.layout = layout;
        this.listSalon = listSalon;
        this.salonSerivce = SalonServiceImpl.getInstance(context);
    }

    @Override
    public int getCount() {
        return listSalon.size();
    }

    @Override
    public Object getItem(int i) {
        return listSalon.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    static class ViewHolder{
        ImageView imgImage, imgActive;
        ImageButton btnEdit, btnDelete, btnMenu;
        TextView txtId, txtName;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            // Ánh xạ các thành phần trong ViewHolder
            holder.imgImage = view.findViewById(R.id.salonImgImage);
            holder.imgActive = view.findViewById(R.id.salonImgActive);
            holder.btnEdit = view.findViewById(R.id.salonBtnEdit);
            holder.btnDelete = view.findViewById(R.id.salonBtnDelete);
            holder.btnMenu = view.findViewById(R.id.salonBtnMenu);
            holder.txtId = view.findViewById(R.id.salonTxtID);
            holder.txtName = view.findViewById(R.id.salonTxtName);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Salon salon = listSalon.get(i);

//         Thiết lập dữ liệu cho các thành phần trong ViewHolder

        holder.imgActive.setImageResource(salon.isActive() ? R.drawable.ic_active_management_20 : R.drawable.ic_cross_management_20);
        holder.txtId.setText(String.valueOf(salon.getId()));
        holder.txtName.setText(salon.getName());
        byte[] image = salon.getImage();

        if(image != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            holder.imgImage.setImageBitmap(bitmap);
        }

        // Đặt xử lý sự kiện cho nút Edit và Delete nếu cần
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(context, EditSalonActivity.class);
                    intent.putExtra("salonId", salon.getId());
                    context.startActivity(intent);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert  =new AlertDialog.Builder(context);
                alert.setMessage("Bạn có muốn xóa salon " + salon.getName() +" không?");
                alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (salon.getId() == 1){
                            Toast.makeText(context, "Không được xóa salon mặc định", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            // Xóa salon từ database
                            salonSerivce.deleteSalon(salon.getId());

                            // Xóa salon khỏi danh sách trong adapter
                            listSalon.remove(salon);

                            // Cập nhật lại giao diện
                            notifyDataSetChanged();
                        }

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

        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        if(id == R.id.itemScheduleMn){
                            Toast.makeText(context, "Xin lỗi vì chưa hoàn thiện chức năng này!", Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (id == R.id.itemStylistMn) {
                            GoToActivityWithSalonId(StylistManagementActivity.class, salon.getId());
                            return true;
                        } else if (id == R.id.itemServiecMn) {
                            GoToActivityWithSalonId(ServiceManagementActivity.class, salon.getId());
                            return true;
                        }
                        return false;
                    }
                });
                popupMenu.inflate(R.menu.popup_menu_salon_manage);
                popupMenu.show();
            }
        });

        return view;
    }
    private void GoToActivityWithSalonId(Class<?> targetActivityClass, int salonId) {
        Intent intent = new Intent(context, targetActivityClass);
        intent.putExtra("salonId", salonId);
        context.startActivity(intent);
    }
}
