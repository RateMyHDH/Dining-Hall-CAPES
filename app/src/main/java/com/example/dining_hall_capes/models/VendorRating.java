package com.example.dining_hall_capes.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("VendorRating")
public class VendorRating extends ParseObject {

    public static final String KEY_RATING_VALUE = "rating";
    public static final String KEY_USER = "user";
    public static final String KEY_VENDOR = "vendor";
    public static final String KEY_VENDOR_ID = "vendorID";

    public double getRating() {
        return getInt(KEY_RATING_VALUE);
    }

    public void setRating(double rating) {
        put(KEY_RATING_VALUE, rating);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public ParseObject getVendor() {
        return getParseObject(KEY_VENDOR);
    }

    public void setVendor(ParseObject vendor) {
        put(KEY_VENDOR, vendor);
    }

    public String getVendorID() {
        return getString(KEY_VENDOR_ID);
    }

    public void setVendorID(String vendorID) {
        put(KEY_VENDOR_ID, vendorID);
    }
}
