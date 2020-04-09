package com.example.bluetoothtest.controllerData;

public class Dpad {
    final static private int UP = 3, DOWN = 1, LEFT = 2, RIGHT = 0;
    private Btn[] btns = new Btn[4]; //right, down, left, up
    private Boolean[] triggered = {false, false, false, false};

    public Dpad(String u, String d, String l, String r) {
        btns[UP] = new Btn(u);
        btns[DOWN] = new Btn(d);
        btns[LEFT] = new Btn(l);
        btns[RIGHT] = new Btn(r);
    }

    public String setPos(double x, double y) {
        String res = "";
        if (x == 0 && y == 0) {
            for (int i = 0; i < 4; i++) {
                if (triggered[i]) {
                    triggered[i] = false;
                    res += btns[i].up();
                }
            }
        } else {
            double angle = Math.acos(x);
            if (y < 0) angle = 2 * Math.PI - angle;

            boolean[] curr = new boolean[4];

            curr[0] = (6.5 / 4 * Math.PI < angle || angle < 1.5 / 4 * Math.PI);
            curr[1] = (0.5 / 4 * Math.PI < angle && angle < 3.5 / 4 * Math.PI);
            curr[2] = (2.5 / 4 * Math.PI < angle && angle < 5.5 / 4 * Math.PI);
            curr[3] = (4.5 / 4 * Math.PI < angle && angle < 7.5 / 4 * Math.PI);

            for (int i = 0; i < 4; i++) {
                if (curr[i] != triggered[i]) {
                    triggered[i] = curr[i];
                    if (triggered[i]) {
                        res += btns[i].down();
                    } else {
                        res += btns[i].up();
                    }
                }
            }
        }

        return res;
    }

    public String getUp() {
        return btns[UP].getOutput();
    }

    public String getDown() {
        return btns[DOWN].getOutput();
    }

    public String getLeft() {
        return btns[LEFT].getOutput();
    }

    public String getRight() {
        return btns[RIGHT].getOutput();
    }

    public void setDir(String u, String d, String l, String r) {
        btns[UP].setOutput(u);
        btns[DOWN].setOutput(d);
        btns[LEFT].setOutput(l);
        btns[RIGHT].setOutput(r);
    }

    public String getOutput() {
        return btns[UP].getOutput() + "\0" + btns[DOWN].getOutput() + "\0" + btns[LEFT].getOutput() + "\0" + btns[RIGHT].getOutput();
    }

}
