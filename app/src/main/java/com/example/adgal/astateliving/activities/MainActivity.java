package com.example.adgal.astateliving.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.adgal.astateliving.DataUploadPackage.RoomAdFragment;
import com.example.adgal.astateliving.fragments.ContactUs;
import com.example.adgal.astateliving.fragments.DriveListFragment;
import com.example.adgal.astateliving.fragments.ManagerInputFragment;
import com.example.adgal.astateliving.fragments.ProfileFragment;
import com.example.adgal.astateliving.R;
import com.example.adgal.astateliving.fragments.RequestDriveFragment;
import com.example.adgal.astateliving.fragments.RoomMateFragment;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                    ContactUs.OnFragmentCloseListener{

    NavigationView navigationView;
    String fbName, fbEmail, fbId;
    TextView tvNavName, tvNavEmail;
    ImageView ivProfilePic;
    View headerView;
//    ProfilePictureView profilePictureView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();

        setEmailName();
//        profilePictureView.setProfileId(fbId);
        populateList();

        RoomAdFragment adList = new RoomAdFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.content_main, adList).addToBackStack(null).commit();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            Toast.makeText(getApplicationContext(), user.getDisplayName(), Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(), "user not logged in", Toast.LENGTH_LONG).show();
        }

        //set profile picture
        ivProfilePic = (ImageView) headerView.findViewById(R.id.imgViewProfile);
        final Uri profilePicUri = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();
        Glide
                .with(this)
                .load(profilePicUri)
                .centerCrop()
                .into(ivProfilePic);
    }

    private void populateList(){
    }

    private void setEmailName(){
        fbName = getIntent().getStringExtra("name");
        fbEmail = getIntent().getStringExtra("email");
        fbId = getIntent().getStringExtra("id");
//        getFacebookProfilePicture(fbId);


        //updating email n name in header
         headerView = navigationView.getHeaderView(0);
        tvNavName = (TextView) headerView.findViewById(R.id.tvNavName);

//        profilePictureView = (ProfilePictureView) headerView.findViewById(R.id.profilePicView);

        if(!TextUtils.isEmpty(fbName))
            tvNavName.setText(fbName);
    }

    private void initComponents() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle;
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //disableNavBarItems();
    }

    @Override
    public void onClose() {
        RoomAdFragment adList = new RoomAdFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, adList).addToBackStack(null).commit();
    }


    private class HTTPExample extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            try {
            URL imageURL = new URL(params[0]);
            bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap s) {
            super.onPostExecute(s);
//            ivProfilePic.setImageBitmap(s);
        }
    }

    private void disableNavBarItems() {
        //disable items in nav bar
        Menu nav_menu = navigationView.getMenu();
        nav_menu.findItem(R.id.nav_contact_us).setVisible(false);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
//            Intent intent = new Intent(this, Main2Activity.class);
//            startActivity(intent);

            return true;
        }
        if (id == R.id.nav_log_out){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LoginActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_watchlist) {
            RoomAdFragment roomAdFragment = new RoomAdFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.content_main, roomAdFragment).commit();
        } else if (id == R.id.nav_notifications) {

        } else if (id == R.id.nav_room_mate) {
            RoomMateFragment roomMateFragment = new RoomMateFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.content_main, roomMateFragment).commit();
        }else if (id == R.id.nav_log_out) {
            LoginManager.getInstance().logOut();
            Intent logOutIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(logOutIntent);

        }else if (id == R.id.nav_contact_us) {
            ContactUs contactUs = new ContactUs();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, contactUs).commit();
        }else if (id == R.id.nav_profile) {
            ProfileFragment profileFragment = new ProfileFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, profileFragment).commit();
        }else if (id == R.id.nav_mgr_input){
            ManagerInputFragment managerInputFragment = new ManagerInputFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, managerInputFragment).commit();
        }else if (id == R.id.request_drive){
            RequestDriveFragment requestDriveFragment = RequestDriveFragment.newInstance(null);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, requestDriveFragment).commit();
        }
        else if(id == R.id.nav_see_drive_ad){
            DriveListFragment driveListFragment = new DriveListFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, driveListFragment).commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
