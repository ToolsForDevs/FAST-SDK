package com.toolsfordevs.fast.plugins.viewinspector.ui.view

import android.content.Context
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import com.toolsfordevs.fast.modules.androidx.recyclerview.ext.vRecyclerView
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.LinearLayoutManager
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.RecyclerView
import com.toolsfordevs.fast.modules.recyclerview.ExpandableRendererAdapter
import com.toolsfordevs.fast.modules.recyclerview.RendererAdapter
import com.toolsfordevs.fast.modules.subheader.ExpandableSubheaderRenderer
import com.toolsfordevs.fast.modules.subheader.Subheader
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewProperty
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.PropertyCategory
import com.toolsfordevs.fast.plugins.viewinspector.ui.ViewInspectorModel


internal class PropertiesView(context: Context) : FrameLayout(context)
{
    private val recyclerView: RecyclerView = vRecyclerView(context)
    private val adapter = ExpandableRendererAdapter()

    private val attrs: ArrayList<ViewProperty<*, *>> = arrayListOf()

    var usePixels:Boolean = false
        set(value)
        {
            for (attr in attrs)
                attr.usePixels = value

            adapter.notifyItemRangeChanged(0, adapter.itemCount)
            adapter.notifyDataSetChanged()

            field = value
        }

    init
    {
        ViewInspectorModel.setupRenderersForAdapter(adapter)

        adapter.addRenderer(Subheader::class, ::ExpandableSubheaderRenderer, true)

        adapter.mode = RendererAdapter.MODE_INSTANCE_OF

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = null
        recyclerView.adapter = adapter

        addView(recyclerView, LayoutParams(MATCH_PARENT, MATCH_PARENT))
    }

    fun setView(v: View)
    {
        adapter.clear()
        attrs.clear()

        val list = arrayListOf<Any>()

        val props = ViewInspectorModel.getProperties(v).reversed()
        for (item in props)
        {
            if (item.getProperties().isEmpty())
                continue

            val map = item.getProperties()

            val viewProperties = arrayListOf<ViewProperty<*, *>>()

            for (key in map.keys)
            {

                if (key == PropertyCategory.LAYOUT_PARAMS
                        || key == PropertyCategory.LAYOUT)
                    continue

                val properties = map.get(key)!!

                for (viewProperty in properties)
                {
                    viewProperty.usePixels = usePixels
                    viewProperty.assignView(v)
                }

                attrs.addAll(properties)
                viewProperties.addAll(properties)
            }

            if (viewProperties.isNotEmpty())
            {
                list.add(Subheader(item.viewClass.name))
                list.addAll(viewProperties)
            }
        }

        adapter.addAll(list)
    }
}