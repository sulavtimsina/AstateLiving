package com.example.adgal.astateliving.fragments;


import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adgal.astateliving.R;
import com.example.adgal.astateliving.activities.LoginActivity;
import com.example.adgal.astateliving.adapters.DriveAdRecyclerViewAdapter;
import com.example.adgal.astateliving.model.Drive;
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
public class DriveListFragment extends Fragment {

    private RecyclerView recyclerView;
    public static final String ANONYMOUS = "anonymous";
    private DatabaseReference database;
    private DriveAdRecyclerViewAdapter adapter;
    private String mUsername;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String mPhotoUrl;

    public DriveListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_drive_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewDriveList);

        database = FirebaseDatabase.getInstance().getReference();
        adapter = new DriveAdRecyclerViewAdapter(Collections.<Drive>emptyList());

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
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
//                new MateAdItemTouchHelperCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this));
//        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        database.child("drive").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Drive> drives = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    Drive drive = noteDataSnapshot.getValue(Drive.class);
                    drives.add(drive);
                }
                adapter.updateList(drives);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
