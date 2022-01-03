package com.example.dining_hall_capes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dining_hall_capes.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

// this is the LoginActivity
public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "Login Activity";

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(ParseUser.getCurrentUser() != null){
            goMainActivity();
        }

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(v -> {
            Log.i(TAG, "onClick login button");
            String newEmail = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            loginUser(newEmail, password);
        });

        btnRegister.setOnClickListener(v -> {
            Log.i(TAG, "Register account button");
            goRegisterAccountActivity();
        });
    }

    private void loginUser(String newEmail, String password){
        Log.i(TAG, "Attempting to login user " + newEmail);
        ParseUser.logInInBackground(newEmail, password, (user, e) -> {
            if(e != null){
                Log.e(TAG, "Issue with login", e);
                Toast.makeText(LoginActivity.this, "Login in Failed, try again", Toast.LENGTH_LONG).show();
                return;
            }

            // Toast.makeText(LoginActivity.this,"Login Success!", Toast.LENGTH_LONG).show();
            goMainActivity();
        });
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void goRegisterAccountActivity() {
        Intent i = new Intent(this, RegisterAccountActivity.class);
        startActivity(i);
    }
}