package com.toolsfordevs.fast.modules.resourcepicker.string

import android.content.Context
import android.widget.Space
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.modules.resourcepicker.FilterDelegate
import com.toolsfordevs.fast.modules.resourcepicker.R
import com.toolsfordevs.fast.modules.resourcepicker.ResourcePickerView
import com.toolsfordevs.fast.modules.resources.ResourceManager
import com.toolsfordevs.fast.modules.resources.StringResource

@Keep
internal class StringResourcePicker(context: Context) : ResourcePickerView(context)
{
    private var viewDelegate: StringViewDelegate
    private var listener: ((stringRes: StringResource) -> Unit)? = null

    init
    {
        buttonBar.addView(Space(context), LayoutParams(0, LayoutParams.WRAP_CONTENT).apply { weight = 1f })

        val filterButton = addButton(R.drawable.fast_resourcepicker_ic_filter)
        val localeButton = addButton(R.drawable.fast_resourcepicker_ic_locale)
        //addButton(R.drawable.fast_resourcepicker_ic_search)

        viewDelegate = StringViewDelegate(recyclerView) { stringRes -> listener?.invoke(stringRes) }

        val filterDelegate = FilterDelegate(filterButton) { data: List<Any> ->
            viewDelegate.setData(data)
        }

        val localeDelegate = LocaleDelegate(localeButton) { locale ->
            viewDelegate.locale = locale
        }

        filterDelegate.updateResources(ResourceManager.strings)
    }

    fun setOnStringSelectedListener(callback: (stringRes: StringResource) -> Unit)
    {
        listener = callback
    }
}