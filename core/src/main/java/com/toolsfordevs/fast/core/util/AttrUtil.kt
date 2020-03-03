package com.toolsfordevs.fast.core.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import com.toolsfordevs.fast.core.annotation.Keep

@Keep
object AttrUtil {
    fun getString(context: Context, attr: Int): String? {
        val ta = context.obtainStyledAttributes(intArrayOf(attr))
        val string = ta.getString(0)
        ta.recycle()

        return string
    }

    fun getDrawable(context: Context, attr: Int): Drawable? {
        val ta = context.obtainStyledAttributes(intArrayOf(attr))
        val drawable = ta.getDrawable(0)
        ta.recycle()

        return drawable
    }

    fun getDrawables(context: Context, attrs: IntArray): Array<Drawable?> {
        val ta = context.obtainStyledAttributes(attrs)
        val drawables = arrayOfNulls<Drawable>(attrs.size)

        for (i in attrs.indices) {
            drawables[i] = ta.getDrawable(i)
        }

        ta.recycle()

        return drawables
    }

    fun getBoolean(context: Context, attr: Int, defaultValue: Boolean?): Boolean? {
        val ta = context.obtainStyledAttributes(intArrayOf(attr))
        val bool = ta.getBoolean(0, defaultValue!!)
        ta.recycle()

        return bool
    }

    fun getColor(context: Context, attr: Int): Int {
        val ta = context.obtainStyledAttributes(intArrayOf(attr))
        val color = ta.getColor(0, 0)
        ta.recycle()

        return color
    }

    fun getValue(context: Context, attr: Int): TypedValue? {
        val ta = context.obtainStyledAttributes(intArrayOf(attr))
        val tv = TypedValue()
        val bool = ta.getValue(0, tv)
        ta.recycle()
        return if (bool) tv else null

    }

    @JvmOverloads
    fun getResourceId(context: Context, attr: Int, defaultValue: Int = 0): Int {
        val ta = context.obtainStyledAttributes(intArrayOf(attr))
        val resourteId = ta.getResourceId(0, defaultValue)
        ta.recycle()

        return resourteId
    }
}
