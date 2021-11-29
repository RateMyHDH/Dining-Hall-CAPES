package com.example.dining_hall_capes;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dining_hall_capes.models.DiningHall;
import com.example.dining_hall_capes.models.VendorRating;

import java.util.List;
import java.util.Locale;

public class DiningHallsAdapter extends RecyclerView.Adapter<DiningHallsAdapter.ViewHolder> {

    public static final String TAG = "DiningHallsAdapter";

    private Context context;
    private List<DiningHall> diningHalls;

    public DiningHallsAdapter(Context context, List<DiningHall> diningHalls) {
        this.context = context;
        this.diningHalls = diningHalls;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dining_hall, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(diningHalls.get(position));
    }

    @Override
    public int getItemCount() {
        return diningHalls.size();
    }

    public void clear() {
        diningHalls.clear();
    }

    public void addAll(List<DiningHall> newHalls) {
        diningHalls.addAll(newHalls);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvDiningHallRating;
        RecyclerView rvVendors;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvDiningHallName);
            tvDiningHallRating = itemView.findViewById(R.id.tvDiningHallRating);
            rvVendors = itemView.findViewById(R.id.rvVendors);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        public void bind(DiningHall hall) {
            tvTitle.setText(hall.getName());
            rvVendors.setAdapter(hall.vendorsAdapter);
            rvVendors.setLayoutManager(new LinearLayoutManager(context));

            if (hall.rating == VendorRating.NULL_RATING) {
                tvDiningHallRating.setText("");
                return;
            }
            tvDiningHallRating.setText(String.format(Locale.US, "%.1f", hall.rating));
            if (hall.rating < 2f) {
                tvDiningHallRating.setTextColor(context.getColor(R.color.red));
            } else if (hall.rating < 3f) {
                tvDiningHallRating.setTextColor(context.getColor(R.color.orange));
            } else if (hall.rating < 4f) {
                tvDiningHallRating.setTextColor(context.getColor(R.color.yellow));
            } else {
                tvDiningHallRating.setTextColor(context.getColor(R.color.lime));
            }
        }
    }
}
