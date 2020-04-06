package com.example.bluetoothtest.controllerData;

import android.util.Pair;

import com.example.bluetoothtest.controllerData.Btn;
import com.example.bluetoothtest.controllerData.Command;

public class KeyStroke extends Command {
    private Btn btn;

    public KeyStroke(String output,boolean start,boolean end,String id){
        super(start,end,id);
        btn=new Btn(output);
    }

    @Override
    public Pair<Integer,String> run(int pos) {
        return null;
    }

    @Override
    public String getPreview(){
        return btn.getOutput();
    }

    @Override
    public String getOutput() {
        return "KeyStroke\0"+btn.getOutput()+"\0"+super.getOutput();
    }
}