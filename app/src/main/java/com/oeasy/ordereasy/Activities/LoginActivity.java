package com.oeasy.ordereasy.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.oeasy.ordereasy.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG ="hello" ;
    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;
    SignInButton google_sign_in_button;
    int RC_SIGN_IN;
    GoogleSignInAccount account;
    TextView text;
    String personEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialise();

        google_sign_in_button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.sign_in_button){

            signIn();
            if(account!=null)
            {
                personEmail=account.getEmail();
                text.setText(personEmail);
            }
        }

    }

    private void initialise() {
        text=findViewById(R.id.txt1);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient= GoogleSignIn.getClient(this, gso);
        google_sign_in_button = findViewById(R.id.sign_in_button);
        // Set the dimensions of the sign-in button.
        google_sign_in_button.setSize(SignInButton.SIZE_STANDARD);
    }


    @Override
    protected void onStart() {
        super.onStart();
        account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);

    }

    private void updateUI(GoogleSignInAccount account) {

        if(account!=null)
        {
            /*TODO Auto redirect to home page*/
            startActivity(new Intent(this,MainActivity.class));
        }
    }


    private void signIn() {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }


}