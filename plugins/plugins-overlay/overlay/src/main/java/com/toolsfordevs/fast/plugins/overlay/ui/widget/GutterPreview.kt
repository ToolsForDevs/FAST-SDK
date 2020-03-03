package com.toolsfordevs.fast.plugins.overlay.ui.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.plugins.overlay.ui.model.Direction
import com.toolsfordevs.fast.plugins.overlay.ui.model.StrokeStyle


internal class GutterPreview(context: Context) : ImageView(context)
{
    private val bgdDrawable: BackgroundDrawable =
            BackgroundDrawable()

    init
    {
        background = bgdDrawable
    }

    fun refresh(color:Int, direction:Int, strokeStyle:Int)
    {
        bgdDrawable.color = color
        bgdDrawable.direction = direction
        bgdDrawable.strokeStyle = strokeStyle
        invalidate()
    }

    private class BackgroundDrawable() : Drawable()
    {
        private val paint = Paint()
        var direction = Direction.VERTICAL
        var strokeStyle = StrokeStyle.SOLID
            set(value)
            {
                field = value
                paint.pathEffect = when (value)
                {
                    StrokeStyle.DASHED -> dashPathEffect
                    StrokeStyle.DOTS   -> dotPathEffect
                    else               -> null
                }
            }

        var color: Int = 0
            set(value)
            {
                field = value
                paint.color = value
            }

        private val dashPathEffect = DashPathEffect(floatArrayOf(Dimens.dpF(2), Dimens.dpF(2)), 0f)
        private val dotPathEffect = DashPathEffect(floatArrayOf(Dimens.dpF(1), Dimens.dpF(1)), 0f)

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
//            val halfLineWidth = dp1 / 2

            if (direction == Direction.VERTICAL)
                canvas.drawLine(cx, dp1, cx, bounds.height() - dp1, paint)
            else
                canvas.drawLine(0f, cy, bounds.width() - dp1, cy, paint)
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