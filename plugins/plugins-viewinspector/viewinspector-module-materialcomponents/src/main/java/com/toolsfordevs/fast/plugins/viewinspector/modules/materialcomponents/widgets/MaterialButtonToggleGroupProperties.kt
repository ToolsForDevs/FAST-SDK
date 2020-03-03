package com.toolsfordevs.fast.plugins.viewinspector.modules.materialcomponents.widgets

import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.BooleanProperty
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.PropertyCategory
import com.google.android.material.button.MaterialButtonToggleGroup


internal class MaterialButtonToggleGroupProperties: ViewPropertyHolder(MaterialButtonToggleGroup::class.java)
{
    init
    {
        add(PropertyCategory.COMMON,
            BooleanProperty(MaterialButtonToggleGroup::isSingleSelection,
                MaterialButtonToggleGroup::setSingleSelection))
    }
}