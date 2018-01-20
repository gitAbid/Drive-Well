package com.drivewell.drivewell.datacollectionmodule;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.drivewell.drivewell.R;
import com.drivewell.drivewell.model.SensorDataModel;
import com.getwandup.rxsensor.RxSensor;
import com.getwandup.rxsensor.domain.RxSensorEvent;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import rx.Subscriber;




public class DataCollectionModule extends AppCompatActivity {

    private TextView mAccX,mAccY,mAccZ,mRTVecX,mRTVecY,mRTVecZ,mGravityX,mGravityY,mGravityZ,mGyroX,mGyroY,mGyroZ,mLrAccX,mLrAccY,mLrAccZ;
    private DatabaseReference myRef;
    private Switch mUploadASwitch,mPauseSwitchX,mPauseSwitchY,mPauseSwitchZ;
    private boolean uploadEnable=false;
    private LineChart mLineChart_X_axis;
    private LineChart mLineChart_Y_axis;
    private LineChart mLineChart_Z_axis;

    int index =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_collection_module);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference mAccelerometer=database.getReference("Accelerometer");
        final DatabaseReference mGyroscope=database.getReference("Gyro");
        final DatabaseReference mGravity=database.getReference("Gravity");
        final DatabaseReference mLinearAccelerometer= database.getReference("LinearAccelerometer");
        final DatabaseReference mRotationVector=database.getReference("RotationVector");


        mAccX=findViewById(R.id.tvAcceX);
        mAccY=findViewById(R.id.tvAcceY);
        mAccZ=findViewById(R.id.tvAcceZ);

        mRTVecX=findViewById(R.id.tvRotationVecX);
        mRTVecY=findViewById(R.id.tvRotationVecY);
        mRTVecZ=findViewById(R.id.tvRotationVecZ);

        mGravityX=findViewById(R.id.tvGravityX);
        mGravityY=findViewById(R.id.tvGravityY);
        mGravityZ=findViewById(R.id.tvGravityZ);

        mGyroX=findViewById(R.id.tvGyroX);
        mGyroY=findViewById(R.id.tvGyroY);
        mGyroZ=findViewById(R.id.tvGyroZ);

        mLrAccX=findViewById(R.id.tvLinearAcceX);
        mLrAccY=findViewById(R.id.tvLinearAcceY);
        mLrAccZ=findViewById(R.id.tvLinearAcceZ);


        mUploadASwitch=findViewById(R.id.swUpload);
        mUploadASwitch.setChecked(false);
        mUploadASwitch.setTextColor(Color.RED);


        mPauseSwitchX=findViewById(R.id.swPauseX);
        mPauseSwitchY=findViewById(R.id.swPauseY);
        mPauseSwitchZ=findViewById(R.id.swPauseZ);

        mPauseSwitchX.setChecked(false);
        mPauseSwitchX.setTextColor(Color.GREEN);
        mPauseSwitchY.setChecked(false);
        mPauseSwitchY.setTextColor(Color.GREEN);
        mPauseSwitchZ.setChecked(false);
        mPauseSwitchZ.setTextColor(Color.GREEN);

        mPauseSwitchX.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    mPauseSwitchX.setTextColor(Color.RED);
                    mPauseSwitchX.setText("   Resume   ");
                }else {
                    mPauseSwitchX.setTextColor(Color.GREEN);
                    mPauseSwitchX.setText("   Pause   ");
                }
            }
        });

        mPauseSwitchY.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    mPauseSwitchY.setTextColor(Color.RED);
                    mPauseSwitchY.setText("   Resume   ");
                }else {
                    mPauseSwitchY.setTextColor(Color.GREEN);
                    mPauseSwitchY.setText("   Pause   ");
                }
            }
        });
        mPauseSwitchZ.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    mPauseSwitchZ.setTextColor(Color.RED);
                    mPauseSwitchZ.setText("   Resume   ");
                }else {
                    mPauseSwitchZ.setTextColor(Color.GREEN);
                    mPauseSwitchZ.setText("   Pause   ");
                }
            }
        });

        mLineChart_X_axis=findViewById(R.id.chartX);
        mLineChart_Y_axis=findViewById(R.id.chartY);
        mLineChart_Z_axis=findViewById(R.id.chartZ);

        final ArrayList<Entry> yDataAccelX=new ArrayList<>();
        final ArrayList<Entry> yDataGyroX=new ArrayList<>();
        final ArrayList<Entry> yDataGravityX=new ArrayList<>();
        final ArrayList<Entry> yDataLrAccelX=new ArrayList<>();
        final ArrayList<Entry> yDataRtVectorX=new ArrayList<>();

        yDataAccelX.add(new Entry(0,0));
        yDataGyroX.add(new Entry(0,0));
        yDataGravityX.add(new Entry(0,0));
        yDataLrAccelX.add(new Entry(0,0));
        yDataRtVectorX.add(new Entry(0,0));


        final LineDataSet lineDataSetAccelX=new LineDataSet(yDataAccelX,"Accelarometer");
        lineDataSetAccelX.setColor(Color.GREEN);
        lineDataSetAccelX.setFillAlpha(110);
        lineDataSetAccelX.setDrawCircles(false);

        final LineDataSet lineDataSetGyroX=new LineDataSet(yDataGyroX,"Gyroscope");
        lineDataSetGyroX.setColor(Color.RED);
        lineDataSetGyroX.setFillAlpha(110);
        lineDataSetGyroX.setDrawCircles(false);

        final LineDataSet lineDataSetGravityX=new LineDataSet(yDataGravityX,"Gravity");
        lineDataSetGravityX.setColor(Color.BLUE);
        lineDataSetGravityX.setFillAlpha(110);
        lineDataSetGravityX.setDrawCircles(false);

        final LineDataSet lineDataSetLrAccX=new LineDataSet(yDataLrAccelX,"Linear Accelarometer");
        lineDataSetLrAccX.setColor(Color.YELLOW);
        lineDataSetLrAccX.setFillAlpha(111);
        lineDataSetLrAccX.setDrawCircles(false);


        final LineDataSet lineDataSetRtVectorX=new LineDataSet(yDataRtVectorX,"Rotation Vector");
        lineDataSetRtVectorX.setColor(Color.GRAY);
        lineDataSetRtVectorX.setFillAlpha(110);
        lineDataSetRtVectorX.setDrawCircles(false);




        final ArrayList<ILineDataSet> iLineDataSetsX=new ArrayList<>();
        iLineDataSetsX.add(lineDataSetAccelX);
        iLineDataSetsX.add(lineDataSetGyroX);
        iLineDataSetsX.add(lineDataSetGravityX);
        iLineDataSetsX.add(lineDataSetLrAccX);
        iLineDataSetsX.add(lineDataSetRtVectorX);

        final LineData lineDataX=new LineData(iLineDataSetsX);
        lineDataX.setValueTextSize(8f);
        lineDataX.setValueTextColor(Color.BLACK);
        lineDataX.setValueFormatter(new LargeValueFormatter());



        mLineChart_X_axis.setData(lineDataX);


        final ArrayList<Entry> yDataAccelY=new ArrayList<>();
        final ArrayList<Entry> yDataGyroY=new ArrayList<>();
        final ArrayList<Entry> yDataGravityY=new ArrayList<>();
        final ArrayList<Entry> yDataLrAccelY=new ArrayList<>();
        final ArrayList<Entry> yDataRtVectorY=new ArrayList<>();

        yDataAccelY.add(new Entry(0,0));
        yDataGyroY.add(new Entry(0,0));
        yDataGravityY.add(new Entry(0,0));
        yDataLrAccelY.add(new Entry(0,0));
        yDataRtVectorY.add(new Entry(0,0));


        final LineDataSet lineDataSetAccelY=new LineDataSet(yDataAccelY,"Accelarometer");
        lineDataSetAccelY.setColor(Color.GREEN);
        lineDataSetAccelY.setFillAlpha(110);
        lineDataSetAccelY.setDrawCircles(false);

        final LineDataSet lineDataSetGyroY=new LineDataSet(yDataGyroY,"Gyroscope");
        lineDataSetGyroY.setColor(Color.RED);
        lineDataSetGyroY.setFillAlpha(110);
        lineDataSetGyroY.setDrawCircles(false);

        final LineDataSet lineDataSetGravityY=new LineDataSet(yDataGravityY,"Gravity");
        lineDataSetGravityY.setColor(Color.BLUE);
        lineDataSetGravityY.setFillAlpha(110);
        lineDataSetGravityY.setDrawCircles(false);

        final LineDataSet lineDataSetLrAccY=new LineDataSet(yDataLrAccelY,"Linear Accelarometer");
        lineDataSetLrAccY.setColor(Color.YELLOW);
        lineDataSetLrAccY.setFillAlpha(111);
        lineDataSetLrAccY.setDrawCircles(false);


        final LineDataSet lineDataSetRtVectorY=new LineDataSet(yDataRtVectorY,"Rotation Vector");
        lineDataSetRtVectorY.setColor(Color.GRAY);
        lineDataSetRtVectorY.setFillAlpha(110);
        lineDataSetRtVectorY.setDrawCircles(false);




        final ArrayList<ILineDataSet> iLineDataSetsY=new ArrayList<>();
        iLineDataSetsY.add(lineDataSetAccelY);
        iLineDataSetsY.add(lineDataSetGyroY);
        iLineDataSetsY.add(lineDataSetGravityY);
        iLineDataSetsY.add(lineDataSetLrAccY);
        iLineDataSetsY.add(lineDataSetRtVectorY);

        final LineData lineDataY=new LineData(iLineDataSetsY);
        lineDataY.setValueTextSize(8f);
        lineDataY.setValueTextColor(Color.BLACK);
        lineDataY.setValueFormatter(new LargeValueFormatter());



        mLineChart_Y_axis.setData(lineDataY);


        final ArrayList<Entry> yDataAccelZ=new ArrayList<>();
        final ArrayList<Entry> yDataGyroZ=new ArrayList<>();
        final ArrayList<Entry> yDataGravityZ=new ArrayList<>();
        final ArrayList<Entry> yDataLrAccelZ=new ArrayList<>();
        final ArrayList<Entry> yDataRtVectorZ=new ArrayList<>();

        yDataAccelZ.add(new Entry(0,0));
        yDataGyroZ.add(new Entry(0,0));
        yDataGravityZ.add(new Entry(0,0));
        yDataLrAccelZ.add(new Entry(0,0));
        yDataRtVectorZ.add(new Entry(0,0));


        final LineDataSet lineDataSetAccelZ=new LineDataSet(yDataAccelZ,"Accelarometer");
        lineDataSetAccelZ.setColor(Color.GREEN);
        lineDataSetAccelZ.setFillAlpha(110);
        lineDataSetAccelZ.setDrawCircles(false);

        final LineDataSet lineDataSetGyroZ=new LineDataSet(yDataGyroY,"Gyroscope");
        lineDataSetGyroZ.setColor(Color.RED);
        lineDataSetGyroZ.setFillAlpha(110);
        lineDataSetGyroZ.setDrawCircles(false);

        final LineDataSet lineDataSetGravityZ=new LineDataSet(yDataGravityZ,"Gravity");
        lineDataSetGravityZ.setColor(Color.BLUE);
        lineDataSetGravityZ.setFillAlpha(110);
        lineDataSetGravityZ.setDrawCircles(false);

        final LineDataSet lineDataSetLrAccZ=new LineDataSet(yDataLrAccelZ,"Linear Accelarometer");
        lineDataSetLrAccZ.setColor(Color.YELLOW);
        lineDataSetLrAccZ.setFillAlpha(111);
        lineDataSetLrAccZ.setDrawCircles(false);


        final LineDataSet lineDataSetRtVectorZ=new LineDataSet(yDataRtVectorZ,"Rotation Vector");
        lineDataSetRtVectorZ.setColor(Color.GRAY);
        lineDataSetRtVectorZ.setFillAlpha(110);
        lineDataSetRtVectorZ.setDrawCircles(false);



        final ArrayList<ILineDataSet> iLineDataSetsZ=new ArrayList<>();
        iLineDataSetsZ.add(lineDataSetAccelZ);
        iLineDataSetsZ.add(lineDataSetGyroZ);
        iLineDataSetsZ.add(lineDataSetGravityZ);
        iLineDataSetsZ.add(lineDataSetLrAccZ);
        iLineDataSetsZ.add(lineDataSetRtVectorZ);

        final LineData lineDataZ=new LineData(iLineDataSetsZ);
        lineDataZ.setValueTextSize(8f);
        lineDataZ.setValueTextColor(Color.BLACK);
        lineDataZ.setValueFormatter(new LargeValueFormatter());



        mLineChart_Z_axis.setData(lineDataZ);


        RxSensor mAccel=new RxSensor(this);
        RxSensor mGrav=new RxSensor(this);
        RxSensor mGyro=new RxSensor(this);
        RxSensor mLrAccel=new RxSensor(this);
        RxSensor mRtVec=new RxSensor(this);

        mAccel.observe(Sensor.TYPE_ACCELEROMETER, SensorManager.SENSOR_DELAY_NORMAL)
                .subscribe(new Subscriber<RxSensorEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RxSensorEvent rxSensorEvent) {

                        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                        if (uploadEnable){
                            mAccelerometer.child(currentDateTimeString).setValue(new SensorDataModel(rxSensorEvent.values[0],
                                    rxSensorEvent.values[1],
                                    rxSensorEvent.values[2]));
                        }
                        
                        if (!mPauseSwitchX.isChecked()){
                            yDataAccelX.add(new Entry(index,rxSensorEvent.values[0]));
                            lineDataSetAccelX.notifyDataSetChanged();
                            lineDataX.notifyDataChanged();
                            mLineChart_X_axis.notifyDataSetChanged();
                            mLineChart_X_axis.invalidate();

                        }

                        if (!mPauseSwitchY.isChecked()){

                            yDataAccelY.add(new Entry(index,rxSensorEvent.values[1]));
                            lineDataSetAccelY.notifyDataSetChanged();
                            lineDataY.notifyDataChanged();
                            mLineChart_Y_axis.notifyDataSetChanged();
                            mLineChart_Y_axis.invalidate();
                        }

                        if (!mPauseSwitchZ.isChecked()){

                            yDataAccelZ.add(new Entry(index,rxSensorEvent.values[2]));
                            lineDataSetAccelZ.notifyDataSetChanged();
                            lineDataZ.notifyDataChanged();
                            mLineChart_Z_axis.notifyDataSetChanged();
                            mLineChart_Z_axis.invalidate();
                        }
                        
                        mAccX.setText(String.valueOf(rxSensorEvent.values[0]));
                        mAccY.setText(String.valueOf(rxSensorEvent.values[1]));
                        mAccZ.setText(String.valueOf(rxSensorEvent.values[2]));

                        index++;
                    }
                });

        mGrav.observe(Sensor.TYPE_GYROSCOPE, SensorManager.SENSOR_DELAY_NORMAL)
                .subscribe(new Subscriber<RxSensorEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RxSensorEvent rxSensorEvent) {
                        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                        if (uploadEnable){
                            mGyroscope.child(currentDateTimeString).setValue(new SensorDataModel(rxSensorEvent.values[0],
                                    rxSensorEvent.values[1],
                                    rxSensorEvent.values[2]));
                        }
                        
                        if (!mPauseSwitchX.isChecked()){

                            yDataGyroX.add(new Entry(index,rxSensorEvent.values[0]));
                            lineDataSetGyroX.notifyDataSetChanged();
                            lineDataX.notifyDataChanged();
                            mLineChart_X_axis.notifyDataSetChanged();
                            mLineChart_X_axis.invalidate();
                        }

                       if (!mPauseSwitchY.isChecked()){

                           yDataGyroY.add(new Entry(index,rxSensorEvent.values[1]));
                           lineDataSetGyroY.notifyDataSetChanged();
                           lineDataY.notifyDataChanged();
                           mLineChart_Y_axis.notifyDataSetChanged();
                           mLineChart_Y_axis.invalidate();
                       }

                      if (!mPauseSwitchZ.isChecked()){

                          yDataGyroZ.add(new Entry(index,rxSensorEvent.values[2]));
                          lineDataSetGyroZ.notifyDataSetChanged();
                          lineDataY.notifyDataChanged();
                          mLineChart_Z_axis.notifyDataSetChanged();
                          mLineChart_Z_axis.invalidate();
                      }

                        index++;

                        mGyroX.setText(String.valueOf(rxSensorEvent.values[0]));
                        mGyroY.setText(String.valueOf(rxSensorEvent.values[1]));
                        mGyroZ.setText(String.valueOf(rxSensorEvent.values[2]));
                    }
                });

        mGyro.observe(Sensor.TYPE_GRAVITY, SensorManager.SENSOR_DELAY_NORMAL)
                .subscribe(new Subscriber<RxSensorEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RxSensorEvent rxSensorEvent) {
                        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                        if (uploadEnable){
                            mGravity.child(currentDateTimeString).setValue(new SensorDataModel(rxSensorEvent.values[0],
                                    rxSensorEvent.values[1],
                                    rxSensorEvent.values[2]));
                        }

                        if (!mPauseSwitchX.isChecked()){

                            yDataGravityX.add(new Entry(index,rxSensorEvent.values[0]));
                            lineDataSetGravityX.notifyDataSetChanged();
                            lineDataX.notifyDataChanged();
                            mLineChart_X_axis.notifyDataSetChanged();
                            mLineChart_X_axis.invalidate();
                        }

                       if (!mPauseSwitchY.isChecked()){

                           yDataGravityY.add(new Entry(index,rxSensorEvent.values[1]));
                           lineDataSetGravityY.notifyDataSetChanged();
                           lineDataY.notifyDataChanged();
                           mLineChart_Y_axis.notifyDataSetChanged();
                           mLineChart_Y_axis.invalidate();
                       }

                        if (!mPauseSwitchZ.isChecked()){

                            yDataGravityZ.add(new Entry(index,rxSensorEvent.values[2]));
                            lineDataSetGravityZ.notifyDataSetChanged();
                            lineDataZ.notifyDataChanged();
                            mLineChart_Z_axis.notifyDataSetChanged();
                            mLineChart_Z_axis.invalidate();
                        }

                        index++;
                        
                        mGravityX.setText(String.valueOf(rxSensorEvent.values[0]));
                        mGravityY.setText(String.valueOf(rxSensorEvent.values[1]));
                        mGravityZ.setText(String.valueOf(rxSensorEvent.values[2]));
                    }
                });

        mLrAccel.observe(Sensor.TYPE_LINEAR_ACCELERATION, SensorManager.SENSOR_DELAY_NORMAL)
                .subscribe(new Subscriber<RxSensorEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RxSensorEvent rxSensorEvent) {
                        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                        if (uploadEnable){
                            mLinearAccelerometer.child(currentDateTimeString).setValue(new SensorDataModel(rxSensorEvent.values[0],
                                    rxSensorEvent.values[1],
                                    rxSensorEvent.values[2]));
                        }

                        if (!mPauseSwitchX.isChecked()){
                            yDataLrAccelX.add(new Entry(index,rxSensorEvent.values[0]));
                            lineDataSetLrAccX.notifyDataSetChanged();
                            lineDataX.notifyDataChanged();
                            mLineChart_X_axis.notifyDataSetChanged();
                            mLineChart_X_axis.invalidate();
                        }

                        if (!mPauseSwitchY.isChecked()){
                            yDataLrAccelY.add(new Entry(index,rxSensorEvent.values[1]));
                            lineDataSetLrAccY.notifyDataSetChanged();
                            lineDataY.notifyDataChanged();
                            mLineChart_Y_axis.notifyDataSetChanged();
                            mLineChart_Y_axis.invalidate();
                        }

                        if (!mPauseSwitchZ.isChecked()){
                            yDataLrAccelZ.add(new Entry(index,rxSensorEvent.values[2]));
                            lineDataSetLrAccZ.notifyDataSetChanged();
                            lineDataZ.notifyDataChanged();
                            mLineChart_Z_axis.notifyDataSetChanged();
                            mLineChart_Z_axis.invalidate();
                        }

                        index++;

                        mLrAccX.setText(String.valueOf(rxSensorEvent.values[0]));
                        mLrAccY.setText(String.valueOf(rxSensorEvent.values[1]));
                        mLrAccZ.setText(String.valueOf(rxSensorEvent.values[2]));
                    }
                });

        mRtVec.observe(Sensor.TYPE_ROTATION_VECTOR, SensorManager.SENSOR_DELAY_NORMAL)
                .subscribe(new Subscriber<RxSensorEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RxSensorEvent rxSensorEvent) {
                        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                        if (uploadEnable){
                            mRotationVector.child(currentDateTimeString).setValue(new SensorDataModel(rxSensorEvent.values[0],
                                    rxSensorEvent.values[1],
                                    rxSensorEvent.values[2]));
                        }

                       if (!mPauseSwitchX.isChecked()){
                           yDataRtVectorX.add(new Entry(index,rxSensorEvent.values[0]));
                           lineDataSetRtVectorX.notifyDataSetChanged();
                           lineDataX.notifyDataChanged();
                           mLineChart_X_axis.notifyDataSetChanged();
                           mLineChart_X_axis.invalidate();

                       }

                       if (!mPauseSwitchY.isChecked()){
                           yDataRtVectorY.add(new Entry(index,rxSensorEvent.values[1]));
                           lineDataSetRtVectorY.notifyDataSetChanged();
                           lineDataY.notifyDataChanged();
                           mLineChart_Y_axis.notifyDataSetChanged();
                           mLineChart_Y_axis.invalidate();
                       }

                       if (!mPauseSwitchZ.isChecked()){
                           yDataRtVectorZ.add(new Entry(index,rxSensorEvent.values[2]));
                           lineDataSetRtVectorZ.notifyDataSetChanged();
                           lineDataZ.notifyDataChanged();
                           mLineChart_Z_axis.notifyDataSetChanged();
                           mLineChart_Z_axis.invalidate();
                       }


                        index++;

                        mRTVecX.setText(String.valueOf(rxSensorEvent.values[0]));
                        mRTVecY.setText(String.valueOf(rxSensorEvent.values[1]));
                        mRTVecZ.setText(String.valueOf(rxSensorEvent.values[2]));
                    }
                });


        mUploadASwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                uploadEnable=b;
                if (uploadEnable){
                    mUploadASwitch.setTextColor(Color.GREEN);
                }else {
                    mUploadASwitch.setTextColor(Color.RED);
                }
            }
        });


    }
}
