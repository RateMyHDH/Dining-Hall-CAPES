package com.example.dining_hall_capes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class RegisterAccountActivity extends AppCompatActivity {

    public static final String REGISTER_TAG = "Register Activity";
    private EditText etEmail;
    private EditText etNewPhone;
    private EditText etNewUsername;
    private EditText etNewPassword;
    private CheckBox cbCheckTerms;
    private Button btnCreateAccount;
    private ImageButton btnBack;

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
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLoginActivity();
            }
        });

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String phone = etNewPhone.getText().toString();
                String username = etNewUsername.getText().toString();
                String password = etNewPassword.getText().toString();

                if(cbCheckTerms.isChecked()){
                    ParseUser user = new ParseUser();
                    user.setEmail(email);
                    user.setUsername(username);
                    user.setPassword(password);

                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Log.i(REGISTER_TAG, "Sign up succeeded");
                                Toast.makeText(RegisterAccountActivity.this, "Sign Up Succeed, use your new credentials now", Toast.LENGTH_LONG).show();
                                goLoginActivity();
                            } else {
                                Log.e(REGISTER_TAG, "Sign up failed", e);
                                Toast.makeText(RegisterAccountActivity.this, "Sign up failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(RegisterAccountActivity.this, "You must agree to our terms and conditions", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void goLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}