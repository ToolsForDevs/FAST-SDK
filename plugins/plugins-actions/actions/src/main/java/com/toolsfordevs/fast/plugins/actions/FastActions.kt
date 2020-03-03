package com.toolsfordevs.fast.plugins.actions

import android.util.ArrayMap
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.plugins.actions.base.*
import com.toolsfordevs.fast.plugins.actions.ui.Repository

class FastActions internal constructor(private val repository: Repository)
{
    @Keep
    fun simple(name: String, callback: () -> Unit)
    {
        repository.addAction(name, callback)
    }

    @Keep
    fun <T : Any> action(name: String, action: Action<T>, callback: (value: T?) -> Unit)
    {
        repository.addAction(name, action, callback)
    }

    @Keep
    fun <T : Any> custom(custom: CustomAction<T>)
    {
        action(custom.name, custom.action, custom.callback)
    }

    @Keep
    fun boolean(name: String, callback: (Boolean?) -> Unit, defaultValue: Boolean = false) = action(name, BooleanAction(defaultValue), callback)

    @Keep
    fun boolean(name: String, callback: (Boolean?) -> Unit, defaultValue: (() -> Boolean?)? = null) =
            action(name, BooleanAction(defaultValue), callback)

    @Keep
    fun float(name: String, callback: (Float?) -> Unit, defaultValue: (() -> Float?)? = null, min: Float = 0f, max: Float = 100f, decimalPrecision: Int = 2) =
            action(name, FloatAction(defaultValue, min, max, decimalPrecision), callback)

    @Keep
    fun float(name: String, callback: (Float?) -> Unit, defaultValue: Float?, min: Float = 0f, max: Float = 100f, decimalPrecision: Int = 2) =
            action(name, FloatAction(defaultValue, min, max, decimalPrecision), callback)

    @Keep
    fun int(name: String, callback: (Int?) -> Unit, defaultValue: (() -> Int?)? = null, min: Int = 0, max: Int = 100) =
            action(name, IntAction(defaultValue, min, max), callback)

    @Keep
    fun int(name: String, callback: (Int?) -> Unit, defaultValue: Int?, min: Int = 0, max: Int = 100) =
            action(name, IntAction(defaultValue, min, max), callback)

    @Keep
    fun long(name: String, callback: (Long?) -> Unit, defaultValue: (() -> Long?)? = null, min: Long = 0L, max: Long = 100L) =
            action(name, LongAction(defaultValue, min, max), callback)

    @Keep
    fun long(name: String, callback: (Long?) -> Unit, defaultValue: Long?, min: Long = 0L, max: Long = 100L) =
            action(name, LongAction(defaultValue, min, max), callback)

    @Keep
    fun string(name: String, callback: (String?) -> Unit, defaultValue: (() -> String?)? = null) = action(name, StringAction(defaultValue), callback)

    @Keep
    fun string(name: String, callback: (String?) -> Unit, defaultValue: String?) = action(name, StringAction(defaultValue), callback)

    @Keep
    fun <T : Any> singleChoice(name: String, choices: (() -> ArrayMap<T, String>), callback: (T?) -> Unit, defaultValue: (() -> T?)? = null) =
            action(name, SingleChoiceAction(choices, defaultValue), callback)

    @Keep
    fun <T : Any> singleChoice(name: String, choices: ArrayMap<T, String>, callback: (T?) -> Unit, defaultValue: T? = null) =
            action(name, SingleChoiceAction(choices, defaultValue), callback)

    @Keep
    fun <T : Any> multiChoice(name: String, choices: (() -> ArrayMap<T, String>), callback: (List<T>?) -> Unit, defaultValue: (() -> List<T>?)? = null) =
            action(name, MultiChoiceAction(choices, defaultValue), callback)

    @Keep
    fun <T : Any> multiChoice(name: String, choices: ArrayMap<T, String>, callback: (List<T>?) -> Unit, defaultValue: List<T>? = null) =
            action(name, MultiChoiceAction(choices, defaultValue), callback)

    @Keep
    fun combo(name: String, actions: List<ComboAction.ActionItem>, callback: (List<Any?>?) -> Unit) =
            action(name, ComboAction(actions), callback)

    @Keep
    fun combo(name: String, actions: (() -> List<ComboAction.ActionItem>?)? = null, callback: (List<Any?>?) -> Unit) =
            action(name, ComboAction(actions), callback)
}