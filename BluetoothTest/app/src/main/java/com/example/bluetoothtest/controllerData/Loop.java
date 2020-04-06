package com.example.bluetoothtest.controllerData;

import android.util.Pair;

import com.example.bluetoothtest.controllerData.Command;

public class Loop extends Command {
    private int times;

    public Loop(int times,boolean start,boolean end,String id){
        super(start,end,id);
        this.times=times;
    }

    @Override
    public Pair<Integer, String> run(int pos) {
        return null;
    }

    @Override
    public String getPreview(){
        if (isStart()){
            return "Loop start";
        }
        else{
            return "Loop end";
        }
    }

    @Override
    public String getOutput() {
        return "Loop\0"+times+"\0"+super.getOutput();
    }
}