package com.toolsfordevs.fast.modules.resourcepicker.dimens

import android.content.Context
import android.view.View
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.modules.resourcepicker.Prefs
import com.toolsfordevs.fast.modules.resources.DimensionResource
import com.toolsfordevs.fast.core.ui.widget.FastTabbedDialog
import com.toolsfordevs.fast.core.ui.widget.adapter.FastViewPagerAdapter
import com.toolsfordevs.fast.modules.resourcepicker.color.ColorPickerDialog

@Keep
class DimensPickerDialog(context: Context, val callback: (stringRes: DimensionResource) -> Unit, val dimensionPx:Int? = null) : FastTabbedDialog(context, Prefs::dimenPickerSelectedTab)
{
    init
    {
        dimensionPx?.let { (viewPager.adapter as DimensionPagerAdapter).setDimensionPx(it) }
    }

    override fun buildViewPagerAdapter(context: Context): FastViewPagerAdapter
    {
        return DimensionPagerAdapter(context) { stringRes ->
            callback(stringRes)
            dismiss()
        }
    }

    private class DimensionPagerAdapter(context: Context, callback: (stringRes: DimensionResource) -> Unit) :
            FastViewPagerAdapter()
    {
        private val view1 = DimensResourcePicker(context).apply { setOnDimensionSelectedListener(callback) }
        private val view2 = CommonDimensionsPicker(context).apply { setOnDimensionSelectedListener(callback) }
        private val view3 = FavoriteDimensionsPicker(context).apply { setOnDimensionSelectedListener(callback) }
        private val view4 = CustomDimensionsPicker(context).apply { setOnDimensionSelectedListener(callback) }

        override fun getItem(position: Int): View
        {
            return when (position)
            {
                0 -> view1
                1 -> view2
                2 -> view3
                else -> view4
            }
        }

        fun setDimensionPx(dimensionPx: Int)
        {
            view4.dimensionPx = dimensionPx
        }

        override fun getCount(): Int = 4

        override fun getPageTitle(position: Int): CharSequence?
        {
            return when (position)
            {
                0 -> "Dimension resources"
                1 -> "Common values"
                2 -> "Favorites"
                 else -> "Custom dimension"
            }
        }
    }
}