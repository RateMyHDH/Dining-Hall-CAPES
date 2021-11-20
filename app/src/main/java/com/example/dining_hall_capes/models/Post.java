package com.example.dining_hall_capes.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;
import org.parceler.Parcel;

@ParseClassName("Post")
public class Post extends ParseObject {
//LOWER CASE AUTHOR HERE
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_LIKES = "likes";
    public static final String KEY_VENDOR = "vendor";


//EMPTY CONSTRUCTOR FOR PARCEL
    public Post(){
} 

    public static final String KEY_REVIEW = "reviewBody";


    public static final String KEY_CREATED_AT = "createdAt";


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

    public String getTime(){
        return getString(KEY_CREATED_AT);
    }
}