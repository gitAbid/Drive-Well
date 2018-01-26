package com.drivewell.drivewell.ui.ranking;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.drivewell.drivewell.model.DriverModel;

import java.util.List;

/**
 * Created by abid on 1/19/18.
 */

public interface IDriverRankingPresenter {

    void initialize(RecyclerView view, Activity activi,Context context);
    List<DriverModel> getUpdatedRanking();
    void subscribeRankingUpdater();
    void unsubscribeRankingUpdater();
}
