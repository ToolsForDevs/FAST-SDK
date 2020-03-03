package com.toolsfordevs.fast.core

import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.core.util.ColorUtil
import com.toolsfordevs.fast.core.util.ResUtils

@Keep
object FastColor
{

    val colorPrimary:Int = ResUtils.getColor(R.color.fast_primary_color)
    val colorAccent:Int = ResUtils.getColor(R.color.fast_accent_color)


    val colorPrimaryDark:Int = ColorUtil.darken(colorPrimary, 0.15f)
    val colorPrimaryDarker:Int = ColorUtil.darken(colorPrimaryDark, 0.15f)

    val colorPrimaryLight:Int = ColorUtil.lighten(colorPrimary, 0.15f)
    val colorPrimaryLighter:Int = ColorUtil.lighten(colorPrimaryLight, 0.15f)

    val colorAccentDark:Int = ColorUtil.darken(colorAccent, 0.15f)
    val colorAccentDarker:Int = ColorUtil.darken(colorAccentDark, 0.15f)

    val colorAccentLight:Int = ColorUtil.lighten(colorAccent, 0.15f)
    val colorAccentLighter:Int = ColorUtil.lighten(colorAccentLight, 0.15f)
}