package com.toolsfordevs.fast.core.ui.image

import android.graphics.Paint
import android.graphics.Path
import com.toolsfordevs.fast.core.util.ResUtils


class MaskOptions private constructor(
    val path: Path,
    val borderWidth: Float?,
    val borderColor: Int?,
    val borderCap: Paint.Cap?,
    val borderJoin: Paint.Join?,
    val borderMiter: Float?,
    val rotationAngle: Float?,
    val scaleEnabled:Boolean = true
)
{
    class Builder(val path: Path)
    {
        private var borderWidth: Float? = null
        private var borderColor: Int? = null
        private var borderCap: Paint.Cap? = null
        private var borderJoin: Paint.Join? = null
        private var borderMiter: Float? = null
        private var angle: Float? = null
        private var scaleEnabled:Boolean = true

        fun borderWidth(width: Float): Builder
        {
            borderWidth = width
            return this
        }

        fun borderColor(color: Int): Builder
        {
            borderColor = color
            return this
        }

        fun borderColorRes(colorResId: Int): Builder
        {
            borderColor = ResUtils.getColor(colorResId)
            return this
        }

        fun borderCap(cap: Paint.Cap): Builder
        {
            borderCap = cap
            return this
        }

        fun borderJoin(join: Paint.Join): Builder
        {
            borderJoin = join
            return this
        }

        fun borderMiter(miter: Float): Builder
        {
            borderMiter = miter
            return this
        }

        fun rotate(angleInDegrees:Float): Builder
        {
            this.angle = angleInDegrees
            return this
        }

        fun disableScaling(): Builder
        {
            scaleEnabled = false
            return this
        }

        fun build(): MaskOptions
        {
            return MaskOptions(path, borderWidth, borderColor, borderCap, borderJoin, borderMiter, angle, scaleEnabled)
        }
    }
}