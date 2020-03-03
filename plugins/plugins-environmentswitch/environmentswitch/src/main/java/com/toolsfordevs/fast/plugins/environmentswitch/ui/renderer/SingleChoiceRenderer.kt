package com.toolsfordevs.fast.plugins.environmentswitch.ui.renderer

import android.view.ViewGroup
import com.toolsfordevs.fast.core.ext.dp
import com.toolsfordevs.fast.core.ui.ext.setPaddingEnd
import com.toolsfordevs.fast.core.ui.widget.FastSpinner
import com.toolsfordevs.fast.plugins.actions.base.actions.SingleChoiceAction

internal class SingleChoiceRenderer(parent: ViewGroup) : EnvRenderer<SingleChoiceAction<Any>, FastSpinner>(parent, FastSpinner(parent.context)) {

    init
    {
        dataView.setPaddingEnd(8.dp)
        dataView.minHeight = 48.dp
    }

    override fun bind(data: SingleChoiceAction<Any>, position:Int)
    {
        super.bind(data, position)

        val map = data.value?.choices

        if (map.isNullOrEmpty())
        {
            dataView.isEnabled = false
            dataView.text = "No option available"
            dataView.alpha = .38f
        }
        else
        {
            dataView.alpha = 1f
            dataView.isEnabled = true
            dataView.setAdapter(map.values.toList(), map.indexOfKey(data.value.value)) { index ->
                data.value.value = map.keyAt(index)
                data.callback(data.value.value)
            }
        }
    }
}