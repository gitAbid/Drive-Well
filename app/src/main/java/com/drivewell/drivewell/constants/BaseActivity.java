package com.drivewell.drivewell.constants;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by abid on 1/19/18.
 */

public class BaseActivity  extends AppCompatActivity{
    public static Context context;
    public static BaseActivity instance;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
    }

    public static BaseActivity getInstance() {
        if (instance==null) instance=new BaseActivity();
        return instance;
    }
}
