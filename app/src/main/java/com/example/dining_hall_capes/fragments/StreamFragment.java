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

import com.example.dining_hall_capes.DiningHallsAdapter;
import com.example.dining_hall_capes.R;
import com.example.dining_hall_capes.models.DiningHall;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class StreamFragment extends Fragment {

    public static final String TAG = "StreamFragment";

    RecyclerView rvDiningHalls;
    List<DiningHall> diningHalls;
    DiningHallsAdapter diningHallsAdapter;

    public StreamFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        diningHalls = new ArrayList<>();
        diningHallsAdapter = new DiningHallsAdapter(getContext(), diningHalls);
        rvDiningHalls = view.findViewById(R.id.rvDiningHalls);
        rvDiningHalls.setAdapter(diningHallsAdapter);
        rvDiningHalls.setLayoutManager(new LinearLayoutManager(getContext()));

        // TODO: load all dining halls
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
                }

                diningHallsAdapter.addAll(fetchedDiningHalls);
            }
        });
    }
}