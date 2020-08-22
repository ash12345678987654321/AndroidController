package com.why.controller.controllerData;

import com.why.controller.bluetooth.Main;

public class JoyStick {
    private int sensitivity;

    public JoyStick(int sensitivity) {
        this.sensitivity = sensitivity;
    }

    public void setPos(double x, double y) {
        Main.mouse.sendMouseMove((int)(x * sensitivity / 10.0),(int)(y * sensitivity / 10.0)); //this directly sends inputs to bluetooth thing
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
