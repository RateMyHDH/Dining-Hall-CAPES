package com.example.dining_hall_capes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.dining_hall_capes.models.Post;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {

    TextView tvUsername;
    TextView tvTimestamp;
    ImageView ivProfileImage;
    TextView tvBody;
    ImageView ivReviewImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvUsername = findViewById(R.id.tvUsername);
        tvTimestamp = findViewById(R.id.tvTimestamp);
        ivProfileImage = findViewById(R.id.ivProfileImage);
        tvBody = findViewById(R.id.tvBody);
        ivReviewImage = findViewById(R.id.ivReviewImage);

        Post post = Parcels.unwrap(getIntent().getParcelableExtra("post"));
        tvUsername.setText(post.getAuthor().getUsername());
        tvTimestamp.setText(post.getTime());
        if(post.getAuthor().getParseFile("profilePic") != null){
            Glide.with(this)
                        .load(post.getAuthor().getParseFile("profilePic").getUrl())
                        .transform(new CenterCrop(), new RoundedCorners(20))
                        .into(ivProfileImage);
        }
        tvBody.setText(post.getReview());
        if(post.getParseFile("image") != null){
            Glide.with(this).load(post.getParseFile("image").getUrl()).into(ivReviewImage);
        }
    }
}