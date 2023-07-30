package ou.lhn.salon.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import ou.lhn.salon.R;
import ou.lhn.salon.db.model.Appointment;
import ou.lhn.salon.db.model.Service;

public class BookingServiceAdapter extends ArrayAdapter<Service> {
    private ArrayList<Service> list;
    private Context context;
    private int layoutId;

    private static class ViewHolder {
        TextView serviceSpTxtName, serviceSpTxtPrice;
    }

    public BookingServiceAdapter(Context context, int layoutId, ArrayList<Service> list) {
        super(context, layoutId, list);
        this.context = context;
        this.layoutId = layoutId;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(convertView, position, layoutId);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(convertView, position, R.layout.spinner_service_item2);
    }

    private View initView(View convertView, int position, int layout) {
        ViewHolder viewHolder = null;

        if(convertView == null || convertView.getTag() == null) {
            convertView = LayoutInflater.from(context).inflate(layout, null);
            viewHolder = new ViewHolder();

            viewHolder.serviceSpTxtName = convertView.findViewById(R.id.serviceSpTxtName);
            viewHolder.serviceSpTxtPrice = convertView.findViewById(R.id.serviceSpTxtPrice);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Service service = list.get(position);

        viewHolder.serviceSpTxtName.setText(service.getName().toString());
        viewHolder.serviceSpTxtPrice.setText(String.format("%d", service.getPrice()));

        return convertView;
    }
}
