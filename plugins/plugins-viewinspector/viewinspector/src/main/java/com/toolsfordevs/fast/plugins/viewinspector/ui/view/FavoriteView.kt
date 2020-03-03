package com.toolsfordevs.fast.plugins.viewinspector.ui.view

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import com.toolsfordevs.fast.modules.androidx.recyclerview.ext.vRecyclerView
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.LinearLayoutManager
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.RecyclerView
import com.toolsfordevs.fast.modules.recyclerview.RendererAdapter
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewProperty
import com.toolsfordevs.fast.plugins.viewinspector.ui.FavoriteManager
import com.toolsfordevs.fast.plugins.viewinspector.ui.ViewInspectorModel

internal class FavoriteView(context: Context) : FrameLayout(context)
{
    private val recyclerView: RecyclerView = vRecyclerView(context)
    private val adapter: RendererAdapter = RendererAdapter()

    private val attrs:ArrayList<ViewProperty<*, *>> = arrayListOf()

    private val callback:() -> Unit = { refresh() }

    var usePixels = false
        set(value)
        {
            for (attr in attrs)
                attr.usePixels = value

            adapter.notifyItemRangeChanged(0, adapter.itemCount)

            field = value
        }

    private lateinit var view:View

    init
    {
        ViewInspectorModel.setupRenderersForAdapter(adapter)

        adapter.mode = RendererAdapter.MODE_INSTANCE_OF

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = null
        recyclerView.adapter = adapter

        addView(recyclerView, LayoutParams(MATCH_PARENT, MATCH_PARENT))
    }

    fun setView(v: View)
    {
        Log.d("ViewInspector", "set view")
        view = v
        refresh()
    }

    fun refresh()
    {
        Log.d("ViewInspector", "refresh")
        attrs.clear()

        val favorites = ViewInspectorModel.getFavoriteProperties(view)
        //val favorites = properties.filter { FavoriteManager.isFavorite(view, it.id) }

        attrs.addAll(favorites)

        for (attr in attrs) {
            attr.usePixels = usePixels
            attr.assignView(view)
        }

        adapter.clear()
        adapter.addAll(attrs)
    }

    override fun onAttachedToWindow()
    {
        Log.d("ViewInspector", "onAttachedToWindow")
        super.onAttachedToWindow()
        FavoriteManager.addOnFavoriteChangeCallback(callback)

        if (::view.isInitialized)
            refresh()
    }

    override fun onDetachedFromWindow()
    {
        Log.d("ViewInspector", "onDetachedFromWindow")
        super.onDetachedFromWindow()
        FavoriteManager.removeOnFavoriteChangeCallback(callback)
    }
}