package com.toolsfordevs.fast.plugins.viewinspector.ui.renderer

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.Button
import android.widget.ImageButton
import android.widget.ViewSwitcher
import com.toolsfordevs.fast.core.UnitConverter
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyRenderer
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.LayoutParamWHProperty
import com.toolsfordevs.fast.plugins.viewinspector.R
import com.toolsfordevs.fast.core.ui.seekbar.OnSeekChangeListener
import com.toolsfordevs.fast.core.ui.seekbar.SeekParams
import com.toolsfordevs.fast.core.ui.seekbar.TickSeekBar


internal class LayoutParamsRenderer(parent: ViewGroup) : ViewPropertyRenderer<LayoutParamWHProperty<*>>(parent,
    R.layout.fast_renderer_item_layout_params) {
    private val switcher: ViewSwitcher = itemView.findViewById(R.id.view_switcher)
    private val seekbar: TickSeekBar = itemView.findViewById(R.id.seek_bar)
    private val switchButton: ImageButton = itemView.findViewById(R.id.switch_button)
    private val matchButton: Button = itemView.findViewById(R.id.button_match)
    private val wrapButton: Button = itemView.findViewById(R.id.button_wrap)
    private val toggleGroup: ViewGroup = itemView.findViewById(R.id.toggle_group)


    override fun bind(data: LayoutParamWHProperty<*>)
    {
        seekbar.onSeekChangeListener = null

        refresh(data)

        seekbar.onSeekChangeListener = object : OnSeekChangeListener
        {
            override fun onSeeking(seekParams: SeekParams?) {
                data.setValue(UnitConverter.getPxValue(seekParams!!.progress, data.unit))
                refresh(data)
            }

            override fun onStartTrackingTouch(seekBar: TickSeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: TickSeekBar?) {
            }
        }

        switchButton.setOnClickListener {
            val size = data.getValue() ?: 0
            if(size >= 0)
                data.setValue(LayoutParams.MATCH_PARENT)
            else
                data.setValue(0)

            refresh(data)
        }
        matchButton.setOnClickListener {
            data.setValue(LayoutParams.MATCH_PARENT)
            refresh(data)
        }
        wrapButton.setOnClickListener {
            data.setValue(LayoutParams.WRAP_CONTENT)
            refresh(data)
        }
    }

    private fun refresh(data: LayoutParamWHProperty<*>)
    {
        val size = data.getValue() ?: 0

        switcher.displayedChild = if (size < 0) 0 else 1

        seekbar.min = UnitConverter.getScaledValue(data.getMinimum(), data.unit).toFloat()
        seekbar.max = UnitConverter.getScaledValue(data.getMaximum(), data.unit).toFloat()

        if (size >= 0)
        {
            switchButton.setImageResource(R.drawable.fast_renderer_ic_arrow_split_vertical)
            seekbar.setProgress(UnitConverter.getScaledValue(size, data.unit).toFloat())
        }
        else {
            switchButton.setImageResource(R.drawable.fast_renderer_ic_numeric)
            matchButton.isSelected = size == LayoutParams.MATCH_PARENT
            wrapButton.isSelected = size == LayoutParams.WRAP_CONTENT
        }
    }
}