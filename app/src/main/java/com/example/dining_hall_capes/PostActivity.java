package com.example.dining_hall_capes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.dining_hall_capes.fragments.StreamFragment;
import com.example.dining_hall_capes.models.Post;
import com.example.dining_hall_capes.models.VendorRating;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity {

    public static final String TAG = "PostActivity";

    String vendorID;
    String vendorName;
    List<Post> posts;
    PostsAdapter postsAdapter;
    Button createPosts;
    RatingBar ratingByUser;
    VendorRating vendorRating;

    boolean refreshPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        refreshPosts = false;

        // Retrieve info sent form parent intent
        // Use info to query for Vendor specific posts
        // Get user's rating for specified Vendor
        vendorID = getIntent().getExtras().getString(StreamFragment.EXTRA_VENDOR_ID);
        vendorName = getIntent().getExtras().getString(StreamFragment.EXTRA_VENDOR_NAME);

        TextView dhName = findViewById(R.id.tvDHtitle);
        dhName.setText(vendorName);
        createPosts = findViewById(R.id.btnCreatePost);
        createPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PostActivity.this,CreationActivity.class);
                i.putExtra(Post.KEY_VENDOR_ID, vendorID);
                startActivity(i);
            }
        });

        RecyclerView rvPosts = findViewById(R.id.rvPosts);
        posts = new ArrayList<>();

        queryPosts();
        postsAdapter = new PostsAdapter(this, posts);

        rvPosts.setAdapter(postsAdapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));

        ratingByUser = findViewById(R.id.ratingByUser);

        queryRatingByUser();
    }

    private void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.whereEqualTo(Post.KEY_VENDOR_ID, vendorID);
        query.orderByDescending(Post.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> fetchedPosts, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Error getting posts: ", e);
                    return;
                }
                posts.clear();
                posts.addAll(fetchedPosts);
                postsAdapter.notifyDataSetChanged();
            }
        });
    }

    private void queryRatingByUser() {
        ParseQuery<VendorRating> query = ParseQuery.getQuery(VendorRating.class);
        query.whereEqualTo(VendorRating.KEY_USER, ParseUser.getCurrentUser());
        query.whereEqualTo(VendorRating.KEY_VENDOR_ID, vendorID);
        query.addDescendingOrder(VendorRating.KEY_UPDATED_AT);
        query.findInBackground(new FindCallback<VendorRating>() {
            @Override
            public void done(List<VendorRating> fetchedRatings, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error with fetching the user's rating: ", e);
                } else if (fetchedRatings.size() > 0) {
                    vendorRating = fetchedRatings.get(0);
                    ratingByUser.setRating((float) vendorRating.getRating());
                    // There should only be one rating.
                    // Duplicates are deleted from the server
                    // Duplicates are made when an exception is passed into this method
                    for (int i = 1; i < fetchedRatings.size(); ++i) {
                        fetchedRatings.get(i).deleteInBackground();
                    }
                }

                setRatingChangeListener();
            }
        });
    }

    private void setRatingChangeListener() {
        ratingByUser.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Log.i(TAG, "Rating changed by " + ParseUser.getCurrentUser().getUsername());
                ratingBar.setRating(rating);
                if (vendorRating == null) {
                    vendorRating = new VendorRating();
                    vendorRating.setVendorID(vendorID);
                    vendorRating.setUser(ParseUser.getCurrentUser());
                }
                vendorRating.setRating(rating);
                vendorRating.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            Log.e(TAG, "Failed to save rating: ", e);
                        } else {
                            Log.i(TAG, "Saved rating for " + ParseUser.getCurrentUser().getUsername());
                        }
                    }
                });
            }
        });
    }

    // Refreshes the posts so that recently created posts by the user appear on the thread
    // TODO: replace by manually adding user posts to reduce network calls
    @Override
    protected void onResume() {
        super.onResume();
        if (refreshPosts)
            queryPosts();
        refreshPosts = true;
    }
}
