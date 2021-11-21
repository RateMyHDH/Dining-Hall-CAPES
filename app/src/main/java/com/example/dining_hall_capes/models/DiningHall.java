package com.example.dining_hall_capes.models;

import com.example.dining_hall_capes.VendorsAdapter;
import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.List;

@ParseClassName("DiningHall")
public class DiningHall extends ParseObject {

    public static final String KEY_NAME = "name";

    public String getName() {
        return getString(KEY_NAME);
    }

    public void setName(String name) {
        put(KEY_NAME, name);
    }

    // The following vars are used to assist with local data caching
    public List<Vendor> vendors;
    public VendorsAdapter vendorsAdapter;
    public float rating = 0f;
}
