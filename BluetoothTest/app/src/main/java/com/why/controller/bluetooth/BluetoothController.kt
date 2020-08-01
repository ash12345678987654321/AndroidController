package com.why.bluetoothtouchpad2.bluetooth

import android.bluetooth.*
import android.content.Context
import android.content.Intent
import android.util.Log


@Suppress("MemberVisibilityCanBePrivate")
object BluetoothController: BluetoothHidDevice.Callback(), BluetoothProfile.ServiceListener {

    val featureReport = FeatureReport()
    var context:Context?=null


    override fun onGetReport(device: BluetoothDevice?, type: Byte, id: Byte, bufferSize: Int) {

        super.onGetReport(device, type, id, bufferSize)

        if (type == BluetoothHidDevice.REPORT_TYPE_FEATURE) {
            featureReport.wheelResolutionMultiplier = true
            featureReport.acPanResolutionMultiplier = true

            btHid?.replyReport(device, type, FeatureReport.ID, featureReport.bytes)
        }


    }


    val btAdapter by lazy { BluetoothAdapter.getDefaultAdapter()!! }
    var btHid: BluetoothHidDevice? = null
    var hostDevice: BluetoothDevice? = null
    var autoPairFlag = true

    var mpluggedDevice :BluetoothDevice? = null



    private var deviceListener: ((BluetoothHidDevice, BluetoothDevice)->Unit)? = null
    private var disconnectListener: (()->Unit)? = null

    fun init(ctx: Context) {
        if (btHid != null)
            return
        btAdapter.getProfileProxy(ctx, this, BluetoothProfile.HID_DEVICE)
    }

    fun getSender(callback: (BluetoothHidDevice, BluetoothDevice)->Unit) {
        btHid?.let { hidd ->
            hostDevice?.let { host ->
                callback(hidd, host)
                return
            }
        }
        deviceListener = callback
    }


    fun getDisconnector(callback: ()->Unit) {

        disconnectListener = callback
    }

    /*****************************************************/
    /** BluetoothProfile.ServiceListener implementation **/
    /*****************************************************/

    override fun onServiceDisconnected(profile: Int) {
        println("Service disconnected!")
        if (profile == BluetoothProfile.HID_DEVICE)
            btHid = null
    }

    override fun onServiceConnected(profile: Int, proxy: BluetoothProfile) {
        println( "Connected to service")
        if (profile != BluetoothProfile.HID_DEVICE) {
            return
        }

        val btHid = proxy as? BluetoothHidDevice ?: return
        this.btHid = btHid
        btHid.registerApp(sdpRecord, null, qosOut, {it.run()}, this)//--

        btAdapter.setScanMode(BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE, 300000)




    }



    /************************************************/
    /** BluetoothHidDevice.Callback implementation **/
    /************************************************/



    override fun onConnectionStateChanged(device: BluetoothDevice?, state: Int) {
        super.onConnectionStateChanged(device, state)
        if (state == BluetoothProfile.STATE_CONNECTED) {
            if (device != null) {
                hostDevice = device

                deviceListener?.invoke(btHid!!, device)

                //deviceListener = null
            } else {
                println( "Device not connected")
            }
        } else {
            hostDevice = null
            if(state == BluetoothProfile.STATE_DISCONNECTED)
            {
                disconnectListener?.invoke()
            }

        }
    }

    override fun onAppStatusChanged(pluggedDevice: BluetoothDevice?, registered: Boolean) {
        super.onAppStatusChanged(pluggedDevice, registered)
        if(registered)
        {
            val pairedDevices = btHid?.getDevicesMatchingConnectionStates(intArrayOf(BluetoothProfile.STATE_CONNECTING,BluetoothProfile.STATE_CONNECTED,BluetoothProfile.STATE_DISCONNECTED,BluetoothProfile.STATE_DISCONNECTING))
            //Log.d("paired d", "paired devices are : $pairedDevices")
            //Log.d("paired d","${btHid?.getConnectionState(pairedDevices?.get(0))}")
            mpluggedDevice = pluggedDevice
            if(btHid?.getConnectionState(pluggedDevice)==0 && pluggedDevice!= null && autoPairFlag)
            {
                btHid?.connect(pluggedDevice)
                //hostDevice.toString()


            }


            else if(btHid?.getConnectionState(pairedDevices?.get(0))==0 && autoPairFlag)
            {
                btHid?.connect(pairedDevices?.get(0))
                println( "Device not connected")

            }


        }


    }





    private val sdpRecord by lazy {
        BluetoothHidDeviceAppSdpSettings(
            "WHY",
            "WHY's Bluetooth controller",
            "WHY",
            BluetoothHidDevice.SUBCLASS1_COMBO,
            DescriptorCollection.MOUSE_KEYBOARD_COMBO
        )
    }



    private val qosOut by lazy {
        BluetoothHidDeviceAppQosSettings(
            BluetoothHidDeviceAppQosSettings.SERVICE_BEST_EFFORT,
            800,
            9,
            0,
            11250,
            BluetoothHidDeviceAppQosSettings.MAX
        )
    }

}