package com.toolsfordevs.fast.plugins.viewinspector.modules.materialcomponents.widgets

import android.util.ArrayMap
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.*
import com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.properties.IdProperty
import com.google.android.material.floatingactionbutton.FloatingActionButton


internal class FloatingActionButtonProperties : ViewPropertyHolder(FloatingActionButton::class.java)
{
    init
    {
        // setBackgroundColor is in SuperClass
        // setBackgroundDrawable is in SuperClass
        // setBackgroundResource is in SuperClass
        // setBackgroundTintList is in SuperClass
        // setBackgroundTintMode is in SuperClass
        add(PropertyCategory.COMMON,
            DimensionFloatRangeProperty(FloatingActionButton::getCompatElevation, FloatingActionButton::setCompatElevation, max = dp(50f)))
        add(PropertyCategory.COMMON,
            DimensionFloatRangeProperty(FloatingActionButton::getCompatHoveredFocusedTranslationZ,
                FloatingActionButton::setCompatHoveredFocusedTranslationZ,
                max = dp(50f)))
        add(PropertyCategory.COMMON,
            DimensionFloatRangeProperty(FloatingActionButton::getCompatPressedTranslationZ, FloatingActionButton::setCompatPressedTranslationZ, max = dp(50f)))
        add(PropertyCategory.COMMON, DimensionIntRangeProperty(FloatingActionButton::getCustomSize, FloatingActionButton::setCustomSize, max = screenWidth))
        // setElevation is in SuperClass
        add(PropertyCategory.COMMON, BooleanProperty(FloatingActionButton::shouldEnsureMinTouchTargetSize, FloatingActionButton::setEnsureMinTouchTargetSize))
        add(PropertyCategory.COMMON, BooleanProperty(FloatingActionButton::isExpanded) { isExpanded = it })
        add(PropertyCategory.COMMON, IdProperty(FloatingActionButton::getExpandedComponentIdHint, FloatingActionButton::setExpandedComponentIdHint))
        // setImageDrawable is in SuperClass
        // setImageResource is in SuperClass
        add(PropertyCategory.COMMON, ColorStateListProperty(FloatingActionButton::getRippleColorStateList, FloatingActionButton::setRippleColor))
        // setScaleX is in SuperClass
        // setScaleY is in SuperClass
        add(PropertyCategory.COMMON, BooleanProperty(FloatingActionButton::isExpanded, FloatingActionButton::setUseCompatPadding))
        add(PropertyCategory.COMMON, SizeProperty())
        add(PropertyCategory.COMMON,
            ColorStateListProperty(FloatingActionButton::getSupportBackgroundTintList, FloatingActionButton::setSupportBackgroundTintList))
        add(PropertyCategory.COMMON,
            ColorTintModeProperty(FloatingActionButton::getSupportBackgroundTintMode, FloatingActionButton::setSupportBackgroundTintMode))
        add(PropertyCategory.COMMON, ColorStateListProperty(FloatingActionButton::getSupportImageTintList, FloatingActionButton::setSupportImageTintList))
        add(PropertyCategory.COMMON, ColorTintModeProperty(FloatingActionButton::getSupportImageTintMode, FloatingActionButton::setSupportImageTintMode))
        // setTranslationX is in SuperClass
        // setTranslationY is in SuperClass
        // setTranslationZ is in SuperClass
        // setUseCompatPadding
        add(PropertyCategory.COMMON, BooleanProperty(FloatingActionButton::getUseCompatPadding, FloatingActionButton::setUseCompatPadding))
        // setVisibility is in SuperClass
    }

    private class SizeProperty : SingleChoiceProperty<FloatingActionButton, Int>(FloatingActionButton::getSize, FloatingActionButton::setSize)
    {
        override fun defineKeyValues(map: ArrayMap<Int, String>)
        {
            map.put(FloatingActionButton.SIZE_AUTO, "AUTO")
            map.put(FloatingActionButton.SIZE_MINI, "MINI")
            map.put(FloatingActionButton.SIZE_NORMAL, "NORMAL")
        }
    }
}