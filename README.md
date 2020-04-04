
# Android Controller

This is an android app to allow you to send keyboard controls to a computer.

This uses your internet to control your computer, so be sure to have a decently fast and stable internet connection so that you can have a good gaming experience.

## Installing

Download the files under release.

Install the android app on your phone and run the python script on your computer. You will need to have [pyautogui](https://pyautogui.readthedocs.io/en/latest/) installed.

Firstly, to connect your phone with your computer, you will need both devices to be connected to the same wifi. Then you will need to get your computer's public IP. To do that on a windows OS, go to command prompt and type in `ipconfig`.

```
Wireless LAN adapter Wi-Fi:

   Connection-specific DNS Suffix  . :
   Link-local IPv6 Address . . . . . : fe80::787d:c9d8:9a82:7f4%6
   IPv4 Address. . . . . . . . . . . : 192.168.1.229
   Subnet Mask . . . . . . . . . . . : 255.255.255.0
   Default Gateway . . . . . . . . . : 192.168.1.254
```

Something like this should come out. So your IP is `192.168.1.229`.

For the port, you can use any number from 1024 to 65535.

## Features
### Support for many keys
This is the full list of what keys are supported:
```
!, ', #, $, %, &, ", (, ), *, +, ,, -, ., /,
0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 
:, ;, <, =, >, ?, @, [, \, ], ^, _, `,
a, b, c, d, e, f, g, h, i, j, k, l, m,
n, o, p, q, r, s, t, u, v, w, x, y, z,
{, |, }, ~, up, down, left, right,
add, subtract, multiply, divide,
home, end, insert, delete, pagedown, pageup, pgdn, pgup, 
printscreen, prntscrn,
fn, f1, f2, f3, f4, f5, f6, f7, f8, f9, f10,
f11, f12, f13, f14, f15, f16, f17, f18, f19, f20,
f21, f22, f23, f24,
num0, num1, num2, num3, num4, num5, num6, num7, num8, num9, numlock,
prtsc, prtscr, return, scrolllock, alt, backspace,
capslock, ctrl, enter, esc, shift, space, tab,
volumedown, volumemute, volumeup, win, m1, m2, m3
```

### Hotkey support
When entering keycodes, seperate them with a space.
For example to do the copy hotkey which is `Ctrl+C`, the keycode will will `ctrl c`.


## Future features

### Macro support
Ability to send certain precisely timed keystrokes for certain games.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgements
I would like to thank my friends for finding bugs and giving suggestions for my app. Thanks :)
