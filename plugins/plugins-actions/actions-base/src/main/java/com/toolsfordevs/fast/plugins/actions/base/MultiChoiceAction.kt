package com.toolsfordevs.fast.plugins.actions.base

import android.util.ArrayMap
import com.toolsfordevs.fast.core.annotation.Keep


/**
 * In the ArrayMap, your values must be the keys, and the labels (of the choice list) must be the values
 */
@Keep
open class MultiChoiceAction<T:Any>(val choices: (() -> ArrayMap<T, String>), defaultValues:(()->List<T>?)? = null) : Action<List<T>>(defaultValues)
{
    /**
     * In the ArrayMap, your values must be the keys, and the labels (of the choice list) must be the values
     */
    constructor(choices: ArrayMap<T, String>, defaultValues:List<T>? = null) : this({ choices }, { defaultValues })
}