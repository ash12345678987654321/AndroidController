package com.why.controller.controllerData;

import android.util.Log;

public class JoyStick {
    private double sensitivity;

    public JoyStick(double sensitivity) {
        this.sensitivity = sensitivity;
    }

    public String setPos(double x, double y) {
        return "J " + Integer.toString((int) (x * sensitivity)) + " " + Integer.toString((int) (y * sensitivity)) + "\0";
    }

    public double getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(double sensitivity) {
        this.sensitivity = sensitivity;
    }

    public String getOutput() {
        return Double.toString(sensitivity);
    }

}
