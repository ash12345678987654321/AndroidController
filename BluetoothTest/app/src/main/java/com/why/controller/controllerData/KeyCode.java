package com.why.controller.controllerData;

import android.util.Pair;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class KeyCode {
    private static final Set<String> keycodes = new HashSet<>(Arrays.asList("!", "'", "#", "$", "%", "&", "\"", "(",
            ")", "*", "+", ",", "-", ".", "/", "0", "1", "2", "3", "4", "5", "6", "7",
            "8", "9", ":", ";", "<", "=", ">", "?", "@", "[", "\\", "]", "^", "_", "`",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o",
            "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "{", "|", "}", "~",
            "up", "down", "left", "right",
            "add", "subtract", "multiply", "divide",
            "home", "end", "insert", "delete", "pagedown", "pageup", "pgdn", "pgup", "printscreen", "prntscrn",
            "fn", "f1", "f2", "f3", "f4", "f5", "f6", "f7", "f8", "f9", "f10",
            "f11", "f12", "f13", "f14", "f15", "f16", "f17", "f18", "f19", "f20",
            "f21", "f22", "f23", "f24",
            "num0", "num1", "num2", "num3", "num4", "num5", "num6", "num7", "num8", "num9", "numlock",
            "prtsc", "prtscr", "return", "scrolllock",
            "alt", "backspace", "capslock", "ctrl", "enter", "esc", "shift", "space", "tab", "volumedown", "volumemute", "volumeup", "win",
            "m1", "m2", "m3"));


    public static Pair<Boolean, String> invalid(String s) {
        if (s.equals("")) { //so apparently now im going to allow users to just not input anything wow
            return new Pair<>(false, null);
        }

        for (String i : s.split(" ")) {
            if (!keycodes.contains(i)) return new Pair<>(true, i + " is not a valid key code");
        }

        return new Pair<>(false, null);
    }
}
