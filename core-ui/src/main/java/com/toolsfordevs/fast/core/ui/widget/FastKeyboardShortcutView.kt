package com.toolsfordevs.fast.core.ui.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.view.Gravity
import android.widget.TextView
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.core.ext.dp
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.core.ui.R
import com.toolsfordevs.fast.core.ui.ext.setPadding
import com.toolsfordevs.fast.core.ui.ext.setPaddingHorizontal

@Keep
class FastKeyboardShortcutView(context: Context) : TextView(context)
{
    init
    {
        setPadding(4.dp)
        setPaddingHorizontal(12.dp)
        setTextColor(MaterialColor.WHITE_87)

        val rad = 4f.dp
        val d = ShapeDrawable(RoundRectShape(floatArrayOf(rad, rad, rad,rad,rad,rad,rad,rad), null, null)).apply {
            backgroundTintList = ColorStateList.valueOf(MaterialColor.BLACK_54)
        }
        background = d
        setCompoundDrawablesWithIntrinsicBounds(R.drawable.fast_core_ic_keyboard, 0, 0, 0)
        compoundDrawablePadding = 8.dp
        gravity = Gravity.CENTER_VERTICAL
        typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
    }
}