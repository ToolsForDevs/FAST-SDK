package com.toolsfordevs.fast.plugins.viewinspector.modules.materialcomponents.widgets

import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.*
import com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.properties.GravityProperty
import com.google.android.material.appbar.CollapsingToolbarLayout


internal class CollapsingToolbarLayoutProperties : ViewPropertyHolder(CollapsingToolbarLayout::class.java)
{
    init
    {
        add(PropertyCategory.COMMON, GravityProperty(CollapsingToolbarLayout::getCollapsedTitleGravity, CollapsingToolbarLayout::setCollapsedTitleGravity))
        // setCollapsedTitleTextAppearance // ToDo
        // add(PropertyCategory.COMMON, ColorProperty(CollapsingToolbarLayout::colla, CollapsingToolbarLayout::setCollapsedTitleTextColor)) no getter :(
        // setCollapsedTitleTypeface // ToDo
        add(PropertyCategory.COMMON,
            DrawableProperty(CollapsingToolbarLayout::getContentScrim, CollapsingToolbarLayout::setContentScrim)) // ToDo merge with ColorDrawablePicker
        // setContentScrimColor
        // add(PropertyCategory.COMMON, ColorProperty(CollapsingToolbarLayout::expanded, CollapsingToolbarLayout::setExpandedTitleColor)) no getter :(
        add(PropertyCategory.COMMON, GravityProperty(CollapsingToolbarLayout::getExpandedTitleGravity, CollapsingToolbarLayout::setExpandedTitleGravity))
        add(PropertyCategory.COMMON,
            DimensionIntRangeProperty(CollapsingToolbarLayout::getExpandedTitleMarginBottom,
                CollapsingToolbarLayout::setExpandedTitleMarginBottom,
                max = screenHeight))
        add(PropertyCategory.COMMON,
            DimensionIntRangeProperty(CollapsingToolbarLayout::getExpandedTitleMarginEnd,
                CollapsingToolbarLayout::setExpandedTitleMarginEnd,
                max = screenWidth))
        add(PropertyCategory.COMMON,
            DimensionIntRangeProperty(CollapsingToolbarLayout::getExpandedTitleMarginStart,
                CollapsingToolbarLayout::setExpandedTitleMarginStart,
                max = screenWidth))
        add(PropertyCategory.COMMON,
            DimensionIntRangeProperty(CollapsingToolbarLayout::getExpandedTitleMarginTop,
                CollapsingToolbarLayout::setExpandedTitleMarginTop,
                max = screenHeight))
        // setExpandedTitleTextAppearance // ToDO
        // add(PropertyCategory.COMMON, ColorStateListProperty(CollapsingToolbarLayout::expanded, CollapsingToolbarLayout::setExpandedTitleTextColor)) no getter :(
        // setExpandedTitleTypeface // ToDo
        add(PropertyCategory.COMMON,
            LongRangeProperty(CollapsingToolbarLayout::getScrimAnimationDuration, CollapsingToolbarLayout::setScrimAnimationDuration, max = 10000L))
        add(PropertyCategory.COMMON,
            DimensionIntRangeProperty(CollapsingToolbarLayout::getScrimVisibleHeightTrigger,
                CollapsingToolbarLayout::setScrimVisibleHeightTrigger,
                max = screenHeight))
        // add(PropertyCategory.COMMON, BooleanProperty(CollapsingToolbarLayout::scrim, CollapsingToolbarLayout::setScrimsShown)) // no getter :(
        // setStatusBarScrim
        add(PropertyCategory.COMMON,
            DrawableProperty(CollapsingToolbarLayout::getStatusBarScrim, CollapsingToolbarLayout::setStatusBarScrim)) // ToDo merge with ColorDrawablePicker
        // setStatusBarScrimColor
        add(PropertyCategory.COMMON, StringProperty(CollapsingToolbarLayout::getTitle, CollapsingToolbarLayout::setTitle))
        add(PropertyCategory.COMMON, BooleanProperty(CollapsingToolbarLayout::isTitleEnabled, CollapsingToolbarLayout::setTitleEnabled))
        // setVisibility is in View
    }
}