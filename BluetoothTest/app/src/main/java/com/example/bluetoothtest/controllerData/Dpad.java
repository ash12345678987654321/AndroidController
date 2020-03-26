package com.example.bluetoothtest.controllerData;

public class Dpad {
    String up,down,left,right;


    public Dpad(String up, String down, String left, String right){
        this.up=up;
        this.down=down;
        this.left=left;
        this.right=right;
    }

    public void setUp(String up) {
        this.up = up;
    }

    public void setDown(String down) {
        this.down = down;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public String getDir(){
        return up+" "+down+" "+left+" "+right;
    }

}
