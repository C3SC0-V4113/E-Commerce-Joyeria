package com.udb.edu.joyeria_commerce.ui;


import android.os.Bundle;


import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udb.edu.joyeria_commerce.R;


public class PendientesFragment extends Fragment {

    public PendientesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pendientes, container, false);
    }

}