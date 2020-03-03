package com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.widgets

import android.widget.CompoundButton
import com.toolsfordevs.fast.core.util.AndroidVersion
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.*


internal class CompoundButtonProperties: ViewPropertyHolder(CompoundButton::class.java) {
    init {
        add(PropertyCategory.COMMON, BooleanProperty(CompoundButton::isChecked, CompoundButton::setChecked))

        if (AndroidVersion.isMinMarshmallow())
            add(PropertyCategory.COMMON, DrawableProperty(CompoundButton::getButtonDrawable, CompoundButton::setButtonDrawable))

        add(PropertyCategory.COMMON, ColorStateListProperty(CompoundButton::getButtonTintList, CompoundButton::setButtonTintList))
        add(PropertyCategory.COMMON, ColorTintModeProperty(CompoundButton::getButtonTintMode, CompoundButton::setButtonTintMode))
    }
}