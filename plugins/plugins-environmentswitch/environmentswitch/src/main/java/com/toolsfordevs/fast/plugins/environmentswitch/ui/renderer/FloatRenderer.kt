package com.toolsfordevs.fast.plugins.environmentswitch.ui.renderer

import android.view.Gravity
import android.view.ViewGroup
import com.toolsfordevs.fast.core.ui.widget.FastNumberStepper
import com.toolsfordevs.fast.plugins.actions.base.actions.FloatAction

internal class FloatRenderer(parent: ViewGroup) : EnvRenderer<FloatAction, FastNumberStepper>(parent, FastNumberStepper(parent.context))
{
    init
    {
        dataView.gravity = Gravity.END
    }

    override fun bind(data: FloatAction, position: Int)
    {
        super.bind(data, position)

        dataView.setValue(data.value?.value ?: 0)
        dataView.setOnChangeCallback { value ->
            data.value?.value = value as Float
            data.callback(data.value?.value)
        }
    }
}