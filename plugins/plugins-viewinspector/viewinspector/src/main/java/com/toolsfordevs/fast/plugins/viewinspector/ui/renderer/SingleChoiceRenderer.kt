package com.toolsfordevs.fast.plugins.viewinspector.ui.renderer

import android.view.ViewGroup
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.core.ui.ext.setPaddingEnd
import com.toolsfordevs.fast.core.ui.widget.FastSpinner
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyRenderer
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.SingleChoiceProperty

internal class SingleChoiceRenderer(parent: ViewGroup) : ViewPropertyRenderer<SingleChoiceProperty<*, Any>>(
    FastSpinner(parent.context)) {
    private val spinner = itemView as FastSpinner

    init
    {
        spinner.setPaddingEnd(Dimens.dp(8))
    }

    override fun bind(data: SingleChoiceProperty<*, Any>)
    {
        val map = data.map

        if (map.isEmpty())
        {
            spinner.isEnabled = false
            spinner.text = "No option available"
            spinner.alpha = .38f
        }
        else
        {
            spinner.alpha = 1f
            spinner.isEnabled = true
            spinner.setAdapter(map.values.toList(), map.indexOfKey(data.getValue())) { index -> data.setValue(data.map.keyAt(index)) }
        }
    }
}

/*class SingleChoiceRenderer(parent: ViewGroup) : ViewPropertyRenderer<SingleChoiceProperty<*, Any>>(parent,
    R.layout.fast_renderer_item_spinner) {
    private val spinner: Spinner = itemView.findViewById(R.id.spinner)

    override fun bind(data: SingleChoiceProperty<*, Any>)
    {
        spinner.onItemSelectedListener = null

//        if (/*spinner.adapter.isEmpty && */spinner.emptyView == null)
            spinner.emptyView = TextView(spinner.context).apply {
                text = "No option available"
                setTextColor(MaterialColor.BLACK_38)
                setBackgroundColor(Color.RED)
                layoutParams = layoutParamsMM()
            }

        spinner.adapter = ArrayAdapter<String>(itemView.context, android.R.layout.simple_dropdown_item_1line, data.map.values.toTypedArray())

//            spinner.setBackgroundColor(Color.TRANSPARENT)
//            spinner.isEnabled = true
        spinner.setSelection(data.map.indexOfKey(data.getValue()))

        spinner.post {
            // Avoid listener being fired even though it has been set up after selection
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
            {
                override fun onNothingSelected(parent: AdapterView<*>?)
                {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
                {
                    data.setValue(data.map.keyAt(position))
                    //data.setSelectedIndex(position)
                    //data.setter?.invoke(data.view, data.map.keyAt(position))
                }
            }
        }
    }
}*/