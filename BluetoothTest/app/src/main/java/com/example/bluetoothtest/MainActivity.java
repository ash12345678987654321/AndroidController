package com.example.bluetoothtest;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.net.Socket;


public class MainActivity extends AppCompatActivity {

    private Button q,w;
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
                    MessageSender messageSender=new MessageSender();
                    messageSender.execute("Q down");
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    MessageSender messageSender=new MessageSender();
                    messageSender.execute("Q up");
                }
                return true;
            }
        });

        w.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    MessageSender messageSender=new MessageSender();
                    messageSender.execute("W down");
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    MessageSender messageSender=new MessageSender();
                    messageSender.execute("W up");
                }
                return true;
            }
        });
    }
}
