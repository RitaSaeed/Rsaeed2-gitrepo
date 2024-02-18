package com.example.cookingovereasy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

/**
 * The activity that provides the password reset functionality. Firebase Auth will handle sending
 * the email, so the only thing outlined here is actually requesting that function.
 */
public class ForgotPassword extends AppCompatActivity {

    EditText email;
    TextView textView;
    Button button;

    /**
     * Super call for onStart method, basic setup.
     */
    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * Establishes all of the buttons from the xml file and gives them functionality.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.
     *                           </i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forgot_password);
        email = findViewById(R.id.emailForgot);
        button = findViewById(R.id.resetBtn);
        textView = findViewById(R.id.backToLogin);

        /**
         * Onclick for reset button, calls the auth sendPasswordResetEmail function to a valid email
         */
        button.setOnClickListener((v) -> {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            String emailAddress = email.getText().toString();
            try {
                if (!(Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches())) {
                    throw new Exception("Invalid email address.");
                }
                else {
                    auth.sendPasswordResetEmail(emailAddress).addOnCompleteListener((task) -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Email sent.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else if (task.getException() instanceof FirebaseAuthInvalidUserException){
                            Toast.makeText(this, "Email doesn't exist.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * Returns the user to the login screen if they wish to no longer reset password
         */
        textView.setOnClickListener((v) -> {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_left, R.anim.slide_out_right);
            finish();
        });
    }
}