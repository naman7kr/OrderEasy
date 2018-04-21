package com.oeasy.ordereasy.Fragments;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.oeasy.ordereasy.Activities.LoginActivity;
import com.oeasy.ordereasy.Activities.MainActivity;
import com.oeasy.ordereasy.R;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Stan on 4/4/2018.
 */

public class ProfileFragment extends Fragment  {
    @Nullable
    CircleImageView circleImageView;
    TextView name,email,googleId;
    Button log_out;
    GoogleApiClient mGoogleApiClient;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.profile_fragment,container,false);
        log_out=view.findViewById(R.id.log_out);
        circleImageView=view.findViewById(R.id.profile);
        name=view.findViewById(R.id.profile_name);
        email=view.findViewById(R.id.profile_email);
        googleId=view.findViewById(R.id.profile_id);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            Picasso.with(getContext())
                    .load(personPhoto)
                    .placeholder(android.R.drawable.sym_def_app_icon)
                    .error(android.R.drawable.sym_def_app_icon)
                    .into(circleImageView);
            name.setText(personName);
            email.setText(personEmail);
            googleId.setText(personId);
        }

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                // ...
                                Toast.makeText(getContext(),"Logged Out",Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(getContext(),LoginActivity.class);
                                startActivity(i);
                            }
                        });
            }
        });
        return view;
    }

    public void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }



}
