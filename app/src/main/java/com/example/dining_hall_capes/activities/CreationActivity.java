package com.example.dining_hall_capes.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dining_hall_capes.R;
import com.example.dining_hall_capes.models.Post;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

public class CreationActivity extends AppCompatActivity {

    public static final String TAG = "CreationActivity";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42; // arbitrary

    private EditText etReview;
    private Button btnCaptureImage;
    private ImageView ivPostImage;
    private Button btnSubmit;
    private File photoFile;
    private String photoFileName = "photo.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);

        etReview = findViewById(R.id.etReview);
        btnCaptureImage = findViewById(R.id.btnCaptureImage);
        ivPostImage = findViewById(R.id.ivPostImage);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnCaptureImage.setOnClickListener(v -> launchCamera());

        btnSubmit.setOnClickListener(v -> {
            String review = etReview.getText().toString();
            if (review.isEmpty()) {
                Toast.makeText(CreationActivity.this, "Review cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            ParseUser currentUser = ParseUser.getCurrentUser();
            savePost(review, currentUser, photoFile);
        });
    }

    private void launchCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = getPhotoFileUri(photoFileName);
        // TODO: account for null photoFile

        Uri fileProvider = FileProvider.getUriForFile(CreationActivity.this, "com.codepath.fileprovider.pictures", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        // TODO: take result and show that picture was taken
    }

    private File getPhotoFileUri(String fileName) {
        File mediaStorageDir = new File(CreationActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        if(!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // TODO: fix for when the above error occurs
        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }


    private void savePost(String review, ParseUser currentUser, File photoFile) {
        Post post = new Post();
        post.setReview(review);

        // TODO: refactor after finishing the camera activity result
        if(photoFile != null || ivPostImage.getDrawable() != null){
            post.setImage(new ParseFile(photoFile));
        }

        post.setAuthor(currentUser);
        post.setVendorID(getIntent().getExtras().getString(Post.KEY_VENDOR_ID));
        post.saveInBackground(e -> {
            if(e != null){
                Log.e(TAG, "Error while saving", e);
                Toast.makeText(CreationActivity.this, "Error while saving", Toast.LENGTH_SHORT).show();
            }
            Log.i(TAG, "Post saved successfully");
            etReview.setText("");
            ivPostImage.setImageResource(0);

            finish();
        });
    }
}