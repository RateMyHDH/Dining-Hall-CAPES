package com.example.dining_hall_capes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dining_hall_capes.models.Post;

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
    }

    /*Post post = Parcels.unwrap(getIntent().getParcelableExtra("post"));
    tvUsername.setText(post.getAuthor());
    tvTimestamp.setText(post.getTime());*/
}