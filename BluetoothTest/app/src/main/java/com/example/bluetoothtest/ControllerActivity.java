package com.example.bluetoothtest;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import static java.lang.Math.PI;
import static java.lang.Math.min;

public class ControllerActivity extends AppCompatActivity {
    public static String cmd ="";

    private RelativeLayout layout;
    private RelativeLayout.LayoutParams layoutParams;
    private View decorView;

    private DataSender ds;

    //for networking to tell other threads what the client is
    public static String ip;
    public static int port;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        //set up TCP with computer to check if it exists
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);


        //check if client exists

        ip = sharedPreferences.getString("ip", "");

        if (!ip.matches("(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])")){
            Toast.makeText(this,"IP is in wrong format",Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        try {
            String port_raw=sharedPreferences.getString("port", "2764");
            port = Integer.parseInt(port_raw);
        }
        catch (NumberFormatException e){
            Toast.makeText(this,"port must be an integer",Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Ping ping=new Ping();
        long start=System.currentTimeMillis();
        ping.start();

        while (true){
            long end=System.currentTimeMillis();
            if (!ping.isAlive()){
                Toast.makeText(this,"Connection succesful. ping: "+(end-start)+"ms",Toast.LENGTH_SHORT).show();
                break; //ok now the port and ip is good
            }
            else if (end-start>1000){ //is 1 seconds enough time?
                ping.interrupt();
                Toast.makeText(this,"network error or computer not responding",Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        }


        layout=findViewById(R.id.layout_controller_tag);

        String preset=getIntent().getStringExtra("preset");
        Log.d("ZZZ","Current layout: "+preset);
        try{
            File file=new File(getFilesDir()+"/"+preset);
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

        ds=new DataSender();
        ds.start();

        //Log.d("ZZZ","Data being sent");
    }

    @Override
    protected void onPause(){
        super.onPause();

        ds.interrupt(); //stop the app from sending anything when not running
        //Log.d("ZZZ","Data stoppped being sent");
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    cmd+="D volumeup\n";
                }
                else if (action==KeyEvent.ACTION_UP){
                    cmd+="U volumeup\n";
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    cmd+="D volumedown\n";
                }
                else if (action==KeyEvent.ACTION_UP){
                    cmd+="U volumedown\n";
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }


    //controller setups (adding them programmically)
    private void Btn (String label,final String output,int height,int width,int marginTop,int marginLeft){
        Button btn=new Button(this);

        btn.setHeight(height);
        btn.setWidth(width);
        btn.setMinimumHeight(0);
        btn.setMinimumWidth(0);
        btn.setText(label);

        btn.setBackgroundResource(R.drawable.button_up);

        layout.addView(btn);

        layoutParams=(RelativeLayout.LayoutParams) btn.getLayoutParams();
        layoutParams.leftMargin=marginLeft;
        layoutParams.topMargin=marginTop;
        btn.setLayoutParams(layoutParams);

        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    cmd +="D "+output+"\n";
                    v.setBackgroundResource(R.drawable.button_down);
                    ((Button) v).setTextColor(getResources().getColor(R.color.background));
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    cmd +="U "+output+"\n";
                    v.setBackgroundResource(R.drawable.button_up);
                    ((Button) v).setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                return true;
            }
        });
    }

    private void Dpad(String up,String down,String left,String right,int diameter,final int marginTop,final int marginLeft){
        final String[] out={right,down,left,up};
        final boolean[] triggered={false,false,false,false};
        final int[] diam={diameter,diameter/4};

        Button btn=new Button(this);
        final Button pointer=new Button(this);

        pointer.setHeight(diam[1]);
        pointer.setWidth(diam[1]);
        pointer.setMinimumHeight(0);
        pointer.setMinimumWidth(0);

        pointer.setBackgroundResource(R.drawable.dpad_pointer);

        layout.addView(pointer);

        layoutParams=(RelativeLayout.LayoutParams) pointer.getLayoutParams();
        layoutParams.leftMargin=marginLeft+(diam[0]-diam[1])/2;
        layoutParams.topMargin=marginTop+(diam[0]-diam[1])/2;
        pointer.setLayoutParams(layoutParams);

        btn.setHeight(diameter);
        btn.setWidth(diameter);
        btn.setMinimumHeight(0);
        btn.setMinimumWidth(0);

        btn.setBackgroundResource(R.drawable.dpad);

        layout.addView(btn);

        layoutParams=(RelativeLayout.LayoutParams) btn.getLayoutParams();
        layoutParams.leftMargin=marginLeft;
        layoutParams.topMargin=marginTop;
        btn.setLayoutParams(layoutParams);

        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction()==MotionEvent.ACTION_MOVE) {
                    double x=event.getX()-(double)diam[0]/2,y=event.getY()-(double)diam[0]/2;
                    double dist=Math.sqrt(x*x+y*y);
                    x /= dist;
                    y /= dist;

                    dist=min(dist,(double)diam[0]/3);

                    layoutParams=(RelativeLayout.LayoutParams) pointer.getLayoutParams();
                    layoutParams.leftMargin=(int)(marginLeft+x*dist)+(diam[0]-diam[1])/2;
                    layoutParams.topMargin=(int)(marginTop+y*dist)+(diam[0]-diam[1])/2;
                    pointer.setLayoutParams(layoutParams);


                    if (dist<(double)diam[0]/10){
                        for (int i=0;i<4;i++){
                            if (triggered[i]){
                                triggered[i]=false;
                                cmd+="U "+out[i]+"\n";
                            }
                        }
                    }
                    else {
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
                                    cmd += "D " + out[i] + "\n";
                                } else {
                                    cmd += "U " + out[i] + "\n";
                                }
                            }
                        }
                    }
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    for (int i=0;i<4;i++){
                        if (triggered[i]){
                            triggered[i]=false;
                            cmd+="U "+out[i]+"\n";
                        }
                    }

                    layoutParams=(RelativeLayout.LayoutParams) pointer.getLayoutParams();
                    layoutParams.leftMargin=marginLeft+(diam[0]-diam[1])/2;
                    layoutParams.topMargin=marginTop+(diam[0]-diam[1])/2;
                    pointer.setLayoutParams(layoutParams);
                }
                return true;
            }
        });

    }
}
