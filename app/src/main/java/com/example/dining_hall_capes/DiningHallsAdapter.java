package com.example.dining_hall_capes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dining_hall_capes.models.DiningHall;
import com.example.dining_hall_capes.models.Vendor;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

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

        public void bind(DiningHall hall) {
            tvTitle.setText(hall.getName());
            tvDiningHallRating.setText(String.format("%.1f", hall.rating));
            int color;
            if (hall.rating < 4f) {
                color = R.color.red;
            } else if (hall.rating < 6f) {
                color = R.color.orange;
            } else if (hall.rating < 8f) {
                color = R.color.yellow;
            } else {
                color = R.color.lime;
            }
            tvDiningHallRating.setTextColor(color);
            rvVendors.setAdapter(hall.vendorsAdapter);
            rvVendors.setLayoutManager(new LinearLayoutManager(context));
        }
    }
}
