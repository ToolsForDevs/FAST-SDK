package com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.widgets

import android.widget.Spinner
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.*
import com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.properties.GravityProperty


internal class SpinnerProperties : ViewPropertyHolder(Spinner::class.java)
{
    init
    {
        add(PropertyCategory.COMMON, DimensionIntRangeProperty(Spinner::getDropDownHorizontalOffset, Spinner::setDropDownHorizontalOffset, max = dp(100)))
        add(PropertyCategory.COMMON, DimensionIntRangeProperty(Spinner::getDropDownVerticalOffset, Spinner::setDropDownVerticalOffset, max = dp(100)))
        add(PropertyCategory.COMMON, DimensionIntRangeProperty(Spinner::getDropDownWidth, Spinner::setDropDownWidth, screenWidth))
        // Enabled is in view
        add(PropertyCategory.COMMON, GravityProperty(Spinner::getGravity, Spinner::setGravity))
        add(PropertyCategory.COMMON, ColorDrawableProperty(Spinner::getPopupBackground, Spinner::setPopupBackgroundDrawable))
        add(PropertyCategory.COMMON, StringProperty(Spinner::getPrompt, Spinner::setPrompt))
    }
}