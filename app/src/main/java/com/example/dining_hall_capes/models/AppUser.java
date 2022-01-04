package com.example.dining_hall_capes.models;

import com.parse.ParseFile;
import com.parse.ParseUser;

public class AppUser {

    public static final String KEY_PROFILE_PICTURE = "profilePic";

    public static ParseFile getProfilePicture(ParseUser user) {
        return user.getParseFile(KEY_PROFILE_PICTURE);
    }

    public static void setProfilePicture(ParseUser user, ParseFile image) {
        user.put(KEY_PROFILE_PICTURE, image);
    }
}
