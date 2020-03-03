package com.toolsfordevs.fast.core.ui.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.core.ui.AspectRatio

@Keep
open class SuperImageView : ImageView
{
    // Nullable type since it's initialized after super constructor
    // which might call setImageDrawable and crash on null reference
    private var maskDelegate: MaskDelegate? = MaskDelegate(this)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    init
    {
        if (drawable != null)
            maskDelegate?.onImageDrawableReset(drawable)
    }

    override fun setImageBitmap(bm: Bitmap)
    {
        super.setImageBitmap(bm)
        maskDelegate?.onImageDrawableReset(drawable)
    }

    override fun setImageDrawable(drawable: Drawable?)
    {
        super.setImageDrawable(drawable)
        maskDelegate?.onImageDrawableReset(getDrawable())
    }

    override fun setImageResource(resId: Int)
    {
        super.setImageResource(resId)
        maskDelegate?.onImageDrawableReset(drawable)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int)
    {
        super.onSizeChanged(w, h, oldw, oldh)
        maskDelegate?.onSizeChanged(w, h)
    }

    override fun onDraw(canvas: Canvas)
    {
        if (!maskDelegate!!.onDraw(canvas))
            super.onDraw(canvas)
    }

    fun setMaskOptions(options: MaskOptions?)
    {
        maskDelegate?.maskOptions = options
    }

    fun hasMask():Boolean
    {
        return maskDelegate?.hasMask ?: false
    }

    // ### Ratio dimensions stuff
    /**
     * Sets the view aspect.
     * @param ratio A ratio of 1 means the view will display as a square.
     * A ratio > 1 means the view will be larger than tall. A ratio < 1 means a view taller than large
     * You can use the <code>AspectRatio</code> constants or a custom value
     * @see com.toolsfordevs.fast.core.view.AspectRatio
     */
    var ratio: Float = AspectRatio.NONE

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int)
    {
//        super.onMeasure(ratioDelegate.getWidthSpec(widthMeasureSpec, heightMeasureSpec),
//            ratioDelegate.getHeightSpec(widthMeasureSpec, heightMeasureSpec))
//
//        setMeasuredDimension(ratioDelegate.getWidthDimension(measuredWidth, measuredHeight),
//            ratioDelegate.getHeightDimension(measuredWidth, measuredHeight))

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (ratio != AspectRatio.NONE)
            setMeasuredDimension(measuredWidth, (measuredWidth / ratio).toInt())
    }
}