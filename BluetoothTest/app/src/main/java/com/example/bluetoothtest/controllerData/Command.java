package com.example.bluetoothtest.controllerData;

import android.util.Pair;

public abstract class Command{
    private boolean start, end;
    private String id; //UUID tag to reference it

    Command(boolean start, boolean end, String id){
        this.start=start;
        this.end=end;
        this.id=id;
    }

    abstract public Pair<Integer,String> run(int pos);

    public abstract String getPreview();

    public String getOutput(){
        return start+"\0"+end+"\0"+id;
    };

    public boolean isStart() {
        return start;
    }

    public boolean isEnd() {
        return end;
    }

    public String getId() {
        return id;
    }
}