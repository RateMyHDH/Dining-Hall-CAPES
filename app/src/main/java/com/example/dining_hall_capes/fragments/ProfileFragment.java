package com.example.dining_hall_capes.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.dining_hall_capes.activities.LoginActivity;
import com.example.dining_hall_capes.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";
    public static final String PROFILE_IMAGE_NAME = "profile_image.jpg";

    private ParseUser currentUser = ParseUser.getCurrentUser();
    private File photoFile;

    private TextView userName;
    private ImageView ivProfilePic;
    private Button btnSwapPic;
    private Button btnLogout;

    private final ActivityResultLauncher<Uri> activityTakePhoto = registerForActivityResult (
            new ActivityResultContracts.TakePicture(),
            result -> {
                if (result) {
                    Bitmap image = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                    ivProfilePic.setImageBitmap(image);
                    currentUser.put("profilePic", new ParseFile(photoFile));
                    currentUser.saveInBackground(e -> {
                        if(e != null){
                            Log.e(TAG, "Error while saving", e);
                            Toast.makeText(getContext(), "Image not saved", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Image not saved", Toast.LENGTH_SHORT).show();
                }
            });

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivProfilePic = view.findViewById(R.id.ivProfilePic);
        btnSwapPic = view.findViewById(R.id.btnSwapPic);
        btnLogout = view.findViewById(R.id.btnLogout);
        userName = view.findViewById(R.id.tvUser);

        userName.setText(currentUser.getUsername());
        ParseFile image = currentUser.getParseFile("profilePic");
        if(image != null) {
            Glide.with(requireContext())
                    .load(image.getUrl())
                    .transform(new CenterCrop(), new RoundedCorners(20))
                    .into(ivProfilePic);
        }

        btnSwapPic.setOnClickListener(v -> launchCamera());
        btnLogout.setOnClickListener(v -> {
            ParseUser.logOut();
            goLoginActivity();
            Toast.makeText(getContext(), "Signed out successful", Toast.LENGTH_LONG).show();
        });
    }

    private void goLoginActivity() {
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
        requireActivity().finish();
    }

    private void launchCamera() {
        // Create a File reference for future access
        photoFile = getPhotoFileUri();
        if (photoFile == null) {
            Toast.makeText(getContext(), "Failed to launch camera", Toast.LENGTH_SHORT).show();
            return;
        }

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(requireContext(), "com.codepath.fileprovider.pictures", photoFile);
        activityTakePhoto.launch(fileProvider);
    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri() {
        // Get safe storage directory for photos
        File mediaStorageDir = new File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
            return null;
        }

        // Return the file target for the photo based on filename
        return new File(mediaStorageDir.getPath() + File.separator + PROFILE_IMAGE_NAME);
    }
}