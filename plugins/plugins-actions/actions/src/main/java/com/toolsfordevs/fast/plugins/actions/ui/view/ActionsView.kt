package com.toolsfordevs.fast.plugins.actions.ui.view

import android.annotation.SuppressLint
import android.content.Context
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.FastRecyclerView
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.LinearLayoutManager
import com.toolsfordevs.fast.modules.recyclerview.RendererAdapter
import com.toolsfordevs.fast.plugins.actions.ui.FavoriteManager
import com.toolsfordevs.fast.plugins.actions.ui.Prefs
import com.toolsfordevs.fast.plugins.actions.ui.Repository
import com.toolsfordevs.fast.plugins.actions.ui.SimpleRenderer


@SuppressLint("ViewConstructor")
internal class ActionsView(context: Context, private val repository: Repository) : FastRecyclerView(context), ISortable
{
    private val adapter: RendererAdapter

    private val callback: () -> Unit = {
        refresh()
    }

    init
    {
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)

        adapter = RendererAdapter()
        adapter.addRenderer(::SimpleRenderer)

        setAdapter(adapter)
    }

    private fun refresh()
    {
        adapter.clear()
        adapter.addAll(SortDelegate.sort(Prefs.sortType, repository.getData()))
    }

    override fun onAttachedToWindow()
    {
        super.onAttachedToWindow()
        refresh()
        FavoriteManager.addOnFavoriteChangeCallback(callback)
    }

    override fun onDetachedFromWindow()
    {
        super.onDetachedFromWindow()
        FavoriteManager.removeOnFavoriteChangeCallback(callback)
    }

    override fun sortData(sortType: Int)
    {
        Prefs.sortType = sortType
        refresh()
    }
}