package com.toolsfordevs.fast.core.ui.widget

import android.content.Context
import com.toolsfordevs.fast.core.FastColor
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.modules.androidx.tabs.TabLayout

@Keep
class FastTabLayout(context:Context): TabLayout(context)
{
    init
    {
        setTabTextColors(0xCCFFFFFF.toInt(), 0xFFFFFFFF.toInt())
        setBackgroundColor(FastColor.colorPrimary)
        setSelectedTabIndicatorColor(FastColor.colorAccent)
        setSelectedTabIndicatorHeight(Dimens.dp(3))
    }
}