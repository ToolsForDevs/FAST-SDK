package com.toolsfordevs.fast.plugins.environmentswitch.ui

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.toolsfordevs.fast.core.ext.*
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.core.ui.ext.setPaddingHorizontal
import com.toolsfordevs.fast.core.ui.widget.BottomSheetLayout
import com.toolsfordevs.fast.core.ui.widget.FastPanelToolbar
import com.toolsfordevs.fast.modules.androidx.recyclerview.ext.vRecyclerView
import com.toolsfordevs.fast.modules.recyclerview.RendererAdapter
import com.toolsfordevs.fast.modules.recyclerview.decoration.Divider
import com.toolsfordevs.fast.plugins.environmentswitch.EnvironmentSwitchPlugin
import com.toolsfordevs.fast.plugins.environmentswitch.R
import com.toolsfordevs.fast.plugins.environmentswitch.Store
import com.toolsfordevs.fast.plugins.environmentswitch.ui.renderer.*
import com.toolsfordevs.fast.plugins.environmentswitch.ui.renderer.BooleanRenderer
import com.toolsfordevs.fast.plugins.environmentswitch.ui.renderer.FloatRenderer
import com.toolsfordevs.fast.plugins.environmentswitch.ui.renderer.IntRenderer
import com.toolsfordevs.fast.plugins.environmentswitch.ui.renderer.LongRenderer

internal class Panel(context: Context) : BottomSheetLayout(context)
{
    private val adapter = RendererAdapter()
    private val store = Store()

    init
    {
        val layout = vLinearLayout(context)

        val toolbar = FastPanelToolbar(context)

        val title = TextView(context).apply {
            setTextColor(MaterialColor.WHITE_87)
            textSize = 16f
            text = "Environment"
            gravity = Gravity.CENTER_VERTICAL
            setPaddingHorizontal(16.dp)
        }

        toolbar.buttonLayout.addView(title, 0, linearLayoutParamsWeM(1f))

        toolbar.createButton(R.drawable.fast_environmentswitch_ic_refresh).setOnClickListener { refresh() }

        val recyclerView = vRecyclerView(context)
        recyclerView.isVerticalScrollBarEnabled = true
        recyclerView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        recyclerView.addItemDecoration(Divider().apply {
            setDividerSize(1.dp)
            setColor(MaterialColor.BLACK_12)
            showLast = true
        })

        adapter.addRenderer(::BooleanRenderer)
        adapter.addRenderer(::IntRenderer)
        adapter.addRenderer(::FloatRenderer)
        adapter.addRenderer(::LongRenderer)
        adapter.addRenderer(::StringRenderer)
        adapter.addRenderer(::SingleChoiceRenderer)
        adapter.addRenderer(::MultiChoiceRenderer)

        recyclerView.adapter = adapter

        layout.addView(toolbar, layoutParamsMW())
        layout.addView(recyclerView, layoutParamsMM())

        addView(layout, layoutParamsMM())
        setCollapsedHeight(500.dp)
    }

    private fun refresh()
    {
        EnvironmentSwitchPlugin.refreshData(store)
        adapter.clear()
        adapter.addAll(store.getData())
    }

    override fun onAttachedToWindow()
    {
        super.onAttachedToWindow()
        refresh()
    }
}