package com.example.bluetoothtest.controllerData;

import android.util.Pair;

public class Text extends Command {
    private String text;

    public Text(String text, boolean start, boolean end, String id) {
        super(start, end, id);
        this.text = text;
    }

    @Override
    public Pair<Integer, String> run(int pos) {
        return null;
    }

    @Override
    public String getPreview() {
        return text;
    }

    @Override
    public String getOutput() {
        return "Text\0" + text + "\0" + super.getOutput();
    }
}