package com.toolsfordevs.fast.core.ui.image

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.graphics.Bitmap


class MaskDelegate(view: View)
{
    private var viewWidth: Int = view.width
    private var viewHeight: Int = view.height

    private var borderWidth = 0f

    var maskOptions: MaskOptions? = null
        set(value)
        {
            value?.let {
                shapePath = Path(it.path)

                it.rotationAngle?.let { angle -> shapePath = PathHelper.rotate(shapePath!!, angle) }

                val rect = RectF()
                shapePath!!.computeBounds(rect, false)
                shapePathWidth = rect.width()
                shapePathHeight = rect.height()

                it.borderColor?.let { borderPaint.color = it }
                it.borderJoin?.let { borderPaint.strokeJoin = it }
                it.borderCap?.let { borderPaint.strokeCap = it }
                it.borderMiter?.let { borderPaint.strokeMiter = it }
                it.borderWidth?.let {
                    borderPaint.strokeWidth = it
                    borderWidth = it
                }
            }

            if (value == null)
                shapePath = null

            field = value
        }

    private val borderPaint: Paint = Paint()
    private val imagePaint: Paint = Paint()
    private var shader: BitmapShader? = null
    private var drawable: Drawable? = null
    private val matrix = Matrix()

    private val path = Path()
    private val borderPath = Path()
    private val pathMatrix = Matrix()

    private var shapePathWidth: Float = 0f
    private var shapePathHeight: Float = 0f

    private var shapePath: Path? = null

    val hasMask: Boolean
        get() = shapePath != null

    private val bitmap: Bitmap?
        get()
        {
            var bitmap: Bitmap? = null
            if (drawable != null)
            {
                if (drawable is BitmapDrawable)
                {
                    bitmap = (drawable as BitmapDrawable).bitmap
                }
                else
                {
                    if (drawable!!.intrinsicWidth <= 0 || drawable!!.intrinsicHeight <= 0)
                        bitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888) // Use view's dimensions
                    else
                        bitmap = Bitmap.createBitmap(drawable!!.intrinsicWidth, drawable!!.intrinsicHeight, Bitmap.Config.ARGB_8888)

                    val canvas = Canvas(bitmap!!)
                    drawable!!.setBounds(0, 0, canvas.width, canvas.height)
                    drawable!!.draw(canvas)
                    return bitmap
                }
            }

            return bitmap
        }

    init
    {
        borderPaint.style = Paint.Style.STROKE
        borderPaint.isAntiAlias = true

        imagePaint.isAntiAlias = true
    }

    fun onDraw(canvas: Canvas): Boolean
    {
        if (shapePath != null)
        {
            if (shader == null)
                createShader()

            if (shader != null && viewWidth > 0 && viewHeight > 0)
            {
                canvas.save()
                canvas.drawPath(borderPath, borderPaint)
                canvas.concat(matrix)
                canvas.drawPath(path, imagePaint)
                canvas.restore()
                return true
            }
        }

        return false
    }

    private fun createShader()
    {
        val bitmap = calculateDrawableSizes()
        if (bitmap != null && bitmap.width > 0 && bitmap.height > 0)
        {
            shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            imagePaint.shader = shader
        }
    }

    private fun calculateDrawableSizes(): Bitmap?
    {
        val bitmap = this.bitmap
        if (bitmap != null)
        {
            val bitmapWidth = bitmap.width
            val bitmapHeight = bitmap.height

            if (bitmapWidth > 0 && bitmapHeight > 0)
            {
                val width = Math.round(viewWidth - 2f * borderWidth).toFloat()
                val height = Math.round(viewHeight - 2f * borderWidth).toFloat()

                var scale: Float
                var translateX = 0f
                var translateY = 0f

                if (bitmapWidth * height > width * bitmapHeight)
                {
                    scale = height / bitmapHeight
                    translateX = Math.round((width / scale - bitmapWidth) / 2f).toFloat()
                }
                else
                {
                    scale = width / bitmapWidth.toFloat()
                    translateY = Math.round((height / scale - bitmapHeight) / 2f).toFloat()
                }

                matrix.setScale(scale, scale)
                matrix.preTranslate(translateX, translateY)
                matrix.postTranslate(borderWidth, borderWidth)

                path.reset()
                borderPath.reset()
                pathMatrix.reset()

                if (maskOptions!!.scaleEnabled)
                {
                    scale = Math.min(width / (shapePathWidth - borderWidth), height / (shapePathHeight - borderWidth))
                    translateX = Math.round((width - shapePathWidth * scale) * 0.5f).toFloat()
                    translateY = Math.round((height - shapePathHeight * scale) * 0.5f).toFloat()
                    pathMatrix.setScale(scale, scale)
                    pathMatrix.postTranslate(translateX, translateY)
                }
                shapePath!!.transform(pathMatrix, path)

                path.offset(borderWidth, borderWidth)

                if (borderWidth > 0f)
                {
                    pathMatrix.reset()

                    if (maskOptions!!.scaleEnabled)
                    {
                        val newWidth: Float = viewWidth - borderWidth
                        val newHeight: Float = viewHeight - borderWidth
                        val delta = borderWidth / 2f

                        scale = Math.min(newWidth / shapePathWidth, newHeight / shapePathHeight)
                        translateX = Math.round((newWidth - shapePathWidth * scale) * 0.5f + delta).toFloat()
                        translateY = Math.round((newHeight - shapePathHeight * scale) * 0.5f + delta).toFloat()
                        pathMatrix.setScale(scale, scale)
                        pathMatrix.postTranslate(translateX, translateY)
                    }

                    shapePath!!.transform(pathMatrix, borderPath)
                }

                pathMatrix.reset()
                matrix.invert(pathMatrix)
                path.transform(pathMatrix)

                return bitmap
            }
        }

        path.reset()
        borderPath.reset()
        return null
    }

    fun onSizeChanged(width: Int, height: Int)
    {
        if (viewWidth == width && viewHeight == height)
            return

        viewWidth = width
        viewHeight = height

        if (shader != null)
            calculateDrawableSizes()
    }

    fun onImageDrawableReset(drawable: Drawable?)
    {
        this.drawable = drawable
        shader = null
        imagePaint.shader = null
    }
}