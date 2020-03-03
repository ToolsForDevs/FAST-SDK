package com.toolsfordevs.fast.modules.resourcepicker.color.components

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.widget.SeekBar
import kotlin.math.round

internal abstract class HSBSeekBar(context: Context, val component:Int, val colorCount:Int) : SeekBar(context)
{
    private val colors: IntArray = IntArray(colorCount)

    private var _color = 0xFFFF0000.toInt()

    init
    {
        max = if (component == 0) colorCount else 100
        thumbTintList = ColorStateList.valueOf(_color)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int)
    {
        super.onSizeChanged(w, h, oldw, oldh)

        val bounds = progressDrawable.bounds
        bounds.inset(0, (bounds.height() * 0.45f).toInt())

        progressDrawable.bounds = bounds

        refresh()
    }

    fun getValue(): Float
    {
        if (component == 0)
            return progress.toFloat()

        return progress / max.toFloat()
    }

    fun setColor(color:Int, updateProgress:Boolean = false)
    {
        _color = color
        thumbTintList = ColorStateList.valueOf(color)
        refresh(updateProgress)
    }

    private fun refresh(updateProgress:Boolean = false)
    {
        val currentColorHSB = FloatArray(3)
        Color.colorToHSV(_color, currentColorHSB)

        val v = currentColorHSB[component]

        for (i in 0 until colorCount)
        {
            currentColorHSB[component] = if (component == 0) i.toFloat()
                                         else (i / colorCount.toFloat()) * v

            colors[i] = Color.HSVToColor(currentColorHSB)
        }

        val gradient = LinearGradient(0.0f, 0.0f, width.toFloat(), 0.0f, colors, null, Shader.TileMode.CLAMP)
        val shape = ShapeDrawable(RectShape())
        shape.paint.shader = gradient

        val bounds = progressDrawable.bounds
        progressDrawable = shape
        shape.bounds = bounds

        if (updateProgress)
        {
            var newProgress = round(v).toInt()

            if (component != 0) newProgress = round(v * max).toInt()

            setProgress(newProgress)
        }
    }
}