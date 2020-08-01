package com.why.controller.bluetooth

import android.util.Log
import com.why.controller.activities.ControllerActivity
import java.util.*
import java.util.regex.Pattern

class DataSender : Thread() {
    lateinit var command:List<String>

    override fun run() {
        while (true) {
            if (isInterrupted) return
            if (ControllerActivity.cmd.isNotEmpty()) {
                Log.d("ZZZ","Command: "+ControllerActivity.cmd);

                synchronized (this) { //in case we send too many commands because of bad threading
                    command = ControllerActivity.cmd.toString().split("\\0")
                    for (i in command) {
                        control(i)
                    }
                    ControllerActivity.cmd.setLength(0);
                }
            }
        }
    }

    fun control(cmd:String){
        val cmmd = cmd.split(Pattern.compile(""),1)
        when(cmmd[0]){
            "D"->{
                keyDown(cmmd[1])
            }
            "U"->{
                keyUp(cmmd[1])
            }
            "T"->{
                keyPress(cmmd[1])
            }
        }
    }

    fun keyDown(cmd:String){
        when(cmd){
            "m1"->{
                Main.mouse?.sendLeftClickOn()
            }
            "m2"->{
                Main.mouse?.sendRightClickOn()
            }
            "m3"->{
                //TODO: middle click in HID ident and send data
            }
            "alt"->{
                Main.keyboard?.keyboardReport?.rightAlt=true
            }
            "shift"->{
                Main.keyboard?.keyboardReport?.rightShift=true
            }
            "ctrl"->{
                Main.keyboard?.keyboardReport?.rightControl=true
            }
            else->{
                Main.keyboard?.sendKeyOn(KeyboardReport.KeyEventMap[cmd])
            }
        }
    }

    fun keyUp(cmd:String){
        when(cmd){
            "m1"->{
                Main.mouse?.sendLeftClickOff()
            }
            "m2"->{
                Main.mouse?.sendRightClickOff()
            }
            "m3"->{
                //TODO: middle click in HID ident and send data
            }
            "alt"->{
                Main.keyboard?.keyboardReport?.rightAlt=false
            }
            "shift"->{
                Main.keyboard?.keyboardReport?.rightShift=false
            }
            "ctrl"->{
                Main.keyboard?.keyboardReport?.rightControl=false
            }
            else->{
                Main.keyboard?.sendKeyOff()
            }
        }
    }

    fun keyPress(cmd:String){
        Main.keyboard?.sendKeyOn(KeyboardReport.KeyEventMap[cmd])
        //Thread.sleep(1) //uncomment if latency screws stuff up
        Main.keyboard?.sendKeyOff()
    }
}

