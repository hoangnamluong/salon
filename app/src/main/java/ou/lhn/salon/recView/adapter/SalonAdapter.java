package ou.lhn.salon.recView.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

import ou.lhn.salon.R;
import ou.lhn.salon.db.model.Salon;
import ou.lhn.salon.recView.impl.RecyclerViewInterface;

public class SalonAdapter extends RecyclerView.Adapter<SalonAdapter.ViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;

    private Context context;
    private ArrayList<Salon> salonModels;

    public SalonAdapter(Context context, ArrayList<Salon> salonModels, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.salonModels = salonModels;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public SalonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_salon, parent, false);

        return new SalonAdapter.ViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull SalonAdapter.ViewHolder holder, int position) {
        holder.salonName.setText(salonModels.get(position).getName());
        holder.salonAddress.setText(salonModels.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return salonModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ShapeableImageView salonImage;
        private TextView salonName, salonAddress;

        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            salonImage = itemView.findViewById(R.id.salonImage);
            salonName = itemView.findViewById(R.id.salonName);
            salonAddress = itemView.findViewById(R.id.salonAddress);

            itemView.setOnClickListener((v) -> {
                if(recyclerViewInterface != null) {
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onItemClick(pos);
                    }
                }
            });
        }
    }
}
