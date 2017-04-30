package com.example.adgal.astateliving.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adgal.astateliving.DataUploadPackage.RoomAdFragment;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import com.example.adgal.astateliving.R;
import com.example.adgal.astateliving.model.RoomMate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoomMateFragment extends Fragment {

    private static final String EXTRA_NOTE = "NOTE";
    private static final String TAG = "RoomMateActivity";

    private DatabaseReference database;
    private TextView titleTextView;
    private TextView descriptionTextView;
    private TextView rentTextView;
    private RoomMate roomMate;
    private String googleEmail;
    private Button btnAddDetail;
    private PlaceAutocompleteFragment autocompleteFragment;
    private Place mPlace;
    private FloatingActionButton fabSave;

    public RoomMateFragment() {
        // Required empty public constructor
    }

    public static RoomMateFragment newInstance(RoomMate roomMate){
        RoomMateFragment f = new RoomMateFragment();
        if (roomMate != null){
            Bundle args = new Bundle();
            args.putParcelable("mate",roomMate);
            f.setArguments(args);
        }
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_room_mate, container, false);
        titleTextView = (TextView) view.findViewById(R.id.et_title);
        descriptionTextView = (TextView) view.findViewById(R.id.et_description);
        rentTextView = (TextView) view.findViewById(R.id.etRent);
        fabSave = (FloatingActionButton) view.findViewById(R.id.fabSaveItem);
        autocompleteFragment = (PlaceAutocompleteFragment)getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        //handle fragment arguments
        Bundle bundle = getArguments();
        if(bundle != null){
            roomMate = bundle.getParcelable("mate");
            titleTextView.setText(roomMate.getTitle());
            descriptionTextView.setText(roomMate.getDescription());
            rentTextView.setText(Integer.toString(roomMate.getRent()));
            autocompleteFragment.setText(roomMate.getPlaceName());
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        database = database2.getReference();

        autocompleteFragment.setHint("Enter/Select Name of Place");
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());
                mPlace = place;
                Toast.makeText(getActivity(), "Places: "+place.getName(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });


        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (roomMate == null) {
                    roomMate = new RoomMate();
                    roomMate.setUid(database.child("notes").push().getKey());
                    roomMate.setGoogleUid(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    if(mPlace != null){
                        roomMate.setLat(mPlace.getLatLng().latitude);
                        roomMate.setLng(mPlace.getLatLng().longitude);
                        roomMate.setPlaceId(mPlace.getId());
                    }
                }
                roomMate.setTitle(titleTextView.getText().toString());
                roomMate.setDescription(descriptionTextView.getText().toString());
                roomMate.setRent(Integer.parseInt(rentTextView.getText().toString()));
                roomMate.setPlaceId(mPlace.getId());
                roomMate.setPlaceName((String) mPlace.getName());

                database.child("notes").child(roomMate.getUid()).setValue(roomMate);

                //go to previous list of Ads
                RoomAdFragment adList = new RoomAdFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_main, adList).commit();
            }
        });

    }
}
