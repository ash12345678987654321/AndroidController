package com.example.bluetoothtest.controllerData;

import android.util.Log;

import com.example.bluetoothtest.dataStructures.Vector;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Macro {
    private Vector<Command> commands;
    private String fileName;
    private boolean once;

    private MacroThread macroThread = new MacroThread();

    public Macro(String fileName, boolean once) {
        try {
            commands = Macro.getMacros(fileName);
            this.fileName = fileName;
        } catch (Exception e) {
            commands = new Vector<>();
            this.fileName = "";
        }

        this.once = once;
    }

    public void setToggle(boolean toggle) {
        if (toggle) {
            if (!macroThread.isAlive()) {
                macroThread = new MacroThread(commands, once);
                macroThread.start();
            }
        } else {
            macroThread.kill();
        }
    }

    public String getFileName() {
        return fileName;
    }

    public Boolean getOnce() {
        return once;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setOnce(Boolean once) {
        this.once = once;
    }

    public String getOutput() {
        return fileName + "\0" + once;
    }

    public void kill() {
        if (macroThread.isAlive()) macroThread.interrupt();
    }

    //random getters and setters from file because i have no idea where else to dump this
    public static Vector<Command> getMacros(String fileName) throws Exception {
        Vector<Command> res = new Vector<>();

        Log.d("ZZZ", fileName);
        Scanner scanner = new Scanner(new File(fileName));

        scanner.nextLine();

        while (scanner.hasNextLine()) {
            String[] args;
            String temp = scanner.nextLine();

            args = temp.split("\0");

            switch (args[0]) {
                case "KeyStroke":
                    res.add(new KeyStroke(args[1], Boolean.parseBoolean(args[2]), Boolean.parseBoolean(args[3]), args[4]));
                    break;
                case "Text":
                    res.add(new Text(args[1], Boolean.parseBoolean(args[2]), Boolean.parseBoolean(args[3]), args[4]));
                    break;
                case "Delay":
                    res.add(new Delay(Integer.parseInt(args[1]), Boolean.parseBoolean(args[2]), Boolean.parseBoolean(args[3]), args[4]));
                    break;
                case "Loop":
                    res.add(new Loop(Integer.parseInt(args[1]), Boolean.parseBoolean(args[2]), Boolean.parseBoolean(args[3]), args[4]));
                    break;
            }
        }

        return res;
    }

    public static void save(String fileName, Vector<Command> commands) throws Exception {
        File file = new File(fileName);

        String name = new Scanner(file).nextLine();

        PrintWriter pw = new PrintWriter(file);
        pw.println(name);

        for (int i = 0; i < commands.size(); i++) {
            pw.println(commands.get(i).getOutput());
        }

        pw.flush();
        pw.close();
    }
}
