package com.why.bluetoothtouchpad2.bluetooth

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothHidDevice
import android.util.Log
import com.why.bluetoothtouchpad2.bluetooth.MouseReport
import java.nio.ByteBuffer
import java.util.*
import kotlin.concurrent.schedule
import kotlin.math.roundToInt

@Suppress("MemberVisibilityCanBePrivate")
open class MouseSender(
    val hidDevice: BluetoothHidDevice,
    val host: BluetoothDevice

) {
    val mouseReport = MouseReport()
    var previousvscroll :Int=0
    var previoushscroll :Int =0


    protected open fun sendMouse() {
        if (!hidDevice.sendReport(host, MouseReport.ID, mouseReport.bytes)) {
            println("Report wasn't sent")
        }
    }

    fun sendMouseMove(dxInt:Int,dyInt:Int) {

        var bytesArrX = ByteArray(2) { 0 }
        var buffX: ByteBuffer = ByteBuffer.wrap(bytesArrX)
        buffX.putShort(dxInt.toShort())

        var bytesArrY = ByteArray(2) { 0 }
        var buffY: ByteBuffer = ByteBuffer.wrap(bytesArrY)
        buffY.putShort(dyInt.toShort())


        mouseReport.dxMsb = bytesArrX[0]
        mouseReport.dxLsb = bytesArrX[1]

        mouseReport.dyMsb = bytesArrY[0]
        mouseReport.dyLsb = bytesArrY[1]
        sendMouse()
        //println("Mouse Moving")
    }

    var toggleLeftMouse=true
    fun toggleLeftMouse(){
        println("hi i am no")
        if(toggleLeftMouse){
            mouseReport.leftButton=true
            toggleLeftMouse=!toggleLeftMouse
            sendMouse()
        }else{
            mouseReport.leftButton=false
            toggleLeftMouse=!toggleLeftMouse
            sendMouse()
        }
    }

    var toggleRightMouse=true
    fun toggleRightMouse(){
        if(toggleRightMouse){
            mouseReport.leftButton=true
            toggleRightMouse=!toggleRightMouse
            sendMouse()
        }else{
            mouseReport.leftButton=false
            toggleRightMouse=!toggleRightMouse
            sendMouse()
        }
    }

    fun sendAutoClick() {
        sendLeftClickOn()
        sendLeftClickOff()
    }
    fun sendDoubleTapClick() {
        mouseReport.leftButton = true
        sendMouse()
        Timer().schedule(100L) {
            mouseReport.leftButton = false
            sendMouse()
            Timer().schedule(100L) {
                mouseReport.leftButton = true
                sendMouse()
                Timer().schedule(100L) {
                    mouseReport.leftButton = false
                    sendMouse()
                }




            }
        }
    }



    fun sendLeftClickOn() {
        mouseReport.leftButton = true
        sendMouse()


    }
    fun sendLeftClickOff() {
        mouseReport.leftButton = false
        sendMouse()

    }
    fun sendRightClickOn() {
        mouseReport.rightButton = true
        sendMouse()

    }
    fun sendRightClickOff() {
        mouseReport.rightButton = false
        sendMouse()

    }
    fun sendRightClick(){
        sendRightClickOn()
        sendRightClickOff()
    }

    fun sendScroll(vscroll:Int,hscroll:Int){

        var hscrollmutable=0
        var vscrollmutable =0

        hscrollmutable=hscroll
        vscrollmutable= vscroll

        var vs:Int =(vscrollmutable)
        var hs:Int =(hscrollmutable)
        Log.i("vscroll ",vscroll.toString())
        Log.i("vs ",vs.toString())
        Log.i("hscroll ",hscroll.toString())
        Log.i("hs ",hs.toString())


        mouseReport.vScroll=vs.toByte()
        mouseReport.hScroll= hs.toByte()

        sendMouse()


    }






}