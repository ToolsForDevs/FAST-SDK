package com.toolsfordevs.fast.plugins.viewinspector.modules.materialcomponents.widgets

import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.BooleanProperty
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.PropertyCategory
import com.google.android.material.switchmaterial.SwitchMaterial


internal class SwitchMaterialProperties: ViewPropertyHolder(SwitchMaterial::class.java)
{
    init
    {
        add(PropertyCategory.COMMON,
            BooleanProperty(SwitchMaterial::isUseMaterialThemeColors,
                SwitchMaterial::setUseMaterialThemeColors))
    }
}