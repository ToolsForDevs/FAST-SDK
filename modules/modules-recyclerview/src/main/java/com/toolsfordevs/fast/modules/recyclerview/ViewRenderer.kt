package com.toolsfordevs.fast.modules.recyclerview

import android.view.View
import com.toolsfordevs.fast.core.annotation.Keep

@Keep
abstract class ViewRenderer<T, R: View> (view:R) : Renderer<T>(view)
{
    @Suppress("UNCHECKED_CAST")
    val view:R = itemView as R
}