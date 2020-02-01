package com.example.recyclebuddy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.auth.providers.userpassword.UserPasswordAuthProviderClient;

public class SignupActivity extends AppCompatActivity {


    /**
     * Creates the screen of the app that allows the user to signup and make an account
     * for the first time by inputting their information
     *
     * @param savedInstanceState
     *            the previous state of the application
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // When user signs up, we need to take their email and password to send to MongoDB
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        // First check to see if email fields and password fields match
        Button submit = (Button) findViewById(R.id.signupID);
        EditText email = (EditText) findViewById(R.id.emailID);
        EditText password = (EditText) findViewById(R.id.passwordID);
        String emailText;
        String passwordText;
        submit.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        String emailText = email.getText().toString();
                        Log.w("SignupActivity", emailText);
                        String passwordText = password.getText().toString();
                        Log.w("SignupActivity", passwordText);
                        signup(emailText, passwordText);
                        cancel(view);
                    }
                });

    }

    public void signup(String email, String password) {
        UserPasswordAuthProviderClient emailPassClient = Stitch.getDefaultAppClient().getAuth().getProviderClient(
                UserPasswordAuthProviderClient.factory
        );

        emailPassClient.registerWithEmail(email, password)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                           @Override
                                           public void onComplete(@NonNull final Task<Void> task) {
                                               if (task.isSuccessful()) {
                                                   Log.d("SignupActivity", "Successfully sent account confirmation email");
                                               } else {
                                                   Log.e("SignupActivity", "Error registering new user:", task.getException());
                                               }
                                           }
                                       }
                );
    }

    /**
     * Sets up the screen that comes up when the user clicks the 'cancel' button during
     * the signup phase. It brings the user back to the first page that gives them the option
     * to signup or make an account.
     *
     * @param v
     *            the screen view
     */
    public void cancel(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }
}
