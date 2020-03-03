package com.toolsfordevs.fast.core.ui.image

import android.graphics.Path
import android.graphics.Matrix
import android.graphics.RectF


object PathHelper
{
    fun oval(width: Float, height: Float): Path = Path().apply { addOval(0f, 0f, width, height, Path.Direction.CW) }
    fun circle(size: Float): Path = Path().apply { addOval(0f, 0f, size, size, Path.Direction.CW) }
    fun rect(width: Float, height: Float): Path = Path().apply { addRect(0f, 0f, width, height, Path.Direction.CW) }
    fun square(size: Float): Path = Path().apply { addRect(0f, 0f, size, size, Path.Direction.CW) }
    fun roundedRect(
        width: Float,
        height: Float,
        topLeftRadius: Float,
        topRightRadius: Float,
        bottomLeftRadius: Float,
        bottomRightRadius: Float
    ) = Path().apply {
        addRoundRect(
            0f,
            0f,
            width,
            height,
            floatArrayOf(topLeftRadius, topLeftRadius, topRightRadius, topRightRadius, bottomLeftRadius, bottomLeftRadius, bottomRightRadius, bottomRightRadius),
            Path.Direction.CW
        )
    }

    fun roundedRect(width: Float, height: Float, radius: Float) =
            roundedRect(width, height, radius, radius, radius, radius)

    fun diamond(width: Float, height: Float): Path = Path().apply {
        moveTo(width / 2, 0f)
        lineTo(width, height / 2)
        lineTo(width / 2, height)
        lineTo(0f, height / 2)
        close()
    }

    fun star(size: Float): Path =
            star(size, 0.45f)

    fun star(size: Float, innerRadiusRatio: Float, heads: Int = 5): Path = Path().apply {
        val outerRadius = size / 2f
        val innerRadius = (size * innerRadiusRatio) / 2f

        val center = size / 2f

        var dx = 0f
        var dy = 0f

        for (i in 0..(heads * 2))
        {
            val angle = (2 * Math.PI / heads) * i / 2
            val r: Float = if (i % 2 == 0) innerRadius else outerRadius

            val x = (center + r * Math.sin(angle)).toFloat()
            val y = (center + r * Math.cos(angle)).toFloat()

            if (i == 0)
            {
                moveTo(x, y)
                dx = x
                dy = y
            }
            else
            {
                lineTo(x, y)
                dx = Math.min(x, dx)
                dy = Math.min(y, dy)
            }
        }

        close()
        offset(-dx, -dy)
    }

    fun polygon(size: Float, sides: Int = 5): Path = Path().apply {
        val radius = size / 2f
        val center = size / 2f

        var dx = 0f
        var dy = 0f

        for (i in 0..sides)
        {
            val angle = (2 * Math.PI / sides) * i
            val x = (center + radius * Math.sin(angle)).toFloat()
            val y = (center + radius * Math.cos(angle)).toFloat()

            if (i == 0)
            {
                moveTo(x, y)
                dx = x
                dy = y
            }
            else
            {
                lineTo(x, y)
                dx = Math.min(x, dx)
                dy = Math.min(y, dy)
            }
        }

        close()
        offset(-dx, -dy)
    }

    fun heart(width: Float, height: Float): Path = Path().apply {
        // Starting point
        moveTo(width / 2, height / 5)
        // Upper left path
        cubicTo(5 * width / 14, 0f, 0f, height / 15, width / 28, 2 * height / 5)

        // Lower left path
        cubicTo(width / 14, 2 * height / 3, 3 * width / 7, 5 * height / 6, width / 2, height)

        // Lower right path
        cubicTo(4 * width / 7, 5 * height / 6, 13 * width / 14, 2 * height / 3, 27 * width / 28, 2 * height / 5);
        // Upper right path
        cubicTo(width, height / 15, 9 * width / 14, 0f, width / 2, height / 5)
    }

    /**
     * Rotate a path from the center of the path
     */
    fun rotate(srcPath:Path, angleInDegrees:Float):Path
    {
        val bounds = RectF()
        srcPath.computeBounds(bounds, true)
        return rotateFromPoint(srcPath, angleInDegrees, bounds.centerX(), bounds.centerY())
    }

    /**
     * Rotate a path from a defined point
     */
    fun rotateFromPoint(srcPath:Path, angleInDegrees: Float, rotationX:Float, rotationY:Float):Path
    {
        val bounds = RectF()
        srcPath.computeBounds(bounds, true)

        val outPath = Path(srcPath)
        val matrix = Matrix()
        matrix.postRotate(angleInDegrees, rotationX, rotationY)

        outPath.transform(matrix)
        bounds.transform(matrix)

        outPath.offset(-bounds.left, -bounds.top)

        return outPath
    }
}

/* from Core KTX Rect */
fun RectF.transform(m: Matrix) = apply { m.mapRect(this@transform) }