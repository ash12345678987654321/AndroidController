package com.example.bluetoothtest.controllerData;

import android.util.Pair;

public class Btn {
    private String[] output;

    public Btn(){ //TODO refactor code such that we dont need this anymore

    }

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

    public Pair<Boolean, String> setOutput(String output) {
        output=output.trim();

        if (output.equals("")){ //so apparently now im going to allow users to just not input anything wow
            this.output=new String[0];
            return new Pair<>(false, null);
        }

        String[] temp = output.split(" ");

        for (String i : temp) {
            if (KeyCode.invalid(i)) return new Pair<>(true, i);
        }

        //success
        this.output = temp;
        return new Pair<>(false, null);
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
