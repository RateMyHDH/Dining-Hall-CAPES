package com.example.dining_hall_capes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
        RatingBar rbDiningHall;
        List<Vendor> vendors;
        RecyclerView rvVendors;
        VendorsAdapter vendorsAdapter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvDiningHallName);
            rbDiningHall= itemView.findViewById(R.id.rbDiningHall);
            vendors = new ArrayList<>();
            rvVendors = itemView.findViewById(R.id.rvVendors);
            vendorsAdapter = new VendorsAdapter(itemView.getContext(), vendors);
        }

        public void bind(DiningHall hall) {
            tvTitle.setText(hall.getName());
            queryVendors(vendorsAdapter, hall);
            // TODO: add ratings
        }
    }

    private void queryVendors(VendorsAdapter vendorsAdapter, DiningHall hall) {

        ParseQuery<Vendor> vendorQuery = ParseQuery.getQuery(Vendor.class);

        vendorQuery.whereEqualTo(Vendor.KEY_DINING_HALL, hall.getObjectId());
        vendorQuery.addAscendingOrder(Vendor.KEY_NAME);

        vendorQuery.findInBackground(new FindCallback<Vendor>() {
            @Override
            public void done(List<Vendor> fetchedVendors, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting vendors for " + hall.getName(), e);
                    return;
                }

                for (Vendor v : fetchedVendors) {
                    Log.i(TAG, "Vendor: " + v.getName());
                }

                vendorsAdapter.addAll(fetchedVendors);
            }
        });
    }
}
