package com.example.dining_hall_capes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.example.dining_hall_capes.models.Post;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity {
    List<Post> posts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle info = getIntent().getExtras();
        //SEND INFO FROM ONE intent TO ANOTHER
        //ALT METHOD POST.GET VENDOR AND VENDOR RATING
        //get the vendor and tnen vednor.getVendor and vendor.getRating
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        RecyclerView rvPosts = findViewById(R.id.rvPosts);
        posts = new ArrayList<>();
        //GET SPECIFIC POSTS FOR VEDNOR ( CENDOR PASSED THROUGH INTENTS?
        //VENDOR.GETPOSTS
        final PostsAdapter postsAdapter = new PostsAdapter(this,posts);

        rvPosts.setAdapter(postsAdapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));
    }
}