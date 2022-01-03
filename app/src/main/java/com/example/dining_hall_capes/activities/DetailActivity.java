package com.example.dining_hall_capes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.dining_hall_capes.R;
import com.example.dining_hall_capes.models.Post;
import com.parse.ParseFile;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {

    private TextView tvUsername;
    private TextView tvTimestamp;
    private ImageView ivProfileImage;
    private TextView tvBody;
    private ImageView ivReviewImage;

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

        ParseFile image = post.getAuthor().getParseFile("profilePic");
        if (image != null){
            Glide.with(this)
                    .load(image.getUrl())
                    .transform(new CenterCrop(), new RoundedCorners(20))
                    .into(ivProfileImage);
        }
        tvBody.setText(post.getReview());
        if(post.getImage() != null){
            Glide.with(this).load(post.getImage().getUrl()).into(ivReviewImage);
        }
    }
}