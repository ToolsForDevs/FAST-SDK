package com.toolsfordevs.fast.modules.resources

import com.toolsfordevs.fast.core.annotation.Keep

@Keep
class DimensionResource(name:String, resId:Int = -1, value:Float? = null) : ValueResource<Float>(DIMEN, name, resId, value)