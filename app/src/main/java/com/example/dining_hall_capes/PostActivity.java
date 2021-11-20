package com.example.dining_hall_capes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import com.example.dining_hall_capes.models.Post;
import com.example.dining_hall_capes.models.Vendor;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity {
    List<Post> allposts;
    PostsAdapter postsAdapter;
    Vendor vendor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Retrieve info sent form parent intent
        //Use info to query for Dining Hall specific posts
        //Would potentially need Dining hall specified
    //Get rating for specified dining hall
    //Retrieve objects where Vendor is specified DH
        Bundle Data = getIntent().getExtras();
        //vendor = getIntent().getExtras().getParcelable("Vendor");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        RecyclerView rvPosts = findViewById(R.id.rvPosts);
        allposts = new ArrayList<>();
        queryPosts();
         postsAdapter = new PostsAdapter(this,allposts);

        rvPosts.setAdapter(postsAdapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));
    }
    public void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        //query.include(Post.KEY_AUTHOR);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null){
                    Log.e("rrrr","errror");
                    return ;

                }
                for (Post post:posts){
                    String name = "";
                    try {
                        name = post.fetchIfNeeded().getString("author");


                    } catch (ParseException i) {
                        Log.v("LOG_TAG", i.toString());
                        i.printStackTrace();
                    }
                    Log.i("tttt","Post" + name);
                    Log.i("tttt","Post" + post.getReview());
                    //Log.i("tttt","Post" + post.get);
                }
                allposts.addAll(posts);
                postsAdapter.notifyDataSetChanged();
                //postsAdapter.
            }
        });
    }
}


