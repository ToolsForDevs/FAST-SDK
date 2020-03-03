package com.toolsfordevs.fast.core.ui

import android.view.KeyEvent
import com.toolsfordevs.fast.core.annotation.Keep

@Keep
object KeyboardShorcutManager
{
    val lowercaseKeys = listOf(
            Key("a", KeyEvent.KEYCODE_A),
            Key("b", KeyEvent.KEYCODE_B),
            Key("c", KeyEvent.KEYCODE_C),
            Key("d", KeyEvent.KEYCODE_D),
            Key("e", KeyEvent.KEYCODE_E),
            Key("f", KeyEvent.KEYCODE_F),
            Key("g", KeyEvent.KEYCODE_G),
            Key("h", KeyEvent.KEYCODE_H),
            Key("i", KeyEvent.KEYCODE_I),
            Key("j", KeyEvent.KEYCODE_J),
            Key("k", KeyEvent.KEYCODE_K),
            Key("l", KeyEvent.KEYCODE_L),
            Key("m", KeyEvent.KEYCODE_M),
            Key("n", KeyEvent.KEYCODE_N),
            Key("o", KeyEvent.KEYCODE_O),
            Key("p", KeyEvent.KEYCODE_P),
            Key("q", KeyEvent.KEYCODE_Q),
            Key("r", KeyEvent.KEYCODE_R),
            Key("s", KeyEvent.KEYCODE_S),
            Key("t", KeyEvent.KEYCODE_T),
            Key("u", KeyEvent.KEYCODE_U),
            Key("v", KeyEvent.KEYCODE_V),
            Key("w", KeyEvent.KEYCODE_W),
            Key("x", KeyEvent.KEYCODE_X),
            Key("y", KeyEvent.KEYCODE_Y),
            Key("z", KeyEvent.KEYCODE_Z))

    val digits = listOf(
            Key("0", KeyEvent.KEYCODE_0),
            Key("1", KeyEvent.KEYCODE_1),
            Key("2", KeyEvent.KEYCODE_2),
            Key("3", KeyEvent.KEYCODE_3),
            Key("4", KeyEvent.KEYCODE_4),
            Key("5", KeyEvent.KEYCODE_5),
            Key("6", KeyEvent.KEYCODE_6),
            Key("7", KeyEvent.KEYCODE_7),
            Key("8", KeyEvent.KEYCODE_8),
            Key("9", KeyEvent.KEYCODE_9))

    data class Key(val name:String, val keyCode:Int)
}