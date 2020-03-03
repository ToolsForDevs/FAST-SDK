package com.toolsfordevs.fast.modules.resourcepicker.color.components

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.widget.SeekBar

internal class RGBSeekBar(context: Context, val component: Int, backgroundColor: Int) : SeekBar(context)
{
    private var _color = 0xFFFF0000.toInt()
    private val gradient = LinearGradient(0.0f, 0.0f, 100f, 0.0f, intArrayOf(backgroundColor, backgroundColor), null, Shader.TileMode.CLAMP)

    init
    {
        max = 255
        thumbTintList = ColorStateList.valueOf(backgroundColor)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int)
    {
        super.onSizeChanged(w, h, oldw, oldh)

        val bounds = progressDrawable.bounds
        bounds.inset(0, (bounds.height() * 0.45f).toInt())

        progressDrawable = ShapeDrawable(RectShape()).apply { paint.shader = gradient }
        progressDrawable.bounds = bounds
    }

    fun getValue(): Float
    {
        return progress / max.toFloat()
    }

    fun setColor(color: Int, updateProgress: Boolean = false)
    {
        _color = color
        refresh(updateProgress)
    }

    private fun refresh(updateProgress: Boolean = false)
    {
        if (updateProgress)
        {
            val progress = when (component)
            {
                0    -> Color.red(_color)
                1    -> Color.green(_color)
                2    -> Color.blue(_color)
                else -> Color.alpha(_color)
            }

            setProgress(progress)
        }
    }
}