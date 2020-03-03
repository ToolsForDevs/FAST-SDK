package com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.widgets

import android.util.ArrayMap
import android.view.View
import android.widget.LinearLayout
import com.toolsfordevs.fast.core.util.AndroidVersion
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.*
import com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.properties.GravityProperty

internal class LinearLayoutProperties() : ViewPropertyHolder(LinearLayout::class.java)
{
    init
    {
        add(PropertyCategory.COMMON, LinearLayoutOrientationProperty())

        if (AndroidVersion.isMinNougat())
            add(PropertyCategory.COMMON, GravityProperty(LinearLayout::getGravity, LinearLayout::setGravity))

        add(PropertyCategory.COMMON, LinearLayoutShowDividersProperty())
        add(PropertyCategory.COMMON, ColorDrawableProperty(LinearLayout::getDividerDrawable, LinearLayout::setDividerDrawable))
        add(PropertyCategory.COMMON, DimensionIntRangeProperty(LinearLayout::getDividerPadding, LinearLayout::setDividerPadding, max = dp(32)))
        add(PropertyCategory.COMMON, BooleanProperty(LinearLayout::isBaselineAligned, LinearLayout::setBaselineAligned))
        add(PropertyCategory.COMMON, BaselineAlignedChildIndex())
        add(PropertyCategory.COMMON, BooleanProperty(LinearLayout::isMeasureWithLargestChildEnabled, LinearLayout::setMeasureWithLargestChildEnabled))
        add(PropertyCategory.COMMON, FloatRangeProperty(LinearLayout::getWeightSum, LinearLayout::setWeightSum, 0f, 100f, 1))

        add(PropertyCategory.LAYOUT_PARAMS, LinearLayoutGravityProperty())
        add(PropertyCategory.LAYOUT_PARAMS, WeightProperty())
    }

    private class BaselineAlignedChildIndex :
            IntRangeProperty<LinearLayout>(LinearLayout::getBaselineAlignedChildIndex, LinearLayout::setBaselineAlignedChildIndex, -1)
    {
        override fun getMaximum(): Int
        {
            return view.childCount - 1
        }
    }

    private class LinearLayoutGravityProperty() : GravityProperty<View>()
    {
        init {
            name = "layoutGravity"
        }
        override fun getValue(): Int
        {
            val params = view.layoutParams as LinearLayout.LayoutParams
            return params.gravity
        }

        override fun setValue(value: Int?): Boolean
        {
            val params = view.layoutParams as LinearLayout.LayoutParams
            params.gravity = value!!
            view.layoutParams = params

            return true
        }
    }

    private class WeightProperty() : FloatRangeProperty<View>(max = 10f, decimalScale = 1)
    {
        init
        {
            name = "layoutWeight"
        }

        override fun getValue(): Float
        {
            return (view.layoutParams as LinearLayout.LayoutParams).weight
        }

        override fun setValue(value: Float?): Boolean
        {
            (view.layoutParams as LinearLayout.LayoutParams).weight = value ?: 0f
            view.layoutParams = view.layoutParams
            return true
        }
    }

    private class LinearLayoutOrientationProperty() : SingleChoiceProperty<LinearLayout, Int>(LinearLayout::getOrientation, LinearLayout::setOrientation)
    {
        override fun defineKeyValues(map: ArrayMap<Int, String>)
        {
            map.put(LinearLayout.HORIZONTAL, "HORIZONTAL")
            map.put(LinearLayout.VERTICAL, "VERTICAL")
        }
    }

    private class LinearLayoutShowDividersProperty : BitwiseMultiChoiceProperty<LinearLayout>(LinearLayout::getShowDividers, LinearLayout::setShowDividers)
    {
        override fun defineKeyValues(map: ArrayMap<Int, String>)
        {
            map.put(LinearLayout.SHOW_DIVIDER_NONE, "NONE")
            map.put(LinearLayout.SHOW_DIVIDER_BEGINNING, "BEGINNING")
            map.put(LinearLayout.SHOW_DIVIDER_MIDDLE, "MIDDLE")
            map.put(LinearLayout.SHOW_DIVIDER_END, "END")
        }

    }
}