package com.example.bluetoothtest;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.lang.annotation.Inherited;
import java.net.Socket;

public class DataSender extends Thread {

    Socket s;
    PrintWriter pw;

    @Override
    public void run(){
        while (true){
            if (MainActivity.message!=""){
                //Log.d("ZZZ","Sending: "+MainActivity.message);

                try {
                    s = new Socket("192.168.1.229", 7800);
                    pw=new PrintWriter(s.getOutputStream());

                    synchronized (this) {
                        pw.write(MainActivity.message);
                        MainActivity.message = "";
                    }

                    pw.flush();
                    pw.close();
                    s.close();
                }
                catch(Exception e){
                    Log.d("ZZZ",""+e.getMessage());
                }
            }
        }
    }
}
