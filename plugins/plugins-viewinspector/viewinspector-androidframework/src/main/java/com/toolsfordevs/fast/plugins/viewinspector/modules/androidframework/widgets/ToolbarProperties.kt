package com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.widgets

import android.os.Build
import android.widget.Toolbar
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.DimensionIntRangeProperty
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.DrawableProperty
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.PropertyCategory
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.StringProperty


internal class ToolbarProperties: ViewPropertyHolder(Toolbar::class.java)
{
    init
    {
        // Android Q
//        add(PropertyCategory.COMMON, StringProperty(Toolbar::getCollapseContentDescription, Toolbar::setCollapseContentDescription))
//        add(PropertyCategory.COMMON, DrawableProperty(Toolbar::getCollapseIcon, Toolbar::setCollapseIcon))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            add(PropertyCategory.COMMON, DimensionIntRangeProperty(Toolbar::getContentInsetEndWithActions, Toolbar::setContentInsetEndWithActions).apply { newApi = "24" })
            add(PropertyCategory.COMMON, DimensionIntRangeProperty(Toolbar::getContentInsetStartWithNavigation, Toolbar::setContentInsetStartWithNavigation).apply { newApi = "24" })
        }

        add(PropertyCategory.COMMON, DrawableProperty(Toolbar::getLogo, Toolbar::setLogo))
        add(PropertyCategory.COMMON, StringProperty(Toolbar::getLogoDescription, Toolbar::setLogoDescription))
        add(PropertyCategory.COMMON, StringProperty(Toolbar::getNavigationContentDescription, Toolbar::setNavigationContentDescription))
        add(PropertyCategory.COMMON, DrawableProperty(Toolbar::getNavigationIcon, Toolbar::setNavigationIcon))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            add(PropertyCategory.COMMON, DrawableProperty(Toolbar::getOverflowIcon, Toolbar::setOverflowIcon).apply { newApi = "23" })

        // setPopupTheme // ToDo
        add(PropertyCategory.COMMON, StringProperty(Toolbar::getSubtitle, Toolbar::setSubtitle))
        // setSubtitleTextAppearance // ToDo
        // add(PropertyCategory.COMMON, ColorProperty(Toolbar::getSuTitle, Toolbar::setSubtitleTextColor)) // No getter :(
        add(PropertyCategory.COMMON, StringProperty(Toolbar::getTitle, Toolbar::setTitle))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            add(PropertyCategory.COMMON, DimensionIntRangeProperty(Toolbar::getTitleMarginBottom, Toolbar::setTitleMarginBottom).apply { newApi = "24" })
            add(PropertyCategory.COMMON, DimensionIntRangeProperty(Toolbar::getTitleMarginEnd, Toolbar::setTitleMarginEnd).apply { newApi = "24" })
            add(PropertyCategory.COMMON, DimensionIntRangeProperty(Toolbar::getTitleMarginStart, Toolbar::setTitleMarginStart).apply { newApi = "24" })
            add(PropertyCategory.COMMON, DimensionIntRangeProperty(Toolbar::getTitleMarginTop, Toolbar::setTitleMarginTop).apply { newApi = "24" })
        }
        // add(PropertyCategory.COMMON, ColorProperty(Toolbar::getTitle, Toolbar::setTitleTextColor)) // No getter :(
    }
}