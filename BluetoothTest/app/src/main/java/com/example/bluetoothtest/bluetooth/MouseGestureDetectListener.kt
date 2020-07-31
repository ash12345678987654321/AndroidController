package com.why.bluetoothtouchpad2.bluetooth

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ViewConfiguration
import java.util.*
import kotlin.concurrent.schedule

class MouseGestureDetectListener(val rMouseSender : MouseSender,val c: Context) : GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    val TAP = 0
    val DOUBLE_TAP = 1

    val DOUBLE_TAP_TIMEOUT = ViewConfiguration
        .getDoubleTapTimeout().toLong()
    var mViewScaledTouchSlop: Float = 0.toFloat()
    //internal var mGestureName: EditText

    private var mCurrentDownEvent: MotionEvent? = null

    private var mPtrCount = 0


    private var possibleTwoFingerTapFlag =0
    private var mPossibleTwoFingerTapStartTime=System.currentTimeMillis()
    private var mPrimStartTouchEventX = 0f
    private var mPrimStartTouchEventY = 0f
    private var mSecStartTouchEventX = 0f
    private var mSecStartTouchEventY = 0f
    private var mPrimSecStartTouchDistance = 0f
    private var notAConfirmedDoubleTapFlag=0
    private var disableSingleTapFlag=0
    private var previousScrollX : Float = 0f
    private var previousScrollY : Float = 0f


    private var testerp1=0
    private var testerp2 =0
    private var stopScrollFlag=0

    internal var downTimestamp = System.currentTimeMillis()
    fun onTouchEvent(ev: MotionEvent?): Boolean {
        if(ev !=null) {
            val action = ev.action and MotionEvent.ACTION_MASK
            if(ev.pointerCount==1)
            {
                if(stopScrollFlag==1)
                {
                    rMouseSender.mouseReport.hScroll=0
                    rMouseSender.mouseReport.vScroll=0
                    stopScrollFlag=0
                }

            }

            // prepend("onTouchEvent() ptrs:" + ev.getPointerCount() + " "
            // + actionToString(action));
            when (action) {
                MotionEvent.ACTION_POINTER_DOWN -> {
                    mPtrCount++
                    if (ev.pointerCount > 1) {

                        testerp1=ev.getPointerId(0)//remove at end of testing
                        testerp2=ev.getPointerId(1)



                        mSecStartTouchEventX = ev.getX(1)
                        mSecStartTouchEventY = ev.getY(1)
                        mPrimSecStartTouchDistance = distance(ev, 0, 1)
                        if (ev.pointerCount == 2) possibleTwoFingerTapFlag = 1
//                    if (mCurrentDownEvent != null)
//                        mCurrentDownEvent.recycle()
                        mCurrentDownEvent = MotionEvent.obtain(ev)


                        downTimestamp = System.currentTimeMillis()

                        // return true to prevent other actions.
                        return true
                    }
                }
                MotionEvent.ACTION_POINTER_UP ->{ mPtrCount--}
                MotionEvent.ACTION_DOWN -> {

                    mPtrCount++

                    mPossibleTwoFingerTapStartTime = System.currentTimeMillis()
                }
                MotionEvent.ACTION_UP -> {
                    mPtrCount--

                    if(possibleTwoFingerTapFlag == 1)
                    {
                        possibleTwoFingerTapFlag = 0

                        if (mPtrCount == 0 && ((System.currentTimeMillis() - mPossibleTwoFingerTapStartTime) <= ViewConfiguration.getTapTimeout()) ) {

                            disableSingleTapFlag =1
                            Log.i("thisistwofinger", "two finger single tap is implemented")

                            rMouseSender.sendRightClick()
                        }

                    }

                    rMouseSender.sendMouseMove(0,0)
                    rMouseSender.sendScroll(0,0)


                }
            }
        }
        return false
    }
    override fun onDoubleTap(e: MotionEvent?): Boolean {
println("doubletap")
        return false
    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
println("doubletap event")
        if(mPtrCount==1)
        {
            if(e!=null) {
                if (e.action == MotionEvent.ACTION_DOWN)
                    Timer().schedule(150L) {
                        if(mPtrCount==1)
                        {
                            notAConfirmedDoubleTapFlag=1
                            rMouseSender.sendLeftClickOn()
                            Log.i("doubleddhtnew","this is on double tap and hold and also $DOUBLE_TAP_TIMEOUT and $e ")

                        }
                    }


            }


        }
        if(mPtrCount==0)
        {
            if(e!=null) {
                if (e.action == MotionEvent.ACTION_UP) {
                    if (notAConfirmedDoubleTapFlag == 0) {
                        rMouseSender.sendDoubleTapClick()
                        Log.i("doubleddhtnew", "this is on double tap confirmed $e")
                    } else {
                        notAConfirmedDoubleTapFlag=0
                        rMouseSender.sendLeftClickOff()


                    }
                }
            }
        }

        return false
    }

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        Log.i("doubleddhs","this is on single tap confirmed $e")
        if(disableSingleTapFlag==1)
        {
            disableSingleTapFlag=0
        }
        else {
            rMouseSender.sendAutoClick()
        }
        return false
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        Log.i("doubleddhu","this is on single tap up $e")
        //
        return true
    }

    override fun onDown(e: MotionEvent?): Boolean {
        Log.d("ggkjh", "onDown: $e")
        return false

    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        Log.i("this is a fling e1 ","$e1")
        Log.i("this is a fling e2 ","$e2")
        Log.i("this is a fling vx ","$velocityX")
        Log.i("this is a fling vy ","$velocityY")


        return false
    }
    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {


        if(mPtrCount==2) {


            val dy = when {
                distanceY>7 -> -1
                distanceY<-7 -> 1
                distanceY==0f -> 0
                else -> 0
            }

            val dx = when {
                distanceX>2 -> 1
                distanceX<-2 -> -1
                else -> 0
            }
            /*
            if (dx > 127){
                dx = 127
            }
            else if (dx < -127){
                dx = -127
            }*/







            rMouseSender.sendScroll(dy, dx)


            stopScrollFlag=1

        }
        /*else if (mPtrCount==3){
            println("3fingerscroll")
            var dx = when {
                distanceX>10 -> 1
                distanceX<-10 -> -1
                else -> 0
            }

            if (dx>0){
                println("changeto?")
                //change to ?? mode TODO()
                //c.startActivity(Intent())
            }else if (dx<-0){
                //change to nub mode

                println("changetonub")
            }

        }*/
        return false
    }

    override fun onLongPress(e: MotionEvent?) {

    }

    override fun onShowPress(e: MotionEvent?) {

    }

    fun distance(event: MotionEvent, first: Int, second: Int): Float {
        if (event.pointerCount >= 2) {
            val x = event.getX(first) - event.getX(second)
            val y = event.getY(first) - event.getY(second)

            return Math.sqrt((x * x + y * y).toDouble()).toFloat()
        } else {
            return 0f
        }
    }

}