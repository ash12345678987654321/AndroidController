package com.example.bluetoothtest;

import android.util.Log;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class DataSender extends Thread {
    @Override
    public void run(){
        while (true){
            if (ControllerActivity.cmd !=""){
                //Log.d("ZZZ","Command: "+MainActivity.cmd);
                try {
                    Socket s = new Socket(ControllerActivity.ip,ControllerActivity.port);
                    PrintWriter pw=new PrintWriter(s.getOutputStream());

                    synchronized (this) { //in case we send too many commands because of bad threading
                        pw.write(ControllerActivity.cmd);
                        ControllerActivity.cmd = "";
                    }

                    pw.flush();
                    pw.close();
                    s.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
