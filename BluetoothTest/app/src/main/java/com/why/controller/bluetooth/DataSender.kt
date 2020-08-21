package com.why.controller.bluetooth

import android.util.Log
import com.why.controller.activities.ControllerActivity


class DataSender : Thread() {
    lateinit var command: List<String>

    override fun run() {
        println("datasender running")

        while (true) {
            if (isInterrupted) {
                Log.d("ZZZ", "interrupted I am dead now " + hashCode())
                return
            }
            //println(ControllerActivity.cmd.toString())
            if (ControllerActivity.cmd.isNotEmpty()) {
                proc()
            }
        }
    }

    @Synchronized
    fun proc(){
        Log.d("ZZZ", "Command: " + ControllerActivity.cmd)
        command = ControllerActivity.cmd.toString().split("\u0000") //this is escape character in kotlin because kotlin is fucking good

        for (i in command.dropLast(1)) {
            control(i)
        }
        ControllerActivity.cmd.setLength(0)
    }

    fun control(cmd: String) {
        val cmmd = cmd.split(" ", limit = 2)

        when (cmmd[0]) {
            "D" -> {
                keyDown(cmmd[1])
            }
            "U" -> {
                keyUp(cmmd[1])
            }
            "T" -> {
                keyPress(cmmd[1])
            }
            "J" -> {
                val temp = cmmd[1].split(" ")
                mouseVelocity(temp[0].toInt(), temp[1].toInt())
            }
        }
    }

    fun keyDown(cmd: String) {
        println(cmd)
        when (cmd) {
            "m1" -> {
                Main.mouse?.sendLeftClickOn()
            }
            "m2" -> {
                Main.mouse?.sendRightClickOn()
            }
            "m3" -> {
                //TODO: middle click in HID ident and send data
            }
            "alt" -> {
                Main.keyboard?.keyboardReport?.rightAlt = true
            }
            "shift" -> {
                Main.keyboard?.keyboardReport?.rightShift = true
            }
            "ctrl" -> {
                Main.keyboard?.keyboardReport?.rightControl = true
            }
            else -> {
                Log.d("ZZZ", cmd)
                Log.d("ZZZ", KeyboardReport.KeyEventMap[cmd].toString())
                Main.keyboard?.sendKeyOn(KeyboardReport.KeyEventMap[cmd])
            }
        }
    }

    fun keyUp(cmd: String) {
        when (cmd) {
            "m1" -> {
                Main.mouse?.sendLeftClickOff()
            }
            "m2" -> {
                Main.mouse?.sendRightClickOff()
            }
            "m3" -> {
                //TODO: middle click in HID ident and send data
            }
            "alt" -> {
                Main.keyboard?.keyboardReport?.rightAlt = false
            }
            "shift" -> {
                Main.keyboard?.keyboardReport?.rightShift = false
            }
            "ctrl" -> {
                Main.keyboard?.keyboardReport?.rightControl = false
            }
            else -> {
                Main.keyboard?.sendKeyOff()
            }
        }
    }

    fun keyPress(cmd: String) {
        Log.d("ZZZ", cmd)

        for (x in 0 until cmd.length) {
            Main.keyboard?.sendKeyOn(KeyboardReport.KeyEventMap[cmd[x].toString()])
            Main.keyboard?.sendKeyOff()
            //Thread.sleep(1) //uncomment if latency screws stuff up
        }
    }

    fun mouseVelocity(dx: Int, dy: Int) {
        Log.d("ZZZ",""+dx+" "+dy);
        Main.mouse?.sendMouseMove(dx, dy)
    }
}

