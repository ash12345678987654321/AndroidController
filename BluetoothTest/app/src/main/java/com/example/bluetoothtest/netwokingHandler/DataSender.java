package com.example.bluetoothtest.netwokingHandler;

import com.example.bluetoothtest.activities.ControllerActivity;

import java.io.PrintWriter;
import java.net.Socket;

public class DataSender extends Thread {
    @Override
    public void run() {
        while (true) {
            if (ControllerActivity.cmd.length()!=0) {
                //Log.d("ZZZ","Command: "+MainActivity.cmd);
                try {
                    Socket s = new Socket(ControllerActivity.ip, ControllerActivity.port);
                    PrintWriter pw = new PrintWriter(s.getOutputStream());

                    synchronized (this) { //in case we send too many commands because of bad threading
                        pw.write(ControllerActivity.cmd.toString());
                        ControllerActivity.cmd.setLength(0);
                    }

                    pw.flush();
                    pw.close();
                    s.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
