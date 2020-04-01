package com.example.bluetoothtest;

import android.util.Log;

import java.io.PrintWriter;
import java.net.Socket;

public class Ping extends Thread {
    @Override
    public void run(){
        try {
            Log.d("ZZZ",ControllerActivity.ip+" "+ControllerActivity.port);
            Socket socket = new Socket(ControllerActivity.ip, ControllerActivity.port);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.write("PING!");
            printWriter.flush();
            printWriter.close();

            //Log.d("ZZZ","worked");
        }
        catch (Exception e){
            e.printStackTrace();
            try {
                Thread.sleep(1000); //waste time
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
