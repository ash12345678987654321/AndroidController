package com.example.bluetoothtest.controllerData;

import android.util.Pair;

public class Loop extends Command {
    private int times;

    public Loop(int times, boolean start, boolean end, String id) {
        super(start, end, id);
        this.times = times;
    }

    @Override
    public Pair<Integer, String> run(int pos) {
        return null;
    }

    @Override
    public String getArg(){
        return Integer.toString(times);
    }

    @Override
    public Pair<Boolean,String> setArg(String arg){
        if (arg.length()>9) return new Pair<>(true,"Cannot loop more than 1 billion times");

        times=Integer.parseInt(arg);

        return new Pair<>(false,null);
    }

    @Override
    public String getPreview() {
        if (isStart()) {
            return "Loop start";
        } else {
            return "Loop end";
        }
    }

    @Override
    public String getOutput() {
        return "Loop\0" + times + "\0" + super.getOutput();
    }
}