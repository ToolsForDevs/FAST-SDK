package com.toolsfordevs.fast.plugins.viewinspector.ui.renderer

import android.view.View
import android.view.ViewGroup
import com.toolsfordevs.fast.core.UnitConverter
import com.toolsfordevs.fast.core.ui.seekbar.OnSeekChangeListener
import com.toolsfordevs.fast.core.ui.seekbar.SeekParams
import com.toolsfordevs.fast.core.ui.seekbar.TickSeekBar
import com.toolsfordevs.fast.core.util.ResUtils
import com.toolsfordevs.fast.modules.resourcepicker.dimens.DimensPickerDialog
import com.toolsfordevs.fast.plugins.viewinspector.R
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyRenderer
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.DimensionRangeProperty
import kotlin.math.max
import kotlin.math.min


internal class DimensionRangeRenderer(parent: ViewGroup) : ViewPropertyRenderer<DimensionRangeProperty<*, Number>>(parent,
            R.layout.fast_renderer_item_dimension)
{
    private val seekbar: TickSeekBar = itemView.findViewById(R.id.seek_bar)
    private val button: View = itemView.findViewById(R.id.button)

    override fun bind(data: DimensionRangeProperty<*, Number>)
    {
        seekbar.onSeekChangeListener = null

        refresh(data)

        seekbar.onSeekChangeListener = object : OnSeekChangeListener
        {
            override fun onStartTrackingTouch(seekBar: TickSeekBar?)
            {

            }

            override fun onStopTrackingTouch(seekBar: TickSeekBar?)
            {

            }

            override fun onSeeking(seekParams: SeekParams?)
            {
                seekParams?.let {
                    data.setValue(UnitConverter.getPxValue(it.progress, data.unit))
                }
            }
        }

        button.setOnClickListener {
            DimensPickerDialog(itemView.context, { dimensRes ->
                data.setValue(dimensRes.value?.toInt() ?: ResUtils.getDimensionPxSize(dimensRes.resId))
                refresh(data)
            }, data.getValue()?.toInt()).show()
        }
    }

    private fun refresh(data: DimensionRangeProperty<*, Number>)
    {
        val min = UnitConverter.getScaledValue(data.getMinimum().toInt(), data.unit).toFloat()
        val max = UnitConverter.getScaledValue(data.getMaximum().toInt(), data.unit).toFloat()
        val value = if (data.getValue()!! != Float.MAX_VALUE && data.getValue()!! != Int.MAX_VALUE)
            UnitConverter.getScaledValue(data.getValue()!!.toInt(), data.unit).toFloat()
        else 0f

        seekbar.min = min(min, value)
        seekbar.max = max(max, value)
        seekbar.setProgress(value)
    }
}