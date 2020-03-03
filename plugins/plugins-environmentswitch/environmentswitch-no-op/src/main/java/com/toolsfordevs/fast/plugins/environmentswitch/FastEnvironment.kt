package com.toolsfordevs.fast.plugins.environmentswitch

import android.util.ArrayMap
import com.toolsfordevs.fast.core.annotation.Keep

// ToDo Remove when actions is an independent package
//  and remove package actions as well
import com.toolsfordevs.fast.plugins.actions.base.actions.*

@Keep
class FastEnvironment private constructor()
{
    @Keep
    fun <T : Any> action(action: Action<T>)
    {
        // No-op
    }

    @Keep
    fun simple(name: String, callback: () -> Unit)
    {
        // No-op
    }

    @Keep
    fun boolean(name: String, callback: (Boolean?) -> Unit, defaultValue: Boolean = false)
    {
        // No-op
    }

    @Keep
    fun boolean(name: String, callback: (Boolean?) -> Unit, defaultValue: (() -> Boolean?)? = null)
    {
        // No-op
    }

    @Keep
    fun float(name: String, callback: (Float?) -> Unit, defaultValue: (() -> Float?)? = null, min: Float = 0f, max: Float = 100f, decimalPrecision: Int = 2)
    {
        // No-op
    }

    @Keep
    fun float(name: String, callback: (Float?) -> Unit, defaultValue: Float?, min: Float = 0f, max: Float = 100f, decimalPrecision: Int = 2)
    {
        // No-op
    }

    @Keep
    fun int(name: String, callback: (Int?) -> Unit, defaultValue: (() -> Int?)? = null, min: Int = 0, max: Int = 100)
    {
        // No-op
    }

    @Keep
    fun int(name: String, callback: (Int?) -> Unit, defaultValue: Int?, min: Int = 0, max: Int = 100)
    {
        // No-op
    }

    @Keep
    fun long(name: String, callback: (Long?) -> Unit, defaultValue: (() -> Long?)? = null, min: Long = 0L, max: Long = 100L)
    {
        // No-op
    }

    @Keep
    fun long(name: String, callback: (Long?) -> Unit, defaultValue: Long?, min: Long = 0L, max: Long = 100L)
    {
        // No-op
    }

    @Keep
    fun string(name: String, callback: (String?) -> Unit, defaultValue: (() -> String?)? = null)
    {
        // No-op
    }

    @Keep
    fun string(name: String, callback: (String?) -> Unit, defaultValue: String?)
    {
        // No-op
    }

    @Keep
    fun <T : Any> singleChoice(name: String, callback: (T?) -> Unit, choices: ArrayMap<T, String>, defaultValue: (() -> T?)? = null)
    {
        // No-op
    }

    @Keep
    fun <T : Any> singleChoice(name: String, callback: (T?) -> Unit, choices: ArrayMap<T, String>, defaultValue: T? = null)
    {
        // No-op
    }

    @Keep
    fun <T : Any> multiChoice(name: String, callback: (List<T>?) -> Unit, choices: ArrayMap<T, String>, defaultValue: (() -> List<T>?)? = null)
    {
        // No-op
    }

    @Keep
    fun <T : Any> multiChoice(name: String, callback: (List<T>?) -> Unit, choices: ArrayMap<T, String>, defaultValue: List<T>? = null)
    {
        // No-op
    }

    @Keep
    fun combo(name: String, callback: (List<Any?>?) -> Unit, actions:List<Action<Any>>)
    {
        // No-op
    }

    @Keep
    fun combo(name: String, callback: (List<Any?>?) -> Unit, actions: (() -> List<Action<Any>>)? = null)
    {
        // No-op
    }
}