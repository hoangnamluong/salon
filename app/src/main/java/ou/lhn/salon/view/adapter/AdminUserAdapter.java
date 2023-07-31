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
import ou.lhn.salon.activity.EditUserActivity;
import ou.lhn.salon.db.model.User;
import ou.lhn.salon.db.service.User_db.UserService;
import ou.lhn.salon.db.service.User_db.UserServiceImpl;

public class AdminUserAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<User> listUser;
    private UserService userService;

    public AdminUserAdapter(Context context, int layout, ArrayList<User> listUser) {
        this.context = context;
        this.layout = layout;
        this.listUser = listUser;
        this.userService = UserServiceImpl.getInstance(context);
    }

    @Override
    public int getCount() {
        return listUser.size();
    }

    @Override
    public Object getItem(int i) {
        return listUser.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    static class ViewHolder {
        TextView txtUserId, txtUsername, txtFullname;
        ImageView imgActive, imgAvatar;
        ImageButton btnEdit, btnDelete;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtUserId = (TextView) view.findViewById(R.id.userTxtID);
            holder.txtUsername = (TextView) view.findViewById(R.id.userTxtUsername);
            holder.txtFullname = (TextView) view.findViewById(R.id.userTxtFullname);
            holder.imgActive = (ImageView) view.findViewById(R.id.userImgActive);
            holder.imgAvatar = (ImageView) view.findViewById(R.id.userImgAvatar);
            holder.btnEdit = (ImageButton) view.findViewById(R.id.userBtnEdit);
            holder.btnDelete = (ImageButton) view.findViewById(R.id.userBtnDelete);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Lấy thông tin người dùng tại vị trí i trong danh sách
        User user = listUser.get(i);

        // Đặt thông tin người dùng vào các thành phần tương ứng
        holder.txtUserId.setText(String.valueOf(user.getId()));
        holder.txtUsername.setText(user.getUsername());
        holder.txtFullname.setText(user.getFullName());

        byte[] image = user.getAvatar();

        if(image != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            holder.imgAvatar.setImageBitmap(bitmap);
        }

        holder.imgActive.setImageResource(user.isActive() ? R.drawable.ic_active_management_20 : R.drawable.ic_cross_management_20);

        // Đặt xử lý sự kiện cho nút Edit và Delete nếu cần
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditUserActivity.class);
                intent.putExtra("userId", user.getId());
                context.startActivity(intent);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setMessage("Bạn có muốn xóa user " + user.getFullName() + " không?");
                alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try{
                            // Xóa salon từ database
                            userService.deleteUser(user.getId());

                            // Xóa salon khỏi danh sách trong adapter
                            listUser.remove(user);

                            // Cập nhật lại giao diện
                            notifyDataSetChanged();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                            userService.softDeleteUser(user.getId());
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

        return view;
    }
}
