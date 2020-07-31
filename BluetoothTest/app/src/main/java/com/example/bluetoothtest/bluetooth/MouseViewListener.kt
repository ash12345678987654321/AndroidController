package com.why.bluetoothtouchpad2.bluetooth

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothHidDevice
import android.util.Log
import android.view.MotionEvent
import android.view.View
import java.nio.ByteBuffer
import kotlin.math.roundToInt

@ExperimentalUnsignedTypes
class MouseViewListener(val hidDevice: BluetoothHidDevice, val host: BluetoothDevice, val rMouseSender : MouseSender):  View.OnTouchListener{

    private var previousX: Float = 0f
    private var previousY: Float = 0f
    private var pointerMotionStopFlag :Int =0


    override fun onTouch(v: View, event: MotionEvent): Boolean {

        val x: Float = event.x
        val y: Float = event.y
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {


                //Log.d("pointerCount_is",event.pointerCount.toString())
                if(event.pointerCount==1) {
                    if(pointerMotionStopFlag==1) pointerMotionStopFlag=0
                    //Log.d("is this working",event.pointerCount.toString())
                    val dx: Float = x - previousX
                    var dxInt: Int = (dx/1.5).roundToInt()


                    if (dxInt > 2047) dxInt = 2047

                    if (dxInt < -2047) dxInt = -2047

                    val dy: Float = y - previousY
                    var dyInt: Int = (dy/1.5).roundToInt()
                    if (dyInt > 2047) dyInt = 2047


                    if (dyInt < -2047) dyInt = -2047

                    rMouseSender.sendMouseMove(dxInt,dyInt)
                    /*
                    var bytesArrX = ByteArray(2) { 0 }
                    var buffX: ByteBuffer = ByteBuffer.wrap(bytesArrX)
                    buffX.putShort(dxInt.toShort())

                    var bytesArrY = ByteArray(2) { 0 }
                    var buffY: ByteBuffer = ByteBuffer.wrap(bytesArrY)
                    buffY.putShort(dyInt.toShort())

                    rMouseSender.mouseReport.dxMsb = bytesArrX[0]
                    rMouseSender.mouseReport.dxLsb = bytesArrX[1]

                    rMouseSender.mouseReport.dyMsb = bytesArrY[0]
                    rMouseSender.mouseReport.dyLsb = bytesArrY[1]
                    //bytes2[0]= bytes1[2]
                    //bytes2[1]= bytes1[3]





                    hidDevice.sendReport(this.host, 4, rMouseSender.mouseReport.bytes)*/


                }
                else {
                    if(pointerMotionStopFlag==0) {
                        rMouseSender.sendMouseMove(0,0)
                    }
                    pointerMotionStopFlag=1

                }

            }
            MotionEvent.ACTION_UP->{
                rMouseSender.sendMouseMove(0,0)
                /*
                rMouseSender.mouseReport.dxMsb =0
                rMouseSender.mouseReport.dxLsb = 0

                rMouseSender.mouseReport.dyMsb = 0
                rMouseSender.mouseReport.dyLsb = 0

                hidDevice.sendReport(this.host, 4, rMouseSender.mouseReport.bytes)*/
            }
        }


        previousX = x
        previousY = y


        return true

    }






}