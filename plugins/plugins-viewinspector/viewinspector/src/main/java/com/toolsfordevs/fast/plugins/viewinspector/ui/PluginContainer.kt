package com.toolsfordevs.fast.plugins.viewinspector.ui

import android.app.Activity
import android.content.Context
import android.widget.FrameLayout
import com.toolsfordevs.fast.core.FastManager
import com.toolsfordevs.fast.core.ext.layoutParamsMM
import com.toolsfordevs.fast.core.widget.FastPanel
import com.toolsfordevs.fast.core.widget.FastPanelDelegate
import com.toolsfordevs.fast.modules.viewhierarchyexplorer.TransparentViewOverlay
import com.toolsfordevs.fast.modules.viewhierarchyexplorer.ViewHierarchyExplorer


internal class PluginContainer(context: Context) : FrameLayout(context), FastPanel by FastPanelDelegate()
{
    private val panel by lazy { ViewInspectorPanel(context) }

    private val explorer:ViewHierarchyExplorer by lazy { ViewHierarchyExplorer() }
    private val overlay = TransparentViewOverlay(context)
    private val boundsOverlay = ViewBoundsOverlay(context)

    init
    {
        layoutParams = layoutParamsMM()

    }

    override fun onAttachedToWindow()
    {
        super.onAttachedToWindow()

        overlay.onClickCallback = { x, y ->
            val v = explorer.getViewAt((context as Activity).window.decorView, x, y)

            if (v != null)
            {
                panel.setView(v)
                FastManager.addView(panel)
            }
        }

        FastManager.addOverlay(overlay)
        FastManager.addOverlay(boundsOverlay)
    }

    override fun onDetachedFromWindow()
    {
        super.onDetachedFromWindow()

        FastManager.removeOverlay(overlay)
        FastManager.removeOverlay(boundsOverlay)
    }
}