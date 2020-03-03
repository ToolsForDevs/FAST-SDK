package com.toolsfordevs.fast.core.util

import android.annotation.SuppressLint
import android.provider.Settings
import com.toolsfordevs.fast.core.AppInstance
import com.toolsfordevs.fast.core.annotation.Keep
import java.lang.Exception
import java.nio.charset.StandardCharsets

@Keep
object CoreUtil
{
    @SuppressLint("HardwareIds")
    @JvmStatic
    fun getDeviceId():String
    {
        return Settings.Secure.getString(AppInstance.get().contentResolver, Settings.Secure.ANDROID_ID)
    }


    @JvmStatic
    fun getTime():String
    {
        return System.currentTimeMillis().toString().substring(0..10)
    }

    @JvmStatic
    fun getUtfString(string:String):String
    {
        return String(string.toByteArray(StandardCharsets.UTF_8), StandardCharsets.UTF_8)
    }

    @JvmStatic
    fun parseLong(string:String):Long
    {
        return try {
            string.toLong()
        }
        catch(e: Exception)
        {
            0L
        }
    }

    @JvmStatic
    fun append(str1:String, str2:String):String
    {
        return str1 + str2
    }

    @JvmStatic
    fun randStr(length:Int):String
    {
        val s = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz"

        val sb = java.lang.StringBuilder(length)

        for (i in 0 until length)
        {
            // generate a random number between
            // 0 to AlphaNumericString variable length
            val index:Int = (s.length * Math.random()).toInt()
            // add Character one by one in end of sb
            sb.append(s.get(index))
        }

        return sb.toString()
    }

    @JvmStatic
    fun areStringEqual(str1:String, str2:String):Boolean
    {
        return str1 == str2
    }

    @JvmStatic
    fun substr(src:String, start:Int, length: Int):String
    {
        return src.substring(start..(start + length))
    }

}