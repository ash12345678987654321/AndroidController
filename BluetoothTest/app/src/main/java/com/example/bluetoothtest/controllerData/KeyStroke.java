package com.example.bluetoothtest.controllerData;

import android.util.Pair;

import com.example.bluetoothtest.dataStructures.Vector;

public class KeyStroke extends Command {
    private Btn btn;

    public KeyStroke(String output, boolean start, boolean end, String id) {
        super(start, end, id);
        btn = new Btn(output);
    }

    @Override
    public Pair<Integer, String> run(int pos, Vector<Integer> stk) {
        if (isStart()) return new Pair<>(pos + 1, btn.down());
        else return new Pair<>(pos + 1, btn.up());
    }

    @Override
    public String getArg() {
        return btn.getOutput();
    }

    @Override
    public Pair<Boolean, String> setArg(String arg) {
        Pair<Boolean, String> res = KeyCode.invalid(arg);
        if (res.first) return res;

        btn.setOutput(arg);

        return new Pair<>(false, null);
    }

    @Override
    public String getPreview() {
        return btn.getOutput();
    }

    @Override
    public String getOutput() {
        return "KeyStroke\0" + btn.getOutput() + "\0" + super.getOutput();
    }
}