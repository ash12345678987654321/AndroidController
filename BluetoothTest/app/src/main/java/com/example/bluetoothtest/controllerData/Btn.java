package com.example.bluetoothtest.controllerData;

import android.util.Log;

import java.util.ArrayList;

public class Btn {
    String[] output;

    public Btn(){}
    public Btn(String output) {
        this();
        setOutput(output);
    }

    public String down(){
        String res="";

        for (int x=0;x<output.length;x++){
            res+="D "+output[x]+"\n";
        }

        return res;
    }

    public String up(){
        String res="";

        for (int x=output.length-1;x>=0;x--){
            res+="U "+output[x]+"\n";
        }

        return res;
    }

    public String setOutput(String output) {
        String[] temp=output.split(" ");

        Log.d("ZZZ",output);

        for (String i:temp){
            if (!KeyCode.valid(i)) return i;
        }

        //success
        this.output=temp;
        return "\0";
    }

    public String getOutput() {
        String res="";

        for (String i:output){
            res+=i;
            res+=" ";
        }

        return res.substring(0,res.length()-1);
    }
}
