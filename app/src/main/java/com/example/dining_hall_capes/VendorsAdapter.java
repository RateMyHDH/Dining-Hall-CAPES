package com.example.dining_hall_capes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dining_hall_capes.fragments.StreamFragment;
import com.example.dining_hall_capes.models.Vendor;
import com.example.dining_hall_capes.models.VendorRating;

import java.util.List;
import java.util.Locale;

public class VendorsAdapter extends RecyclerView.Adapter<VendorsAdapter.ViewHolder> {

    public static final String TAG = "VendorsAdapter";

    private Context context;
    private List<Vendor> vendors;

    public VendorsAdapter(Context context, List<Vendor> vendors) {
        this.context = context;
        this.vendors = vendors;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
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

            if (vendor.rating == VendorRating.NULL_RATING) {
                tvVendorRating.setText("");
                return;
            }
            tvVendorRating.setText(String.format(Locale.US, "%.1f", vendor.rating));
            if (vendor.rating < 2f) {
                tvVendorRating.setBackground(AppCompatResources.getDrawable(context, R.drawable.rounded_corners_rating_red));
            } else if (vendor.rating < 3f) {
                tvVendorRating.setBackground(AppCompatResources.getDrawable(context, R.drawable.rounded_corners_rating_orange));
            } else if (vendor.rating < 4f) {
                tvVendorRating.setBackground(AppCompatResources.getDrawable(context, R.drawable.rounded_corners_rating_yellow));
            } else {
                tvVendorRating.setBackground(AppCompatResources.getDrawable(context, R.drawable.rounded_corners_rating_lime));
            }
        }
    }
}
