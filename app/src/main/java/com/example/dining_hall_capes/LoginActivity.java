package com.example.dining_hall_capes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.dining_hall_capes.models.Post;

import java.util.ArrayList;
import java.util.List;

// this is the LoginActivity
public class LoginActivity extends AppCompatActivity {
List<Post> posts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        RecyclerView rvPosts = findViewById(R.id.rvPosts);
//        posts = new ArrayList<Post>();
//        Post p = new Post() ;
//
//        final PostsAdapter postsAdapter = new PostsAdapter(this,posts);
//
//        rvPosts.setAdapter(postsAdapter);
//        rvPosts.setLayoutManager(new LinearLayoutManager(this));
    }
}//DEL EVERYTHING BELOW SCV