package com.example.bluetoothtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    public static String cmd ="";

    private Button q,w;

    private SensorManager sensorManager;
    private Sensor accel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("");


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)==null) {
            Toast toast= Toast.makeText(this,"Accelerometer not found :(",Toast.LENGTH_LONG);
            toast.show();
            finish();
        }
        else{
            accel=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }

        q=findViewById(R.id.q);
        w=findViewById(R.id.w);

        q.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    cmd +="Q down|";
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    cmd +="Q up|";
                }
                return true;
            }
        });

        w.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    cmd +="W down|";
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    cmd +="W up|";
                }
                return true;
            }
        });

        DataSender ds=new DataSender();
        ds.start();

    }


    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_FASTEST);

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);

        try{
            String ip = sharedPreferences.getString("ip", "");
            int port= Integer.parseInt(sharedPreferences.getString("port", "2764"));

            Log.d("ZZZ",ip+" "+port);

            DataSender.setIP(ip);
            DataSender.setPort(port);
        }
        catch (Exception e){
            e.printStackTrace();
            DataSender.setInvalid();
            Toast.makeText(this,"Could not parse port",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.top_nav_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settingsBtn:
                startActivity(new Intent(this.getApplicationContext(), SettingsActivity.class));
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        double x=event.values[0],y=event.values[1],z=event.values[2];

        //Log.d("ZZZ","accel: "+x+" "+y+" "+z);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
