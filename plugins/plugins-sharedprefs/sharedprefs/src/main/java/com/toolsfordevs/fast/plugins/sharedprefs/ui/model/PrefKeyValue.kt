package com.toolsfordevs.fast.plugins.sharedprefs.ui.model

import java.util.*

internal sealed class PrefKeyValue(val name: String) : Comparable<PrefKeyValue>
{
    override fun compareTo(other: PrefKeyValue): Int
    {
        return name.toLowerCase(Locale.getDefault()).compareTo(other.name.toLowerCase(Locale.getDefault()))
    }
}

internal class BooleanPref(name:String) : PrefKeyValue(name)
internal class IntPref(name:String) : PrefKeyValue(name)
internal class FloatPref(name:String) : PrefKeyValue(name)
internal class LongPref(name:String) : PrefKeyValue(name)
internal class StringPref(name:String) : PrefKeyValue(name)
internal class StringSetPref(name:String) : PrefKeyValue(name)