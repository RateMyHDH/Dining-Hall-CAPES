package com.example.dining_hall_capes.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dining_hall_capes.DiningHallsAdapter;
import com.example.dining_hall_capes.R;
import com.example.dining_hall_capes.VendorsAdapter;
import com.example.dining_hall_capes.models.DiningHall;
import com.example.dining_hall_capes.models.Vendor;
import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StreamFragment extends Fragment {

    public static final String TAG = "StreamFragment";
    public static final String EXTRA_VENDOR_ID = "id";
    public static final String EXTRA_VENDOR_NAME = "name";

    RecyclerView rvDiningHalls;
    List<DiningHall> diningHalls;
    DiningHallsAdapter diningHallsAdapter;
    HashMap<String, DiningHall> diningHallIndex;
    HashMap<String, Vendor> vendorIndex;

    boolean refreshRatings;

    public StreamFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        refreshRatings = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stream, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toast.makeText(getContext(), "Stream", Toast.LENGTH_SHORT).show();

        diningHalls = new ArrayList<>();
        diningHallsAdapter = new DiningHallsAdapter(getContext(), diningHalls);
        rvDiningHalls = view.findViewById(R.id.rvDiningHalls);
        rvDiningHalls.setAdapter(diningHallsAdapter);
        rvDiningHalls.setLayoutManager(new LinearLayoutManager(getContext()));
        diningHallIndex = new HashMap<>();
        vendorIndex = new HashMap<>();

        queryDiningHalls();
    }

    private void queryDiningHalls() {

        ParseQuery<DiningHall> hallQuery = ParseQuery.getQuery(DiningHall.class);
        hallQuery.addAscendingOrder(DiningHall.KEY_NAME);
        hallQuery.findInBackground(new FindCallback<DiningHall>() {
            @Override
            public void done(List<DiningHall> fetchedDiningHalls, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting dining halls", e);
                    return;
                }

                for (DiningHall hall : fetchedDiningHalls) {
                    Log.i(TAG, "Dining Hall: " + hall.getName());
                    diningHallIndex.put(hall.getObjectId(), hall);
                    hall.vendors = new ArrayList<>();
                    hall.vendorsAdapter = new VendorsAdapter(getContext(), hall.vendors);
                }

                diningHallsAdapter.addAll(fetchedDiningHalls);
                queryVendors();
            }
        });
    }

    private void queryVendors() {

        ParseQuery<Vendor> vendorQuery = ParseQuery.getQuery(Vendor.class);
        vendorQuery.addAscendingOrder(Vendor.KEY_NAME);
        vendorQuery.findInBackground(new FindCallback<Vendor>() {
            @Override
            public void done(List<Vendor> fetchedVendors, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting vendors", e);
                    return;
                }

                for (Vendor v : fetchedVendors) {
                    Log.i(TAG, "Vendor: " + v.getName());

                    DiningHall hall = (DiningHall) v.getDiningHall();
                    if (vendorIndex.containsKey(v.getObjectId())
                            || hall == null
                            || !diningHallIndex.containsKey(hall.getObjectId())) {
                        Log.e(TAG, "Queried stray vendor: " + v.getName());
                    } else {
                        hall = diningHallIndex.get(hall.getObjectId());
                        if (hall != null) {
                            hall.vendors.add(v);
                            Log.i(TAG, "Got vendor " + v.getName() + " for " + hall.getName());
                        }
                        vendorIndex.put(v.getObjectId(), v);

                    }
                }

                queryRatings();
            }
        });
    }

    private void queryRatings() {

        HashMap<String, Object> params = new HashMap<>();
        ParseCloud.callFunctionInBackground("getVendorRatings", params, new FunctionCallback<Map<String, Number>>() {
            @Override
            public void done(Map<String, Number> avgRatings, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error getting ratings", e);
                    return;
                }

                Log.i(TAG, "Got ratings");
                for (String id : avgRatings.keySet()) {
                    // The java.lang.Number class can handle either Integer or Double,
                    // the JS server function can return either.
                    Number r = avgRatings.get(id);
                    Vendor v = vendorIndex.get(id);

                    if (v == null) {
                        Log.e(TAG, "Null vendor in rating");
                        continue;
                    }

                    v.rating = r == null ? 0 : r.floatValue();
                    if (r == null) {
                        Log.e(TAG, "Null rating for " + v.getName());
                    }
                }

                for (DiningHall dh : diningHalls) {
                    float ratingSum = 0;
                    for (Vendor v : dh.vendors) {
                        ratingSum += v.rating;
                    }

                    dh.rating = ratingSum / dh.vendors.size();
                }

                diningHallsAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (refreshRatings)
            queryRatings();
        refreshRatings = true;
    }
}