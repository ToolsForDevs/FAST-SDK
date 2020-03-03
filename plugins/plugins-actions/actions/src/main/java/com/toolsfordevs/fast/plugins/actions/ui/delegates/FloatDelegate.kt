package com.toolsfordevs.fast.plugins.actions.ui.delegates

import android.view.View
import android.view.ViewGroup
import com.toolsfordevs.fast.core.FastColor
import com.toolsfordevs.fast.core.ext.dp
import com.toolsfordevs.fast.core.ext.layoutParamsMM
import com.toolsfordevs.fast.plugins.actions.base.FloatAction
import com.toolsfordevs.fast.plugins.actions.ui.ActionDelegate
import com.toolsfordevs.fast.core.ui.ext.inflate
import com.toolsfordevs.fast.core.ui.seekbar.OnSeekChangeListener
import com.toolsfordevs.fast.core.ui.seekbar.SeekParams
import com.toolsfordevs.fast.core.ui.seekbar.TickSeekBar
import com.toolsfordevs.fast.plugins.actions.R
import kotlin.math.max
import kotlin.math.min


internal class FloatDelegate : ActionDelegate<Float, FloatAction>()
{
    private lateinit var seekbar: TickSeekBar

    override fun createView(parent: ViewGroup): View
    {
        seekbar = TickSeekBar(TickSeekBar.with(parent.context)
                                  .min(0f)
                                  .max(256f)
                                  .seekSmoothly(true)
                                  .thumbTextPosition(2)
                                  .tickTextsColor(0xFFFFFFFF.toInt())
                                  .trackProgressColor(0xFFFFFFFF.toInt())
                                  .thumbTextColor(0xFFFFFFFF.toInt())
                                  .thumbColor(FastColor.colorAccent)
                                  .onlyThumbDraggable(true)
                                  .progressValueFloat(true))

        seekbar.layoutParams = layoutParamsMM().apply { bottomMargin = 16.dp }

        return seekbar
    }

    override fun bind(action: FloatAction)
    {
        val value = action.value

        seekbar.setDecimalScale(action.decimalPrecision)
        seekbar.min = min(action.min, value ?: action.min)
        seekbar.max = max(action.max, value ?: action.max)
        seekbar.setProgress(value ?: action.min)

        seekbar.onSeekChangeListener = object : OnSeekChangeListener
        {
            override fun onSeeking(seekParams: SeekParams?)
            {
                seekParams?.let {
                    action.value = it.progressFloat
                    onValueChange() }
            }

            override fun onStartTrackingTouch(seekBar: TickSeekBar?)
            {

            }

            override fun onStopTrackingTouch(seekBar: TickSeekBar?)
            {

            }
        }
    }
}