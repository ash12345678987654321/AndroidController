package com.example.bluetoothtest;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import java.io.File;
import java.util.Scanner;

public class ControllerActivity extends AppCompatActivity implements SensorEventListener {
    public static String cmd ="";

    private RelativeLayout layout;
    private View decorView;

    private SensorManager sensorManager;
    private Sensor accel;

    private DataSender ds;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        layout=findViewById(R.id.layout_controller_tag);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)==null) {
            Toast toast= Toast.makeText(this,"Accelerometer not found :(",Toast.LENGTH_LONG);
            toast.show();
            finish();
        }
        else{
            accel=sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        }

        String preset=getIntent().getStringExtra("preset");
        Log.d("ZZZ",preset);
        try{
            File file=new File(getFilesDir()+"/"+preset+".txt");
            Scanner scanner=new Scanner(file);

            while (scanner.hasNext()){
                switch(scanner.next()){
                    case "Btn":
                        Btn(scanner.next(),scanner.next(),scanner.nextInt(),scanner.nextInt(),scanner.nextInt(),scanner.nextInt());
                        break;

                    case "Dpad":
                        Dpad(scanner.next(),scanner.next(),scanner.next(),scanner.next(),scanner.nextInt(),scanner.nextInt(),scanner.nextInt());
                        break;
                }
            }


        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"File corrupted >.<",Toast.LENGTH_SHORT).show();
            finish();
        }

        //code to make app bigger
        decorView=getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility==0){
                    setHighVisibility();
                }
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            setHighVisibility();
        }
    }

    private void setHighVisibility(){ //hide nav bar and make app fullscreen
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|
                View.SYSTEM_UI_FLAG_FULLSCREEN|
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        );
    }

    @Override
    public void onResume() {
        super.onResume();

        //sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_FASTEST);

        ds=new DataSender();
        ds.start();
    }

    @Override
    protected void onPause(){
        super.onPause();
        //sensorManager.unregisterListener(this);

        ds.interrupt(); //stop the app from sending anything when not running
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


    //controller setups (adding them programmically)
    private void Btn (String label,String output,int height,int width,int marginTop,int marginLeft){
        final String[] out={output}; //what the fuck

        Button btn=new Button(this);

        btn.setId(View.generateViewId());
        btn.setHeight(height);
        btn.setWidth(width);
        btn.setMinimumHeight(0);
        btn.setMinimumWidth(0);
        btn.setText(label);

        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    cmd +="D "+out[0]+"|";
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    cmd +="U "+out[0]+"|";
                }
                return true;
            }
        });

        layout.addView(btn);

        RelativeLayout.LayoutParams layoutParams=(RelativeLayout.LayoutParams) btn.getLayoutParams();
        layoutParams.leftMargin=marginLeft;
        layoutParams.topMargin=marginTop;
        btn.setLayoutParams(layoutParams);
    }

    private void Dpad(String up,String down,String left,String right,int diameter,int marginTop,int marginLeft){
        final String[] out={right,down,left,up};
        final boolean[] triggered={false,false,false,false};
        final double[] diam={diameter};

        Button btn=new Button(this);

        btn.setId(View.generateViewId());
        btn.setHeight(diameter);
        btn.setWidth(diameter);
        btn.setMinimumHeight(0);
        btn.setMinimumWidth(0);

        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction()==MotionEvent.ACTION_MOVE) {
                    double x=event.getX()-diam[0]/2,y=event.getY()-diam[0]/2;
                    double dist=Math.sqrt(x*x+y*y);
                    if (dist<diam[0]/6){
                        for (int i=0;i<4;i++){
                            if (triggered[i]){
                                triggered[i]=false;
                                cmd+="U "+out[i]+"|";
                            }
                        }
                    }
                    else {
                        x /= dist;
                        y /= dist;

                        double angle = Math.acos(x);
                        if (y < 0) angle = 2 * Math.PI - angle;

                        boolean[] curr = new boolean[4];

                        curr[0] = (6.5 / 4 * Math.PI < angle || angle < 1.5 / 4 * Math.PI);
                        curr[1] = (0.5 / 4 * Math.PI < angle && angle < 3.5 / 4 * Math.PI);
                        curr[2] = (2.5 / 4 * Math.PI < angle && angle < 5.5 / 4 * Math.PI);
                        curr[3] = (4.5 / 4 * Math.PI < angle && angle < 7.5 / 4 * Math.PI);

                        for (int i = 0; i < 4; i++) {
                            if (curr[i] != triggered[i]) {
                                triggered[i] = curr[i];
                                if (triggered[i]) {
                                    cmd += "D " + out[i] + "|";
                                } else {
                                    cmd += "U " + out[i] + "|";
                                }
                            }
                        }
                    }
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    for (int i=0;i<4;i++){
                        if (triggered[i]){
                            triggered[i]=false;
                            cmd+="U "+out[i]+"|";
                        }
                    }
                }
                return true;
            }
        });

        layout.addView(btn);

        RelativeLayout.LayoutParams layoutParams=(RelativeLayout.LayoutParams) btn.getLayoutParams();
        layoutParams.leftMargin=marginLeft;
        layoutParams.topMargin=marginTop;
        btn.setLayoutParams(layoutParams);
    }
}