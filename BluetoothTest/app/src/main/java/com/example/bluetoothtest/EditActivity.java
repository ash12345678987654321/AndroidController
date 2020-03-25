package com.example.bluetoothtest;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class EditActivity extends AppCompatActivity {
    private RelativeLayout layout;

    private View decorView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        layout=findViewById(R.id.layout_controller_tag);

        String preset=getIntent().getStringExtra("preset");
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
    public void onBackPressed(){
        Log.d("ZZZ","Back is pressed");

        String preset=getIntent().getStringExtra("preset");
        try{
            File file=new File(getFilesDir()+"/"+preset+".txt");
            PrintWriter pw=new PrintWriter(file);

            for (int x=0;x<layout.getChildCount();x++){
                Button v=(Button) layout.getChildAt(x);
                String[] args=v.getTag().toString().split(" ");
                RelativeLayout.LayoutParams layoutParams=(RelativeLayout.LayoutParams) v.getLayoutParams();

                switch (args[0]){
                    case "Btn":
                        pw.write("Btn "+v.getText().toString()+" "+args[1]+" "+v.getHeight()+" "+v.getWidth()+" "+layoutParams.topMargin+" "+layoutParams.leftMargin+"\n");
                        break;

                    case "Dpad":
                        pw.write("Dpad "+args[1]+" "+args[2]+" "+args[3]+" "+args[4]+" "+v.getHeight()+" "+layoutParams.topMargin+" "+layoutParams.leftMargin+"\n");
                        break;
                }
            }

            pw.close();
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"File corrupted >.<",Toast.LENGTH_SHORT).show();
        }

        super.onBackPressed();
    }


    //controller setups (adding them programmically)
    private void Btn (String label, String output, int height, int width, int marginTop, int marginLeft){
        Button btn=new Button(this);

        btn.setId(View.generateViewId());
        btn.setHeight(height);
        btn.setWidth(width);
        btn.setMinimumHeight(0);
        btn.setMinimumWidth(0);
        btn.setText(label);

        btn.setTag("Btn "+output);

        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction()==MotionEvent.ACTION_MOVE) {
                    RelativeLayout.LayoutParams layoutParams=(RelativeLayout.LayoutParams) v.getLayoutParams();
                    //Log.d("ZZZ",event.getX()+" "+event.getY());
                    layoutParams.leftMargin+=(event.getX()-v.getWidth()/2);
                    layoutParams.topMargin+=(event.getY()-v.getHeight()/2);
                    v.setLayoutParams(layoutParams);

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
        Button btn=new Button(this);

        btn.setId(View.generateViewId());
        btn.setHeight(diameter);
        btn.setWidth(diameter);
        btn.setMinimumHeight(0);
        btn.setMinimumWidth(0);

        btn.setTag("Dpad "+up+" "+down+" "+left+" "+right);

        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction()==MotionEvent.ACTION_MOVE) {
                    RelativeLayout.LayoutParams layoutParams=(RelativeLayout.LayoutParams) v.getLayoutParams();
                    //Log.d("ZZZ",event.getX()+" "+event.getY());
                    layoutParams.leftMargin+=(event.getX()-v.getWidth()/2);
                    layoutParams.topMargin+=(event.getY()-v.getHeight()/2);
                    v.setLayoutParams(layoutParams);

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
