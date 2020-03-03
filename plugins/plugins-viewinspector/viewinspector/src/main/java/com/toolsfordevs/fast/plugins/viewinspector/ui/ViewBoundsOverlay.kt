package com.toolsfordevs.fast.plugins.viewinspector.ui

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.modules.viewhierarchyexplorer.ViewHierarchyExplorer

internal class ViewBoundsOverlay(context: Context) : View(context)
{
    private val cornerRadius = Dimens.dpF(5f)

    private val cornerPaint = Paint().apply {
        isAntiAlias = true
        strokeWidth = Dimens.dpF(1)
        color = 0xFFCCCCCC.toInt()
    }

    private val hierarchyBorderPaint = Paint().apply {
        isAntiAlias = true
        color = 0xFFCCCCCC.toInt()
        style = Paint.Style.STROKE
        strokeWidth = 1f
    }


    private val cornerPath = Path()

    private val explorer: ViewHierarchyExplorer by lazy { ViewHierarchyExplorer() }

    private val listener = ViewTreeObserver.OnPreDrawListener {
        // Called on every frame and there's nothing anormal
        // That's just how Android works
        invalidate()
        true
    }

    init
    {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun onAttachedToWindow()
    {
        super.onAttachedToWindow()
        viewTreeObserver.addOnPreDrawListener(listener)
    }

    override fun onDetachedFromWindow()
    {
        super.onDetachedFromWindow()
        viewTreeObserver.removeOnPreDrawListener(listener)
    }

    override fun onDraw(canvas: Canvas?)
    {
        super.onDraw(canvas)


        val root = (context as Activity).window.decorView
        val hierarchy = explorer.getHierarchy(root)

        for (view in hierarchy)
        {
            //if (profile.hierarchyOptions.excludeViewGroups && view is ViewGroup)
            //  continue

            getRect(view)

            canvas?.drawRect(rect, hierarchyBorderPaint)

            drawCorners(canvas, cornerPaint)
        }
    }

    private fun drawCorners(canvas: Canvas?, paint: Paint)
    {
        val top = rect.top.toFloat()
        val bottom = rect.bottom.toFloat()
        val left = rect.left.toFloat()
        val right = rect.right.toFloat()

        //canvas?.drawCircle(left, top, cornerRadius, cornerPaint)
        //canvas?.drawCircle(right, top, cornerRadius, cornerPaint)
        //canvas?.drawCircle(left, bottom, cornerRadius, cornerPaint)
        //canvas?.drawCircle(right, bottom, cornerRadius, cornerPaint)

        cornerPath.reset()
        cornerPath.moveTo(left, top)
        cornerPath.lineTo(left + cornerRadius, top)
        cornerPath.lineTo(left, top + cornerRadius)
        cornerPath.close()
        canvas?.drawPath(cornerPath, paint)

        cornerPath.reset()
        cornerPath.moveTo(right - cornerRadius, top)
        cornerPath.lineTo(right, top)
        cornerPath.lineTo(right, top + cornerRadius)
        cornerPath.close()
        canvas?.drawPath(cornerPath, paint)

        cornerPath.reset()
        cornerPath.moveTo(left, bottom - cornerRadius)
        cornerPath.lineTo(left, bottom)
        cornerPath.lineTo(left + cornerRadius, bottom)
        cornerPath.close()
        canvas?.drawPath(cornerPath, paint)

        cornerPath.reset()
        cornerPath.moveTo(right, bottom - cornerRadius)
        cornerPath.lineTo(right, bottom)
        cornerPath.lineTo(right - cornerRadius, bottom)
        cornerPath.close()
        canvas?.drawPath(cornerPath, paint)
    }


    private val rect = Rect()
    private fun getRect(view: View): Rect
    {
        val location = IntArray(2)

        view.getLocationOnScreen(location)

        val thisLocation = IntArray(2)
        getLocationOnScreen(thisLocation)

        val width = view.width
        val height = view.height

        val left = location[0]
        val right = left + width
        val top = location[1] - thisLocation[1]

        val bottom = top + height

        rect.set(left, top, right, bottom)
        return rect
    }
}