package com.toolsfordevs.fast.plugins.overlay.ui.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.widget.TextView
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.plugins.overlay.ui.model.StrokeStyle
import kotlin.reflect.KMutableProperty0


internal class BackgroundPreview(context: Context) : TextView(context)
{
    private val bgdDrawable: BackgroundDrawable =
            BackgroundDrawable()

    private var bgdColor:KMutableProperty0<Int>? = null
    private var borderColor:KMutableProperty0<Int>? = null
    private var borderStyle:KMutableProperty0<Int>? = null
    private var textColor:KMutableProperty0<Int>? = null

    init
    {
        background = bgdDrawable
        gravity = Gravity.CENTER
        textSize = 12f
    }

    fun setColors(bgdColor:KMutableProperty0<Int>?, borderColor:KMutableProperty0<Int>?, borderStyle:KMutableProperty0<Int>? = null, textColor:KMutableProperty0<Int>? = null)
    {
        this.bgdColor = bgdColor
        this.borderColor = borderColor
        this.borderStyle = borderStyle
        this.textColor = textColor
        invalidate()
    }

    override fun invalidate()
    {
        // invalidate is called during superclass init
        // before bgdDrawable is created
        if (bgdDrawable != null)
        {
            bgdDrawable.bgdColor = bgdColor?.get() ?: 0
            bgdDrawable.borderColor = borderColor?.get() ?: 0
            bgdDrawable.borderStyle = borderStyle?.get() ?: StrokeStyle.SOLID
        }
        setTextColor(textColor?.get() ?: 0)
        super.invalidate()
    }

    private class BackgroundDrawable() : Drawable()
    {
        private val bgdPaint = Paint()
        private val borderPaint = Paint()
        private val gridPaint = Paint()

        private val dashPathEffect = DashPathEffect(floatArrayOf(Dimens.dpF(3), Dimens.dpF(4)), 0f)
        private val dotPathEffect = DashPathEffect(floatArrayOf(Dimens.dpF(1), Dimens.dpF(2)), 0f)

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

        var borderStyle: Int = 0
            set(value)
            {
                field = value
                borderPaint.pathEffect = when(value)
                {
                    StrokeStyle.DASHED -> dashPathEffect
                    StrokeStyle.DOTS   -> dotPathEffect
                    else                                                                 -> null
                }
            }

        init
        {
            bgdPaint.style = Paint.Style.FILL
            bgdPaint.color = bgdColor
            bgdPaint.isAntiAlias = true

            borderPaint.style = Paint.Style.STROKE
            borderPaint.color = borderColor
            borderPaint.strokeWidth = Dimens.dpF(2)
            borderPaint.isAntiAlias = true

            gridPaint.style = Paint.Style.FILL
            gridPaint.color = 0xFFCCCCCC.toInt()
            gridPaint.isAntiAlias = true
        }

        override fun draw(canvas: Canvas)
        {

            val radius = (bounds.width() - Dimens.dpF(2)) / 2
            val x = bounds.width() / 2f
            val y = bounds.height() / 2f

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

        /**
         * val dp4 = Dimens.dp(2)
        val dp4f = Dimens.dpF(2)

        var isOdd = false

        for (i in 0 until bounds.width() step dp4)
        {
        Log.d("Preview", "i $i")
        for (j in 0 until bounds.height() step dp4*2)
        {
        val start:Float = if (isOdd) j + dp4f else j.toFloat()
        Log.d("Preview", "j $j " + i * dp4f)
        canvas.drawRect(i.toFloat(), start, i.toFloat() + dp4f, start + dp4f, gridPaint)
        }

        isOdd = !isOdd
        }
         */
    }
}