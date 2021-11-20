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
        //Retrieve info sent form parent intent
        //Use info to query for Dining Hall specific posts
        Bundle Data = getIntent().getExtras();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        RecyclerView rvPosts = findViewById(R.id.rvPosts);
        posts = new ArrayList<>();

        final PostsAdapter postsAdapter = new PostsAdapter(this,posts);

        rvPosts.setAdapter(postsAdapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));
    }
}
