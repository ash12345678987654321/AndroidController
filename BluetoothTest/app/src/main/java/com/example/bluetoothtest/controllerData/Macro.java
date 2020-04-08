package com.example.bluetoothtest.controllerData;

import android.util.Log;
import android.util.Pair;

import com.example.bluetoothtest.activities.ControllerActivity;
import com.example.bluetoothtest.dataStructures.Vector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Macro extends Thread {
    private Vector<Command> commands;

    public Macro(String fileName) throws FileNotFoundException {
        commands = Macro.getMacros(fileName);
    }

    @Override
    public void run() {
        //for when we are actually excuting this

        //basic idea is that everyone increments pointer by 1
        // for loops we can use a stack since they form a valid bracket sequence
        // the loop start pushes its index into a stack and the end loop access takes from the top of the stack
        // now we need for them to have their own increments

        int pointer = 0;
        while (pointer < commands.size()) {
            Pair<Integer, String> res = commands.get(pointer).run(pointer);

            pointer = res.first;
            ControllerActivity.cmd += res.second;
        }
    }

    //random getters and setters from file because i have no idea where else to dump this
    public static Vector<Command> getMacros(String fileName) throws FileNotFoundException {
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

    public static void save(String fileName, Vector<Command> commands) throws FileNotFoundException {
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
