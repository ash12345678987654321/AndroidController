package com.example.bluetoothtest.controllerData;

import android.util.Pair;

import com.example.bluetoothtest.dataStructures.Vector;

public abstract class Command {
    private boolean start, end;
    private String id; //UUID tag to reference it
    private Vector<Command> children = new Vector<>(); //children when collapsing loop

    Command(boolean start, boolean end, String id) {
        this.start = start;
        this.end = end;
        this.id = id;
    }

    abstract public Pair<Integer, String> run(int pos, Vector<Integer> stk);

    public abstract String getPreview();

    public String getOutput() {
        String res = start + "\0" + end + "\0" + id;

        for (int i = 0; i < children.size(); i++) res += "\n" + children.get(i).getOutput();

        return res;
    }

    public boolean notSwappable() {
        return (start || end) && children.isEmpty();
    }

    public boolean isStart() {
        return start;
    }

    public boolean isEnd() {
        return end;
    }

    public String getId() {
        return id;
    }

    public Vector<Command> getChildren() {
        return children;
    }

    public void setChildren(Vector<Command> children) {
        this.children = children;
    }

    public abstract String getArg();

    public abstract Pair<Boolean, String> setArg(String arg);
}