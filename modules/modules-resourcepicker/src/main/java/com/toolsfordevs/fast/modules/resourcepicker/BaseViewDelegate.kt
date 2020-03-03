package com.toolsfordevs.fast.modules.resourcepicker


import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.LinearLayoutManager
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.RecyclerView
import com.toolsfordevs.fast.modules.recyclerview.ExpandableRendererAdapter
import com.toolsfordevs.fast.modules.subheader.ExpandableSubheaderRenderer
import com.toolsfordevs.fast.modules.subheader.Subheader


internal abstract class BaseViewDelegate(recyclerView: RecyclerView)
{
    protected val adapter = ExpandableRendererAdapter()

    init
    {
        adapter.addRenderer(Subheader::class, ::ExpandableSubheaderRenderer, true)

        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        /*recyclerView.addItemDecoration(ExpandableDivider().apply {
            setDividerSizeDp(1)
            setColorRes(R._color.fast_md_black_38)
        })*/
        recyclerView.adapter = adapter

//        val a:DefaultItemAnimator = recyclerView.itemAnimator as DefaultItemAnimator
//        a.supportsChangeAnimations = false
    }

    fun setData(data: List<Any>)
    {
        adapter.clear()
        adapter.addAll(data)
    }
}