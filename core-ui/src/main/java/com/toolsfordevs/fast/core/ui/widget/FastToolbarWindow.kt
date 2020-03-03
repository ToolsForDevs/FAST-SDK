package com.toolsfordevs.fast.core.ui.widget

import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import android.widget.PopupWindow
import com.toolsfordevs.fast.core.FastColor
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.core.util.Dimens

@Keep
open class FastToolbarWindow : PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, Dimens.dp(56))
{
    init
    {
        isOutsideTouchable = true
        setBackgroundColor(FastColor.colorPrimary) // ToDo ColorUtil.lighten(0.2)
    }

    fun setBackgroundColor(color:Int)
    {
        setBackgroundDrawable(ColorDrawable(color))
    }
}