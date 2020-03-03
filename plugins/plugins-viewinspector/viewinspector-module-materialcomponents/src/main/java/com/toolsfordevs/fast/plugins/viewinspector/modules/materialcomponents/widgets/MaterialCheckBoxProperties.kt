package com.toolsfordevs.fast.plugins.viewinspector.modules.materialcomponents.widgets

import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.BooleanProperty
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.PropertyCategory
import com.google.android.material.checkbox.MaterialCheckBox


internal class MaterialCheckBoxProperties: ViewPropertyHolder(MaterialCheckBox::class.java)
{
    init
    {
        add(PropertyCategory.COMMON,
            BooleanProperty(MaterialCheckBox::isUseMaterialThemeColors,
                MaterialCheckBox::setUseMaterialThemeColors))
    }
}