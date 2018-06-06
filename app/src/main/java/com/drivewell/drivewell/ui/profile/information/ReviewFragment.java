package com.drivewell.drivewell.ui.profile.information;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drivewell.drivewell.R;

public class ReviewFragment extends Fragment {

    private static ReviewFragment instance;
    public ReviewFragment() {
        // Required empty public constructor
    }

    public static ReviewFragment newInstance() {
        ReviewFragment fragment = new ReviewFragment();
        return fragment;
    }

    public static Fragment getInstance() {
        return instance==null?new ReviewFragment():instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review, container, false);
    }


}
