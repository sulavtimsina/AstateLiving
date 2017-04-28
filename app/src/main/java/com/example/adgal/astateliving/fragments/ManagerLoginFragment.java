package com.example.adgal.astateliving.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adgal.astateliving.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ManagerLoginFragment extends Fragment {


    public ManagerLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manager_login, container, false);
    }

}
