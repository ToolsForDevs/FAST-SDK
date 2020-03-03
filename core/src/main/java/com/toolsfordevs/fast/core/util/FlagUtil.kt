package com.toolsfordevs.fast.core.util

object FlagUtil
{
    fun isFlagIn(flag:Int, flags:Int):Boolean
    {
        return flags or flag == flags
    }

    fun addFlag(flag:Int, flags:Int):Int
    {
        return flags or flag
    }

    fun removeFlag(flag:Int, flags:Int):Int
    {
        return flags and flag.inv()
    }

    fun getFlagsIn(flags:Int, map:Map<Int, String>):String
    {
        if (flags == 0)
            return "None (0)"

        val flagsIn = arrayListOf<String>()

        map.forEach { entry ->
            if (isFlagIn(entry.key, flags))
                flagsIn.add(entry.value)
        }

        return flagsIn.sorted().joinToString("\n")
    }
}