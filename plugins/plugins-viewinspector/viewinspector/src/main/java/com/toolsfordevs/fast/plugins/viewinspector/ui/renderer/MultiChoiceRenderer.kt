package com.toolsfordevs.fast.plugins.viewinspector.ui.renderer

import android.app.AlertDialog
import android.view.ViewGroup
import android.widget.TextView
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyRenderer
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.MultiChoiceProperty
import com.toolsfordevs.fast.plugins.viewinspector.R


internal class MultiChoiceRenderer(parent: ViewGroup) : ViewPropertyRenderer<MultiChoiceProperty<*, Any>>(parent,
    R.layout.fast_renderer_item_multi_selectable_value) {

    private val textView = itemView.findViewById<TextView>(R.id.value)

    override fun bind(data: MultiChoiceProperty<*, Any>)
    {
        itemView.setOnClickListener {

            val indices = BooleanArray(data.map.size)
            val selectedValues = data.getSelectedValues()

            for (i in 0 until indices.size)
                indices[i] = selectedValues.contains(data.map.keyAt(i))

            AlertDialog.Builder(itemView.context)
                .setMultiChoiceItems(data.map.values.toTypedArray(), indices
                ) { dialog, which, isChecked -> indices[which] = isChecked }
                .setPositiveButton("OK") { dialog, which ->
                    val list = arrayListOf<Any>()
                    for (i in 0 until indices.size)
                    {
                        if (indices[i])
                            list.add(data.map.keyAt(i))
                    }
                    data.setSelectedValues(list)
                    refresh(data) }
                .setCancelable(true)
                .show()
        }

        refresh(data)
    }

    private fun refresh(data: MultiChoiceProperty<*, *>) {
        textView.text = data.getLabel()
    }
}