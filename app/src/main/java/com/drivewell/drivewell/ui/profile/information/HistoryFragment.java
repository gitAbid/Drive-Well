package com.drivewell.drivewell.ui.profile.information;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drivewell.drivewell.R;


public class HistoryFragment extends Fragment {

    private static HistoryFragment instance;
    public HistoryFragment() {
        // Required empty public constructor
    }


    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        return fragment;
    }

    public static Fragment getInstance() {
        return instance==null?new HistoryFragment():instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }


}
