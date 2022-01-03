package com.example.dining_hall_capes.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import com.example.dining_hall_capes.R;
import com.example.dining_hall_capes.models.Post;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.File;

public class CreatePostDialogFragment extends DialogFragment {

    public static final String TAG = "CreatePostFragment";
    public static final String IMAGE_NAME = "review_image.jpg";

    private EditText etReviewText;
    private ImageButton btnReviewCamera;
    private ImageButton btnPostReview;
    private ImageView ivImagePreview;

    private String vendorID;
    private File photoFile;

    private final ActivityResultLauncher<Uri> activityTakePhoto = registerForActivityResult(new ActivityResultContracts.TakePicture(), result -> {
        if (result) {
            Bitmap image = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            ivImagePreview.setImageBitmap(image);
        } else {
            Toast.makeText(getContext(), "No image saved", Toast.LENGTH_SHORT).show();
        }
    });

    public interface OnCreatePostDialogListener {
        void onCreatePost(Post post);
    }

    public CreatePostDialogFragment() {
        // Empty constructor is required for DialogFragment
    }

    public static CreatePostDialogFragment newInstance(String vendorID) {
        CreatePostDialogFragment frag = new CreatePostDialogFragment();
        Bundle args = new Bundle();
        args.putString(Post.KEY_VENDOR_ID, vendorID);
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_post, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vendorID = requireArguments().getString(Post.KEY_VENDOR_ID);

        etReviewText = view.findViewById(R.id.etReviewText);
        btnReviewCamera = view.findViewById(R.id.btnReviewCamera);
        btnPostReview = view.findViewById(R.id.btnPostReview);
        ivImagePreview = view.findViewById(R.id.ivImagePreview);

        btnReviewCamera.setOnClickListener(v -> launchCamera());
        btnPostReview.setOnClickListener(v -> {
            String review = etReviewText.getText().toString();
            if (review.isEmpty()) {
                Toast.makeText(getContext(), "Review cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            savePost(review, ParseUser.getCurrentUser(), photoFile);
        });
    }

    private void launchCamera() {
        photoFile = getPhotoFileUri();
        if (photoFile == null) {
            Toast.makeText(getContext(), "Failed to launch camera", Toast.LENGTH_SHORT).show();
            return;
        }

        Uri fileProvider = FileProvider.getUriForFile(requireContext(), "com.codepath.fileprovider.pictures", photoFile);
        activityTakePhoto.launch(fileProvider);
    }

    // Returns the File for a photo stored on disk given the fileName
    private File getPhotoFileUri() {
        // Get safe storage directory for photos
        File mediaStorageDir = new File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "Failed to create directory");
            return null;
        }

        return new File(mediaStorageDir.getPath() + File.separator + IMAGE_NAME);
    }

    private void savePost(String review, ParseUser author, File photoFile) {

        Post post = new Post();
        post.setReview(review);
        post.setAuthor(author);
        post.setVendorID(vendorID);
        if (photoFile != null) {
            post.setImage(new ParseFile(photoFile));
        }

        post.saveInBackground(e -> {
            if (e != null) {
                Log.e(TAG, "Error while saving", e);
                Toast.makeText(getContext(), "Error while saving", Toast.LENGTH_SHORT).show();
                return;
            }
            ((OnCreatePostDialogListener) requireContext()).onCreatePost(post);
            dismiss();
        });
    }
}