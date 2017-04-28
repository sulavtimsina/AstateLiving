package com.example.adgal.astateliving.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adgal.astateliving.R;
import com.example.adgal.astateliving.model.Message;
import com.example.adgal.astateliving.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUs extends Fragment {


//    FloatingActionButton fab;
    EditText tvMessage;
    Button btnSubmit;
    TextView phone;
    OnFragmentCloseListener onFragmentCloseListener;

    public ContactUs() {
        // Required empty public constructor
    }
    // Container Activity(MainActivity) must implement this interface
    public interface OnFragmentCloseListener {
        public void onClose();
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
                    + " must implement OnHeadlineSelectedListener");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        tvMessage = (EditText) view.findViewById(R.id.messageContact);
        btnSubmit = (Button) view.findViewById(R.id.submitContact);
        phone = (TextView) view.findViewById(R.id.phoneContact);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //hideFAB();
        //populate views
        ((TextView)getView().findViewById(R.id.emailContact)).setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        ((TextView)getView().findViewById(R.id.nameContact)).setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        populatePhoneNumber();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });


        tvMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if(actionId == EditorInfo.IME_ACTION_SEND){
                    sendMessage();
                    handled = true;
                }
                return handled;
            }
        });
    }

    private void sendMessage() {
        Message message = new Message();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String key = databaseReference.child("messages").push().getKey();
        message.setEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        message.setMessage(tvMessage.getText().toString());
        message.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());

        databaseReference.child("messages").child(key).setValue(message);
        Toast.makeText(getActivity(), "Message send to Admin of Astate Living", Toast.LENGTH_LONG).show();
        onFragmentCloseListener.onClose();
    }


    private void populatePhoneNumber() {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.child("users").child("users").child(currentUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    User user =  noteDataSnapshot.getValue(User.class);
                    if(user.getPhone() != null) {
                        phone.setEnabled(false);
                        ((TextView)getView().findViewById(R.id.phoneContact)).setText(user.getPhone());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

//    private void hideFAB() {
//        fab = (FloatingActionButton) getView().findViewById(R.id.fab) ;
//        CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
//        p.setAnchorId(View.NO_ID);
//        fab.setLayoutParams(p);
//        fab.setVisibility(View.GONE);
//    }
}
