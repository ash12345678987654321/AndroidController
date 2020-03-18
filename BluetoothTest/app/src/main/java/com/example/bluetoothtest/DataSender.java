package com.example.bluetoothtest;

import android.util.Log;

import java.io.PrintWriter;
import java.net.Socket;

public class DataSender extends Thread {

    private Socket s;
    private PrintWriter pw;

    private static String ip="127.0.0.1";
    private static int port=0;

    @Override
    public void run(){
        while (true){
            if (MainActivity.cmd !=""){
                try {
                    //Log.d("ZZZ","Sending data to: "+ip+" "+port);
                    s = new Socket(ip,port);
                    pw=new PrintWriter(s.getOutputStream());

                    pw.write(MainActivity.cmd);
                    MainActivity.cmd = "";

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

    public static void setInvalid(){
        DataSender.ip="127.0.0.1";
        DataSender.port=0;
    }

    public static void setIP(String ip){
        DataSender.ip=ip;
    }

    public static void setPort(int port){
        DataSender.port=port;
    }
}
