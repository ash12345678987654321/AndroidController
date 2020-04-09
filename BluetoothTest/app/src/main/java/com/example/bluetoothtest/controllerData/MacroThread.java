package com.example.bluetoothtest.controllerData;

import android.util.Pair;

import com.example.bluetoothtest.activities.ControllerActivity;
import com.example.bluetoothtest.dataStructures.Vector;

public class MacroThread extends Thread {
    private Vector<Command> commands;
    private boolean toggle;

    public MacroThread(Vector<Command> commands,boolean toggle){
        this.commands=commands;
        this.toggle=!toggle;
    }

    @Override
    public void run() {
        //for when we are actually excuting this

        //basic idea is that everyone increments pointer by 1
        // for loops we can use a stack since they form a valid bracket sequence
        // the loop start pushes its index into a stack and the end loop access takes from the top of the stack
        // now we need for them to have their own increments

        Vector<Integer> stk=new Vector<>();

        do {
            int pointer = 0;
            while (pointer < commands.size()) {
                Pair<Integer, String> res = commands.get(pointer).run(pointer,stk);

                pointer = res.first;
                ControllerActivity.cmd.append(res.second);
            }
        } while (toggle);
    }

    public void kill(){
        toggle=false;
    }
}
