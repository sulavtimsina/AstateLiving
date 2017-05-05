package com.example.adgal.astateliving.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.adgal.astateliving.R;
import com.example.adgal.astateliving.model.User;
import com.example.adgal.astateliving.utils.OnFragmentCloseListener;
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
public class ProfileFragment extends Fragment implements View.OnClickListener{


    NavigationView navigationView;
    ImageView profilePic;
    TextView profileName, profileEmail, aboutMe;
    TextView profileAge, profileGender,profileAptName;
    Button btnSave;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private TextView profilePhone;
    User user, user2;
    boolean userAlreadyPresent;
    private Button btnClose;
    OnFragmentCloseListener onFragmentCloseListener;

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
        profilePhone = (TextView) view.findViewById(R.id.et_phone);
        profileAptName = (TextView) view.findViewById(R.id.et_apt_name_profile);
        btnSave = (Button) view.findViewById(R.id.btnSaveProfile);
        btnClose = (Button)view.findViewById(R.id.btnCloseProfile);
        btnClose.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        aboutMe = (TextView) view.findViewById(R.id.etAboutMe);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            onFragmentCloseListener = (OnFragmentCloseListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentCloseListener");
        }
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
        Log.e("sulav 83 uid=",currentUid);
        databaseReference.child("users").child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    user =  noteDataSnapshot.getValue(User.class);
                    Log.i("sulav 89 email",user.getEmail());
                    if(user!=null && user.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                        user2 = user;
                        userAlreadyPresent = true;
                        profileAge.setText(Integer.toString(user.getAge()));
                        profileGender.setText(user.getGender());
                        profileAptName.setText(user.getAptName());
                        aboutMe.setText(user.getAboutMe());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSaveProfile:
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference();
                if(!userAlreadyPresent) {
                    user2 = new User();
                    user2.setUid(databaseReference.child("users").child("users").push().getKey());
                }

                user2.setEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                user2.setAge(Integer.parseInt(profileAge.getText().toString()));
                user2.setAptName(profileAptName.getText().toString());
                user2.setFullName(profileName.getText().toString());
                user2.setPhone(profilePhone.getText().toString());
                user2.setAboutMe(aboutMe.getText().toString());
                databaseReference.child("users").child("users").child(user2.getUid()).setValue(user2);
                break;
            case R.id.btnCloseProfile:
                onFragmentCloseListener.onClose(this);
                break;
            default:
                break;
        }
    }
}
