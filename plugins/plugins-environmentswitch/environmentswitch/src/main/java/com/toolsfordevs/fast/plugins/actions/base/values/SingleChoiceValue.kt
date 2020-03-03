package com.toolsfordevs.fast.plugins.actions.base.values

import android.util.ArrayMap
import com.toolsfordevs.fast.core.annotation.Keep


/**
 * In the ArrayMap, your values must be the keys, and the labels (of the choice list) must be the values
 */
@Keep
open class SingleChoiceValue<T>(val choices: ArrayMap<T, String>, defaultValue:(()->T?)? = null) : Value<T>(defaultValue)
{
    /**
     * In the ArrayMap, your values must be the keys, and the labels (of the choice list) must be the values
     */
    constructor(choices: ArrayMap<T, String>, defaultValue:T? = null) : this(choices, { defaultValue })
}