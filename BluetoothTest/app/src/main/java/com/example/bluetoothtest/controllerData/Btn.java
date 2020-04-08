package com.example.bluetoothtest.controllerData;

import android.util.Pair;

public class Btn {
    private String[] output;

    public Btn(String output) {
        setOutput(output);
    }

    public String down() {
        String res = "";

        for (int x = 0; x < output.length; x++) {
            res += "D " + output[x] + "\n";
        }

        return res;
    }

    public String up() {
        String res = "";

        for (int x = output.length - 1; x >= 0; x--) {
            res += "U " + output[x] + "\n";
        }

        return res;
    }

    public void setOutput(String output) {
        if (output.equals("")){ //so apparently now im going to allow users to just not input anything wow
            this.output=new String[0];
        }
        else {
            this.output = output.split(" ");
        }
    }

    public String getOutput() {
        if (output.length==0){
            return "";
        }

        String res = "";

        for (String i : output) {
            res += i;
            res += " ";
        }

        return res.substring(0, res.length() - 1);
    }
}
