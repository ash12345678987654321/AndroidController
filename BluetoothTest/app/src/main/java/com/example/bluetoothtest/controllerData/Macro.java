package com.example.bluetoothtest.controllerData;

import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.example.bluetoothtest.activities.ControllerActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Macro extends Thread{
    private String fileName;

    private ArrayList<Command> commands=new ArrayList<>();

    public Macro(String fileName) throws FileNotFoundException {
        Log.d("ZZZ",fileName);
        this.fileName=fileName;
        Scanner scanner=new Scanner(new File(fileName));

        scanner.nextLine();

        while (scanner.hasNextLine()){
            String[] args;
            String temp=scanner.nextLine();

            args=temp.split("\0");

            switch (args[0]){
                case "KeyStroke":
                    commands.add(new KeyStroke(args[1],Boolean.parseBoolean(args[2]),Boolean.parseBoolean(args[3]),args[4]));
                    break;
                case "Text":
                    commands.add(new Text(args[1],Boolean.parseBoolean(args[2]),Boolean.parseBoolean(args[3]),args[4]));
                    break;
                case "Delay":
                    commands.add(new Delay(Integer.parseInt(args[1]),Boolean.parseBoolean(args[2]),Boolean.parseBoolean(args[3]),args[4]));
                    break;
                case "Loop":
                    commands.add(new Loop(Integer.parseInt(args[1]),Boolean.parseBoolean(args[2]),Boolean.parseBoolean(args[3]),args[4]));
                    break;
            }
        }
    }

    public void save() throws FileNotFoundException {
        File file=new File(fileName);

        String name=new Scanner(file).nextLine();

        PrintWriter pw=new PrintWriter(file);
        pw.println(name);

        for (Command i:commands){
            pw.println(i.getOutput());
        }

        pw.flush();
        pw.close();
    }

    public void add_keystroke(){

    }


    @Override
    public void run(){
        //for when we are actually excuting this

        //basic idea is that everyone increments pointer by 1
        // for loops we can use a stack since they form a valid bracket sequence
        // the loop start pushes its index into a stack and the end loop access takes from the top of the stack
        // now we need for them to have their own increments

        int pointer=0;
        while (pointer<commands.size()){
            Pair<Integer,String> res=commands.get(pointer).run(pointer);

            pointer=res.first;
            ControllerActivity.cmd+=res.second;
        }
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }
}
