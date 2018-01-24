package com.drivewell.drivewell.ui.ranking;

import android.os.Build;
import android.support.annotation.RequiresApi;

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

/**
 * Created by abid on 1/19/18.
 */

public class DriverRankingPresenter implements IDriverRankingPresenter {

    List<DriverModel> driverList;

    public DriverRankingPresenter() {


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<DriverModel> getUpdatedRanking() {
        getDataFromRepository();
        Collections.sort(driverList, DriverModel.driverModelComparator);
        return driverList;
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
