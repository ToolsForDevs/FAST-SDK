package com.toolsfordevs.fast.plugins.viewinspector.ui.view

import android.content.Context
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import com.toolsfordevs.fast.modules.androidx.recyclerview.ext.vRecyclerView
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.LinearLayoutManager
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.RecyclerView
import com.toolsfordevs.fast.modules.recyclerview.RendererAdapter
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewProperty
import com.toolsfordevs.fast.plugins.viewinspector.ui.ViewInspectorModel

internal class LayoutView(context: Context) : FrameLayout(context)
{
    private val recyclerView: RecyclerView = vRecyclerView(context)
    private val adapter: RendererAdapter = RendererAdapter()

    private val attrs:ArrayList<ViewProperty<*, *>> = arrayListOf()

    var usePixels: Boolean = false
        set(value)
        {
            for (attr in attrs) {
                attr.usePixels = value
            }

            adapter.notifyItemRangeChanged(0, adapter.itemCount)

            field = value
        }

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
        attrs.clear()
        attrs.addAll(ViewInspectorModel.getLayoutProperties(v))

        for (attr in attrs) {
            attr.usePixels = usePixels
            attr.assignView(v)
        }

        adapter.clear()
        adapter.addAll(attrs)
    }
}