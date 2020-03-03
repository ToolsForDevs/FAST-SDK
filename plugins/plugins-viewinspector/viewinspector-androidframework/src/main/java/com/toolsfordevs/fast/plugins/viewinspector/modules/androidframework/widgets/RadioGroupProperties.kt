package com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.widgets

import android.widget.RadioGroup
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.PropertyCategory
import com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.properties.IdProperty


internal class RadioGroupProperties: ViewPropertyHolder(RadioGroup::class.java)
{
    init
    {
        add(PropertyCategory.COMMON, IdProperty(RadioGroup::getCheckedRadioButtonId, RadioGroup::check, IdProperty.CHILDREN))
    }
}