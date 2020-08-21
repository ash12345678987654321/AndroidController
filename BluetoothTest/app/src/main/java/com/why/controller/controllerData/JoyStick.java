package com.why.controller.controllerData;

public class JoyStick {
    private int sensitivity;

    public JoyStick(int sensitivity) {
        this.sensitivity = sensitivity;
    }

    public String setPos(double x, double y) {
        return "J " + Integer.toString((int) (x * sensitivity / 10.0)) + " " + Integer.toString((int) (y * sensitivity / 10.0)) + "\0";
    }

    public int getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(int sensitivity) {
        this.sensitivity = sensitivity;
    }

    public String getOutput() {
        return Integer.toString(sensitivity);
    }

}
