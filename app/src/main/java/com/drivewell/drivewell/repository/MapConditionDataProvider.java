package com.drivewell.drivewell.repository;

import android.util.Log;

import com.drivewell.drivewell.model.RoadConditionMapModel;
import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by abid on 1/29/18.
 */

public class MapConditionDataProvider implements IDataProvider {

    BufferedReader roadMapCondition;
    String line;
    List<RoadConditionMapModel> roadConditionMapModels;

    LatLng latLng11=new LatLng(23.765998,90.3936009);
    LatLng latLng12=new LatLng(23.7728319,90.3959183);

    LatLng latLng21=new LatLng(23.7728319,90.3959183);
    LatLng latLng22=new LatLng(23.8048951,90.4021196);

    LatLng latLng31=new LatLng(23.8048951,90.4021196);
    LatLng latLng32=new LatLng(23.8284516,90.4112606);

    LatLng latLng41=new LatLng(23.8039741,90.3539119);
    LatLng latLng42=new LatLng(23.7996942,90.3513799);

    LatLng latLng51=new LatLng(23.8039741,90.3539119);
    LatLng latLng52=new LatLng(23.7952963,90.3464876);



    public MapConditionDataProvider() {
        initializeList();
    }

    @Override
    public List<?> getListData() {
        return roadConditionMapModels;
    }

    public void initializeList(){
       roadConditionMapModels=new LinkedList<>();
       roadConditionMapModels.add(new RoadConditionMapModel(latLng11,latLng12,"g"));
       roadConditionMapModels.add(new RoadConditionMapModel(latLng21,latLng22,"b"));
       roadConditionMapModels.add(new RoadConditionMapModel(latLng31,latLng32,"g"));
       roadConditionMapModels.add(new RoadConditionMapModel(latLng41,latLng42,"b"));
       roadConditionMapModels.add(new RoadConditionMapModel(latLng51,latLng52,"g"));
    }
}

