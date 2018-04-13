package com.drivewell.drivewell.coremodule;

import android.os.Bundle;
import android.util.Log;

import com.drivewell.drivewell.constants.EvaluationModuleConstants;
import com.drivewell.drivewell.ui.dashboard.util.Data2D;
import com.drivewell.drivewell.utils.Utils;

public class EvaluationModule {
    private static int POINTS=0;
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

        if ((Utils.round(Math.abs(data2D.acc_brake),100))<-EvaluationModuleConstants.INTENSITY_VALUE ||  (Utils.round(Math.abs(data2D.acc_brake),100))>EvaluationModuleConstants.INTENSITY_VALUE ){
            POINTS= (int) (POINTS-(EvaluationModuleConstants.INTENSITY_VALUE-Utils.round(Math.abs(data2D.acc_brake),100))*10000);
        }else {
            POINTS= (int) (POINTS+(EvaluationModuleConstants.INTENSITY_VALUE+Utils.round(Math.abs(data2D.acc_brake),100))*100);

        }

        if ((Utils.round(Math.abs(data2D.right_left),100))<-EvaluationModuleConstants.INTENSITY_VALUE ||  (Utils.round(Math.abs(data2D.right_left),100))>EvaluationModuleConstants.INTENSITY_VALUE ){
            POINTS= (int) (POINTS-(EvaluationModuleConstants.INTENSITY_VALUE-Utils.round(Math.abs(data2D.acc_brake),100))*10000);
        }else {
            POINTS= (int) (POINTS+(EvaluationModuleConstants.INTENSITY_VALUE+ Utils.round(Math.abs(data2D.acc_brake),100))*100);

        }
        return POINTS;
    }
}
