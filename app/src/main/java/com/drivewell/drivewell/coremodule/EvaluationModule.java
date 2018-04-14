package com.drivewell.drivewell.coremodule;

import android.os.Bundle;
import android.util.Log;

import com.drivewell.drivewell.constants.EvaluationModuleConstants;
import com.drivewell.drivewell.ui.dashboard.util.Data2D;
import com.drivewell.drivewell.utils.Utils;

public class EvaluationModule {
    private static int POINTS=0;
    private static int ACC_POINTS=0;
    private static int BRAKE_POINTS=0;
    private static int TURN_POINTS=0;
    private static EvaluationModule instance;
    public EvaluationModule() {
    }

    public static EvaluationModule newInstance() {
        EvaluationModule fragment = new EvaluationModule();
        return fragment;
    }

    public static EvaluationModule getInstance() {
        return instance==null?new EvaluationModule():instance;
    }

    public int calculatePoints(Data2D data2D){

        Log.d("Evaluate",data2D.toString());
        Log.d("Points",String.valueOf(Utils.round(Math.abs(data2D.acc_brake),100)));


        if (Utils.round(Math.abs(data2D.acc_brake),100)<-EvaluationModuleConstants.INTENSITY_VALUE){
            BRAKE_POINTS= (int) (BRAKE_POINTS-EvaluationModuleConstants.INTENSITY_VALUE-(Utils.round(Math.abs(data2D.acc_brake),100))*100);
        }else {
            BRAKE_POINTS= (int) (BRAKE_POINTS+EvaluationModuleConstants.INTENSITY_VALUE+(Utils.round(Math.abs(data2D.acc_brake),100))*100);

        }

        if ((Utils.round(Math.abs(data2D.acc_brake),100))>EvaluationModuleConstants.INTENSITY_VALUE ){
            ACC_POINTS= (int) (ACC_POINTS-EvaluationModuleConstants.INTENSITY_VALUE-(Utils.round(Math.abs(data2D.acc_brake),100))*100);
        }else {
            ACC_POINTS= (int) (ACC_POINTS+EvaluationModuleConstants.INTENSITY_VALUE+(Utils.round(Math.abs(data2D.acc_brake),100))*100);

        }

        if ((Utils.round(Math.abs(data2D.right_left),100))<-EvaluationModuleConstants.INTENSITY_VALUE || (Utils.round(Math.abs(data2D.right_left),100))>EvaluationModuleConstants.INTENSITY_VALUE ){
            TURN_POINTS= (int) (TURN_POINTS-EvaluationModuleConstants.INTENSITY_VALUE-(Utils.round(Math.abs(data2D.acc_brake),100))*100);
        }else {
            TURN_POINTS= (int) (TURN_POINTS+EvaluationModuleConstants.INTENSITY_VALUE+ (Utils.round(Math.abs(data2D.acc_brake),100))*100);

        }
        return POINTS=ACC_POINTS+BRAKE_POINTS+TURN_POINTS;
    }


    public int getPOINTS() {
        return POINTS;
    }

    public int getAccPoints() {
        return ACC_POINTS;
    }

    public int getBrakePoints() {
        return BRAKE_POINTS;
    }

    public int getTurnPoints() {
        return TURN_POINTS;
    }
}
