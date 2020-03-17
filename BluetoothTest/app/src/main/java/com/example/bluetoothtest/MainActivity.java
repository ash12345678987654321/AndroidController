package com.example.bluetoothtest;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.PrintWriter;
import java.net.Socket;


public class MainActivity extends AppCompatActivity {

    private Button q,w;

    public static String message="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        q=findViewById(R.id.q);
        w=findViewById(R.id.w);

        q.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    message+="Q down|";
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    message+="Q up|";
                }
                return true;
            }
        });

        w.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    message+="W down|";
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    message+="W up|";
                }
                return true;
            }
        });

        DataSender ds=new DataSender();
        ds.start();
    }
}
