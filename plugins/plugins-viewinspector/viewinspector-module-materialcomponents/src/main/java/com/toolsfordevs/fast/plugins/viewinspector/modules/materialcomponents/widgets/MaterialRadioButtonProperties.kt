package com.toolsfordevs.fast.plugins.viewinspector.modules.materialcomponents.widgets

import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.BooleanProperty
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.PropertyCategory
import com.google.android.material.radiobutton.MaterialRadioButton


internal class MaterialRadioButtonProperties: ViewPropertyHolder(MaterialRadioButton::class.java)
{
    init
    {
        add(PropertyCategory.COMMON,
            BooleanProperty(MaterialRadioButton::isUseMaterialThemeColors,
                MaterialRadioButton::setUseMaterialThemeColors))
    }
}