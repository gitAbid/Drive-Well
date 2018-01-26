package com.drivewell.drivewell.ui.ranking;


import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drivewell.drivewell.R;
import com.drivewell.drivewell.adapter.AdapterRankingLeaderBoard;
import com.drivewell.drivewell.constants.Ranking;
import com.drivewell.drivewell.model.DriverModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DriverRankingFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView mLeaderBoardRecyclerView;
    private AdapterRankingLeaderBoard mAdapterRankingLeaderBoard;
    private Context context;
    private List<DriverModel> driverList;


    private IDriverRankingPresenter iDriverRankingPresenter;

    private String mParam1;
    private String mParam2;


    public DriverRankingFragment() {
        // Required empty public constructor
    }

    public static DriverRankingFragment newInstance(String param1, String param2) {
        DriverRankingFragment fragment = new DriverRankingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        context=getActivity().getApplicationContext();
        iDriverRankingPresenter=new DriverRankingPresenter();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView= inflater.inflate(R.layout.fragment_driver_ranking, container, false);
        driverList=new ArrayList<>();

        driverList=iDriverRankingPresenter.getUpdatedRanking();


        mLeaderBoardRecyclerView=mView.findViewById(R.id.rcvRankingBoardRecylerView);
        mAdapterRankingLeaderBoard=new AdapterRankingLeaderBoard(context,driverList);
        mLeaderBoardRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mLeaderBoardRecyclerView.setAdapter(mAdapterRankingLeaderBoard);

        iDriverRankingPresenter.initialize(mLeaderBoardRecyclerView,getActivity(),context);

        //subscribe to the updater
        iDriverRankingPresenter.subscribeRankingUpdater();



        return mView;
    }

}
