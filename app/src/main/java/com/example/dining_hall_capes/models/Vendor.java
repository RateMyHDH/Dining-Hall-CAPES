package com.example.dining_hall_capes.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.List;

@ParseClassName("Vendor")
public class Vendor extends ParseObject {

    public static final String KEY_DISCUSSION_THREAD = "discussionThread";
    public static final String KEY_RATING = "rating";

    public List<Post> getDiscussionThread() {
        return getList(KEY_DISCUSSION_THREAD);
    }

    public void setDiscussionThread(List<Post> discussionThread) {
        put(KEY_DISCUSSION_THREAD, discussionThread);
    }

    public double getRating() {
        return getDouble(KEY_RATING);
    }

    public void setRating(double rating) {
        put(KEY_RATING, rating);
    }
}
