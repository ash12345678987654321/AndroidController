package com.why.bluetoothtouchpad2.bluetooth

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothHidDevice
import android.util.Log
import java.util.*
import kotlin.concurrent.schedule

@Suppress("MemberVisibilityCanBePrivate")
open class Sender(
    val rMouseSender: MouseSender,
    val hidDevice: BluetoothHidDevice,
    val host: BluetoothDevice
) {
    protected val mouseReport = MouseReport()

    open fun test(){
        println("hihi")
       rMouseSender.sendLeftClickOn()


    }


}