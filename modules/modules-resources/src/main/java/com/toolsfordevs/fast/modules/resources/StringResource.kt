package com.toolsfordevs.fast.modules.resources

import com.toolsfordevs.fast.core.annotation.Keep

@Keep
class StringResource(name:String, resId:Int = -1, value:String? = null) : ValueResource<String>(STRING, name, resId, value)