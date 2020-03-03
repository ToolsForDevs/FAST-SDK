package com.toolsfordevs.fast.modules.resources

import com.toolsfordevs.fast.core.annotation.Keep

@Keep
open class ValueResource<T>(type: String, name:String, resId:Int = -1, var value:T? = null) : TypedResource(type, name, resId)