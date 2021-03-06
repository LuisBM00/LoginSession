package com.example.loginsession;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;


public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;
    private SignInButton signInButton;
    public static final int SIGN_IN_CODE = 777;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);
        builder.enableAutoManage(this, this);
        builder.addApi(Auth.GOOGLE_SIGN_IN_API, gso);
        googleApiClient = builder
                .build();


        signInButton = (SignInButton) findViewById(R.id.signInButton);

        signInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN_CODE);
            }
        });

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGN_IN_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);

        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if(result.isSuccess()){
            goMainScreen();
        } else{
            Toast.makeText(this, R.string.not_log_in, Toast.LENGTH_LONG).show();
        }


    }

    private void goMainScreen() {
           Intent intent =  new Intent(this, MainActivity.class);
           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
           startActivity(intent);
    }
}