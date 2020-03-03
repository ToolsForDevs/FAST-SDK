package com.toolsfordevs.fast.plugins.overlay.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.toolsfordevs.fast.core.util.Dimens


internal class GridPreview(context: Context) : ImageView(context)
{
    private val bgdDrawable: BackgroundDrawable =
            BackgroundDrawable()

    init
    {
        background = bgdDrawable
    }

    fun refresh(color:Int, showVertical:Boolean, showHorizontal:Boolean)
    {
        bgdDrawable.color = color
        bgdDrawable.showVertical = showVertical
        bgdDrawable.showHorizontal = showHorizontal
        invalidate()
    }

    private class BackgroundDrawable() : Drawable()
    {
        private val paint = Paint()
        var showVertical = true
        var showHorizontal = true

        var color: Int = 0
            set(value)
            {
                field = value
                paint.color = value
            }

        init
        {
            paint.style = Paint.Style.STROKE
            paint.color = color
            paint.isAntiAlias = true
            paint.strokeWidth = Dimens.dpF(1)
        }

        override fun draw(canvas: Canvas)
        {
            val cx = bounds.width() / 2f
            val cy = bounds.height() / 2f

            val dp1 = Dimens.dpF(1)
            val halfLineWidth = dp1 / 2

            val both = !(showVertical xor showHorizontal)

            if (both)
            {
                canvas.drawRect(halfLineWidth, halfLineWidth, bounds.width() - halfLineWidth, bounds.height() - halfLineWidth, paint)
            }
            else if (showVertical)
            {
                canvas.drawLine(halfLineWidth, 0f, halfLineWidth, bounds.height().toFloat(), paint)
                canvas.drawLine(bounds.width() - halfLineWidth, 0f, bounds.width() - halfLineWidth, bounds.height().toFloat(), paint)
            }
            else
            {
                canvas.drawLine(0f, halfLineWidth, bounds.width().toFloat(), halfLineWidth, paint)
                canvas.drawLine(0f, bounds.height() - halfLineWidth, bounds.width().toFloat(), bounds.height() - halfLineWidth, paint)
            }

            if (both or showHorizontal)
                canvas.drawLine(0f, cy, bounds.width() - dp1, cy, paint)

            if (both or showVertical)
                canvas.drawLine(cx, dp1, cx, bounds.height() - dp1, paint)
        }

        override fun setAlpha(alpha: Int)
        {
            paint.alpha = alpha
        }

        override fun getOpacity(): Int
        {
            return PixelFormat.TRANSLUCENT
        }

        override fun setColorFilter(colorFilter: ColorFilter?)
        {

        }
    }
}