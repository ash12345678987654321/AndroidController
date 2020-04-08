package com.example.bluetoothtest.controllerData;

import android.util.Base64;
import android.util.Pair;

public class Text extends Command {
    private String text;

    public Text(String text, boolean start, boolean end, String id) {
        super(start, end, id);

        this.text = new String(Base64.decode(text, Base64.DEFAULT));
    }

    @Override
    public Pair<Integer, String> run(int pos) {
        return null;
    }

    @Override
    public String getArg() {
        return text;
    }

    @Override
    public Pair<Boolean, String> setArg(String arg) {
        if (arg.length() > 100)
            return new Pair<>(true, "Text cannot be longer than 100 characters");

        text = arg;

        return new Pair<>(false, null);
    }

    @Override
    public String getPreview() {
        return text;
    }

    @Override
    public String getOutput() {
        return "Text\0" + Base64.encodeToString(text.getBytes(), Base64.URL_SAFE | Base64.NO_PADDING | Base64.NO_WRAP) + "\0" + super.getOutput();
    }
}