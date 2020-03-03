package com.toolsfordevs.fast.plugins.overlay.ui.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.view.View
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.plugins.overlay.ui.model.StrokeStyle
import kotlin.reflect.KMutableProperty0


internal class RulerPreview(context: Context) : View(context)
{
    private val bgdDrawable: BackgroundDrawable =
            BackgroundDrawable()

    private var color:KMutableProperty0<Int>? = null
    private var borderStyle:KMutableProperty0<Int>? = null

    init
    {
        background = bgdDrawable
    }

    fun setColor(color:KMutableProperty0<Int>?, borderStyle:KMutableProperty0<Int>? = null)
    {
        this.color = color
        this.borderStyle = borderStyle
        invalidate()
    }

    override fun invalidate()
    {
        // invalidate is called during superclass init
        // before bgdDrawable is created
        if (bgdDrawable != null)
        {
            bgdDrawable.color = color?.get() ?: 0
            bgdDrawable.borderStyle = borderStyle?.get() ?: StrokeStyle.SOLID
        }
        super.invalidate()
    }

    private class BackgroundDrawable() : Drawable()
    {
        private val paint = Paint()

        private val dashPathEffect = DashPathEffect(floatArrayOf(Dimens.dpF(2), Dimens.dpF(1)), 0f)
        private val dotPathEffect = DashPathEffect(floatArrayOf(Dimens.dpF(1), Dimens.dpF(1)), 0f)

        var color: Int = 0
            set(value)
            {
                field = value
                paint.color = value
            }

        var borderStyle: Int = 0
            set(value)
            {
                field = value
                paint.pathEffect = when(value)
                {
                    StrokeStyle.DASHED -> dashPathEffect
                    StrokeStyle.DOTS   -> dotPathEffect
                    else                                                                 -> null
                }
            }

        init
        {
            paint.style = Paint.Style.STROKE
            paint.color = color
            paint.strokeWidth = Dimens.dpF(1)
            paint.isAntiAlias = true
        }

        override fun draw(canvas: Canvas)
        {
            val thirdW = bounds.width() / 3f
            val thirdH = bounds.height() / 3f
            val twoThirdW = thirdW * 2
            val twoThirdH = thirdH * 2

            canvas.drawLine(0f, thirdH, thirdW, thirdH, paint)
            canvas.drawLine(thirdW, 0f, thirdW, thirdH, paint)
            canvas.drawLine(twoThirdW, 0f, twoThirdW, thirdH, paint)
            canvas.drawLine(twoThirdW, thirdH, bounds.width().toFloat(), thirdH, paint)
            canvas.drawLine(twoThirdW, twoThirdH, bounds.width().toFloat(), twoThirdH, paint)
            canvas.drawLine(twoThirdW, twoThirdH, twoThirdW, bounds.height().toFloat(), paint)
            canvas.drawLine(thirdW, twoThirdH, thirdW, bounds.height().toFloat(), paint)
            canvas.drawLine(0F, twoThirdH, thirdW, twoThirdH, paint)
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