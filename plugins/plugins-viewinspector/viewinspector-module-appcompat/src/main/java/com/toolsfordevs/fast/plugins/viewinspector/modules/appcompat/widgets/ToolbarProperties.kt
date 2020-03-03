package com.toolsfordevs.fast.plugins.viewinspector.modules.appcompat.widgets

import androidx.appcompat.widget.Toolbar
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.DimensionIntRangeProperty
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.DrawableProperty
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.PropertyCategory
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.StringProperty


internal class ToolbarProperties: ViewPropertyHolder(Toolbar::class.java)
{
    init
    {
        add(PropertyCategory.COMMON,
            StringProperty(Toolbar::getCollapseContentDescription,
                Toolbar::setCollapseContentDescription))
        add(PropertyCategory.COMMON,
            DrawableProperty(Toolbar::getCollapseIcon, Toolbar::setCollapseIcon))
        add(PropertyCategory.COMMON,
            DimensionIntRangeProperty(Toolbar::getContentInsetEndWithActions,
                Toolbar::setContentInsetEndWithActions))
        add(PropertyCategory.COMMON,
            DimensionIntRangeProperty(Toolbar::getContentInsetStartWithNavigation,
                Toolbar::setContentInsetStartWithNavigation))
        add(PropertyCategory.COMMON,
            DrawableProperty(Toolbar::getLogo, Toolbar::setLogo))
        add(PropertyCategory.COMMON,
            StringProperty(Toolbar::getLogoDescription, Toolbar::setLogoDescription))
        add(PropertyCategory.COMMON,
            StringProperty(Toolbar::getNavigationContentDescription,
                Toolbar::setNavigationContentDescription))
        add(PropertyCategory.COMMON,
            DrawableProperty(Toolbar::getNavigationIcon, Toolbar::setNavigationIcon))
        add(PropertyCategory.COMMON,
            DrawableProperty(Toolbar::getOverflowIcon, Toolbar::setOverflowIcon))
        // setPopupTheme // ToDo style when Theme/Style Resource picker is done
        add(PropertyCategory.COMMON,
            StringProperty(Toolbar::getSubtitle, Toolbar::setSubtitle))
        // setSubtitleTextAppearance // ToDo style when Theme/Style Resource picker is done
        // add(PropertyCategory.COMMON, ColorProperty(Toolbar::getSuTitle, Toolbar::setSubtitleTextColor)) // No getter :(
        add(PropertyCategory.COMMON,
            StringProperty(Toolbar::getTitle, Toolbar::setTitle))
        add(PropertyCategory.COMMON,
            DimensionIntRangeProperty(Toolbar::getTitleMarginBottom,
                Toolbar::setTitleMarginBottom))
        add(PropertyCategory.COMMON,
            DimensionIntRangeProperty(Toolbar::getTitleMarginEnd,
                Toolbar::setTitleMarginEnd))
        add(PropertyCategory.COMMON,
            DimensionIntRangeProperty(Toolbar::getTitleMarginStart,
                Toolbar::setTitleMarginStart))
        add(PropertyCategory.COMMON,
            DimensionIntRangeProperty(Toolbar::getTitleMarginTop,
                Toolbar::setTitleMarginTop))
        // add(PropertyCategory.COMMON, ColorProperty(Toolbar::getTitle, Toolbar::setTitleTextColor)) // No getter :(
    }
}