package com.example.bluetoothtest;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.lang.annotation.Inherited;
import java.net.Socket;

public class MessageSender extends AsyncTask<String,Void,Void> {


    Socket s;
    DataOutputStream dos;
    PrintWriter pw;

    @Override
    protected Void doInBackground(String... voids){

        String msg= voids[0];

        try{
            s=new Socket("192.168.1.229",7800);
            pw=new PrintWriter(s.getOutputStream());
            pw.write(msg);
            pw.flush();
            pw.close();
            s.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
