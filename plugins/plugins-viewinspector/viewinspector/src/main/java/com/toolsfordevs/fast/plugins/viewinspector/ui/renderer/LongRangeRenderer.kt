package com.toolsfordevs.fast.plugins.viewinspector.ui.renderer

import android.view.ViewGroup
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyRenderer
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.LongRangeProperty
import com.toolsfordevs.fast.plugins.viewinspector.R
import com.toolsfordevs.fast.core.ui.seekbar.OnSeekChangeListener
import com.toolsfordevs.fast.core.ui.seekbar.SeekParams
import com.toolsfordevs.fast.core.ui.seekbar.TickSeekBar

internal class LongRangeRenderer(parent: ViewGroup) : ViewPropertyRenderer<LongRangeProperty<*>>(parent,
    R.layout.fast_renderer_item_seek_bar) {
    private val seekbar: TickSeekBar = itemView.findViewById(R.id.seek_bar)

    override fun bind(data: LongRangeProperty<*>)
    {
        seekbar.onSeekChangeListener = null

        seekbar.min = data.getMinimum().toFloat()
        seekbar.max = data.getMaximum().toFloat()
        seekbar.setProgress(data.getValue()!!.toFloat())

        seekbar.onSeekChangeListener = object: OnSeekChangeListener {
            override fun onStartTrackingTouch(seekBar: TickSeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: TickSeekBar?) {

            }

            override fun onSeeking(seekParams: SeekParams?) {
                seekParams?.let {
                    data.setValue(it.progress.toLong())
                }
            }
        }
    }
}