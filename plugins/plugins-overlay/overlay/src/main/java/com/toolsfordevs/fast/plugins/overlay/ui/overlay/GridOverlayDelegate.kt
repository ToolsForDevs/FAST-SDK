package com.toolsfordevs.fast.plugins.overlay.ui.overlay

import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.plugins.overlay.ui.Prefs
import com.toolsfordevs.fast.plugins.overlay.ui.model.Direction
import com.toolsfordevs.fast.plugins.overlay.ui.model.HStartFrom
import com.toolsfordevs.fast.plugins.overlay.ui.model.StrokeStyle
import com.toolsfordevs.fast.plugins.overlay.ui.model.VStartFrom

internal class GridOverlayDelegate : OverlayDelegate
{
    private val paint = Paint().apply {
        isAntiAlias = true
        strokeWidth = 1f
        style = Paint.Style.STROKE
    }

    private val dashPathEffect = DashPathEffect(floatArrayOf(Dimens.dpF(2), Dimens.dpF(2)), 0f)
    private val dotPathEffect = DashPathEffect(floatArrayOf(Dimens.dpF(1), Dimens.dpF(1)), 0f)

    override fun draw(canvas: Canvas, width:Int, height:Int)
    {
        for (gridOption in Prefs.currentProfile.grids)
        {
            if (!gridOption.enabled) continue

            paint.color = gridOption.color
            paint.pathEffect = when (gridOption.strokeStyle)
            {
                StrokeStyle.DASHED -> dashPathEffect
                StrokeStyle.DOTS   -> dotPathEffect
                else                                                                 -> null
            }

            val vOffset = gridOption.vOffsetPixel
            val hOffset = gridOption.hOffsetPixel

            val startX = when (gridOption.vStartFrom)
            {
                VStartFrom.START -> hOffset
                VStartFrom.END   -> width - hOffset
                else                                                               -> 0
            }

            val endX = when (gridOption.vStartFrom)
            {
                VStartFrom.START -> width
                VStartFrom.END   -> 0
                else                                                               -> width
            }

            val startY = when (gridOption.hStartFrom)
            {
                HStartFrom.TOP    -> vOffset
                HStartFrom.BOTTOM -> height - vOffset
                else                                                                -> 0
            }

            val endY = when (gridOption.hStartFrom)
            {
                HStartFrom.TOP    -> height
                HStartFrom.BOTTOM -> 0
                else                                                                -> height
            }

            if (gridOption.vStartFrom != VStartFrom.CENTER)
            {
                if (gridOption.cellWidthPixel > 0)
                {
                    if (startX < endX)
                    {
                        for (i in startX until endX step gridOption.cellWidthPixel)
                            canvas.drawLine(i.toFloat(), startY.toFloat(), i.toFloat(), endY.toFloat(), paint)
                    }
                    else
                    {
                        for (i in startX downTo endX step gridOption.cellWidthPixel)
                            canvas.drawLine(i.toFloat(), startY.toFloat(), i.toFloat(), endY.toFloat(), paint)
                    }
                }
            }
            else
            {
                val centerX = width / 2
                canvas.drawLine(centerX.toFloat(), 0f, centerX.toFloat(), height.toFloat(), paint)

                if (gridOption.cellWidthPixel > 0)
                {
                    var startX = if (hOffset > 0) centerX - hOffset else centerX - gridOption.cellWidthPixel
                    for (i in startX downTo 0 step gridOption.cellWidthPixel)
                        canvas.drawLine(i.toFloat(), 0f, i.toFloat(), height.toFloat(), paint)

                    startX = if (hOffset > 0) centerX + hOffset else centerX + gridOption.cellWidthPixel
                    for (i in startX until width step gridOption.cellWidthPixel)
                        canvas.drawLine(i.toFloat(), 0f, i.toFloat(), height.toFloat(), paint)
                }
            }

            if (gridOption.hStartFrom != HStartFrom.CENTER)
            {
                if (gridOption.cellHeightPixel > 0)
                {
                    if (startY < endY)
                    {
                        for (i in startY until endY step gridOption.cellHeightPixel)
                            canvas.drawLine(startX.toFloat(), i.toFloat(), endX.toFloat(), i.toFloat(), paint)
                    }
                    else
                    {
                        for (i in startY downTo endY step gridOption.cellHeightPixel)
                            canvas.drawLine(startX.toFloat(), i.toFloat(), endX.toFloat(), i.toFloat(), paint)
                    }
                }
            }
            else
            {
                val centerY = height / 2
                canvas.drawLine(0f, centerY.toFloat(), width.toFloat(), centerY.toFloat(), paint)

                if (gridOption.cellHeightPixel > 0)
                {
                    var startY = if (vOffset > 0) centerY - vOffset else centerY - gridOption.cellHeightPixel
                    for (i in startY downTo 0 step gridOption.cellHeightPixel)
                        canvas.drawLine(0f, i.toFloat(), width.toFloat(), i.toFloat(), paint)

                    startY = if (vOffset > 0) centerY + vOffset else centerY + gridOption.cellHeightPixel
                    for (i in startY until height step gridOption.cellHeightPixel)
                        canvas.drawLine(0f, i.toFloat(), width.toFloat(), i.toFloat(), paint)
                }
            }
        }

        for (option in Prefs.currentProfile.gutters)
        {
            if (!option.enabled)
                continue

            paint.color = option.color
            paint.pathEffect = when (option.strokeStyle)
            {
                StrokeStyle.DASHED -> dashPathEffect
                StrokeStyle.DOTS   -> dotPathEffect
                else                                                                 -> null
            }

            if (option.direction == Direction.VERTICAL)
            {
                val x:Float = when (option.startFrom)
                {
                    VStartFrom.START  -> option.offsetPixel.toFloat()
                    VStartFrom.CENTER -> width / 2f + option.offsetPixel
                    VStartFrom.END    -> width.toFloat() - option.offsetPixel
                    else                                                                -> 0f
                }

//                x += option.offsetPixel.toFloat()

                canvas.drawLine(x, 0f, x, height.toFloat(), paint)
            }
            else
            {
                val y:Float = when (option.startFrom)
                {
                    VStartFrom.START  -> option.offsetPixel.toFloat()
                    VStartFrom.CENTER -> height / 2f + option.offsetPixel
                    VStartFrom.END    -> height.toFloat() - option.offsetPixel
                    else                                                                -> 0f
                }

                //x += option.offsetPixel.toFloat()

                canvas.drawLine(0f, y, width.toFloat(), y, paint)
            }
        }
    }
}