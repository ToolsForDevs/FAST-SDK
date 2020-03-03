package com.toolsfordevs.fast.plugins.environmentswitch.ui.renderer

import android.view.Gravity
import android.view.ViewGroup
import com.toolsfordevs.fast.core.ext.dp
import com.toolsfordevs.fast.core.ext.layoutParamsWM
import com.toolsfordevs.fast.core.ext.linearLayoutParamsWM
import com.toolsfordevs.fast.core.ui.ext.setPaddingEnd
import com.toolsfordevs.fast.core.ui.widget.FastSwitch
import com.toolsfordevs.fast.plugins.actions.base.actions.BooleanAction

internal class BooleanRenderer(parent: ViewGroup) :
        EnvRenderer<BooleanAction, FastSwitch>(parent, FastSwitch.create(parent.context, true))
{
    init
    {
        dataView.setPaddingEnd(8.dp)
        dataView.gravity = Gravity.CENTER_VERTICAL
        itemView.setOnClickListener {  }
    }

    override fun bind(data: BooleanAction, position: Int)
    {
        super.bind(data, position)

        dataView.isChecked = data.value?.value ?: false

        dataView.setOnClickListener {
            data.value?.value = dataView.isChecked
            data.callback(data.value?.value)
        }
    }
}