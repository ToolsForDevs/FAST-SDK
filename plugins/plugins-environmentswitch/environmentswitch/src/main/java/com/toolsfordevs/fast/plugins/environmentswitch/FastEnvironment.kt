package com.toolsfordevs.fast.plugins.environmentswitch

import android.util.ArrayMap
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.plugins.actions.base.actions.*
import com.toolsfordevs.fast.plugins.actions.base.values.*

class FastEnvironment internal constructor(private val store: Store)
{
    @Keep
    fun <T : Any> action(action: Action<T>)
    {
        store.add(action)
    }

    @Keep
    fun simple(name: String, callback: () -> Unit) = action(SimpleAction(name, callback))

    @Keep
    fun boolean(name: String, callback: (Boolean?) -> Unit, defaultValue: Boolean = false) = action(BooleanAction(name, callback, BooleanValue(defaultValue)))

    @Keep
    fun boolean(name: String, callback: (Boolean?) -> Unit, defaultValue: (() -> Boolean?)? = null) =
            action(BooleanAction(name, callback, BooleanValue(defaultValue)))

    @Keep
    fun float(name: String, callback: (Float?) -> Unit, defaultValue: (() -> Float?)? = null, min: Float = 0f, max: Float = 100f, decimalPrecision: Int = 2) =
            action(FloatAction(name, callback, FloatValue(defaultValue, min, max, decimalPrecision)))

    @Keep
    fun float(name: String, callback: (Float?) -> Unit, defaultValue: Float?, min: Float = 0f, max: Float = 100f, decimalPrecision: Int = 2) =
            action(FloatAction(name, callback, FloatValue(defaultValue, min, max, decimalPrecision)))

    @Keep
    fun int(name: String, callback: (Int?) -> Unit, defaultValue: (() -> Int?)? = null, min: Int = 0, max: Int = 100) =
            action(IntAction(name, callback, IntValue(defaultValue, min, max)))

    @Keep
    fun int(name: String, callback: (Int?) -> Unit, defaultValue: Int?, min: Int = 0, max: Int = 100) =
            action(IntAction(name, callback, IntValue(defaultValue, min, max)))

    @Keep
    fun long(name: String, callback: (Long?) -> Unit, defaultValue: (() -> Long?)? = null, min: Long = 0L, max: Long = 100L) =
            action(LongAction(name, callback, LongValue(defaultValue, min, max)))

    @Keep
    fun long(name: String, callback: (Long?) -> Unit, defaultValue: Long?, min: Long = 0L, max: Long = 100L) =
            action(LongAction(name, callback, LongValue(defaultValue, min, max)))

    @Keep
    fun string(name: String, callback: (String?) -> Unit, defaultValue: (() -> String?)? = null) = action(StringAction(name, callback, StringValue(defaultValue)))

    @Keep
    fun string(name: String, callback: (String?) -> Unit, defaultValue: String?) = action(StringAction(name, callback, StringValue(defaultValue)))

    @Keep
    fun <T : Any> singleChoice(name: String, callback: (T?) -> Unit, choices: ArrayMap<T, String>, defaultValue: (() -> T?)? = null) =
            action(SingleChoiceAction(name, callback, SingleChoiceValue(choices, defaultValue)))

    @Keep
    fun <T : Any> singleChoice(name: String, callback: (T?) -> Unit, choices: ArrayMap<T, String>, defaultValue: T? = null) =
            action(SingleChoiceAction(name, callback, SingleChoiceValue(choices, defaultValue)))

    @Keep
    fun <T : Any> multiChoice(name: String, callback: (List<T>?) -> Unit, choices: ArrayMap<T, String>, defaultValue: (() -> List<T>?)? = null) =
            action(MultiChoiceAction(name, callback, MultiChoiceValue(choices, defaultValue)))

    @Keep
    fun <T : Any> multiChoice(name: String, callback: (List<T>?) -> Unit, choices: ArrayMap<T, String>, defaultValue: List<T>? = null) =
            action(MultiChoiceAction(name, callback, MultiChoiceValue(choices, defaultValue)))

    @Keep
    fun combo(name: String, callback: (List<Any?>?) -> Unit, actions:List<Action<Any>>) =
            action(ComboAction(name, callback, ComboValue(actions)))

    @Keep
    fun combo(name: String, callback: (List<Any?>?) -> Unit, actions: (() -> List<Action<Any>>)? = null) =
            action(ComboAction(name, callback, ComboValue(actions)))
}