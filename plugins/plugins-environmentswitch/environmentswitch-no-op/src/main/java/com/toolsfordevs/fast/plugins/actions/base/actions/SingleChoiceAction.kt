package com.toolsfordevs.fast.plugins.actions.base.actions

import android.util.ArrayMap
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.plugins.actions.base.values.SingleChoiceValue


/**
 * In the ArrayMap, your values must be the keys, and the labels (of the choice list) must be the values
 */
@Keep
class SingleChoiceAction<T:Any>(name: String, callback: (T?) -> Unit, override val value: SingleChoiceValue<T>? = null) : Action<T>(name, callback, value)
{
}