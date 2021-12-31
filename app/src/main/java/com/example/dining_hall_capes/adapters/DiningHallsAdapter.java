package com.example.dining_hall_capes.adapters;

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

import com.example.dining_hall_capes.R;
import com.example.dining_hall_capes.models.DiningHall;

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
        RecyclerView rvVendors;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvDiningHallName);
            rvVendors = itemView.findViewById(R.id.rvVendors);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        public void bind(DiningHall hall) {
            tvTitle.setText(hall.getName());
            rvVendors.setAdapter(hall.vendorsAdapter);
            rvVendors.setLayoutManager(new LinearLayoutManager(context));
        }
    }
}
