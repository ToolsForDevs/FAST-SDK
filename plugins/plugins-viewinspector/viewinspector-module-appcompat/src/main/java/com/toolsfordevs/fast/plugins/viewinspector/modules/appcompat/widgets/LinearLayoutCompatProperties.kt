package com.toolsfordevs.fast.plugins.viewinspector.modules.appcompat.widgets

import android.util.ArrayMap
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.LinearLayoutCompat
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.*
import com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.properties.GravityProperty


internal class LinearLayoutCompatProperties(): ViewPropertyHolder(LinearLayoutCompat::class.java)
{
    init
    {
        add(PropertyCategory.COMMON, OrientationProperty())
        add(PropertyCategory.COMMON, GravityProperty(LinearLayoutCompat::getGravity, LinearLayoutCompat::setGravity))
        // add(PropertyCategory.COMMON, LinearLayoutShowDividersProperty())
        add(PropertyCategory.COMMON,
            DrawableProperty(LinearLayoutCompat::getDividerDrawable,
                LinearLayoutCompat::setDividerDrawable))
        add(PropertyCategory.COMMON,
            DimensionIntRangeProperty(LinearLayoutCompat::getDividerPadding,
                LinearLayoutCompat::setDividerPadding,
                max = dp(32)))
        add(PropertyCategory.COMMON,
            BooleanProperty(LinearLayoutCompat::isBaselineAligned,
                LinearLayoutCompat::setBaselineAligned))
        add(PropertyCategory.COMMON, BaselineAlignedChildIndex())
        add(PropertyCategory.COMMON,
            BooleanProperty(LinearLayoutCompat::isMeasureWithLargestChildEnabled,
                LinearLayoutCompat::setMeasureWithLargestChildEnabled))
        add(PropertyCategory.COMMON,
            FloatRangeProperty(LinearLayoutCompat::getWeightSum,
                LinearLayoutCompat::setWeightSum,
                0f,
                100f,
                1))

        add(PropertyCategory.LAYOUT_PARAMS, LLCGravityProperty())
        add(PropertyCategory.LAYOUT_PARAMS, WeightProperty())
    }

    private class BaselineAlignedChildIndex : IntRangeProperty<LinearLayoutCompat>(LinearLayoutCompat::getBaselineAlignedChildIndex, LinearLayoutCompat::setBaselineAlignedChildIndex, -1)
    {
        override fun getMaximum(): Int
        {
            return view.childCount - 1
        }
    }

    private class LLCGravityProperty() : GravityProperty<View>()
    {
        override fun getValue(): Int
        {
            val params = view.layoutParams as LinearLayoutCompat.LayoutParams
            return params.gravity
        }

        override fun setValue(value: Int?):Boolean
        {
            val params = view.layoutParams as LinearLayoutCompat.LayoutParams
            params.gravity = value!!
            view.layoutParams = params
            return true
        }
    }

    private class WeightProperty() : FloatRangeProperty<View>(max = 10f, decimalScale = 1) {
        override fun getValue(): Float {
            return (view.layoutParams as LinearLayoutCompat.LayoutParams).weight
        }

        override fun setValue(value: Float?):Boolean {
            (view.layoutParams as LinearLayoutCompat.LayoutParams).weight = value ?: 0f
            view.layoutParams = view.layoutParams
            return true
        }
    }


    private class OrientationProperty() : SingleChoiceProperty<LinearLayoutCompat, Int>(LinearLayoutCompat::getOrientation, LinearLayoutCompat::setOrientation) {
        override fun defineKeyValues(map: ArrayMap<Int, String>) {
            map.put(LinearLayout.HORIZONTAL, "HORIZONTAL")
            map.put(LinearLayout.VERTICAL, "VERTICAL")
        }
    }

    /*
    private class ShowDividersDelegate(view: LinearLayoutCompat) : MultiSelectableValueDelegate<LinearLayoutCompat>(view) {
        companion object {
            private val labels = arrayListOf(
                "NONE",
                "BEGINNING",
                "MIDDLE",
                "END"
            )
    
            private val values = arrayListOf(
                LinearLayout.SHOW_DIVIDER_NONE,
                LinearLayout.SHOW_DIVIDER_BEGINNING,
                LinearLayout.SHOW_DIVIDER_MIDDLE,
                LinearLayout.SHOW_DIVIDER_END
            )
        }
    
        override fun getLabel(): String {
            if (getSelectedValues().isEmpty())
                return "NONE"
    
            var label = ""
    
            for (flag in getSelectedValues()) {
                if (label.isNotEmpty())
                    label += "|"
    
                label += labels[values.indexOf(flag)]
            }
    
            return label
        }
    
        override fun getLabels(): List<String> {
            return labels
        }
    
        override fun getValues(): List<Int> {
            return values
        }
    
        override fun getSelectedValues(): List<Int> {
            val flags = view.showDividers
    
            val list = arrayListOf<Int>()
    
            if (flags == LinearLayout.SHOW_DIVIDER_NONE)
                add(PropertyCategory.COMMON, LinearLayout.SHOW_DIVIDER_NONE)
            else {
                for (flag in values.drop(1)) {
                    if (flag and flags == flag)
                        add(PropertyCategory.COMMON, flag)
                }
            }
    
            return list
        }
    
        override fun setSelectedValues(values: List<Int>) {
            var flags = LinearLayoutCompat.SHOW_DIVIDER_NONE
    
            for (flag in values)
                flags = flags or flag
    
            view.showDividers = flags
        }
    }
     */
}