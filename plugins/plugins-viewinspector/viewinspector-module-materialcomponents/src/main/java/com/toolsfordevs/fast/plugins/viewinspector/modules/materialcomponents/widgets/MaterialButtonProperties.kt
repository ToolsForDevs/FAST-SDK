package com.toolsfordevs.fast.plugins.viewinspector.modules.materialcomponents.widgets

import android.util.ArrayMap
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.*
import com.google.android.material.button.MaterialButton


internal class MaterialButtonProperties: ViewPropertyHolder(MaterialButton::class.java)
{
    init
    {
        // setBackground is in View
        // setBackgroundTintList is in View
        // setBackgroundTintMode is in View
        add(PropertyCategory.COMMON,
            BooleanProperty(MaterialButton::isCheckable,
                MaterialButton::setCheckable))
        add(PropertyCategory.COMMON,
            BooleanProperty(MaterialButton::isChecked, MaterialButton::setChecked))
        add(PropertyCategory.COMMON,
            DimensionIntRangeProperty(MaterialButton::getCornerRadius,
                MaterialButton::setCornerRadius,
                max = dp(50)))
        // setElevation is in View
        add(PropertyCategory.COMMON,
            DrawableProperty(MaterialButton::getIcon, MaterialButton::setIcon))
        add(PropertyCategory.COMMON, IconGravityProperty())
        add(PropertyCategory.COMMON,
            DimensionIntRangeProperty(MaterialButton::getIconPadding,
                MaterialButton::setIconPadding,
                max = dp(128)))
        add(PropertyCategory.COMMON,
            ColorStateListProperty(MaterialButton::getIconTint,
                MaterialButton::setIconTint))
        add(PropertyCategory.COMMON,
            DimensionIntRangeProperty(MaterialButton::getIconSize,
                MaterialButton::setIconSize,
                max = dp(128)))
        add(PropertyCategory.COMMON,
            ColorTintModeProperty(MaterialButton::getIconTintMode,
                MaterialButton::setIconTintMode))
        // setPressed is in View
        add(PropertyCategory.COMMON,
            ColorStateListProperty(MaterialButton::getRippleColor,
                MaterialButton::setRippleColor))
        add(PropertyCategory.COMMON,
            ColorStateListProperty(MaterialButton::getStrokeColor,
                MaterialButton::setStrokeColor))
        add(PropertyCategory.COMMON,
            DimensionIntRangeProperty(MaterialButton::getStrokeWidth,
                MaterialButton::setStrokeWidth,
                max = dp(20)))
    }

    private class IconGravityProperty : SingleChoiceProperty<MaterialButton, Int>(MaterialButton::getIconGravity, MaterialButton::setIconGravity)
    {
        override fun defineKeyValues(map: ArrayMap<Int, String>)
        {
            map.put(MaterialButton.ICON_GRAVITY_START, "START")
            map.put(MaterialButton.ICON_GRAVITY_END, "END")
            map.put(MaterialButton.ICON_GRAVITY_TEXT_START, "TEXT START")
            map.put(MaterialButton.ICON_GRAVITY_TEXT_END, "TEXT END")
        }
    }
}