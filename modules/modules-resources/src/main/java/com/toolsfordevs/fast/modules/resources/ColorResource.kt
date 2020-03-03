package com.toolsfordevs.fast.modules.resources

import android.content.res.ColorStateList
import com.toolsfordevs.fast.core.annotation.Keep

@Keep
class ColorResource(name:String, resId:Int = -1, value:Int? = null) : ValueResource<Int>(COLOR, name, resId, value)
{
    var colorStateList: ColorStateList? = null
    val isColorStateList
        get() = colorStateList != null
}