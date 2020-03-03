package com.toolsfordevs.fast.plugins.overlay.ui.overlay

import android.content.Context
import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup
import com.toolsfordevs.fast.modules.viewhierarchyexplorer.TransparentViewOverlay
import com.toolsfordevs.fast.modules.viewhierarchyexplorer.ViewHierarchyExplorer

internal class DelegateViewOverlay(context: Context) : TransparentViewOverlay(context)
{
    private val listener = { invalidate() }

    private val explorer = ViewHierarchyExplorer()

    private val viewBoundsOverlayDelegate = ViewBoundsOverlayDelegate()
    private val delegates = arrayListOf(GridOverlayDelegate(), viewBoundsOverlayDelegate)

    init
    {
        layoutParams = ViewGroup
            .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun onAttachedToWindow()
    {
        super.onAttachedToWindow()
        viewBoundsOverlayDelegate.onViewAttached(this)
//        viewTreeObserver.addOnDrawListener(listener)

        rootView.findViewById<View>(android.R.id.content).addOnLayoutChangeListener { view, i, i2, i3, i4, i5, i6, i7, i8 ->
            invalidate()
        }
    }

    override fun onDetachedFromWindow()
    {
        super.onDetachedFromWindow()
        viewTreeObserver.removeOnDrawListener(listener)
    }

    fun onViewClicked(view: View)
    {
        viewBoundsOverlayDelegate.addOrRemoveView(view)
        invalidate()
        // No need to invalidate as we will redraw in next frame
    }

    override fun onDraw(canvas: Canvas?)
    {
        super.onDraw(canvas)

        canvas?.let { c ->
            delegates.forEach { it.draw(c, width, height) }
        }
    }
}