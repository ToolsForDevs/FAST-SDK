package com.toolsfordevs.fast.modules.resourcepicker.string

import android.content.Context
import android.view.View
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.modules.resourcepicker.Prefs
import com.toolsfordevs.fast.modules.resources.StringResource
import com.toolsfordevs.fast.core.ui.widget.FastTabbedDialog
import com.toolsfordevs.fast.core.ui.widget.adapter.FastViewPagerAdapter

@Keep
class StringPickerDialog(context: Context, val callback: (stringRes: StringResource) -> Unit) : FastTabbedDialog(context, Prefs::stringPickerSelectedTab)
{
    override fun buildViewPagerAdapter(context: Context): FastViewPagerAdapter
    {
        return StringPagerAdapter(context) { stringRes ->
            callback(stringRes)
            dismiss()
        }
    }

    private class StringPagerAdapter(context: Context, callback: (stringRes: StringResource) -> Unit) :
            FastViewPagerAdapter()
    {
        private val view1 = StringResourcePicker(context).apply { setOnStringSelectedListener(callback) }
        private val view2 = FavoriteStringPicker(context).apply { setOnStringSelectedListener(callback) }

        override fun getItem(position: Int): View
        {
            return when (position)
            {
                0 -> view1
                1 -> view2
                else -> view1
            }
        }

        override fun getCount(): Int = 2

        override fun getPageTitle(position: Int): CharSequence?
        {
            return when (position)
            {
                0 -> "String Resources"
                1 -> "Favorites"
                else -> "Favorites"
                // else -> "Custom"
            }
        }
    }
}