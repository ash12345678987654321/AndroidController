package com.why.controller.controllerData;

import android.util.Pair;

import com.why.controller.dataStructures.Vector;

public class Loop extends Command {
    private int times;
    private int curr = 0;

    public Loop(int times, boolean start, boolean end, String id) {
        super(start, end, id);
        this.times = times;
    }

    @Override
    public Pair<Integer, String> run(int pos, Vector<Integer> stk) {
        curr++;
        if (curr == times) {
            curr = 0;
            return new Pair<>(pos + 1, "");
        }

        if (isStart()) {
            stk.add(pos);
            return new Pair<>(pos + 1, "");
        } else {
            int temp = stk.back();
            stk.pop();
            return new Pair<>(temp, "");
        }
    }

    @Override
    public String getArg() {
        return Integer.toString(times);
    }

    @Override
    public Pair<Boolean, String> setArg(String arg) {
        if (arg.length() > 9) return new Pair<>(true, "Cannot loop more than 1 billion times");

        times = Integer.parseInt(arg);

        return new Pair<>(false, null);
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