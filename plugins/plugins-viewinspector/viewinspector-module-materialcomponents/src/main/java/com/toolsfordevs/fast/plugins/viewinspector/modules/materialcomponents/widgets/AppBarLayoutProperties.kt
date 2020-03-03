package com.toolsfordevs.fast.plugins.viewinspector.modules.materialcomponents.widgets

import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.BooleanProperty
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.DimensionFloatRangeProperty
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.DrawableProperty
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.PropertyCategory
import com.google.android.material.appbar.AppBarLayout


internal class AppBarLayoutProperties: ViewPropertyHolder(AppBarLayout::class.java)
{
    init
    {
        // setElevation is in View
        // add(PropertyCategory.COMMON, BooleanProperty(AppBarLayout::exp, AppBarLayout::setExpanded)) no getter :()
        add(PropertyCategory.COMMON,
            BooleanProperty(AppBarLayout::isLiftOnScroll,
                AppBarLayout::setLiftOnScroll))
        // add(PropertyCategory.COMMON, BooleanProperty(AppBarLayout::getLiftOnScrollTargetViewId, AppBarLayout::setLiftOnScrollTargetViewId)) ToDo id
        // add(PropertyCategory.COMMON, BooleanProperty(AppBarLayout::isLiftable, AppBarLayout::setLiftable)) no getter :()
        // add(PropertyCategory.COMMON, BooleanProperty(AppBarLayout::isLifted, AppBarLayout::setLifted)) no getter :()
        // setOrientation is in LinearLayout
        // ToDo merge with ColorDrawablePicker
        add(PropertyCategory.COMMON,
            DrawableProperty(AppBarLayout::getStatusBarForeground,
                AppBarLayout::setStatusBarForeground))
        // add(PropertyCategory.COMMON, ColorProperty(AppBarLayout::getStatusBarForeground, AppBarLayout::setStatusBarForegroundColor))
        add(PropertyCategory.COMMON, DimensionFloatRangeProperty(
            AppBarLayout::getTargetElevation,
            AppBarLayout::setTargetElevation,
            max = dp(50f)).apply { deprecatedApi = "D" })
        // setVisibility is in View
    }
}