package com.example.adgal.astateliving.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adgal.astateliving.DataUploadPackage.AstateLiving;
import com.example.adgal.astateliving.R;
import com.example.adgal.astateliving.model.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.Map;

public class LoginActivity
        extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener,
                    GoogleApiClient.ConnectionCallbacks,
                    View.OnClickListener{

    LoginButton btnlogin;
    CallbackManager callbackManager;
    private SignInButton btngoogleSignIn;
    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mFirebaseAuth;
    private CheckBox cbDriver;

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Click Exit to Quit", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        initializeControls();

        mFirebaseAuth = FirebaseAuth.getInstance();

        btngoogleSignIn.setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, (GoogleApiClient.OnConnectionFailedListener)this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();



        btnlogin.setOnClickListener(this);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivityIntent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(mainActivityIntent);
            }
        });
    }
    private void handleFirebaseAuthResult(AuthResult authResult) {
        if (authResult != null) {
            // Welcome the user
            FirebaseUser user = authResult.getUser();
            Toast.makeText(this, "Welcome " + user.getEmail(), Toast.LENGTH_SHORT).show();
            // Go back to the main activity
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    private void initializeControls() {
        btngoogleSignIn = (SignInButton) findViewById(R.id.btn_google_signin);
        btnlogin = (LoginButton) findViewById(R.id.btn_fb_login);
        btnlogin.setReadPermissions(Arrays.asList("public_profile, email, user_friends"));
//        tvStatus = (TextView) findViewById(R.id.tvStatus);
        callbackManager = CallbackManager.Factory.create();
        cbDriver = (CheckBox) findViewById(R.id.cb_driver);
    }

    private void loginWithFB(){
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                final AccessToken accessToken = loginResult.getAccessToken();
                GraphRequestAsyncTask graphRequestAsyncTask = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        String fbName = object.optString("name");
                        String fbEmail = object.optString("email");
                        String fbId = object.optString("id");

                        Log.d("xxx", "raw= "+ response.getRawResponse());
                        Log.d("xxx", "email= "+fbEmail);
                        Log.d("xxx", "name= "+fbName);
                        Log.d("xxx", "id= "+fbId);
                        saveFbId(fbId);

                        Intent mainActivityIntent = new Intent(LoginActivity.this, MainActivity.class);
                        mainActivityIntent.putExtra("name",fbName);
                        mainActivityIntent.putExtra("id",fbId);
                        mainActivityIntent.putExtra("email",fbEmail);
                        startActivity(mainActivityIntent);
                    }
                }).executeAsync();

            }

            private void saveFbId(String fbId1) {
                SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
                    SharedPreferences.Editor ed = pref.edit();
                    ed.putString("fbId", fbId1);
                    ed.commit();
            }
            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed
                Log.e(TAG, "Google Sign In failed.");
            }
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                            AstateLiving.setFirebaseUser(firebaseAuth.getCurrentUser());
                            addUserToDatabase();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });
    }

    private void addUserToDatabase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users").child("users");


        User user = new User();
        if(ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()) != null){
            Toast.makeText(this, "user present", Toast.LENGTH_SHORT).show();
        }
        ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null)
                    for(DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                        User user =  noteDataSnapshot.getValue(User.class);

                    }
                else {
                    Toast.makeText(getApplicationContext(), "No user", Toast.LENGTH_SHORT).show();
                }
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        AstateLiving.set(AstateLiving.getFirebaseUser().getUid());
//        user.setFullName(AstateLiving.getFirebaseUser().getDisplayName());
//        user.setEmail(AstateLiving.getUserEmail());
//        user.setADriver(cbDriver.isChecked());
//        user.setFullName(AstateLiving.getUserDisplayName());
//        Map<>
//        usersRef.child(user.getUid()).setValue(user);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_google_signin:
                signIn();
                break;
            case R.id.btn_fb_login:
                loginWithFB();
                break;
            default:
                return;
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
