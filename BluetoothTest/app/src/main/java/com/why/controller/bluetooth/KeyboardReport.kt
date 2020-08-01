package com.why.controller.bluetooth

import kotlin.experimental.and
import kotlin.experimental.or

@Suppress("EXPERIMENTAL_FEATURE_WARNING")
inline class KeyboardReport (
    val bytes: ByteArray = ByteArray(3) {0}
) {


    var leftControl: Boolean
        get() = bytes[0] and 0b1 != 0.toByte()
        set(value) {
            bytes[0] = if (value)
                bytes[0] or 0b1
            else
                bytes[0] and 0b11111110.toByte()
        }

    var leftShift: Boolean
        get() = bytes[0] and 0b10 != 0.toByte()
        set(value) {
            bytes[0] = if (value)
                bytes[0] or 0b10
            else
                bytes[0] and 0b11111101.toByte()
        }

    var leftAlt: Boolean
        get() = bytes[0] and 0b10 != 0.toByte()
        set(value) {
            bytes[0] = if (value)
                bytes[0] or 0b100
            else
                bytes[0] and 0b11111011.toByte()
        }
    var leftGui: Boolean
        get() = bytes[0] and 0b10 != 0.toByte()
        set(value) {
            bytes[0] = if (value)
                bytes[0] or 0b1000
            else
                bytes[0] and 0b11110111.toByte()
        }

    var rightControl: Boolean
        get() = bytes[0] and 0b1 != 0.toByte()
        set(value) {
            bytes[0] = if (value)
                bytes[0] or 0b10000
            else
                bytes[0] and 0b11101111.toByte()
        }

    var rightShift: Boolean
        get() = bytes[0] and 0b10 != 0.toByte()
        set(value) {
            bytes[0] = if (value)
                bytes[0] or 0b100000
            else
                bytes[0] and 0b11011111.toByte()
        }

    var rightAlt: Boolean
        get() = bytes[0] and 0b10 != 0.toByte()
        set(value) {
            bytes[0] = if (value)
                bytes[0] or 0b1000000
            else
                bytes[0] and 0b10111111.toByte()
        }
    var rightGui: Boolean
        get() = bytes[0] and 0b10 != 0.toByte()
        set(value) {
            bytes[0] = if (value)
                bytes[0] or 0b10000000.toByte()
            else
                bytes[0] and 0b01111111
        }

    var key1: Byte
        get() = bytes[2]
        set(value) { bytes[2] = value }



    fun reset() = bytes.fill(0)

    companion object {
        const val ID = 8

        val KeyEventMap = mapOf<String,Int>(
                "a" to 4,
                "b" to 5,
                "c" to 6,
                "d" to 7,
                "e" to 8,
                "f" to 9,
                "g" to 10,
                "h" to 11,
                "i" to 12,
                "j" to 13,
                "k" to 14,
                "l" to 15,
                "m" to 16,
                "n" to 17,
                "o" to 18,
                "p" to 19,
                "q" to 20,
                "r" to 21,
                "s" to 22,
                "t" to 23,
                "u" to 24,
                "v" to 25,
                "w" to 26,
                "x" to 27,
                "y" to 28,
                "z" to 29,
                "1" to 30,
                "2" to 31,
                "3" to 32,
                "4" to 33,
                "5" to 34,
                "6" to 35,
                "7" to 36,
                "8" to 37,
                "9" to 38,
                "0" to 39,
                "f1" to 58,
                "f2" to 59,
                "f3" to 60,
                "f4" to 61,
                "f5" to 62,
                "f6" to 63,
                "f7" to 64,
                "f8" to 65,
                "f9" to 66,
                "f10" to 67,
                "f11" to 68,
                "f12" to 69,
                "enter" to 40,
                "esc" to 41,
                "backspace" to 42,
                "tab" to 43,
                "space" to 44,
                "-" to 45,
                "=" to 46,
                "(" to 47,
                ")" to 48,
                "\\" to 49,
                ";" to 51,
                "'" to 52,
                "`" to 53,
                "," to 54,
                "." to 55,
                "/" to 56,
                "scrolllock" to 71,
                "insert" to 73,
                "home" to 74,
                "pgup" to 75,
                "pageup" to 75,
                "delete" to 76,
                //KeyEvent.KEYCODE_MOVE_END to 77,
                //there's a keycode to move to the end???????
                "pgdn" to 78,
                "pagedown" to 78,
                "numlock" to 83,
                "right" to 79,
                "left" to 80,
                "down" to 81,
                "up" to 82,
                "@" to 31,
                "#" to 32,
                "*" to 37
        )
    }
}