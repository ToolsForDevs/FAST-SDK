package com.toolsfordevs.fast.plugins.viewinspector.modules.materialcomponents.widgets

import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.*
import com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.properties.IdProperty
import com.google.android.material.navigation.NavigationView


internal class NavigationViewProperties : ViewPropertyHolder(NavigationView::class.java)
{
    init
    {
        // setCheckedItem id ToDo
        add(PropertyCategory.COMMON, IdProperty({ checkedItem?.itemId ?: 0 }, NavigationView::setCheckedItem).apply { name = "setCheckedItem" })
        // setElevation is in View
        add(PropertyCategory.COMMON, DrawableProperty(NavigationView::getItemBackground, NavigationView::setItemBackground))
        add(PropertyCategory.COMMON,
            DimensionIntRangeProperty(NavigationView::getItemHorizontalPadding, NavigationView::setItemHorizontalPadding, max = dp(128)))
        add(PropertyCategory.COMMON, DimensionIntRangeProperty(NavigationView::getItemIconPadding, NavigationView::setItemIconPadding, max = dp(128)))
        // add(PropertyCategory.COMMON, DimensionIntRangeProperty(NavigationView::getItemIconSize, NavigationView::setItemIconSize, max = dp(128))) no getter :(
        add(PropertyCategory.COMMON, ColorStateListProperty(NavigationView::getItemIconTintList, NavigationView::setItemIconTintList))
        add(PropertyCategory.COMMON, IntRangeProperty(NavigationView::getItemMaxLines, NavigationView::setItemMaxLines, max = 10))
        // setItemTextAppearance // ToDo
        add(PropertyCategory.COMMON, ColorStateListProperty(NavigationView::getItemTextColor, NavigationView::setItemTextColor))
    }
}