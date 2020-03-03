package com.toolsfordevs.fast.plugins.actions.base

import android.util.ArrayMap
import com.toolsfordevs.fast.core.annotation.Keep


/**
 * In the ArrayMap, your values must be the keys, and the labels (of the choice list) must be the values
 */
@Keep
class SingleChoiceAction<T:Any>(val choices: (() -> ArrayMap<T, String>), defaultValue:(()->T?)? = null) : Action<T>(defaultValue)
{
    /**
     * In the ArrayMap, your values must be the keys, and the labels (of the choice list) must be the values
     */
    constructor(choices: ArrayMap<T, String>, defaultValue:T? = null) : this({ choices }, { defaultValue })
}