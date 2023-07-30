package ou.lhn.salon.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

import ou.lhn.salon.R;
import ou.lhn.salon.db.model.Stylist;
import ou.lhn.salon.db.service.Stylist_db.StylistService;
import ou.lhn.salon.db.service.Stylist_db.StylistServiceImpl;
import ou.lhn.salon.util.BytesBitmapConverter;

public class StylistSelectAdapter extends ArrayAdapter<Stylist> {
    private Context context;
    private ArrayList<Stylist> list;
    private int layoutId;

    private StylistService stylistService;

    private static class ViewHolder{
        ShapeableImageView stylistImgAvatar;
        ImageView stylistImgAvailable;
        TextView stylistTxtName;
    }

    public StylistSelectAdapter(Context context, int layoutId, ArrayList<Stylist> list) {
        super(context, layoutId, list);
        this.context = context;
        this.list = list;
        this.layoutId = layoutId;
        this.stylistService = StylistServiceImpl.getInstance(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;

        if(viewHolder == null || convertView.getTag() == null) {
            convertView = LayoutInflater.from(context).inflate(layoutId, null);
            viewHolder = new ViewHolder();

            viewHolder.stylistImgAvatar = convertView.findViewById(R.id.stylistImgAvatar);
            viewHolder.stylistImgAvailable = convertView.findViewById(R.id.stylistImgAvailable);
            viewHolder.stylistTxtName = convertView.findViewById(R.id.stylistTxtName);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Stylist stylist = list.get(position);

        int stylistAvailable = stylistService.countCustomerToday(stylist.getId());


        viewHolder.stylistTxtName.setText(stylist.getName().toString());

        if(stylistAvailable == stylist.getCustomerPerDay()) {
            viewHolder.stylistImgAvailable.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_block_red_24dp));
        } else {
            viewHolder.stylistImgAvailable.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_check_circle_outline_green_24dp));
        }

        return convertView;
    }
}
