package com.example.adgal.astateliving.DataUploadPackage;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adgal.astateliving.R;
import com.example.adgal.astateliving.activities.LoginActivity;
import com.example.adgal.astateliving.fragments.ContactUs;
import com.example.adgal.astateliving.fragments.RoomMateFragment;
import com.example.adgal.astateliving.model.RoomMate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoomAdFragment extends Fragment implements DeletionListener {
    DatabaseReference database;
    MateAdRecyclerViewAdapter adapter;
    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    public static final String ANONYMOUS = "anonymous";
    private String mUsername;
    private String mPhotoUrl;
    FloatingActionButton fabAddItem;
    FloatingActionButton fabSign;
    RecyclerView recyclerView;
    View view;


    public RoomAdFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.room_fragment_ad, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        fabAddItem = (FloatingActionButton) view.findViewById(R.id.fabSaveItem);
        fabSign = (FloatingActionButton) view.findViewById(R.id.fabSign);

        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RoomMateFragment roomMateFragment = RoomMateFragment.newInstance(null);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_main, roomMateFragment).commit();

            }
        });


        fabSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAuth.signOut();
            }
        });

        database = FirebaseDatabase.getInstance().getReference();
        adapter = new MateAdRecyclerViewAdapter(Collections.<RoomMate>emptyList());

        //  add these just after setContentView(R.layout.activity_main);
        // Initialize Firebase Auth
        mUsername = ANONYMOUS;
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            Intent logOutIntent = new Intent(getActivity(), LoginActivity.class);
            startActivity(logOutIntent);
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
            Log.i("User Name:",mUsername);
        }

//        getActivity().getActionBar().setTitle(mFirebaseAuth.getCurrentUser().getDisplayName());


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        final int offset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics());
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
                    outRect.bottom = offset;
                }
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new MateAdItemTouchHelperCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        database.child("notes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<RoomMate> notes = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    RoomMate note = noteDataSnapshot.getValue(RoomMate.class);
                    notes.add(note);
                }

                adapter.updateList(notes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void itemRemoved(int position) {
        RoomMate note = adapter.getItem(position);
        adapter.removeItem(position);
        database.child("notes").child(note.getUid()).removeValue();
    }
}
