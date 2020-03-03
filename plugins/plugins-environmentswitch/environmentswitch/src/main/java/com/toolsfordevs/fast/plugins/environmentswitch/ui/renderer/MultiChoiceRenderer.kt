package com.toolsfordevs.fast.plugins.environmentswitch.ui.renderer

import android.view.ViewGroup
import com.toolsfordevs.fast.core.ext.dp
import com.toolsfordevs.fast.core.ui.ext.setPaddingEnd
import com.toolsfordevs.fast.core.ui.widget.FastSpinner
import com.toolsfordevs.fast.plugins.actions.base.actions.MultiChoiceAction

internal class MultiChoiceRenderer(parent: ViewGroup) : EnvRenderer<MultiChoiceAction<Any>, FastSpinner>(parent, FastSpinner(parent.context))
{
    init
    {
        dataView.setPaddingEnd(8.dp)
        dataView.minHeight = 48.dp
    }

    override fun bind(data: MultiChoiceAction<Any>, position: Int)
    {
        super.bind(data, position)

        val map = data.value?.choices

        map?.let {

            val values = arrayListOf<Any>()
            val labels = arrayListOf<String>()

            for (i in 0 until it.size)
            {
                values.add(it.keyAt(i))
                labels.add(it.valueAt(i))
            }

            val selectedValues = data.value?.value ?: listOf()
            val selectedIndexes = selectedValues.map { value -> map.indexOfKey(value) }

            dataView.setAdapter(labels, selectedIndexes) { selectedIndexes: List<Int> ->
                data.value?.value = selectedIndexes.map { index -> map.keyAt(index) }
                data.callback(data.value?.value)
            }
        }
    }
}