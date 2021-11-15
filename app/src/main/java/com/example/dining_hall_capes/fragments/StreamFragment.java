package com.example.dining_hall_capes.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dining_hall_capes.DiningHallsAdapter;
import com.example.dining_hall_capes.R;
import com.example.dining_hall_capes.models.DiningHall;

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

        rvDiningHalls = view.findViewById(R.id.rvDiningHalls);

        // TODO: load all dining halls

        diningHallsAdapter = new DiningHallsAdapter(getContext(), diningHalls);
    }
}