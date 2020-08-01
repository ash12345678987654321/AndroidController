package com.why.bluetoothtouchpad2.bluetooth

object DescriptorCollection {



    //BluetoothHidDevice.SUBCLASS1_COMBO
    val MOUSE_ABSOLUTE = byteArrayOf(
        0x05.toByte(), 0x01.toByte(), //USAGE_PAGE (Digitizers)
        0x09.toByte(), 0x02.toByte(), //USAGE (Touch Screen)
        0xa1.toByte(), 0x01.toByte(), //COLLECTION (Application)
        0x85.toByte(), 0x02.toByte(), //    REPORT_ID (MOUSE)
        0x09.toByte(), 0x01.toByte(), //    USAGE (Pointer)
        0xa1.toByte(), 0x00.toByte(), //    COLLECTION (Physical)
        0x05.toByte(), 0x01.toByte(), //        USAGE_PAGE (Generic Desk..
        0x26.toByte(), 0xff.toByte(), 0x0f, //  LOGICAL_MAXIMUM (32767)
        0x75.toByte(), 0x10.toByte(), //        REPORT_SIZE (16)
        0x95.toByte(), 0x01.toByte(), //        REPORT_COUNT (1)
        0x55.toByte(), 0x0e.toByte(), //        UNIT_EXPONENT (-2)
        0x65.toByte(), 0x13.toByte(), //        UNIT (Inch,EngLinear)
        0x09.toByte(), 0x30.toByte(), //        USAGE (X)
        0x35.toByte(), 0x00.toByte(), //        PHYSICAL_MINIMUM (0)
        0x46.toByte(), 0x70.toByte(), 0x08, //  PHYSICAL_MAXIMUM (1080)
        0x81.toByte(), 0x02.toByte(), //        INPUT (Data,Var,Abs)
        0x46.toByte(), 0x00.toByte(), 0x0f, //  PHYSICAL_MAXIMUM (1920)
        0x09.toByte(), 0x31.toByte(), //        USAGE (Y)
        0x81.toByte(), 0x02.toByte(), //        INPUT (Data,Var,Abs)
        0xc0.toByte(),                //    END_COLLECTION
        0xc0.toByte() //                END_COLLECTION
    )// End

    val MOUSE_KEYBOARD_COMBO = byteArrayOf(
        //MOUSE TLC
        0x05.toByte(), 0x01.toByte(),                         // USAGE_PAGE (Generic Desktop)
        0x09.toByte(), 0x02.toByte(),                         // USAGE (Mouse)

        0xa1.toByte(), 0x01.toByte(),                         // COLLECTION (Application)
        0x05.toByte(), 0x01.toByte(),                         // USAGE_PAGE (Generic Desktop)
        0x09.toByte(), 0x02.toByte(),                         // USAGE (Mouse)
        0xa1.toByte(), 0x02.toByte(),        //       COLLECTION (Logical)

        0x85.toByte(), 0x04.toByte(),               //   REPORT_ID (Mouse)
        0x09.toByte(), 0x01.toByte(),                         //   USAGE (Pointer)
        0xa1.toByte(), 0x00.toByte(),                         //   COLLECTION (Physical)
        0x05.toByte(), 0x09.toByte(),                         //     USAGE_PAGE (Button)
        0x19.toByte(), 0x01.toByte(),                         //     USAGE_MINIMUM (Button 1)
        0x29.toByte(), 0x02.toByte(),                         //     USAGE_MAXIMUM (Button 2)
        0x15.toByte(), 0x00.toByte(),                         //     LOGICAL_MINIMUM (0)
        0x25.toByte(), 0x01.toByte(),                         //     LOGICAL_MAXIMUM (1)
        0x75.toByte(), 0x01.toByte(),                         //     REPORT_SIZE (1)
        0x95.toByte(), 0x02.toByte(),                         //     REPORT_COUNT (2)
        0x81.toByte(), 0x02.toByte(),                         //     INPUT (Data,Var,Abs)
        0x95.toByte(), 0x01.toByte(),                         //     REPORT_COUNT (1)
        0x75.toByte(), 0x06.toByte(),                         //     REPORT_SIZE (6)
        0x81.toByte(), 0x03.toByte(),                         //     INPUT (Cnst,Var,Abs)
        0x05.toByte(), 0x01.toByte(),                         //     USAGE_PAGE (Generic Desktop)
        0x09.toByte(), 0x30.toByte(),                         //     USAGE (X)
        0x09.toByte(), 0x31.toByte(),                         //     USAGE (Y)
        0x16.toByte(), 0x01.toByte(),0xf8.toByte(),                         //     LOGICAL_MINIMUM (-2047)
        0x26.toByte(), 0xff.toByte(),0x07.toByte(),                         //     LOGICAL_MAXIMUM (2047)
        0x75.toByte(), 0x10.toByte(),                         //     REPORT_SIZE (16)
        0x95.toByte(), 0x02.toByte(),                         //     REPORT_COUNT (2)
        0x81.toByte(), 0x06.toByte(),                         //     INPUT (Data,Var,Rel)

        0xa1.toByte(), 0x02.toByte(),        //       COLLECTION (Logical)
        0x85.toByte(), 0x06.toByte(),               //   REPORT_ID (Feature)
        0x09.toByte(), 0x48.toByte(),        //         USAGE (Resolution Multiplier)

        0x15.toByte(), 0x00.toByte(),        //         LOGICAL_MINIMUM (0)
        0x25.toByte(), 0x01.toByte(),        //         LOGICAL_MAXIMUM (1)
        0x35.toByte(), 0x01.toByte(),        //         PHYSICAL_MINIMUM (1)
        0x45.toByte(), 0x04.toByte(),        //         PHYSICAL_MAXIMUM (4)
        0x75.toByte(), 0x02.toByte(),        //         REPORT_SIZE (2)
        0x95.toByte(), 0x01.toByte(),        //         REPORT_COUNT (1)

        0xb1.toByte(), 0x02.toByte(),        //         FEATURE (Data,Var,Abs)


        0x85.toByte(), 0x04.toByte(),               //   REPORT_ID (Mouse)
        //0x05.toByte(), 0x01.toByte(),                         //     USAGE_PAGE (Generic Desktop)
        0x09.toByte(), 0x38.toByte(),        //         USAGE (Wheel)

        0x15.toByte(), 0x81.toByte(),        //         LOGICAL_MINIMUM (-127)
        0x25.toByte(), 0x7f.toByte(),        //         LOGICAL_MAXIMUM (127)
        0x35.toByte(), 0x00.toByte(),        //         PHYSICAL_MINIMUM (0)        - reset physical
        0x45.toByte(), 0x00.toByte(),        //         PHYSICAL_MAXIMUM (0)
        0x75.toByte(), 0x08.toByte(),        //         REPORT_SIZE (8)
        0x95.toByte(), 0x01.toByte(),                         //     REPORT_COUNT (1)
        0x81.toByte(), 0x06.toByte(),                         //     INPUT (Data,Var,Rel)
        0xc0.toByte(),              //       END_COLLECTION

        0xa1.toByte(), 0x02.toByte(),        //       COLLECTION (Logical)
        0x85.toByte(), 0x06.toByte(),               //   REPORT_ID (Feature)
        0x09.toByte(), 0x48.toByte(),        //         USAGE (Resolution Multiplier)

        0x15.toByte(), 0x00.toByte(),        //         LOGICAL_MINIMUM (0)
        0x25.toByte(), 0x01.toByte(),        //         LOGICAL_MAXIMUM (1)
        0x35.toByte(), 0x01.toByte(),        //         PHYSICAL_MINIMUM (1)
        0x45.toByte(), 0x04.toByte(),        //         PHYSICAL_MAXIMUM (4)
        0x75.toByte(), 0x02.toByte(),        //         REPORT_SIZE (2)
        0x95.toByte(), 0x01.toByte(),        //         REPORT_COUNT (1)

        0xb1.toByte(), 0x02.toByte(),        //         FEATURE (Data,Var,Abs)

        0x35.toByte(), 0x00.toByte(),        //         PHYSICAL_MINIMUM (0)        - reset physical
        0x45.toByte(), 0x00.toByte(),        //         PHYSICAL_MAXIMUM (0)
        0x75.toByte(), 0x04.toByte(),        //         REPORT_SIZE (4)
        0xb1.toByte(), 0x03.toByte(),        //         FEATURE (Cnst,Var,Abs)



        0x85.toByte(), 0x04.toByte(),               //   REPORT_ID (Mouse)
        0x05.toByte(), 0x0c.toByte(),        //         USAGE_PAGE (Consumer Devices)
        0x0a.toByte(), 0x38.toByte(), 0x02.toByte(),  //         USAGE (AC Pan)

        0x15.toByte(), 0x81.toByte(),        //         LOGICAL_MINIMUM (-127)
        0x25.toByte(), 0x7f.toByte(),        //         LOGICAL_MAXIMUM (127)
        0x75.toByte(), 0x08.toByte(),        //         REPORT_SIZE (8)
        0x95.toByte(), 0x01.toByte(),        //         REPORT_COUNT (1)
        0x81.toByte(), 0x06.toByte(),        //         INPUT (Data,Var,Rel)
        0xc0.toByte(),              //       END_COLLECTION
        0xc0.toByte(),              //       END_COLLECTION

        0xc0.toByte(),                               //   END_COLLECTION
        0xc0.toByte(),                                //END_COLLECTION

        0x05.toByte(), 0x01.toByte(),                         // USAGE_PAGE (Generic Desktop)

        0x09.toByte(), 0x06.toByte(),                         // Usage (Keyboard)
        0xA1.toByte(), 0x01.toByte(),                         // Collection (Application)
        0x85.toByte(), 0x08.toByte(),                           //   REPORT_ID (Keyboard)
        0x05.toByte(), 0x07.toByte(),                         //     Usage Page (Key Codes)
        0x19.toByte(), 0xe0.toByte(),                         //     Usage Minimum (224)
        0x29.toByte(), 0xe7.toByte(),                         //     Usage Maximum (231)
        0x15.toByte(), 0x00.toByte(),                         //     Logical Minimum (0)
        0x25.toByte(), 0x01.toByte(),                         //     Logical Maximum (1)
        0x75.toByte(), 0x01.toByte(),                         //     Report Size (1)
        0x95.toByte(), 0x08.toByte(),                         //     Report Count (8)
        0x81.toByte(), 0x02.toByte(),                         //     Input (Data, Variable, Absolute)

        0x95.toByte(), 0x01.toByte(),                         //     Report Count (1)
        0x75.toByte(), 0x08.toByte(),                         //     Report Size (8)
        0x81.toByte(), 0x01.toByte(),                         //     Input (Constant) reserved byte(1)



        0x95.toByte(), 0x01.toByte(),                         //     Report Count (1)
        0x75.toByte(), 0x08.toByte(),                         //     Report Size (8)
        0x15.toByte(), 0x00.toByte(),                         //     Logical Minimum (0)
        0x25.toByte(), 0x65.toByte(),                         //     Logical Maximum (101)
        0x05.toByte(), 0x07.toByte(),                         //     Usage Page (Key codes)
        0x19.toByte(), 0x00.toByte(),                         //     Usage Minimum (0)
        0x29.toByte(), 0x65.toByte(),                         //     Usage Maximum (101)
        0x81.toByte(), 0x00.toByte(),                         //     Input (Data, Array) Key array(6 bytes)
        0xc0.toByte()                               // End Collection (Application)

    )


    val featurerr = byteArrayOf(

        //MOUSE TLC
        0x05.toByte(), 0x01.toByte(),                         // USAGE_PAGE (Generic Desktop)
        0x09.toByte(), 0x02.toByte(),                         // USAGE (Mouse)

        0xa1.toByte(), 0x01.toByte(),                         // COLLECTION (Application)
        0x85.toByte(), 0x06.toByte(),               //   REPORT_ID (Feature)
        0x09.toByte(), 0x48.toByte(),        //         USAGE (Resolution Multiplier)

        0x15.toByte(), 0x00.toByte(),        //         LOGICAL_MINIMUM (0)
        0x25.toByte(), 0x01.toByte(),        //         LOGICAL_MAXIMUM (1)
        0x35.toByte(), 0x01.toByte(),        //         PHYSICAL_MINIMUM (1)
        0x45.toByte(), 0x04.toByte(),        //         PHYSICAL_MAXIMUM (4)
        0x75.toByte(), 0x02.toByte(),        //         REPORT_SIZE (2)
        0x95.toByte(), 0x01.toByte(),        //         REPORT_COUNT (1)

        0xb1.toByte(), 0x02.toByte(),        //         FEATURE (Data,Var,Abs)




        0x85.toByte(), 0x06.toByte(),               //   REPORT_ID (Feature)
        0x09.toByte(), 0x48.toByte(),        //         USAGE (Resolution Multiplier)

        0x15.toByte(), 0x00.toByte(),        //         LOGICAL_MINIMUM (0)
        0x25.toByte(), 0x01.toByte(),        //         LOGICAL_MAXIMUM (1)
        0x35.toByte(), 0x01.toByte(),        //         PHYSICAL_MINIMUM (1)
        0x45.toByte(), 0x04.toByte(),        //         PHYSICAL_MAXIMUM (4)
        0x75.toByte(), 0x02.toByte(),        //         REPORT_SIZE (2)
        0x95.toByte(), 0x01.toByte(),        //         REPORT_COUNT (1)

        0xb1.toByte(), 0x02.toByte(),        //         FEATURE (Data,Var,Abs)
        0x35.toByte(), 0x00.toByte(),        //         PHYSICAL_MINIMUM (0)        - reset physical
        0x45.toByte(), 0x00.toByte(),        //         PHYSICAL_MAXIMUM (0)
        0x75.toByte(), 0x04.toByte(),        //         REPORT_SIZE (4)
        0x95.toByte(), 0x01.toByte(),        //         REPORT_COUNT (1)
        0xb1.toByte(), 0x03.toByte(),        //         FEATURE (Cnst,Var,Abs)
        0xc0.toByte(),                                //END_COLLECTION

        0x05.toByte(), 0x01.toByte(),                         // USAGE_PAGE (Generic Desktop)

        0x09.toByte(), 0x06.toByte(),                         // Usage (Keyboard)
        0xA1.toByte(), 0x01.toByte(),                         // Collection (Application)
        0x85.toByte(), 0x08.toByte(),                           //   REPORT_ID (Keyboard)
        0x05.toByte(), 0x07.toByte(),                         //     Usage Page (Key Codes)
        0x19.toByte(), 0xe0.toByte(),                         //     Usage Minimum (224)
        0x29.toByte(), 0xe7.toByte(),                         //     Usage Maximum (231)
        0x15.toByte(), 0x00.toByte(),                         //     Logical Minimum (0)
        0x25.toByte(), 0x01.toByte(),                         //     Logical Maximum (1)
        0x75.toByte(), 0x01.toByte(),                         //     Report Size (1)
        0x95.toByte(), 0x08.toByte(),                         //     Report Count (8)
        0x81.toByte(), 0x02.toByte(),                         //     Input (Data, Variable, Absolute)

        0x95.toByte(), 0x01.toByte(),                         //     Report Count (1)
        0x75.toByte(), 0x08.toByte(),                         //     Report Size (8)
        0x81.toByte(), 0x01.toByte(),                         //     Input (Constant) reserved byte(1)



        0x95.toByte(), 0x01.toByte(),                         //     Report Count (1)
        0x75.toByte(), 0x08.toByte(),                         //     Report Size (8)
        0x15.toByte(), 0x00.toByte(),                         //     Logical Minimum (0)
        0x25.toByte(), 0x65.toByte(),                         //     Logical Maximum (101)
        0x05.toByte(), 0x07.toByte(),                         //     Usage Page (Key codes)
        0x19.toByte(), 0x00.toByte(),                         //     Usage Minimum (0)
        0x29.toByte(), 0x65.toByte(),                         //     Usage Maximum (101)
        0x81.toByte(), 0x00.toByte(),                         //     Input (Data, Array) Key array(6 bytes)
        0xc0.toByte()                               // End Collection (Application)




    )





    val KEYBOARD_MODIFIED = byteArrayOf(

        0x05.toByte(), 0x01.toByte(),                         // Usage Page (Generic Desktop)
        0x09.toByte(), 0x06.toByte(),                         // Usage (Keyboard)
        0xA1.toByte(), 0x01.toByte(),                         // Collection (Application)
        0x85.toByte(), 0x08.toByte(),                           //   REPORT_ID (Keyboard)
        0x05.toByte(), 0x07.toByte(),                         //     Usage Page (Key Codes)
        0x19.toByte(), 0xe0.toByte(),                         //     Usage Minimum (224)
        0x29.toByte(), 0xe7.toByte(),                         //     Usage Maximum (231)
        0x15.toByte(), 0x00.toByte(),                         //     Logical Minimum (0)
        0x25.toByte(), 0x01.toByte(),                         //     Logical Maximum (1)
        0x75.toByte(), 0x01.toByte(),                         //     Report Size (1)
        0x95.toByte(), 0x08.toByte(),                         //     Report Count (8)
        0x81.toByte(), 0x02.toByte(),                         //     Input (Data, Variable, Absolute)

        0x95.toByte(), 0x01.toByte(),                         //     Report Count (1)
        0x75.toByte(), 0x08.toByte(),                         //     Report Size (8)
        0x81.toByte(), 0x01.toByte(),                         //     Input (Constant) reserved byte(1)



        0x95.toByte(), 0x01.toByte(),                         //     Report Count (1)
        0x75.toByte(), 0x08.toByte(),                         //     Report Size (8)
        0x15.toByte(), 0x00.toByte(),                         //     Logical Minimum (0)
        0x25.toByte(), 0x65.toByte(),                         //     Logical Maximum (101)
        0x05.toByte(), 0x07.toByte(),                         //     Usage Page (Key codes)
        0x19.toByte(), 0x00.toByte(),                         //     Usage Minimum (0)
        0x29.toByte(), 0x65.toByte(),                         //     Usage Maximum (101)
        0x81.toByte(), 0x00.toByte(),                         //     Input (Data, Array) Key array(6 bytes)
        0xC0.toByte()                                // End Collection (Application)
    )

    val KEYBOARD = byteArrayOf(

        0x05.toByte(), 0x01.toByte(),                         // Usage Page (Generic Desktop)
        0x09.toByte(), 0x06.toByte(),                         // Usage (Keyboard)
        0xA1.toByte(), 0x01.toByte(),                         // Collection (Application)
        0x05.toByte(), 0x07.toByte(),                         //     Usage Page (Key Codes)
        0x19.toByte(), 0xe0.toByte(),                         //     Usage Minimum (224)
        0x29.toByte(), 0xe7.toByte(),                         //     Usage Maximum (231)
        0x15.toByte(), 0x00.toByte(),                         //     Logical Minimum (0)
        0x25.toByte(), 0x01.toByte(),                         //     Logical Maximum (1)
        0x75.toByte(), 0x01.toByte(),                         //     Report Size (1)
        0x95.toByte(), 0x08.toByte(),                         //     Report Count (8)
        0x81.toByte(), 0x02.toByte(),                         //     Input (Data, Variable, Absolute)

        0x95.toByte(), 0x01.toByte(),                         //     Report Count (1)
        0x75.toByte(), 0x08.toByte(),                         //     Report Size (8)
        0x81.toByte(), 0x01.toByte(),                         //     Input (Constant) reserved byte(1)

        0x95.toByte(), 0x05.toByte(),                         //     Report Count (5)
        0x75.toByte(), 0x01.toByte(),                         //     Report Size (1)
        0x05.toByte(), 0x08.toByte(),                         //     Usage Page (Page# for LEDs)
        0x19.toByte(), 0x01.toByte(),                         //     Usage Minimum (1)
        0x29.toByte(), 0x05.toByte(),                         //     Usage Maximum (5)
        0x91.toByte(), 0x02.toByte(),                         //     Output (Data, Variable, Absolute), Led report
        0x95.toByte(), 0x01.toByte(),                         //     Report Count (1)
        0x75.toByte(), 0x03.toByte(),                         //     Report Size (3)
        0x91.toByte(), 0x01.toByte(),                         //     Output (Data, Variable, Absolute), Led report padding

        0x95.toByte(), 0x06.toByte(),                         //     Report Count (6)
        0x75.toByte(), 0x08.toByte(),                         //     Report Size (8)
        0x15.toByte(), 0x00.toByte(),                         //     Logical Minimum (0)
        0x25.toByte(), 0x65.toByte(),                         //     Logical Maximum (101)
        0x05.toByte(), 0x07.toByte(),                         //     Usage Page (Key codes)
        0x19.toByte(), 0x00.toByte(),                         //     Usage Minimum (0)
        0x29.toByte(), 0x65.toByte(),                         //     Usage Maximum (101)
        0x81.toByte(), 0x00.toByte(),                         //     Input (Data, Array) Key array(6 bytes)
        0xC0.toByte()                                // End Collection (Application)
    )



    val MOUSE_RELATIVE1 = byteArrayOf(
        //MOUSE TLC
        0x05.toByte(), 0x01.toByte(),                         // USAGE_PAGE (Generic Desktop)
        0x09.toByte(), 0x02.toByte(),                         // USAGE (Mouse)
        0xa1.toByte(), 0x01.toByte(),                         // COLLECTION (Application)
        0x85.toByte(), 0x04.toByte(),               //   REPORT_ID (Mouse)
        0x09.toByte(), 0x01.toByte(),                         //   USAGE (Pointer)
        0xa1.toByte(), 0x00.toByte(),                         //   COLLECTION (Physical)
        0x05.toByte(), 0x09.toByte(),                         //     USAGE_PAGE (Button)
        0x19.toByte(), 0x01.toByte(),                         //     USAGE_MINIMUM (Button 1)
        0x29.toByte(), 0x02.toByte(),                         //     USAGE_MAXIMUM (Button 2)
        0x15.toByte(), 0x00.toByte(),                         //     LOGICAL_MINIMUM (0)
        0x25.toByte(), 0x01.toByte(),                         //     LOGICAL_MAXIMUM (1)
        0x75.toByte(), 0x01.toByte(),                         //     REPORT_SIZE (1)
        0x95.toByte(), 0x02.toByte(),                         //     REPORT_COUNT (2)
        0x81.toByte(), 0x02.toByte(),                         //     INPUT (Data,Var,Abs)
        0x95.toByte(), 0x01.toByte(),                         //     REPORT_COUNT (1)
        0x75.toByte(), 0x06.toByte(),                         //     REPORT_SIZE (6)
        0x81.toByte(), 0x03.toByte(),                         //     INPUT (Cnst,Var,Abs)
        0x05.toByte(), 0x01.toByte(),                         //     USAGE_PAGE (Generic Desktop)
        0x09.toByte(), 0x30.toByte(),                         //     USAGE (X)
        0x09.toByte(), 0x31.toByte(),                         //     USAGE (Y)
        0x15.toByte(), 0x01.toByte(),0xf8.toByte(),                         //     LOGICAL_MINIMUM (-2047)
        0x25.toByte(), 0xff.toByte(),0x07.toByte(),                         //     LOGICAL_MAXIMUM (2047)
        0x75.toByte(), 0x0c.toByte(),                         //     REPORT_SIZE (12)
        0x95.toByte(), 0x02.toByte(),                         //     REPORT_COUNT (2)
        0x81.toByte(), 0x06.toByte(),                         //     INPUT (Data,Var,Rel)
        0xc0.toByte(),                               //   END_COLLECTION
        0xc0.toByte()                                //END_COLLECTION
    )

    val MOUSE_RELATIVE_WITHOUT_SCROLL = byteArrayOf(
        //MOUSE TLC
        0x05.toByte(), 0x01.toByte(),                         // USAGE_PAGE (Generic Desktop)
        0x09.toByte(), 0x02.toByte(),                         // USAGE (Mouse)
        0xa1.toByte(), 0x01.toByte(),                         // COLLECTION (Application)
        0x85.toByte(), 0x04.toByte(),               //   REPORT_ID (Mouse)
        0x09.toByte(), 0x01.toByte(),                         //   USAGE (Pointer)
        0xa1.toByte(), 0x00.toByte(),                         //   COLLECTION (Physical)
        0x05.toByte(), 0x09.toByte(),                         //     USAGE_PAGE (Button)
        0x19.toByte(), 0x01.toByte(),                         //     USAGE_MINIMUM (Button 1)
        0x29.toByte(), 0x02.toByte(),                         //     USAGE_MAXIMUM (Button 2)
        0x15.toByte(), 0x00.toByte(),                         //     LOGICAL_MINIMUM (0)
        0x25.toByte(), 0x01.toByte(),                         //     LOGICAL_MAXIMUM (1)
        0x75.toByte(), 0x01.toByte(),                         //     REPORT_SIZE (1)
        0x95.toByte(), 0x02.toByte(),                         //     REPORT_COUNT (2)
        0x81.toByte(), 0x02.toByte(),                         //     INPUT (Data,Var,Abs)
        0x95.toByte(), 0x01.toByte(),                         //     REPORT_COUNT (1)
        0x75.toByte(), 0x06.toByte(),                         //     REPORT_SIZE (6)
        0x81.toByte(), 0x03.toByte(),                         //     INPUT (Cnst,Var,Abs)
        0x05.toByte(), 0x01.toByte(),                         //     USAGE_PAGE (Generic Desktop)
        0x09.toByte(), 0x30.toByte(),                         //     USAGE (X)
        0x09.toByte(), 0x31.toByte(),                         //     USAGE (Y)
        0x16.toByte(), 0x01.toByte(),0xf8.toByte(),                         //     LOGICAL_MINIMUM (-2047)
        0x26.toByte(), 0xff.toByte(),0x07.toByte(),                         //     LOGICAL_MAXIMUM (2047)
        0x75.toByte(), 0x10.toByte(),                         //     REPORT_SIZE (16)
        0x95.toByte(), 0x02.toByte(),                         //     REPORT_COUNT (2)
        0x81.toByte(), 0x06.toByte(),                         //     INPUT (Data,Var,Rel)
        0xc0.toByte(),                               //   END_COLLECTION
        0xc0.toByte()                                //END_COLLECTION
    )


    val MOUSE_RELATIVE_WITH_SCROLL = byteArrayOf(
        //MOUSE TLC
        0x05.toByte(), 0x01.toByte(),                         // USAGE_PAGE (Generic Desktop)
        0x09.toByte(), 0x02.toByte(),                         // USAGE (Mouse)

        0xa1.toByte(), 0x01.toByte(),                         // COLLECTION (Application)
        0x05.toByte(), 0x01.toByte(),                         // USAGE_PAGE (Generic Desktop)
        0x09.toByte(), 0x02.toByte(),                         // USAGE (Mouse)
        0xa1.toByte(), 0x02.toByte(),        //       COLLECTION (Logical)

        0x85.toByte(), 0x04.toByte(),               //   REPORT_ID (Mouse)
        0x09.toByte(), 0x01.toByte(),                         //   USAGE (Pointer)
        0xa1.toByte(), 0x00.toByte(),                         //   COLLECTION (Physical)
        0x05.toByte(), 0x09.toByte(),                         //     USAGE_PAGE (Button)
        0x19.toByte(), 0x01.toByte(),                         //     USAGE_MINIMUM (Button 1)
        0x29.toByte(), 0x02.toByte(),                         //     USAGE_MAXIMUM (Button 2)
        0x15.toByte(), 0x00.toByte(),                         //     LOGICAL_MINIMUM (0)
        0x25.toByte(), 0x01.toByte(),                         //     LOGICAL_MAXIMUM (1)
        0x75.toByte(), 0x01.toByte(),                         //     REPORT_SIZE (1)
        0x95.toByte(), 0x02.toByte(),                         //     REPORT_COUNT (2)
        0x81.toByte(), 0x02.toByte(),                         //     INPUT (Data,Var,Abs)
        0x95.toByte(), 0x01.toByte(),                         //     REPORT_COUNT (1)
        0x75.toByte(), 0x06.toByte(),                         //     REPORT_SIZE (6)
        0x81.toByte(), 0x03.toByte(),                         //     INPUT (Cnst,Var,Abs)
        0x05.toByte(), 0x01.toByte(),                         //     USAGE_PAGE (Generic Desktop)
        0x09.toByte(), 0x30.toByte(),                         //     USAGE (X)
        0x09.toByte(), 0x31.toByte(),                         //     USAGE (Y)
        0x16.toByte(), 0x01.toByte(),0xf8.toByte(),                         //     LOGICAL_MINIMUM (-2047)
        0x26.toByte(), 0xff.toByte(),0x07.toByte(),                         //     LOGICAL_MAXIMUM (2047)
        0x75.toByte(), 0x10.toByte(),                         //     REPORT_SIZE (16)
        0x95.toByte(), 0x02.toByte(),                         //     REPORT_COUNT (2)
        0x81.toByte(), 0x06.toByte(),                         //     INPUT (Data,Var,Rel)

        0xa1.toByte(), 0x02.toByte(),        //       COLLECTION (Logical)
        0x85.toByte(), 0x06.toByte(),               //   REPORT_ID (Feature)
        0x09.toByte(), 0x48.toByte(),        //         USAGE (Resolution Multiplier)

        0x15.toByte(), 0x00.toByte(),        //         LOGICAL_MINIMUM (0)
        0x25.toByte(), 0x01.toByte(),        //         LOGICAL_MAXIMUM (1)
        0x35.toByte(), 0x01.toByte(),        //         PHYSICAL_MINIMUM (1)
        0x45.toByte(), 0x04.toByte(),        //         PHYSICAL_MAXIMUM (4)
        0x75.toByte(), 0x02.toByte(),        //         REPORT_SIZE (2)
        0x95.toByte(), 0x01.toByte(),        //         REPORT_COUNT (1)

        0xb1.toByte(), 0x02.toByte(),        //         FEATURE (Data,Var,Abs)


        0x85.toByte(), 0x04.toByte(),               //   REPORT_ID (Mouse)
        //0x05.toByte(), 0x01.toByte(),                         //     USAGE_PAGE (Generic Desktop)
        0x09.toByte(), 0x38.toByte(),        //         USAGE (Wheel)

        0x15.toByte(), 0x81.toByte(),        //         LOGICAL_MINIMUM (-127)
        0x25.toByte(), 0x7f.toByte(),        //         LOGICAL_MAXIMUM (127)
        0x35.toByte(), 0x00.toByte(),        //         PHYSICAL_MINIMUM (0)        - reset physical
        0x45.toByte(), 0x00.toByte(),        //         PHYSICAL_MAXIMUM (0)
        0x75.toByte(), 0x08.toByte(),        //         REPORT_SIZE (8)
        0x95.toByte(), 0x01.toByte(),                         //     REPORT_COUNT (1)
        0x81.toByte(), 0x06.toByte(),                         //     INPUT (Data,Var,Rel)
        0xc0.toByte(),              //       END_COLLECTION

        0xa1.toByte(), 0x02.toByte(),        //       COLLECTION (Logical)
        0x85.toByte(), 0x06.toByte(),               //   REPORT_ID (Feature)
        0x09.toByte(), 0x48.toByte(),        //         USAGE (Resolution Multiplier)

        0x15.toByte(), 0x00.toByte(),        //         LOGICAL_MINIMUM (0)
        0x25.toByte(), 0x01.toByte(),        //         LOGICAL_MAXIMUM (1)
        0x35.toByte(), 0x01.toByte(),        //         PHYSICAL_MINIMUM (1)
        0x45.toByte(), 0x04.toByte(),        //         PHYSICAL_MAXIMUM (4)
        0x75.toByte(), 0x02.toByte(),        //         REPORT_SIZE (2)
        0x95.toByte(), 0x01.toByte(),        //         REPORT_COUNT (1)

        0xb1.toByte(), 0x02.toByte(),        //         FEATURE (Data,Var,Abs)

        0x35.toByte(), 0x00.toByte(),        //         PHYSICAL_MINIMUM (0)        - reset physical
        0x45.toByte(), 0x00.toByte(),        //         PHYSICAL_MAXIMUM (0)
        0x75.toByte(), 0x04.toByte(),        //         REPORT_SIZE (4)
        0xb1.toByte(), 0x03.toByte(),        //         FEATURE (Cnst,Var,Abs)



        0x85.toByte(), 0x04.toByte(),               //   REPORT_ID (Mouse)
        0x05.toByte(), 0x0c.toByte(),        //         USAGE_PAGE (Consumer Devices)
        0x0a.toByte(), 0x38.toByte(), 0x02.toByte(),  //         USAGE (AC Pan)

        0x15.toByte(), 0x81.toByte(),        //         LOGICAL_MINIMUM (-127)
        0x25.toByte(), 0x7f.toByte(),        //         LOGICAL_MAXIMUM (127)
        0x75.toByte(), 0x08.toByte(),        //         REPORT_SIZE (8)
        0x95.toByte(), 0x01.toByte(),        //         REPORT_COUNT (1)
        0x81.toByte(), 0x06.toByte(),        //         INPUT (Data,Var,Rel)
        0xc0.toByte(),              //       END_COLLECTION
        0xc0.toByte(),              //       END_COLLECTION

        0xc0.toByte(),                               //   END_COLLECTION
        0xc0.toByte()                                //END_COLLECTION

    )


    val MOUSE_RELATIVE_WITH_SCROLL_NOTSMOOTH = byteArrayOf(
        //MOUSE TLC
        0x05.toByte(), 0x01.toByte(),                         // USAGE_PAGE (Generic Desktop)
        0x09.toByte(), 0x02.toByte(),                         // USAGE (Mouse)
        0xa1.toByte(), 0x01.toByte(),                         // COLLECTION (Application)
        0x85.toByte(), 0x04.toByte(),                         //   REPORT_ID (Mouse)
        0x09.toByte(), 0x01.toByte(),                         //   USAGE (Pointer)
        0xa1.toByte(), 0x00.toByte(),                         //   COLLECTION (Physical)
        0x05.toByte(), 0x09.toByte(),                         //     USAGE_PAGE (Button)
        0x19.toByte(), 0x01.toByte(),                         //     USAGE_MINIMUM (Button 1)
        0x29.toByte(), 0x02.toByte(),                         //     USAGE_MAXIMUM (Button 2)
        0x15.toByte(), 0x00.toByte(),                         //     LOGICAL_MINIMUM (0)
        0x25.toByte(), 0x01.toByte(),                         //     LOGICAL_MAXIMUM (1)
        0x75.toByte(), 0x01.toByte(),                         //     REPORT_SIZE (1)
        0x95.toByte(), 0x02.toByte(),                         //     REPORT_COUNT (2)
        0x81.toByte(), 0x02.toByte(),                         //     INPUT (Data,Var,Abs)
        0x95.toByte(), 0x01.toByte(),                         //     REPORT_COUNT (1)
        0x75.toByte(), 0x06.toByte(),                         //     REPORT_SIZE (6)
        0x81.toByte(), 0x03.toByte(),                         //     INPUT (Cnst,Var,Abs)
        0x05.toByte(), 0x01.toByte(),                         //     USAGE_PAGE (Generic Desktop)
        0x09.toByte(), 0x30.toByte(),                         //     USAGE (X)
        0x09.toByte(), 0x31.toByte(),                         //     USAGE (Y)
        0x16.toByte(), 0x01.toByte(),0xf8.toByte(),           //     LOGICAL_MINIMUM (-2047)
        0x26.toByte(), 0xff.toByte(),0x07.toByte(),           //     LOGICAL_MAXIMUM (2047)
        0x75.toByte(), 0x10.toByte(),                         //     REPORT_SIZE (16)
        0x95.toByte(), 0x02.toByte(),                         //     REPORT_COUNT (2)
        0x81.toByte(), 0x06.toByte(),                         //     INPUT (Data,Var,Rel)
        0x05.toByte(), 0x01.toByte(),                         //     USAGE_PAGE (Generic Desktop)
        0x09.toByte(), 0x38.toByte(),                        //      USAGE (Wheel)
        0x15.toByte(), 0x81.toByte(),                       //       LOGICAL_MINIMUM (-127)
        0x25.toByte(), 0x7f.toByte(),                       //       LOGICAL_MAXIMUM (127)
        0x35.toByte(), 0x00.toByte(),                     //         PHYSICAL_MINIMUM (0)        - reset physical
        0x45.toByte(), 0x00.toByte(),                     //         PHYSICAL_MAXIMUM (0)
        0x75.toByte(), 0x08.toByte(),                     //         REPORT_SIZE (8)
        0x95.toByte(), 0x01.toByte(),                         //     REPORT_COUNT (1)
        0x81.toByte(), 0x06.toByte(),                         //     INPUT (Data,Var,Rel)


        0x05.toByte(), 0x0c.toByte(),                     //         USAGE_PAGE (Consumer Devices)
        0x0a.toByte(), 0x38.toByte(), 0x02.toByte(),      //         USAGE (AC Pan)
        0x15.toByte(), 0x81.toByte(),                     //         LOGICAL_MINIMUM (-127)
        0x25.toByte(), 0x7f.toByte(),                     //         LOGICAL_MAXIMUM (127)
        0x75.toByte(), 0x08.toByte(),                     //         REPORT_SIZE (8)
        0x95.toByte(), 0x01.toByte(),                     //         REPORT_COUNT (1)
        0x81.toByte(), 0x06.toByte(),                     //         INPUT (Data,Var,Rel)

        0xc0.toByte(),                                        //   END_COLLECTION
        0xc0.toByte()                                          //END_COLLECTION
    )


    val MOUSE_RELATIVE = byteArrayOf(
        //MOUSE TLC
        0x05.toByte(), 0x01.toByte(),                         // USAGE_PAGE (Generic Desktop)
        0x09.toByte(), 0x02.toByte(),                         // USAGE (Mouse)
        0xa1.toByte(), 0x01.toByte(),                         // COLLECTION (Application)
        0x85.toByte(), 0x04.toByte(),               //   REPORT_ID (Mouse)
        0x09.toByte(), 0x01.toByte(),                         //   USAGE (Pointer)
        0xa1.toByte(), 0x00.toByte(),                         //   COLLECTION (Physical)
        0x05.toByte(), 0x09.toByte(),                         //     USAGE_PAGE (Button)
        0x19.toByte(), 0x01.toByte(),                         //     USAGE_MINIMUM (Button 1)
        0x29.toByte(), 0x02.toByte(),                         //     USAGE_MAXIMUM (Button 2)
        0x15.toByte(), 0x00.toByte(),                         //     LOGICAL_MINIMUM (0)
        0x25.toByte(), 0x01.toByte(),                         //     LOGICAL_MAXIMUM (1)
        0x75.toByte(), 0x01.toByte(),                         //     REPORT_SIZE (1)
        0x95.toByte(), 0x02.toByte(),                         //     REPORT_COUNT (2)
        0x81.toByte(), 0x02.toByte(),                         //     INPUT (Data,Var,Abs)
        0x95.toByte(), 0x01.toByte(),                         //     REPORT_COUNT (1)
        0x75.toByte(), 0x06.toByte(),                         //     REPORT_SIZE (6)
        0x81.toByte(), 0x03.toByte(),                         //     INPUT (Cnst,Var,Abs)
        0x05.toByte(), 0x01.toByte(),                         //     USAGE_PAGE (Generic Desktop)
        0x09.toByte(), 0x30.toByte(),                         //     USAGE (X)
        0x09.toByte(), 0x31.toByte(),                         //     USAGE (Y)
        0x15.toByte(), 0x81.toByte(),                         //     LOGICAL_MINIMUM (-127)
        0x25.toByte(), 0x7f.toByte(),                         //     LOGICAL_MAXIMUM (127)
        0x75.toByte(), 0x08.toByte(),                         //     REPORT_SIZE (8)
        0x95.toByte(), 0x02.toByte(),                         //     REPORT_COUNT (2)
        0x81.toByte(), 0x06.toByte(),                         //     INPUT (Data,Var,Rel)
        0xc0.toByte(),                               //   END_COLLECTION
        0xc0.toByte()                                //END_COLLECTION
    )


/*
    val PRECISION_TOUCHPAD = byteArrayOf(
        //TOUCH PAD input TLC
        0x05.toByte(), 0x0d.toByte(),                         // USAGE_PAGE (Digitizers)
        0x09.toByte(), 0x05.toByte(),                         // USAGE (Touch Pad)
        0xa1.toByte(), 0x01.toByte(),                         // COLLECTION (Application)
        0x85.toByte(), REPORTID_TOUCHPAD,            //   REPORT_ID (Touch pad)
        0x09.toByte(), 0x22.toByte(),                         //   USAGE (Finger)
        0xa1.toByte(), 0x02.toByte(),                         //   COLLECTION (Logical)
        0x15.toByte(), 0x00.toByte(),                         //       LOGICAL_MINIMUM (0)
        0x25.toByte(), 0x01.toByte(),                         //       LOGICAL_MAXIMUM (1)
        0x09.toByte(), 0x47.toByte(),                         //       USAGE (Confidence)
        0x09.toByte(), 0x42.toByte(),                         //       USAGE (Tip switch)
        0x95.toByte(), 0x02.toByte(),                         //       REPORT_COUNT (2)
        0x75.toByte(), 0x01.toByte(),                         //       REPORT_SIZE (1)
        0x81.toByte(), 0x02.toByte(),                         //       INPUT (Data,Var,Abs)
        0x95.toByte(), 0x01.toByte(),                         //       REPORT_COUNT (1)
        0x75.toByte(), 0x02.toByte(),                         //       REPORT_SIZE (2)
        0x25.toByte(), 0x02.toByte(),                         //       LOGICAL_MAXIMUM (2)
        0x09.toByte(), 0x51.toByte(),                         //       USAGE (Contact Identifier)
        0x81.toByte(), 0x02.toByte(),                         //       INPUT (Data,Var,Abs)
        0x75.toByte(), 0x01.toByte(),                         //       REPORT_SIZE (1)
        0x95.toByte(), 0x04.toByte(),                         //       REPORT_COUNT (4)
        0x81.toByte(), 0x03.toByte(),                         //       INPUT (Cnst,Var,Abs)
        0x05.toByte(), 0x01.toByte(),                         //       USAGE_PAGE (Generic Desk..
        0x15.toByte(), 0x00.toByte(),                         //       LOGICAL_MINIMUM (0)
        0x26.toByte(), 0xff.toByte(), 0x0f.toByte(),                   //       LOGICAL_MAXIMUM (4095)
        0x75.toByte(), 0x10.toByte(),                         //       REPORT_SIZE (16)
        0x55.toByte(), 0x0e.toByte(),                         //       UNIT_EXPONENT (-2)
        0x65.toByte(), 0x13.toByte(),                         //       UNIT(Inch,EngLinear)
        0x09.toByte(), 0x30.toByte(),                         //       USAGE (X)
        0x35.toByte(), 0x00.toByte(),                         //       PHYSICAL_MINIMUM (0)
        0x46.toByte(), 0x90.toByte(), 0x01.toByte(),                   //       PHYSICAL_MAXIMUM (400)
        0x95.toByte(), 0x01.toByte(),                         //       REPORT_COUNT (1)
        0x81.toByte(), 0x02.toByte(),                         //       INPUT (Data,Var,Abs)
        0x46.toByte(), 0x13.toByte(), 0x01.toByte(),                   //       PHYSICAL_MAXIMUM (275)
        0x09.toByte(), 0x31.toByte(),                         //       USAGE (Y)
        0x81.toByte(), 0x02.toByte(),                         //       INPUT (Data,Var,Abs)
        0xc0.toByte(),                               //    END_COLLECTION
        0x55.toByte(), 0x0C.toByte(),                         //    UNIT_EXPONENT (-4)
        0x66.toByte(), 0x01.toByte(), 0x10.toByte(),                   //    UNIT (Seconds)
        0x47.toByte(), 0xff.toByte(), 0xff.toByte(), 0x00.toByte(), 0x00.toByte(),      //     PHYSICAL_MAXIMUM (65535)
        0x27.toByte(), 0xff.toByte(), 0xff.toByte(), 0x00.toByte(), 0x00.toByte(),         //  LOGICAL_MAXIMUM (65535)
        0x75.toByte(), 0x10.toByte(),                           //  REPORT_SIZE (16)
        0x95.toByte(), 0x01.toByte(),                           //  REPORT_COUNT (1)
        0x05.toByte(), 0x0d.toByte(),                         //    USAGE_PAGE (Digitizers)
        0x09.toByte(), 0x56.toByte(),                         //    USAGE (Scan Time)
        0x81.toByte(), 0x02.toByte(),                           //  INPUT (Data,Var,Abs)
        0x09.toByte(), 0x54.toByte(),                         //    USAGE (Contact count)
        0x25.toByte(), 0x7f.toByte(),                           //  LOGICAL_MAXIMUM (127)
        0x95.toByte(), 0x01.toByte(),                         //    REPORT_COUNT (1)
        0x75.toByte(), 0x08.toByte(),                         //    REPORT_SIZE (8)
        0x81.toByte(), 0x02.toByte(),                         //    INPUT (Data,Var,Abs)
        0x05.toByte(), 0x09.toByte(),                         //    USAGE_PAGE (Button)
        0x09.toByte(), 0x01.toByte(),                         //    USAGE_(Button 1)
        0x25.toByte(), 0x01.toByte(),                         //    LOGICAL_MAXIMUM (1)
        0x75.toByte(), 0x01.toByte(),                         //    REPORT_SIZE (1)
        0x95.toByte(), 0x01.toByte(),                         //    REPORT_COUNT (1)
        0x81.toByte(), 0x02.toByte(),                         //    INPUT (Data,Var,Abs)
        0x95.toByte(), 0x07.toByte(),                          //   REPORT_COUNT (7)
        0x81.toByte(), 0x03.toByte(),                         //    INPUT (Cnst,Var,Abs)
        0x05.toByte(), 0x0d.toByte(),                         //    USAGE_PAGE (Digitizer)
        0x85.toByte(), REPORTID_MAX_COUNT,            //   REPORT_ID (Feature)
        0x09.toByte(), 0x55.toByte(),                         //    USAGE (Contact Count Maximum)
        0x09.toByte(), 0x59.toByte(),                         //    USAGE (Pad TYpe)
        0x75.toByte(), 0x04.toByte(),                         //    REPORT_SIZE (4)
        0x95.toByte(), 0x02.toByte(),                         //    REPORT_COUNT (2)
        0x25.toByte(), 0x0f.toByte(),                         //    LOGICAL_MAXIMUM (15)
        0xb1.toByte(), 0x02.toByte(),                         //    FEATURE (Data,Var,Abs)
        0x06.toByte(), 0x00.toByte(), 0xff.toByte(),                   //    USAGE_PAGE (Vendor Defined)
        0x85.toByte(), REPORTID_PTPHQA,               //    REPORT_ID (PTPHQA)
        0x09.toByte(), 0xC5.toByte(),                         //    USAGE (Vendor Usage 0xC5)
        0x15.toByte(), 0x00.toByte(),                         //    LOGICAL_MINIMUM (0)
        0x26.toByte(), 0xff.toByte(), 0x00.toByte(),                   //    LOGICAL_MAXIMUM (0xff)
        0x75.toByte(), 0x08.toByte(),                         //    REPORT_SIZE (8)
        0x96.toByte(), 0x00.toByte(), 0x01.toByte(),                   //    REPORT_COUNT (0x100 (256))
        0xb1.toByte(), 0x02.toByte(),                         //    FEATURE (Data,Var,Abs)
        0xc0.toByte(),                               // END_COLLECTION
        //CONFIG TLC
        0x05.toByte(), 0x0d.toByte(),                         //    USAGE_PAGE (Digitizer)
        0x09.toByte(), 0x0E.toByte(),                         //    USAGE (Configuration)
        0xa1.toByte(), 0x01.toByte(),                         //   COLLECTION (Application)
        0x85.toByte(), REPORTID_FEATURE,             //   REPORT_ID (Feature)
        0x09.toByte(), 0x22.toByte(),                         //   USAGE (Finger)
        0xa1.toByte(), 0x02.toByte(),                         //   COLLECTION (logical)
        0x09.toByte(), 0x52.toByte(),                         //    USAGE (Input Mode)
        0x15.toByte(), 0x00.toByte(),                         //    LOGICAL_MINIMUM (0)
        0x25.toByte(), 0x0a.toByte(),                         //    LOGICAL_MAXIMUM (10)
        0x75.toByte(), 0x08.toByte(),                         //    REPORT_SIZE (8)
        0x95.toByte(), 0x01.toByte(),                         //    REPORT_COUNT (1)
        0xb1.toByte(), 0x02.toByte(),                         //    FEATURE (Data,Var,Abs
        0xc0.toByte(),                               //   END_COLLECTION
        0x09.toByte(), 0x22.toByte(),                         //   USAGE (Finger)
        0xa1.toByte(), 0x00.toByte(),                         //   COLLECTION (physical)
        0x85.toByte(), REPORTID_FUNCTION_SWITCH,     //     REPORT_ID (Feature)
        0x09.toByte(), 0x57.toByte(),                         //     USAGE(Surface switch)
        0x09.toByte(), 0x58.toByte(),                         //     USAGE(Button switch)
        0x75.toByte(), 0x01.toByte(),                         //     REPORT_SIZE (1)
        0x95.toByte(), 0x02.toByte(),                         //     REPORT_COUNT (2)
        0x25.toByte(), 0x01.toByte(),                         //     LOGICAL_MAXIMUM (1)
        0xb1.toByte(), 0x02.toByte(),                         //     FEATURE (Data,Var,Abs)
        0x95.toByte(), 0x06.toByte(),                         //     REPORT_COUNT (6)
        0xb1.toByte(), 0x03.toByte(),                         //     FEATURE (Cnst,Var,Abs)
        0xc0.toByte(),                               //   END_COLLECTION
        0xc0.toByte(),                               // END_COLLECTION
        //MOUSE TLC
        0x05.toByte(), 0x01.toByte(),                         // USAGE_PAGE (Generic Desktop)
        0x09.toByte(), 0x02.toByte(),                         // USAGE (Mouse)
        0xa1.toByte(), 0x01.toByte(),                         // COLLECTION (Application)
        0x85.toByte(), REPORTID_MOUSE,               //   REPORT_ID (Mouse)
        0x09.toByte(), 0x01.toByte(),                         //   USAGE (Pointer)
        0xa1.toByte(), 0x00.toByte(),                         //   COLLECTION (Physical)
        0x05.toByte(), 0x09.toByte(),                         //     USAGE_PAGE (Button)
        0x19.toByte(), 0x01.toByte(),                         //     USAGE_MINIMUM (Button 1)
        0x29.toByte(), 0x02.toByte(),                         //     USAGE_MAXIMUM (Button 2)
        0x25.toByte(), 0x01.toByte(),                         //     LOGICAL_MAXIMUM (1)
        0x75.toByte(), 0x01.toByte(),                         //     REPORT_SIZE (1)
        0x95.toByte(), 0x02.toByte(),                         //     REPORT_COUNT (2)
        0x81.toByte(), 0x02.toByte(),                         //     INPUT (Data,Var,Abs)
        0x95.toByte(), 0x06.toByte(),                         //     REPORT_COUNT (6)
        0x81.toByte(), 0x03.toByte(),                         //     INPUT (Cnst,Var,Abs)
        0x05.toByte(), 0x01.toByte(),                         //     USAGE_PAGE (Generic Desktop)
        0x09.toByte(), 0x30.toByte(),                         //     USAGE (X)
        0x09.toByte(), 0x31.toByte(),                         //     USAGE (Y)
        0x75.toByte(), 0x10.toByte(),                         //     REPORT_SIZE (16)
        0x95.toByte(), 0x02.toByte(),                         //     REPORT_COUNT (2)
        0x25.toByte(), 0x0a.toByte(),                          //    LOGICAL_MAXIMUM (10)
        0x81.toByte(), 0x06.toByte(),                         //     INPUT (Data,Var,Rel)
        0xc0.toByte(),                               //   END_COLLECTION
        0xc0.toByte(),                                //END_COLLECTION

        0x05.toByte(), 0x01.toByte(), //USAGE_PAGE (Digitizers)
        0x09.toByte(), 0x02.toByte(), //USAGE (Touch Screen)
        0xa1.toByte(), 0x01.toByte(), //COLLECTION (Application)
        0x85.toByte(), 0x02.toByte(), //    REPORT_ID (MOUSE)
        0x09.toByte(), 0x01.toByte(), //    USAGE (Pointer)
        0xa1.toByte(), 0x00.toByte(), //    COLLECTION (Physical)
        0x05.toByte(), 0x01.toByte(), //        USAGE_PAGE (Generic Desk..
        0x26.toByte(), 0xff.toByte(), 0x0f.toByte(), //  LOGICAL_MAXIMUM (32767)
        0x75.toByte(), 0x10.toByte(), //        REPORT_SIZE (16)
        0x95.toByte(), 0x01.toByte(), //        REPORT_COUNT (1)
        0x55.toByte(), 0x0e.toByte(), //        UNIT_EXPONENT (-2)
        0x65.toByte(), 0x13.toByte(), //        UNIT (Inch,EngLinear)
        0x09.toByte(), 0x30.toByte(), //        USAGE (X)
        0x35.toByte(), 0x00.toByte(), //        PHYSICAL_MINIMUM (0)
        0x46.toByte(), 0x70.toByte(), 0x08.toByte(), //  PHYSICAL_MAXIMUM (1080)
        0x81.toByte(), 0x02.toByte(), //        INPUT (Data,Var,Abs)
        0x46.toByte(), 0x00.toByte(), 0x0f.toByte(), //  PHYSICAL_MAXIMUM (1920)
        0x09.toByte(), 0x31.toByte(), //        USAGE (Y)
        0x81.toByte(), 0x02.toByte(), //        INPUT (Data,Var,Abs)
        0xc0.toByte(),                //    END_COLLECTION
        0xc0.toByte() //                END_COLLECTION
    )*/


}