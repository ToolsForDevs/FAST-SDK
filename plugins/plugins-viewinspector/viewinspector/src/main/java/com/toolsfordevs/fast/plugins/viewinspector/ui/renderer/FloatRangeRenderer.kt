package com.toolsfordevs.fast.plugins.viewinspector.ui.renderer

import android.view.ViewGroup
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyRenderer
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.FloatRangeProperty
import com.toolsfordevs.fast.plugins.viewinspector.R
import com.toolsfordevs.fast.core.ui.seekbar.OnSeekChangeListener
import com.toolsfordevs.fast.core.ui.seekbar.SeekParams
import com.toolsfordevs.fast.core.ui.seekbar.TickSeekBar

internal class FloatRangeRenderer(parent: ViewGroup) : ViewPropertyRenderer<FloatRangeProperty<*>>(parent,
    R.layout.fast_renderer_item_seek_bar_float) {
    val seekbar: TickSeekBar = itemView.findViewById(R.id.seek_bar)

    override fun bind(data: FloatRangeProperty<*>)
    {
        seekbar.onSeekChangeListener = null

        seekbar.min = data.getMinimum()
        seekbar.max = data.getMaximum()
        seekbar.setProgress(data.getValue()!!)
        seekbar.setDecimalScale(data.decimalScale)

        seekbar.onSeekChangeListener = object: OnSeekChangeListener {
            override fun onStartTrackingTouch(seekBar: TickSeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: TickSeekBar?) {

            }

            override fun onSeeking(seekParams: SeekParams?) {
                seekParams?.let {
                    data.setValue(it.progressFloat)
                }
            }
        }
    }
}