package com.drivewell.drivewell.ui.workplace.drivers;




import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drivewell.drivewell.R;
import com.drivewell.drivewell.adapter.AdapterRankingLeaderBoard;
import com.drivewell.drivewell.model.DriverModel;

import java.util.ArrayList;
import java.util.List;

public class DriverRankingFragment extends Fragment {

    private RecyclerView mLeaderBoardRecyclerView;
    private AdapterRankingLeaderBoard mAdapterRankingLeaderBoard;
    private Context context;
    private List<DriverModel> driverList;


    private IDriverRankingPresenter iDriverRankingPresenter;


    private static DriverRankingFragment instance;

    public DriverRankingFragment() {
        // Required empty public constructor
    }

    public static DriverRankingFragment newInstance() {

        return new DriverRankingFragment();
    }

    public static DriverRankingFragment getInstance() {
        return instance=(instance==null)?new DriverRankingFragment():instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
