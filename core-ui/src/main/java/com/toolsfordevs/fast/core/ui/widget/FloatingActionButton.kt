package com.toolsfordevs.fast.core.ui.widget

import android.content.Context
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.widget.ImageButton
import com.toolsfordevs.fast.core.FastColor
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.core.ext.layoutParamsVV
import com.toolsfordevs.fast.core.util.AndroidVersion
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.core.util.ResUtils
import com.toolsfordevs.fast.core.ui.R

@Keep
class FloatingActionButton(context: Context) : ImageButton(context)
{
    init
    {
        elevation = Dimens.dpF(2)
        background = ShapeDrawable(OvalShape()).apply { setTint(FastColor.colorAccent)}

        if (AndroidVersion.isMinMarshmallow())
            foreground = ResUtils.getDrawable(R.drawable.fast_selectable_item_background_borderless)

        val dp56 = Dimens.dp(56)
        layoutParams = layoutParamsVV(dp56, dp56)
    }

    override fun setBackgroundColor(color:Int)
    {
        background = ShapeDrawable(OvalShape()).apply { setTint(color)}
    }
}