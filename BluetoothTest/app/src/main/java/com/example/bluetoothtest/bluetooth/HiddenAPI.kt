package com.why.bluetoothtouchpad2.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice

fun BluetoothAdapter.setScanMode(mode: Int, duration: Int): Boolean =
    this::class.java.getMethod("setScanMode", Int::class.java, Int::class.java).invoke(this, mode, duration) as Boolean

fun BluetoothDevice.cancelBondProcess(): Boolean =
    this::class.java.getMethod("cancelBondProcess").invoke(this) as Boolean

fun BluetoothDevice.removeBond(): Boolean =
    this::class.java.getMethod("removeBond").invoke(this) as Boolean