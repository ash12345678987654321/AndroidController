package com.example.bluetoothtest.controllerData;

public class Dpad {
    String up,down,left,right;

    public Dpad(String up, String down, String left, String right){
        this.up=up;
        this.down=down;
        this.left=left;
        this.right=right;
    }


    public String getUp() {
        return up;
    }

    public String getDown() {
        return down;
    }

    public String getLeft() {
        return left;
    }

    public String getRight() {
        return right;
    }

    public void setDir(String up, String down, String left, String right){
        this.up=up;
        this.down=down;
        this.left=left;
        this.right=right;
    }

    public String getDir(){
        return up+" "+down+" "+left+" "+right;
    }

}
