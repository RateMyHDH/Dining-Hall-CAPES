package com.example.dining_hall_capes;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dining_hall_capes.fragments.StreamFragment;
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
                Intent i = new Intent(context, PostActivity.class);
                i.putExtra(StreamFragment.EXTRA_VENDOR_ID, vh.vendorID);
                i.putExtra(StreamFragment.EXTRA_VENDOR_NAME, vh.tvVendorName.getText());
                context.startActivity(i);
            }
        });
        return vh;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
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

        String vendorID;    // For passing data to the PostActivity
        TextView tvVendorName;
        TextView tvVendorRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvVendorName = itemView.findViewById(R.id.tvVendorName);
            tvVendorRating = itemView.findViewById(R.id.tvVendorRating);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        public void bind(Vendor vendor) {
            vendorID = vendor.getObjectId();
            tvVendorName.setText(vendor.getName());
            int rating = (int) (Math.random() * 5 + 1);
            tvVendorRating.setText(String.format("%.1f", rating));
            if (rating < 2f) {
                tvVendorRating.setTextColor(context.getColor(R.color.red));
            } else if (rating < 3f) {
                tvVendorRating.setTextColor(context.getColor(R.color.orange));
            } else if (rating < 4f) {
                tvVendorRating.setTextColor(context.getColor(R.color.yellow));
            } else {
                tvVendorRating.setTextColor(context.getColor(R.color.lime));
            }
        }
    }
}
