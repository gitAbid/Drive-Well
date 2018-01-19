package com.drivewell.drivewell.ui.ranking;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.drivewell.drivewell.model.DriverModel;

import java.lang.reflect.Array;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.Arrays;
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
        driverList=new ArrayList<>();
        getDataFromRepository();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<DriverModel> getUpdatedRanking() {

        Collections.sort(driverList, DriverModel.driverModelComparator);
        return driverList;
    }

    private void getDataFromRepository(){
        //TODO getting data from repository to be implemented
        //Demo purpose

        for (int i = 0; i <10 ; i++) {
            Random ran = new Random();
            int x = ran.nextInt(6) + 50;
            driverList.add(new DriverModel("FirstName"+i,"LastName"+i,x));
        }
    }
}
