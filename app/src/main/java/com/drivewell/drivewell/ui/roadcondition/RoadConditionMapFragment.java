package com.drivewell.drivewell.ui.roadcondition;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drivewell.drivewell.R;
import com.google.android.gms.maps.MapView;


public class RoadConditionMapFragment extends Fragment {


    private static RoadConditionMapFragment instance;
    private IRoadConditionMapPresenter iRoadConditionMapPresenter;


    public RoadConditionMapFragment() {
        // Required empty public constructor
    }


    public static RoadConditionMapFragment newInstance() {
        RoadConditionMapFragment fragment = new RoadConditionMapFragment();
        return fragment;
    }
    public static Fragment getInstance() {
        return instance=(instance==null)?newInstance():instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iRoadConditionMapPresenter=new RoadConditionMapPresenter();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_road_condition_map, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setting up map instance
        MapView mRoadConditionMap = view.findViewById(R.id.mvRoadConditionMapView);
        iRoadConditionMapPresenter.initializeMap(mRoadConditionMap,getActivity().getApplicationContext());

    }
}
