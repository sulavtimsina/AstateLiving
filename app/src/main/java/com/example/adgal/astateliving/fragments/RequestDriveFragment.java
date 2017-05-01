package com.example.adgal.astateliving.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.adgal.astateliving.R;
import com.example.adgal.astateliving.model.Drive;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestDriveFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "Request Drive";
    TextView tvName, tvDate, tvTime;
    Button btnRequest, btnSelectDate;
    Drive drive;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private int year, month, day;
    String from, to;
    PlaceAutocompleteFragment autocompleteFragmentFrom, autocompleteFragmentTo;


    public RequestDriveFragment() {
        // Required empty public constructor
    }
    public static RequestDriveFragment newInstance(Drive drive){
        RequestDriveFragment f = new RequestDriveFragment();
        if (drive != null){
            Bundle args = new Bundle();
            args.putParcelable("drive", drive);
//            args.putBoolean("creater",creater);
            f.setArguments(args);
        }
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_request_drive, container, false);
        tvName = (TextView)v.findViewById(R.id.tv_name_drive);
        tvDate= (TextView)v.findViewById(R.id.et_date);
        tvTime = (TextView)v.findViewById(R.id.et_time);
        btnRequest = (Button)v.findViewById(R.id.btn_request_drive);
        btnRequest.setOnClickListener(this);
        tvName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
         autocompleteFragmentFrom = (PlaceAutocompleteFragment)
                getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_from);
        autocompleteFragmentTo = (PlaceAutocompleteFragment)
                getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_to);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_request_drive:
                Drive drive = new Drive();
                drive.setUid(databaseReference.child("drive").push().getKey());
                drive.setFrom(from);
                drive.setTo(to);
                drive.setEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                drive.setDate(tvDate.getText().toString());
                drive.setTime(tvTime.getText().toString());
                databaseReference.child("drive").child(drive.getUid()).setValue(drive);
                break;

            default:
                break;
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        if(bundle != null){
            drive = bundle.getParcelable("drive");
            tvName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            autocompleteFragmentFrom.setText(drive.getFrom());
            autocompleteFragmentTo.setText(drive.getTo());
            tvDate.setText(drive.getDate());
            tvTime.setText(drive.getTime());
        }


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        autocompleteFragmentFrom.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                from = place.getAddress().toString();
            }

            @Override
            public void onError(Status status) {

            }
        });
        autocompleteFragmentTo.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                to = place.getAddress().toString();
            }

            @Override
            public void onError(Status status) {

            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //onDestroyView is used as per:
        //http://stackoverflow.com/questions/41613037/google-placeautocompletefragment-crashes-with-error
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Fragment fragmentFrom = (fm.findFragmentById(R.id.place_autocomplete_fragment_from));
        Fragment fragmentTo = (fm.findFragmentById(R.id.place_autocomplete_fragment_to));
        FragmentTransaction ft = fm.beginTransaction();
        ft.remove(fragmentFrom);
        ft.remove(fragmentTo);
        ft.commit();
    }
}
