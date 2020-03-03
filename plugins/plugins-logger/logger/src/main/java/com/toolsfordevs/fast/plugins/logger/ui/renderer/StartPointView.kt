package com.toolsfordevs.fast.plugins.logger.ui.renderer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.toolsfordevs.fast.core.util.Dimens


internal class StartPointView : View
{
    constructor (context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor (context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val paint = Paint().apply {
        isAntiAlias = true
        color = Color.WHITE
        style = Paint.Style.FILL
    }

    private val linePaint = Paint().apply {
        isAntiAlias = true
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = Dimens.dpF(2)
    }

    var isUpDirection = true

    fun setColor(color: Int)
    {
        paint.color = color
        linePaint.color = color
    }

    override fun onDraw(canvas: Canvas?)
    {
        super.onDraw(canvas)

        val w = width
        val h = height

        val halfW = w.toFloat() / 2
        val halfH = h.toFloat() / 2

        if (isUpDirection)
        {
            canvas?.drawCircle(halfW, halfH, halfW - linePaint.strokeWidth, linePaint)
            canvas?.drawLine(halfW, 0f, halfW, halfH - halfW / 2, linePaint)
        }
        else
        {
            canvas?.drawCircle(halfW, halfH, halfW - linePaint.strokeWidth, paint)
            canvas?.drawLine(halfW, halfH + halfW / 2, halfW, h.toFloat(), linePaint)
        }
    }
}