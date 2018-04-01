package com.drivewell.drivewell.ui.dashboard.util;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

  public class AccelerometerService extends Service {
      private IBinder m_binder;
      private Data3D m_data;
      private Handler m_handler;
      private SensorManager m_manager;
      private Sensor m_sensor;
      private int m_sensor_accuracy;
      private SensorEventListener m_sensor_listener;

      class C01941 implements SensorEventListener {
          C01941() {
          }

          public void onSensorChanged(SensorEvent sensorEvent) {
              AccelerometerService.this.m_data.f5x = -sensorEvent.values[0];
              AccelerometerService.this.m_data.f6y = -sensorEvent.values[1];
              AccelerometerService.this.m_data.f7z = -sensorEvent.values[2];
              AccelerometerService.this.m_data.timestamp = AccelerometerService.this.sensorTs2Ts(sensorEvent.timestamp);
              if (AccelerometerService.this.m_handler != null) {
                  Message msg = new Message();
                  msg.obj = AccelerometerService.this.m_data;
                  AccelerometerService.this.m_handler.sendMessage(msg);
              }
          }

          public void onAccuracyChanged(Sensor sensor, int i) {
          }
      }

      public class AccelerometerServiceBinder extends Binder {
          public AccelerometerService getService() {
              return AccelerometerService.this;
          }
      }

      public void onCreate() {
          super.onCreate();
          init();
      }

      public IBinder onBind(Intent intent) {
          return this.m_binder;
      }

      public void onDestroy() {
          super.onDestroy();
          try {
              this.m_manager.unregisterListener(this.m_sensor_listener);
          } catch (Exception e) {
              e.printStackTrace();
          }
      }

      public void setHandler(Handler handler) {
          this.m_handler = handler;
      }

      private void init() {
          this.m_binder = new AccelerometerServiceBinder();
          this.m_data = new Data3D(0.0f, 0.0f, 0.0f);
          this.m_manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
          this.m_sensor = this.m_manager.getDefaultSensor(1);
          this.m_sensor_listener = new C01941();
          this.m_sensor_accuracy = 1;
          this.m_manager.registerListener(this.m_sensor_listener, this.m_sensor, this.m_sensor_accuracy);
      }

      private long sensorTs2Ts(long ts) {
          return (System.currentTimeMillis() * 1000000) + (ts - ((ts / 1000000) * 1000000));
      }
  }
