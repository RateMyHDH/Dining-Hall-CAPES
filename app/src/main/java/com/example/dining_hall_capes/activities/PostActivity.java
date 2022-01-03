package com.example.dining_hall_capes.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.dining_hall_capes.R;
import com.example.dining_hall_capes.adapters.PostsAdapter;
import com.example.dining_hall_capes.fragments.CreatePostDialogFragment;
import com.example.dining_hall_capes.fragments.StreamFragment;
import com.example.dining_hall_capes.models.Post;
import com.example.dining_hall_capes.models.VendorRating;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity implements CreatePostDialogFragment.OnCreatePostDialogListener {

    public static final String TAG = "PostActivity";

    String vendorID;
    String vendorName;
    List<Post> posts;
    PostsAdapter postsAdapter;
    RecyclerView rvPosts;
    TextView tvDHName;
    Button createPosts;
    RatingBar ratingByUser;
    VendorRating vendorRating;
    SwipeRefreshLayout swipeContainer;

    private final FragmentManager fragmentManager = getSupportFragmentManager();

    boolean refreshPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        refreshPosts = false;

        // Retrieve info sent form parent intent
        // Use info to query for Vendor specific posts
        vendorID = getIntent().getExtras().getString(StreamFragment.EXTRA_VENDOR_ID);
        vendorName = getIntent().getExtras().getString(StreamFragment.EXTRA_VENDOR_NAME);

        tvDHName = findViewById(R.id.tvDHtitle);
        createPosts = findViewById(R.id.btnCreatePost);
        rvPosts = findViewById(R.id.rvPosts);
        ratingByUser = findViewById(R.id.ratingByUser);
        swipeContainer = findViewById(R.id.postsSwipeContainer);

        posts = new ArrayList<>();
        postsAdapter = new PostsAdapter(this, posts);

        tvDHName.setText(vendorName);
        createPosts.setOnClickListener(view -> {
            CreatePostDialogFragment frag = CreatePostDialogFragment.newInstance(vendorID);
            frag.show(fragmentManager, CreatePostDialogFragment.TAG);
        });

        rvPosts.setAdapter(postsAdapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));

        swipeContainer.setOnRefreshListener(() -> {
            Log.i(TAG, "Refreshing posts");
            queryPosts();
        });
        swipeContainer.setRefreshing(true);

        queryPosts();
        queryRatingByUser();
    }

    private void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.whereEqualTo(Post.KEY_VENDOR_ID, vendorID);
        query.orderByDescending(Post.KEY_CREATED_AT);
        query.findInBackground((fetchedPosts, e) -> {
            if(e != null){
                Log.e(TAG, "Error getting posts: ", e);
                swipeContainer.setRefreshing(false);
                return;
            }
            postsAdapter.replaceAll(fetchedPosts);
            swipeContainer.setRefreshing(false);
        });
    }

    private void queryRatingByUser() {
        ParseQuery<VendorRating> query = ParseQuery.getQuery(VendorRating.class);
        query.whereEqualTo(VendorRating.KEY_USER, ParseUser.getCurrentUser());
        query.whereEqualTo(VendorRating.KEY_VENDOR_ID, vendorID);
        query.addDescendingOrder(VendorRating.KEY_UPDATED_AT);
        query.findInBackground(this::onUserRatingsFetched);
    }

    private void onUserRatingsFetched(List<VendorRating> fetchedRatings, ParseException e) {
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

        ratingByUser.setOnRatingBarChangeListener(this::onRatingChanged);
    }

    private void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        Log.i(TAG, "Rating changed by " + ParseUser.getCurrentUser().getUsername());
        ratingBar.setRating(rating);
        if (vendorRating == null) {
            vendorRating = new VendorRating();
            vendorRating.setVendorID(vendorID);
            vendorRating.setUser(ParseUser.getCurrentUser());
        }
        vendorRating.setRating(rating);
        vendorRating.saveInBackground(e -> {
            if (e != null) {
                Log.e(TAG, "Failed to save rating: ", e);
            } else {
                Log.i(TAG, "Saved rating for " + ParseUser.getCurrentUser().getUsername());
            }
        });
    }

    @Override
    public void onCreatePost(Post post) {
        posts.add(0, post);
        postsAdapter.notifyItemInserted(0);
        rvPosts.smoothScrollToPosition(0);
    }
}
