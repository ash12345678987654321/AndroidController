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

import static java.lang.StrictMath.abs;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    public static String cmd ="";

    private Button w,a,s,d,space;

    private SensorManager sensorManager;
    private Sensor accel;

    private DataSender ds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("");


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)==null) {
            Toast toast= Toast.makeText(this,"Accelerometer not found :(",Toast.LENGTH_LONG);
            toast.show();
            finish();
        }
        else{
            accel=sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        }

        w=findViewById(R.id.w);
        a=findViewById(R.id.a);
        s=findViewById(R.id.s);
        d=findViewById(R.id.d);
        space=findViewById(R.id.space);

        //TODO get code to automate this

        w.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    cmd +="D w|";
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    cmd +="U w|";
                }
                return true;
            }
        });

        a.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    cmd +="D a|";
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    cmd +="U a|";
                }
                return true;
            }
        });

        s.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    cmd +="D s|";
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    cmd +="U s|";
                }
                return true;
            }
        });

        d.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    cmd +="D d|";
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    cmd +="U d|";
                }
                return true;
            }
        });

        space.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    cmd +="D space|";
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    cmd +="U space|";
                }
                return true;
            }
        });

        vx=vy=0;
    }


    @Override
    protected void onResume(){
        super.onResume();
        //sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_FASTEST);

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

        ds=new DataSender();
        ds.start();

    }

    @Override
    protected void onPause(){
        super.onPause();
        //sensorManager.unregisterListener(this);

        ds.interrupt(); //stop the app from sending anything when not running
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

    

    //TODO make mouse work

    private final static double threshold=0.15; //acceleration is treated as 0 under this limit
    private final static double scaling=5; //amount velocity will be scaled
    private final static int sample_size=5; //so i dont spam the port
    private final static double alpha=0.8;
    private double prev_x=0,prev_y=0;
    private double vx,vy;
    private double px=0,py=0;
    private int samples=0;

    @Override
    public void onSensorChanged(SensorEvent event){
        double x=event.values[0],y=event.values[1];

        x=alpha*x+(1-alpha)*prev_x;
        y=alpha*y+(1-alpha)*prev_y;

        prev_x=x;
        prev_y=y;

        vx += x * scaling;
        vy += y * scaling;

        px += vx;
        py += vy;

        samples++;

        if (samples==sample_size){
            cmd+="velocity "+px+" "+py+"|";
            samples=0;
            px=py=0;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
