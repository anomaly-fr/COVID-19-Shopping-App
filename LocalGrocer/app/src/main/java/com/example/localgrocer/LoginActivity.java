package com.example.localgrocer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.wifi.hotspot2.pps.Credential;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    private EditText phone_number;
    private EditText otp;
    private Button request_otp;
    private Button register;
    private TextInputLayout otp_textlayout;
    private int shortAnimationDuration;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private Activity  activity;
    private FirebaseAuth mAuth;
    private String mVerificationId;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userRef = database.getReference("Users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = this;
        mAuth = FirebaseAuth.getInstance();
        //Animation Duration
        shortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        //Initializing the View Elements
        phone_number = findViewById(R.id.phone_number_login);
        otp = findViewById(R.id.otp_login);
        otp_textlayout = findViewById(R.id.otp_textinputlayout);
        request_otp = findViewById(R.id.login_request_otp_button);
        register = findViewById(R.id.login_register_button);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            final ProgressDialog dialog = new ProgressDialog(activity);


            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                //Log.d(TAG, "onVerificationCompleted:" + credential);
                Toast.makeText(LoginActivity.this, "Phone Number Verified", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                //Log.w(TAG, "onVerificationFailed", e);

                dialog.dismiss();

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }
                Toast.makeText(LoginActivity.this, "Verification Failed", Toast.LENGTH_SHORT).show();
                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                //Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                //mResendToken = token;
                Toast.makeText(LoginActivity.this, "OTP Sent", Toast.LENGTH_SHORT).show(); // ...
                dialog.setMessage("Auto-Verifying Phone Number");
                dialog.setCancelable(true);
                dialog.show();
            }
        };

        //Request OTP Button OnClick Listener
        request_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phone_number.getText().toString().length() == 10) {
                    crossfadeappear(otp_textlayout);
                    crossfadeappear(register);

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+91" + phone_number.getText().toString(),        // Phone number to verify
                            60,                 // Timeout duration
                            TimeUnit.SECONDS,   // Unit of timeout
                            activity,               // Activity (for callback binding)
                            mCallbacks);        // OnVerificationStateChangedCallbacks
                } else {
                    Snackbar.make(view, "Please enter a valid phone number.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        });

        //Register OnClick Listener
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!otp.getText().toString().equals("")) {
                    final ProgressDialog dialog = new ProgressDialog(activity);
                    dialog.setMessage("Verifying Phone Number");
                    dialog.setCancelable(false);
                    dialog.show();
                    try {
                        final PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp.getText().toString().trim());
                        mAuth.signInWithCredential(credential)
                                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            dialog.dismiss();
                                            Toast.makeText(LoginActivity.this, "Phone Number Verified", Toast.LENGTH_SHORT).show();
                                            signInWithPhoneAuthCredential(credential);
                                        } else {
                                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                                dialog.dismiss();
                                                Toast.makeText(LoginActivity.this, "Verification Failed. Invalid OTP.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                });
                    } catch (Exception e) {
                        dialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Verification Failed. Invalid OTP.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Snackbar.make(view, "Please enter an OTP.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        });
    }

    private void crossfadeappear(View contentView) {

        // Set the content view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        contentView.setAlpha(0f);
        contentView.setVisibility(View.VISIBLE);

        // Animate the content view to 100% opacity, and clear any animation
        // listener set on the view.
        contentView.animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration)
                .setListener(null);

        // Animate the loading view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
        /*loadingView.animate()
                .alpha(0f)
                .setDuration(shortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        //loadingView.setVisibility(View.GONE);
                    }
                });*/
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild(phone_number.getText().toString().trim())) {
                        Intent intent = new Intent(activity, ConsumerActivity.class);
                        intent.putExtra("Phone Number", phone_number.getText().toString().trim());
                        startActivity(intent);
                        activity.finish();
                    } else {
                        Intent intent = new Intent(activity, User_Details.class);
                        intent.putExtra("Phone Number", phone_number.getText().toString().trim());
                        startActivity(intent);
                        activity.finish();
                    }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
