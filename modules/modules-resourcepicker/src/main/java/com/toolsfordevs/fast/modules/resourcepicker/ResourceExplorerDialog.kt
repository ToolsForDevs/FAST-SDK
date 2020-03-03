package com.toolsfordevs.fast.modules.resourcepicker

import android.content.Context
import android.view.View
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.modules.resourcepicker.color.ColorResourcePicker
import com.toolsfordevs.fast.modules.resourcepicker.dimens.DimensResourcePicker
import com.toolsfordevs.fast.modules.resourcepicker.drawable.DrawableResourcePicker
import com.toolsfordevs.fast.modules.resourcepicker.string.StringResourcePicker
import com.toolsfordevs.fast.core.ui.widget.FastTabbedDialog
import com.toolsfordevs.fast.core.ui.widget.adapter.FastViewPagerAdapter

@Keep
class ResourceExplorerDialog(context: Context) : FastTabbedDialog(context, Prefs::explorerPickerSelectedTab)
{
    override fun buildViewPagerAdapter(context: Context): FastViewPagerAdapter
    {
        return ResExplorerAdapter(context)
    }

    class ResExplorerAdapter(context:Context) : FastViewPagerAdapter()
    {
        private val view1 = ColorResourcePicker(context)
        private val view2 = DimensResourcePicker(context)
        private val view3 = DrawableResourcePicker(context)
        private val view4 = StringResourcePicker(context)

        override fun getItem(position: Int): View
        {
            return when (position)
            {
                0 -> view1
                1 -> view2
                2 -> view3
                3 -> view4
                else -> throw Exception("No view for index > 3")
            }
        }

        override fun getCount(): Int
        {
            return 4
        }

        override fun getPageTitle(position: Int): CharSequence
        {
            return when (position)
            {
                0 -> "Color"
                1 -> "Dimension"
                2 -> "Drawable"
                3 -> "String"
                else -> "?"
            }
        }
    }
}