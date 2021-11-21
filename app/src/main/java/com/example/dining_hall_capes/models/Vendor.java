package com.example.dining_hall_capes.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Vendor")
public class Vendor extends ParseObject {

    public static final String KEY_NAME = "name";
    public static final String KEY_DINING_HALL = "diningHall";

    public String getName() {
        return getString(KEY_NAME);
    }

    public void setName(String name) {
        put(KEY_NAME, name);
    }

    public ParseObject getDiningHall() {
        return getParseObject(KEY_DINING_HALL);
    }

    public void setDiningHall(ParseObject diningHall) {
        put(KEY_DINING_HALL, diningHall);
    }

    // The following var is used to assist with local data caching
    public float rating = 0f;
}
