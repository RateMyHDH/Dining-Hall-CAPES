package com.example.dining_hall_capes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dining_hall_capes.R;
import com.parse.ParseException;
import com.parse.ParseUser;

public class RegisterAccountActivity extends AppCompatActivity {

    public static final String TAG = "RegisterActivity";

    private EditText etEmail;
    private EditText etNewPhone;
    private EditText etNewUsername;
    private EditText etNewPassword;
    private CheckBox cbCheckTerms;
    private Button btnCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);

        etEmail = findViewById(R.id.etEmail);
        etNewPhone = findViewById(R.id.etNewPhone);
        etNewUsername = findViewById(R.id.etNewUsername);
        etNewPassword = findViewById(R.id.etNewPassword);
        cbCheckTerms = findViewById(R.id.cbCheckTerms);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);

        btnCreateAccount.setOnClickListener(this::onCreateAccountClick);
    }

    private void onCreateAccountClick(View v) {
        if (!cbCheckTerms.isChecked()) {
            Toast.makeText(RegisterAccountActivity.this, "You must agree to our terms and conditions", Toast.LENGTH_LONG).show();
            return;
        }

        String email = etEmail.getText().toString();
        String username = etNewUsername.getText().toString();
        String password = etNewPassword.getText().toString();

        ParseUser user = new ParseUser();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);

        user.signUpInBackground(this::onUserSignUp);
    }

    private void onUserSignUp(ParseException e) {
        if (e == null) {
            Log.i(TAG, "Sign up succeeded");
            Toast.makeText(RegisterAccountActivity.this, "Sign Up Success", Toast.LENGTH_LONG).show();
            goLoginActivity();
        } else {
            Log.e(TAG, "Sign up failed", e);
            Toast.makeText(RegisterAccountActivity.this, "Sign up failed", Toast.LENGTH_LONG).show();
        }
    }

    private void goLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}