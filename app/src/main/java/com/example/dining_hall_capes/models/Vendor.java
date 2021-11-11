package com.example.dining_hall_capes.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Vendor")
public class Vendor extends ParseObject {

    public static final String KEY_RATING = "rating";
    public static final String KEY_DINING_HALL = "diningHall";

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
