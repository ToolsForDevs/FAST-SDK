package com.toolsfordevs.fast.modules.resourcepicker.string

import android.content.Context
import android.widget.Space
import com.toolsfordevs.fast.modules.resourcepicker.*
import com.toolsfordevs.fast.modules.resourcepicker.FavoriteManager
import com.toolsfordevs.fast.modules.resources.StringResource


internal class FavoriteStringPicker(context: Context) : ResourcePickerView(context)
{
    private var viewDelegate: StringViewDelegate
    private var listener: ((stringRes: StringResource) -> Unit)? = null

    private val filterDelegate:FilterDelegate
    private val callback: () -> Unit = { refresh() }

    init
    {
        buttonBar.addView(Space(context), LayoutParams(0, LayoutParams.WRAP_CONTENT).apply { weight = 1f })

        val filterButton = addButton(R.drawable.fast_resourcepicker_ic_filter)
        val localeButton = addButton(R.drawable.fast_resourcepicker_ic_locale)
        //addButton(R.drawable.fast_resourcepicker_ic_search)

        viewDelegate = StringViewDelegate(recyclerView) { stringRes -> listener?.invoke(stringRes) }

        filterDelegate = FilterDelegate(filterButton) { data: List<Any> ->
            viewDelegate.setData(data)
        }

        val localeDelegate = LocaleDelegate(localeButton) { locale ->
            viewDelegate.locale = locale
        }
    }

    fun setOnStringSelectedListener(callback: (stringRes: StringResource) -> Unit)
    {
        listener = callback
    }

    private fun refresh()
    {
        filterDelegate.updateResources(Prefs.favoriteStrings)
    }

    override fun onAttachedToWindow()
    {
        super.onAttachedToWindow()
        FavoriteManager.addOnFavoriteChangeCallback(callback)
        refresh()
    }

    override fun onDetachedFromWindow()
    {
        super.onDetachedFromWindow()
        FavoriteManager.removeOnFavoriteChangeCallback(callback)
    }
}