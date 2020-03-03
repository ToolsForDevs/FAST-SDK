package com.toolsfordevs.fast.modules.resourcepicker

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader.TileMode
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.widget.SeekBar


internal class CustomSeekBar(context:Context) : SeekBar(context)
{
    init {
//        setMin(0)
        max = 100
        thumbTintList = ColorStateList.valueOf(Color.WHITE)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int)
    {
        super.onSizeChanged(w, h, oldw, oldh)

        val gradient = LinearGradient(
            0.0f, 0.0f, getWidth().toFloat(), 0.0f,
            // intArrayOf(-0x10000, -0x100, -0xff0100, -0xff0001, -0xffff01, -0xff01, -0x10000),
            intArrayOf(0xFFFFFFFF.toInt(), 0xFF000000.toInt()),
            null, TileMode.CLAMP
        )
        val shape = ShapeDrawable(RectShape())
        shape.paint.shader = gradient

        val bounds = getProgressDrawable().getBounds()
        bounds.inset(0, (bounds.height() * 0.45f).toInt())

        setProgressDrawable(shape)
        getProgressDrawable().setBounds(bounds)
    }

    fun getColor():Int
    {
        val scale = progress / max.toFloat()
        val value:Int = (0xFF - 0xFF * scale).toInt()
        return Color.argb(0xFF, value, value, value)
    }
}