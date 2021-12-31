package com.example.dining_hall_capes.models;

import com.example.dining_hall_capes.adapters.VendorsAdapter;
import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.List;

@ParseClassName("DiningHall")
public class DiningHall extends ParseObject {

    public static final String KEY_NAME = "name";
    public static final String KEY_ORG = "org";

    public String getName() {
        return getString(KEY_NAME);
    }

    public void setName(String name) {
        put(KEY_NAME, name);
    }

    public ParseObject getOrg() {
        return getParseObject(KEY_ORG);
    }

    public void setOrg(Org org) {
        put(KEY_ORG, org);
    }

    // The following vars do not get stored in Parse
    public List<Vendor> vendors;
    public VendorsAdapter vendorsAdapter;
    public float rating = 0f;

    public void calcRating() {
        float ratingSum = 0;
        int numInvalid = 0;
        for (Vendor v : vendors) {
            if (v.rating > 0) {
                ratingSum += v.rating;
            } else {
                ++numInvalid;
            }
        }

        rating = ratingSum == 0 ? 0 : ratingSum / (vendors.size() - numInvalid);
    }
}
