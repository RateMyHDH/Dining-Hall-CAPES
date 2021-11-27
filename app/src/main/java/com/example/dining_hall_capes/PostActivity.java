package com.example.dining_hall_capes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dining_hall_capes.fragments.StreamFragment;
import com.example.dining_hall_capes.models.Post;
import com.example.dining_hall_capes.models.Vendor;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity {
    public String vendorID;
    public String vendorName;
    List<Post> allposts;
    PostsAdapter postsAdapter;
    Vendor vendor;
    Button createPosts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Retrieve info sent form parent intent
        //Use info to query for Dining Hall specific posts
        //Would potentially need Dining hall specified
    //Get rating for specified dining hall
    //Retrieve objects where Vendor is specified DH
        vendorID = getIntent().getExtras().getString(StreamFragment.EXTRA_VENDOR_ID);
        vendorName = getIntent().getExtras().getString(StreamFragment.EXTRA_VENDOR_NAME);
        //CHANGE VENDOR NAME
        //QUERY POSTS THAT MATCH VENDOR ID
        //vendor = getIntent().getExtras().getParcelable("Vendor");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        TextView dhName = findViewById(R.id.tvDHtitle);
        dhName.setText(vendorName);
        createPosts = findViewById(R.id.btnCreatePost);
        createPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PostActivity.this,CreationActivity.class);
                i.putExtra("vendorID",vendorID);
                startActivity(i);
            }
        });
        //do something like in query posts to retrieve the vendor rating

        RecyclerView rvPosts = findViewById(R.id.rvPosts);
        allposts = new ArrayList<>();



        queryPosts();
        postsAdapter = new PostsAdapter(this,allposts);

        rvPosts.setAdapter(postsAdapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));
    }
    public void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.whereEqualTo(Post.KEY_VENDOR_ID, vendorID);
        //query.include(Post.KEY_AUTHOR);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null){
                    Log.e("rrrr","errror");
                    return ;

                }
//                for (Post post:posts) {
//                    //uncomment if statement and allposts.add(post) once vendor id field is added to posts
//                    if (post.getVendor().getObjectId().equals(vendorID)) {
//
//                        //name is just used for logging purposes
//
//                        allposts.add(post);
//                    }
                allposts.addAll(posts);
                postsAdapter.notifyDataSetChanged();
//                }
                postsAdapter.notifyDataSetChanged();
                //postsAdapter.
            }
        });
    }
}


