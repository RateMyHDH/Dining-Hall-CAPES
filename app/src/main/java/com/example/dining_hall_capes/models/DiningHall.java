package com.example.dining_hall_capes.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.List;

@ParseClassName("DiningHall")
public class DiningHall extends ParseObject {

    public static final String KEY_RATING = "rating";
    public static final String KEY_VENDORS = "vendors";

    public double getRating() {
        return getDouble(KEY_RATING);
    }

    public void setRating(double rating) {
        put(KEY_RATING, rating);
    }

    public List<Vendor> getVendors() {
        return getList(KEY_VENDORS);
    }

    public void setVendors(List<Vendor> vendors) {
        put(KEY_VENDORS, vendors);
    }
}
