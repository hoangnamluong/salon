package ou.lhn.salon.view.adapter;

import android.app.Application;
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
import java.util.Locale;

import ou.lhn.salon.R;
import ou.lhn.salon.db.model.Appointment;

public class BookingHistoryAdapter extends ArrayAdapter<Appointment> {
    private ArrayList<Appointment> list;
    private Context context;
    private int layoutId;

    private static class ViewHolder {
        TextView bhiTxtCustomerName,
                bhiTxtCustomPhone,
                bhiTxtBookedDate,
                bhiTxtCost;
    }

    public BookingHistoryAdapter(Context context, int layoutId, ArrayList<Appointment> list) {
        super(context, layoutId, list);
        this.context = context;
        this.layoutId = layoutId;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;

        if(convertView == null || convertView.getTag() == null) {
            convertView = LayoutInflater.from(context).inflate(layoutId, null);
            viewHolder = new ViewHolder();

            viewHolder.bhiTxtCustomerName = convertView.findViewById(R.id.bhiTxtCustomerName);
            viewHolder.bhiTxtCustomPhone = convertView.findViewById(R.id.bhiTxtCustomPhone);
            viewHolder.bhiTxtBookedDate = convertView.findViewById(R.id.bhiTxtBookedDate);
            viewHolder.bhiTxtCost = convertView.findViewById(R.id.bhiTxtCost);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Appointment appointment = list.get(position);

        viewHolder.bhiTxtCustomerName.setText(appointment.getCustomer().getFullName());
        viewHolder.bhiTxtCustomPhone.setText(appointment.getCustomer().getPhone());
        viewHolder.bhiTxtBookedDate.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(appointment.getAppointmentDate()));
        viewHolder.bhiTxtCost.setText(String.format("%d", appointment.getCost()));

        return convertView;
    }
}
