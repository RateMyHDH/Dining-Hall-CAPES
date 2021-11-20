package com.example.dining_hall_capes.models;

import android.os.Parcelable;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.parceler.Parcel;

@ParseClassName("Post")
@Parcel
public class Post extends ParseObject  {

    public static final String KEY_AUTHOR = "author";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_LIKES = "likes";
    public static final String KEY_VENDOR = "vendor";
    public static final String KEY_REVIEW = "review";
//Empty constsuctor for parceler
    public  Post(){

    }
    public ParseUser getAuthor() {
        return getParseUser(KEY_AUTHOR);
    }

    public void setAuthor(ParseUser author) {
        put(KEY_AUTHOR, author);
    }

    public String getReview(){
        return getString(KEY_REVIEW);
    }

    public void setReview(String review){
        put(KEY_REVIEW, review);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile image) {
        put(KEY_IMAGE, image);
    }

    public int getLikes() {
        return getInt(KEY_LIKES);
    }

    public void setLikes(int likes) {
        put(KEY_LIKES, likes);
    }

    public ParseObject getVendor() {
        return getParseObject(KEY_VENDOR);
    }

    public void setVendor(ParseObject vendor) {
        put(KEY_VENDOR, vendor);
    }
}
