package com.example.dining_hall_capes.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Vendor")
public class Vendor extends ParseObject {

    public static final String KEY_NAME = "name";
    public static final String KEY_RATING = "rating";
    public static final String KEY_DINING_HALL = "diningHall";

    public String getName() {
        return getString(KEY_NAME);
    }

    public void setName(String name) {
        put(name, KEY_NAME);
    }

    public double getRating() {
        return getDouble(KEY_RATING);
    }

    public void setRating(double rating) {
        put(KEY_RATING, rating);
    }

    public ParseObject getDiningHall() {
        return getParseObject(KEY_DINING_HALL);
    }

    public void setDiningHall(ParseObject diningHall) {
        put(KEY_DINING_HALL, diningHall);
    }
}
