package com.example.dining_hall_capes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import com.example.dining_hall_capes.models.Post;

import java.util.ArrayList;
import java.util.List;

// this is the LoginActivity
public class LoginActivity extends AppCompatActivity {


    public static final String TAG = "Login Activity";
    private EditText etNewEmail;
    private EditText etPassword;
    private Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        if(ParseUser.getCurrentUser() != null){
            goMainActivity();
        }

        etNewEmail = findViewById(R.id.etNewEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick login button");
                String newEmail = etNewEmail.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(newEmail, password);
            }
        });
    }
    private void loginUser(String newEmail, String password){
        Log.i(TAG, "Attempting to login user " + newEmail);
        ParseUser.logInInBackground(newEmail, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Issue with login", e);
                    Toast.makeText(LoginActivity.this, "Login in Failed, try again", Toast.LENGTH_LONG).show();
                    return;
                }

                // navigate to main activity if correct credentials
                Toast.makeText(LoginActivity.this,"Login Success!", Toast.LENGTH_LONG).show();
                goMainActivity();
            }
        });
    }

    private void goMainActivity() {
        Intent i = new Intent(this, PostActivity.class);
        startActivity(i);
        finish();

    }
}//DEL EVERYTHING BELOW SCV