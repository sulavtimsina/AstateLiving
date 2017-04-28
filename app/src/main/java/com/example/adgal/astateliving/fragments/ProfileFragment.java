package com.example.adgal.astateliving.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.adgal.astateliving.R;
import com.example.adgal.astateliving.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.R.attr.data;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    NavigationView navigationView;
    ImageView profilePic;
    TextView profileName, profileEmail;
    TextView profileAge, profileGender,profileAptName;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        profilePic = (ImageView) view.findViewById(R.id.user_profile_photo);
        profileName = (TextView) view.findViewById(R.id.user_profile_name);
        profileEmail = (TextView) view.findViewById(R.id.user_profile_email);
        profileAge= (TextView) view.findViewById(R.id.et_Age);
        profileGender = (TextView) view.findViewById(R.id.et_gender);
        profileAptName = (TextView) view.findViewById(R.id.et_apt_name_profile);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
            //set profile picture
            final Uri profilePicUri = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();
            Glide
                    .with(this)
                    .load(profilePicUri)
                    .centerCrop()
                    .into(profilePic);
            //set name and email
            profileName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            profileEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.child("users").child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    User user =  noteDataSnapshot.getValue(User.class);
                    if(user!=null) {
                        profileAge.setText(Integer.toString(user.getAge()));
                        profileGender.setText(user.getGender());
                        profileAptName.setText(user.getAptName());
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
