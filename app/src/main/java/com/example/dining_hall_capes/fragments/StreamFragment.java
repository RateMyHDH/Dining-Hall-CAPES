package com.example.dining_hall_capes.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dining_hall_capes.adapters.DiningHallsAdapter;
import com.example.dining_hall_capes.R;
import com.example.dining_hall_capes.adapters.VendorsAdapter;
import com.example.dining_hall_capes.models.DiningHall;
import com.example.dining_hall_capes.models.Org;
import com.example.dining_hall_capes.models.Vendor;
import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseQuery;

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
    SwipeRefreshLayout swipeContainer;

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

        // Toast.makeText(getContext(), "Stream", Toast.LENGTH_SHORT).show();

        diningHalls = new ArrayList<>();
        diningHallsAdapter = new DiningHallsAdapter(getContext(), diningHalls);
        rvDiningHalls = view.findViewById(R.id.rvDiningHalls);
        rvDiningHalls.setAdapter(diningHallsAdapter);
        rvDiningHalls.setLayoutManager(new LinearLayoutManager(getContext()));
        diningHallIndex = new HashMap<>();
        vendorIndex = new HashMap<>();
        swipeContainer = view.findViewById(R.id.streamSwipeContainer);
        swipeContainer.setOnRefreshListener(() -> {
            Log.i(TAG, "Fetching new data");
            queryDiningHalls();
        });
        swipeContainer.setRefreshing(true);

        queryDiningHalls();
    }

    private void queryDiningHalls() {

        ParseQuery<DiningHall> hallQuery = ParseQuery.getQuery(DiningHall.class);
        hallQuery.addAscendingOrder(DiningHall.KEY_NAME);
        hallQuery.findInBackground((fetchedDiningHalls, e) -> {
            if (e != null) {
                Log.e(TAG, "Issue with getting dining halls", e);
                swipeContainer.setRefreshing(false);
                return;
            }

            for (DiningHall hall : fetchedDiningHalls) {
                Log.i(TAG, "Dining Hall: " + hall.getName());
                diningHallIndex.put(hall.getObjectId(), hall);
                hall.vendors = new ArrayList<>();
                hall.vendorsAdapter = new VendorsAdapter(getContext(), hall.vendors);
            }

            diningHalls.clear();
            diningHalls.addAll(fetchedDiningHalls);
            queryVendors();
        });
    }

    private void queryVendors() {

        ParseQuery<Vendor> vendorQuery = ParseQuery.getQuery(Vendor.class);
        vendorQuery.addAscendingOrder(Vendor.KEY_NAME);
        vendorQuery.findInBackground((fetchedVendors, e) -> {
            if (e != null) {
                Log.e(TAG, "Issue with getting vendors", e);
                swipeContainer.setRefreshing(false);
                return;
            }

            for (Vendor v : fetchedVendors) {
                Log.i(TAG, "Vendor: " + v.getName());

                DiningHall hall = (DiningHall) v.getDiningHall();
                if (hall == null || !diningHallIndex.containsKey(hall.getObjectId())) {
                    Log.e(TAG, "Queried stray vendor: " + v.getName());
                    continue;
                }
                DiningHall indexedHall = diningHallIndex.get(hall.getObjectId());
                if (indexedHall == null) {
                    Log.e(TAG, "Queried vendor " + v.getName() + " with wrong hall");
                    continue;
                }

                indexedHall.vendors.add(v);
                vendorIndex.put(v.getObjectId(), v);
                Log.i(TAG, "Got vendor " + v.getName() + " for " + indexedHall.getName());
            }

            queryRatings();
        });
    }

    private void queryRatings() {

        HashMap<String, Object> params = new HashMap<>();
        ParseCloud.callFunctionInBackground("getVendorRatings", params, (FunctionCallback<Map<String, Number>>) (avgRatings, e) -> {
            if (e != null) {
                Log.e(TAG, "Error getting ratings", e);
                swipeContainer.setRefreshing(false);
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

            diningHallsAdapter.notifyDataSetChanged();
            swipeContainer.setRefreshing(false);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (refreshRatings)
            queryRatings();
        else
            refreshRatings = true;
    }
}