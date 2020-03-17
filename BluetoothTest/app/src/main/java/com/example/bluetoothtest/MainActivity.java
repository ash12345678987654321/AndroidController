package com.example.bluetoothtest;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT=1;
    private static final UUID uuid= UUID.fromString("35e7a034-25c3-4d78-a9b5-0b91e795251f");

    private BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
    private BluetoothSocket mmSocket=null;

    private OutputStream mmOutput=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!bluetoothAdapter.isEnabled()){
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        Set<BluetoothDevice> devices=bluetoothAdapter.getBondedDevices();

        for (BluetoothDevice device:devices) {
            Log.d("ZZZ","Trying device "+device.getName()+" "+device.getAddress());
            BluetoothSocket tmp = null;

            try {
                // Get a BluetoothSocket to connect with the given BluetoothDevice.
                // MY_UUID is the app's UUID string, also used in the server code.
                tmp = device.createRfcommSocketToServiceRecord(uuid);
            } catch (IOException e) {
                Log.e("ZZZ", "Socket's create() method failed", e);
            }
            mmSocket = tmp;

            if (mmSocket!=null) break;
        }

        Log.d("ZZZ","SUccesfully connected with "+mmSocket.getRemoteDevice().getName());

        try{
            mmSocket.connect();
            mmOutput=mmSocket.getOutputStream();
        }
        catch (Exception e){
            Log.d("ZZZ",e.getMessage());
        }
    }

    public void sendData(View view){
        Log.d("ZZZ","Something happened");
        try {
            mmOutput.write("Hello".getBytes());
        }
        catch(Exception e){
            Log.d("ZZZ",e.getMessage());
        }

    }
}
