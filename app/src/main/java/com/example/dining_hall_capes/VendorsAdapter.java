package com.example.dining_hall_capes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dining_hall_capes.models.Vendor;

import java.util.List;

public class VendorsAdapter extends RecyclerView.Adapter<VendorsAdapter.ViewHolder> {

    private Context context;
    private List<Vendor> vendors;

    public VendorsAdapter(Context context, List<Vendor> vendors) {
        this.context = context;
        this.vendors = vendors;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_vendor, parent, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), PostActivity.class);
                i.putExtra("id", vh.vendorID);
                v.getContext().startActivity(i);
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(vendors.get(position));
    }

    @Override
    public int getItemCount() {
        return vendors.size();
    }

    public void clear() {
        vendors.clear();
    }

    public void addAll(List<Vendor> newVendors) {
        vendors.addAll(newVendors);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        String vendorID;
        TextView tvVendorName;
        TextView tvVendorRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvVendorName = itemView.findViewById(R.id.tvVendorName);
            tvVendorRating = itemView.findViewById(R.id.tvVendorRating);
        }

        public void bind(Vendor vendor) {
            vendorID = vendor.getObjectId();
            tvVendorName.setText(vendor.getName());
            tvVendorRating.setText(String.format("%.1f", vendor.rating));
            int color;
            if (vendor.rating < 2f) {
                color = R.color.red;
            } else if (vendor.rating < 3f) {
                color = R.color.orange;
            } else if (vendor.rating < 4f) {
                color = R.color.yellow;
            } else {
                color = R.color.lime;
            }
            tvVendorRating.setTextColor(color);
        }
    }
}
