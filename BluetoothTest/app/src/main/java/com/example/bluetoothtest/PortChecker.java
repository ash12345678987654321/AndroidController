package com.example.bluetoothtest;

public class PortChecker implements Runnable {
    private volatile int value=-1;

    @Override
    public void run() {

    }

    public int getValue() {
        return value;
    }
}