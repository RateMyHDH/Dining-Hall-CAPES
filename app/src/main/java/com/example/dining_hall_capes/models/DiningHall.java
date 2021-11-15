package com.example.dining_hall_capes.models;

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
        put(name, KEY_NAME);
    }

    // Some data stored with this class does not get stored in Parse
    // Any object relations to a DiningHall are stored in the child objects

    List<Vendor> vendors;
}
