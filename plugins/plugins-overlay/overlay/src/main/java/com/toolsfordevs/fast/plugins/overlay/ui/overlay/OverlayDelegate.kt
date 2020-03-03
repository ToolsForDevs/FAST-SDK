package com.toolsfordevs.fast.plugins.overlay.ui.overlay

import android.graphics.Canvas
import android.view.View

internal interface OverlayDelegate
{
    fun draw(canvas: Canvas, width:Int, height:Int)
}