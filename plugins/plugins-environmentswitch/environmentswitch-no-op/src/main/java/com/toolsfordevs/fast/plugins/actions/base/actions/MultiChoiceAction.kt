package com.toolsfordevs.fast.plugins.actions.base.actions

import android.util.ArrayMap
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.plugins.actions.base.values.MultiChoiceValue


/**
 * In the ArrayMap, your values must be the keys, and the labels (of the choice list) must be the values
 */
@Keep
open class MultiChoiceAction<T:Any>(name: String, callback: (List<T>?) -> Unit, override val value:MultiChoiceValue<T>? = null) : Action<List<T>>(name, callback, value)

