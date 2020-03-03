package com.toolsfordevs.fast.core.ui.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.core.util.AndroidVersion
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.core.util.ResUtils
import com.toolsfordevs.fast.core.ui.R


// ToDo Add possibility to display either square or circle
@Keep
open class FastColorView(context: Context) : ImageView(context)
{
    private val bgdDrawable: BackgroundDrawable = BackgroundDrawable()
    var showTransparentGrid = true
        set(value)
        {
            field = value
            bgdDrawable.showTransparentGrid = value
        }

    init
    {
        setImageDrawable(bgdDrawable)

        if (AndroidVersion.isMinMarshmallow())
            foreground = ResUtils.getDrawable(R.drawable.fast_selectable_item_background_borderless)
        else
            setBackgroundResource(R.drawable.fast_selectable_item_background_borderless)
    }

    fun setColor(color: Int, borderColor:Int? = null, borderWidth:Float? = null)
    {
        bgdDrawable.bgdColor = color
        borderColor?.let { bgdDrawable.borderColor = it }
        borderWidth?.let { bgdDrawable.borderWidth = it }
        invalidate()
    }

    private class BackgroundDrawable : Drawable()
    {
        private val gridBgdPaint = Paint()
        private val gridPaint = Paint()
        private val bgdPaint = Paint()
        private val borderPaint = Paint()

        private var path = Path()

        var bgdColor: Int = 0
            set(value)
            {
                field = value
                bgdPaint.color = value
            }

        var borderColor: Int = 0
            set(value)
            {
                field = value
                borderPaint.color = value
            }

        var borderWidth: Float = Dimens.dpF(1)
            set(value)
            {
                field = value
                borderPaint.strokeWidth = value
            }

        var showTransparentGrid = true

        init
        {
            gridBgdPaint.style = Paint.Style.FILL
            gridBgdPaint.color = Color.WHITE
            gridBgdPaint.isAntiAlias = true

            gridPaint.style = Paint.Style.FILL
            gridPaint.color = 0xFFCCCCCC.toInt()
            gridPaint.isAntiAlias = true

            bgdPaint.style = Paint.Style.FILL
            bgdPaint.isAntiAlias = true

            borderPaint.style = Paint.Style.STROKE
            borderPaint.strokeWidth = Dimens.dpF(1)
            borderPaint.color = 0
            borderPaint.isAntiAlias = true
        }

        override fun onBoundsChange(bounds: Rect?)
        {
            super.onBoundsChange(bounds)
            resetPath()
        }

        override fun draw(canvas: Canvas)
        {
            val radius = (bounds.width() - Dimens.dpF(2)) / 2
            val x = bounds.width() / 2f
            val y = bounds.height() / 2f

            // Only show transparent grid if background color is transparent
            if (showTransparentGrid && Color.alpha(bgdPaint.color) < 0xFF)
            {
                canvas.drawCircle(x, y, radius, gridBgdPaint)
                canvas.drawPath(path, gridPaint)
            }

            canvas.drawCircle(x, y, radius, bgdPaint)
            canvas.drawCircle(x, y, radius, borderPaint)
        }

        override fun setAlpha(alpha: Int)
        {
            bgdPaint.alpha = alpha
            borderPaint.alpha = alpha
        }

        override fun getOpacity(): Int
        {
            return PixelFormat.TRANSLUCENT
        }

        override fun setColorFilter(colorFilter: ColorFilter?)
        {

        }

        fun resetPath()
        {
            val offset = borderPaint.strokeWidth
            val r = RectF(bounds.left + offset, bounds.top + offset, bounds.right - offset, bounds.bottom - offset)

            path = Path()
            path.addOval(r, Path.Direction.CCW)

            val grid = Path()

            val dp4 = Dimens.dp(4)
            val dp4f = Dimens.dpF(4)

            var isOdd = false

            for (i in 0 until bounds.width() step dp4)
            {
                for (j in 0 until bounds.height() step dp4 * 2)
                {
                    val start: Float = if (isOdd) j + dp4f else j.toFloat()
                    grid.addRect(i.toFloat(), start, i.toFloat() + dp4f, start + dp4f, Path.Direction.CCW)
                }

                isOdd = !isOdd
            }

            path.op(grid, Path.Op.DIFFERENCE)
        }
    }
}