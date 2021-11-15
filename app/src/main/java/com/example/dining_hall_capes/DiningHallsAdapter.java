package com.example.dining_hall_capes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dining_hall_capes.models.DiningHall;
import com.example.dining_hall_capes.models.Vendor;

import java.util.List;

public class DiningHallsAdapter extends RecyclerView.Adapter<DiningHallsAdapter.ViewHolder> {

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

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        RatingBar ratingBar;
        List<Vendor> vendors;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(DiningHall hall) {
            tvTitle.setText(hall.getName());

            // TODO: load vendors
        }
    }
}
