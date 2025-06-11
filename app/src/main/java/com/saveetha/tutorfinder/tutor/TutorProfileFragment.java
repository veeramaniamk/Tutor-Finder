package com.saveetha.tutorfinder.tutor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saveetha.tutorfinder.R;


public class TutorProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_tutor_profile, container, false);




        return view;
    }
}