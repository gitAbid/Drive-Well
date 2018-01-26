package com.drivewell.drivewell.ui.ranking;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;

import com.drivewell.drivewell.adapter.AdapterRankingLeaderBoard;
import com.drivewell.drivewell.constants.Ranking;
import com.drivewell.drivewell.model.DriverModel;

import java.lang.reflect.Array;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by abid on 1/19/18.
 */

public class DriverRankingPresenter implements IDriverRankingPresenter {

    private List<DriverModel> driverList;
    private RecyclerView mRankingRecyclerView;
    private Timer timer;
    private Activity activity;
    private Context context;

    public DriverRankingPresenter() {


    }

    @Override
    public void initialize(RecyclerView view, Activity activity,Context context) {
        mRankingRecyclerView=view;
        this.activity=activity;
        this.context=context;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<DriverModel> getUpdatedRanking() {
        getDataFromRepository();
        Collections.sort(driverList, DriverModel.driverModelComparator);
        return driverList;
    }

    @Override
    public void subscribeRankingUpdater() {
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                try {
                    activity.runOnUiThread(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void run() {
                            mRankingRecyclerView.setAdapter(new AdapterRankingLeaderBoard(context,getUpdatedRanking()));
                        }
                    });
                }catch (Exception e){

                }
            }
        }, 0, Ranking.LEADERBOARD_REFRESH_TIME_INTERVAL);
    }

    @Override
    public void unsubscribeRankingUpdater() {
        timer.cancel();
        timer.purge();
    }

    private void getDataFromRepository(){
        //TODO getting data from repository to be implemented
        //Demo purpose
        driverList=new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            Random ran = new Random();
            int x = (int) (ran.nextInt(10) + 625*Math.random());
            driverList.add(new DriverModel("FirstName"+i,"LastName"+i,x));
        }
    }
}
