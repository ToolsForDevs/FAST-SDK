package com.toolsfordevs.fast.modules.resources

import android.graphics.drawable.Drawable
import com.toolsfordevs.fast.core.annotation.Keep

@Keep
class DrawableResource(name:String, resId:Int = -1, value: Drawable? = null) : ValueResource<Drawable>(DRAWABLE, name, resId, value)