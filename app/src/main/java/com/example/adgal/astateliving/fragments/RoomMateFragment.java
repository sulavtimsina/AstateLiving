package com.example.adgal.astateliving.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adgal.astateliving.DataUploadPackage.RoomAdFragment;
import com.example.adgal.astateliving.utils.OnFragmentCloseListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.GeoDataApi;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
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
    private FloatingActionButton fabSave, fabCancel;
    OnFragmentCloseListener onFragmentCloseListener;
    private static View view;

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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            onFragmentCloseListener = (OnFragmentCloseListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnRoomMateFragCloseListener");
        }
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        PlaceAutocompleteFragment p = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
//        Fragment fragmentById = getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
        autocompleteFragment=null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_room_mate, container, false);
        titleTextView = (TextView) view.findViewById(R.id.et_title);
        descriptionTextView = (TextView) view.findViewById(R.id.et_description);
        rentTextView = (TextView) view.findViewById(R.id.etRent);
        fabSave = (FloatingActionButton) view.findViewById(R.id.fabSaveItem);
        fabCancel = (FloatingActionButton) view.findViewById(R.id.fabCross);

//        if (view != null){
//            ViewGroup parent = (ViewGroup) view.getParent();
//            if(parent != null){
//                parent.removeView(view);
//            }
//            try{
//                view = inflater.inflate(R.layout.fragment_room_mate, container, false);
//                titleTextView = (TextView) view.findViewById(R.id.et_title);
//                descriptionTextView = (TextView) view.findViewById(R.id.et_description);
//                rentTextView = (TextView) view.findViewById(R.id.etRent);
//                fabSave = (FloatingActionButton) view.findViewById(R.id.fabSaveItem);
//                fabCancel = (FloatingActionButton) view.findViewById(R.id.fabCross);
//            }catch (InflateException e){
//                Log.e("PlaceAutoFragment", "Map is already there,just return view as it is");
//            }
//        }
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    // This method is called after the parent Activity's onCreate() method has completed.
    // Accessing the view hierarchy of the parent activity must be done in the onActivityCreated.
    // At this point, it is safe to search for activity View objects by their ID, for example.
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
                if(mPlace == null){
                    Toast.makeText(getActivity(), "Please select the address,once again if already specified!",Toast.LENGTH_LONG).show();
                    return;
                }
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

                onFragmentCloseListener.onClose(RoomMateFragment.this);
                //go to previous list of Ads
//                RoomAdFragment adList = new RoomAdFragment();
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                fragmentManager.beginTransaction().replace(R.id.content_main, adList).commit();
//                fragmentManager.beginTransaction().detach(getParentFragment());
            }
        });

        fabCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFragmentCloseListener.onClose(RoomMateFragment.this);
            }
        });

    }
}
