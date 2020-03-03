package com.toolsfordevs.fast.plugins.overlay.ui.overlay

import android.app.Activity
import android.graphics.*
import android.view.View
import android.view.ViewGroup
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.modules.viewhierarchyexplorer.ViewHierarchyExplorer
import com.toolsfordevs.fast.plugins.overlay.ui.Prefs
import com.toolsfordevs.fast.plugins.overlay.ui.model.BackgroundStyle
import com.toolsfordevs.fast.plugins.overlay.ui.model.RelativePosition
import com.toolsfordevs.fast.plugins.overlay.ui.model.StrokeStyle
import kotlin.math.abs

internal class ViewBoundsOverlayDelegate() : OverlayDelegate
{
    private lateinit var view:View
    private lateinit var contentView:View
    private lateinit var decorView:View

    private val views = arrayListOf<View>()
    private val cornerRadius = Dimens.dpF(5f)
    private val textCornerRadius = Dimens.dpF(10f)

    private val dashPathEffect = DashPathEffect(floatArrayOf(Dimens.dpF(3), Dimens.dpF(3)), 0f)
    private val dotPathEffect = DashPathEffect(floatArrayOf(Dimens.dpF(1), Dimens.dpF(2)), 0f)

    private val cornerPaint = Paint().apply {
        isAntiAlias = true
        strokeWidth = Dimens.dpF(1)
    }

    private val hierarchyBorderPaint = Paint().apply {
        isAntiAlias = true
        color = 0xFFCCCCCC.toInt()
        style = Paint.Style.STROKE
        strokeWidth = 1f
    }

    private val hierarchyFillPaint = Paint().apply {
        isAntiAlias = true
        color = 0xFFCCCCCC.toInt()
        style = Paint.Style.FILL
    }

    private val boundsBorderPaint = Paint().apply {
        isAntiAlias = true
        color = Color.RED
        style = Paint.Style.STROKE
        strokeWidth = Dimens.dpF(1)
    }

    private val boundsFillPaint = Paint().apply {
        isAntiAlias = true
        color = Color.RED
        style = Paint.Style.FILL
    }

    private val marginFillPaint = Paint().apply {
        isAntiAlias = true
        color = 0x00FF00
        alpha = 0x99
        style = Paint.Style.FILL
    }

    private val marginBorderPaint = Paint().apply {
        isAntiAlias = true
        color = 0x00FF00
        style = Paint.Style.STROKE
        strokeWidth = Dimens.dpF(1)
    }

    private val paddingFillPaint = Paint().apply {
        isAntiAlias = true
        color = 0x0000FF
        style = Paint.Style.FILL
    }

    private val paddingBorderPaint = Paint().apply {
        isAntiAlias = true
        color = 0x0000FF
        style = Paint.Style.STROKE
        strokeWidth = Dimens.dpF(1)
    }

    private val rulerPaint = Paint().apply {
        isAntiAlias = true
        color = 0x0000FF
        style = Paint.Style.STROKE
        strokeWidth = Dimens.dpF(1)
    }

    private val textPaint = Paint().apply {
        isAntiAlias = true
        isSubpixelText = true
        textSize = Dimens.dpF(10f)
        color = Color.RED
        style = Paint.Style.FILL
        strokeWidth = Dimens.dpF(1f)
        typeface = Typeface.MONOSPACE
        flags = Paint.FAKE_BOLD_TEXT_FLAG
    }

    private val scrimPaint = Paint().apply {
        isAntiAlias = true
        color = 0x66000000.toInt()
        style = Paint.Style.FILL
    }

    private val marginDimensionsPaint = Paint().apply {
        isAntiAlias = true
        color = 0x00FF00
        style = Paint.Style.STROKE
        strokeWidth = Dimens.dpF(1)
    }

    private val paddingDimensionsPaint = Paint().apply {
        isAntiAlias = true
        color = 0x00FF00
        style = Paint.Style.STROKE
        strokeWidth = Dimens.dpF(1)
    }

    private val cornerPath = Path()

    private val explorer: ViewHierarchyExplorer by lazy { ViewHierarchyExplorer() }

    private val point = IntArray(2)

    private val dp4 = Dimens.dpF(4)

    fun addOrRemoveView(view:View)
    {
        val index = views.indexOf(view)
        views.clear() // For now we only want one view at a time

        // Remove if this was the one already selected
        if (index == -1)
            views.add(view)
    }

    fun onViewAttached(view:View)
    {
        this.view = view
        contentView = view.rootView.findViewById<View>(android.R.id.content)
        decorView = (view.context as Activity).window.decorView
    }


    override fun draw(canvas: Canvas, width: Int, height: Int)
    {
        val profile = Prefs.currentProfile

        hierarchyFillPaint.color = profile.hierarchyOptions.backgroundColor
        hierarchyBorderPaint.color = profile.hierarchyOptions.borderColor
        boundsFillPaint.color = profile.boundsOptions.backgroundColor
        boundsBorderPaint.color = profile.boundsOptions.borderColor
        marginFillPaint.color = profile.marginOptions.backgroundColor
        marginBorderPaint.color = profile.marginOptions.borderColor
        paddingFillPaint.color = profile.paddingOptions.backgroundColor
        paddingBorderPaint.color = profile.paddingOptions.borderColor
        rulerPaint.color = profile.rulerOptions.color
        scrimPaint.color = profile.hierarchyOptions.scrimColor
        marginDimensionsPaint.color = profile.marginDimensionsOptions.strokeColor
        paddingDimensionsPaint.color = profile.paddingDimensionsOptions.strokeColor

        applyStrokeStyle(hierarchyBorderPaint, profile.hierarchyOptions.strokeStyle)
        applyStrokeStyle(boundsBorderPaint, profile.boundsOptions.strokeStyle)
        applyStrokeStyle(marginBorderPaint, profile.marginOptions.strokeStyle)
        applyStrokeStyle(paddingBorderPaint, profile.paddingOptions.strokeStyle)
        applyStrokeStyle(rulerPaint, profile.rulerOptions.strokeStyle)
        applyStrokeStyle(marginDimensionsPaint, profile.marginDimensionsOptions.strokeStyle)
        applyStrokeStyle(paddingDimensionsPaint, profile.paddingDimensionsOptions.strokeStyle)

        if (profile.hierarchyOptions.enabled)
        {
            val hierarchy = explorer.getHierarchy(decorView)

            for (view in hierarchy)
            {
                if (profile.hierarchyOptions.excludeViewGroups && view is ViewGroup)
                    continue

                if (views.contains(view))
                    continue

                getRect(view)

                applyBackgroundStyle(hierarchyFillPaint, profile.hierarchyOptions.backgroundStyle)

                canvas.drawRect(rect, hierarchyFillPaint)
                canvas.drawRect(rect, hierarchyBorderPaint)

                if (profile.hierarchyOptions.showCorners)
                {
                    cornerPaint.color = profile.hierarchyOptions.borderColor
                    cornerPaint.style = Paint.Style.FILL

                    drawCorners(canvas, cornerPaint)
                }
            }
        }

//        for (view in views)
        if (views.isNotEmpty())
        {
            val view = views.last()
            getRect(view)
            val top = rect.top.toFloat()
            val bottom = rect.bottom.toFloat()
            val left = rect.left.toFloat()
            val right = rect.right.toFloat()

            if (profile.hierarchyOptions.scrim)
            {
                canvas.drawRect(0f, 0f, width.toFloat(), top, scrimPaint)
                canvas.drawRect(0f, bottom, width.toFloat(), height.toFloat(), scrimPaint)
                canvas.drawRect(0f, top, left, bottom, scrimPaint)
                canvas.drawRect(right, top, width.toFloat(), bottom, scrimPaint)
            }

            if (profile.boundsOptions.enabled)
            {
                // draw bounds
                applyBackgroundStyle(boundsFillPaint, profile.boundsOptions.backgroundStyle)

                canvas.drawRect(rect, boundsFillPaint)

                // Border and corners further below, after margin & padding
            }

            // Draw margins
            if (profile.marginOptions.enabled)
            {
                if (view.layoutParams is ViewGroup.MarginLayoutParams)
                {
                    val params = view.layoutParams as ViewGroup.MarginLayoutParams

                    applyBackgroundStyle(marginFillPaint, profile.marginOptions.backgroundStyle)

                    /*if (params.topMargin > 0)
                        canvas.drawRect(left, top - params.topMargin, right, top, marginFillPaint)

                    if (params.bottomMargin > 0)
                        canvas.drawRect(left, bottom, right, bottom + params.bottomMargin, marginFillPaint)

                    if (params.leftMargin > 0)
                        canvas.drawRect(left - params.leftMargin, top, left, bottom, marginFillPaint)

                    if (params.rightMargin > 0)
                        canvas.drawRect(right, top, right + params.rightMargin, bottom, marginFillPaint)

                    // Draw corner squares to fill up the blanks
                    if (params.topMargin > 0 && params.leftMargin > 0)
                        canvas.drawRect(left - params.leftMargin, top - params.topMargin, left, top, marginFillPaint)

                    if (params.topMargin > 0 && params.rightMargin > 0)
                        canvas.drawRect(right, top - params.topMargin, right + params.rightMargin, top, marginFillPaint)

                    if (params.bottomMargin > 0 && params.leftMargin > 0)
                        canvas.drawRect(left - params.leftMargin, bottom, left, bottom + params.bottomMargin, marginFillPaint)

                    if (params.bottomMargin > 0 && params.rightMargin > 0)
                        canvas.drawRect(right, bottom, right + params.rightMargin, bottom + params.bottomMargin, marginFillPaint)*/

                    val offset = marginBorderPaint.strokeWidth / 2f

                    if (params.topMargin > 0)
                        canvas.drawRect(left - params.leftMargin, top - params.topMargin, right + params.rightMargin, top + offset, marginFillPaint)

                    if (params.leftMargin > 0)
                        canvas.drawRect(left - params.leftMargin, top - params.topMargin, left - offset, bottom + params.bottomMargin, marginFillPaint)

                    if (params.bottomMargin > 0)
                        canvas.drawRect(left - params.leftMargin, bottom + offset, right + params.rightMargin, bottom + params.bottomMargin, marginFillPaint)

                    if (params.rightMargin > 0)
                        canvas.drawRect(right + offset, top - params.topMargin, right + params.rightMargin, bottom + params.bottomMargin, marginFillPaint)

                    if (profile.marginOptions.strokeStyle != StrokeStyle.NONE
                        && (params.topMargin > 0 || params.leftMargin > 0 || params.bottomMargin > 0 || params.rightMargin > 0))
                        canvas.drawRect(left - params.leftMargin + offset,
                            top - params.topMargin + offset,
                            right + params.rightMargin - offset,
                            bottom + params.bottomMargin - offset,
                            marginBorderPaint)
                }
            }

            if (profile.paddingOptions.enabled)
            {
                applyBackgroundStyle(paddingFillPaint, profile.paddingOptions.backgroundStyle)
                // Draw padding
                /*if (view.paddingTop > 0)
                    canvas.drawRect(left + view.paddingLeft, top, right - view.paddingRight, top + view.paddingTop, paddingFillPaint)

                if (view.paddingBottom > 0)
                    canvas.drawRect(left + view.paddingLeft, bottom - view.paddingBottom, right - view.paddingRight, bottom, paddingFillPaint)

                if (view.paddingLeft > 0)
                    canvas.drawRect(left, top + view.paddingTop, left + view.paddingLeft, bottom - view.paddingBottom, paddingFillPaint)

                if (view.paddingRight > 0)
                    canvas.drawRect(right - view.paddingRight, top + view.paddingTop, right, bottom - view.paddingBottom, paddingFillPaint)

                // Draw corner squares to fill up the blank
                if (view.paddingTop > 0 && view.paddingLeft > 0)
                    canvas.drawRect(left, top, left + view.paddingLeft, top + view.paddingTop, paddingFillPaint)

                if (view.paddingTop > 0 && view.paddingRight > 0)
                    canvas.drawRect(right - view.paddingRight, top, right, top + view.paddingTop, paddingFillPaint)

                if (view.paddingBottom > 0 && view.paddingLeft > 0)
                    canvas.drawRect(left, bottom - view.paddingBottom, left + view.paddingLeft, bottom, paddingFillPaint)

                if (view.paddingBottom > 0 && view.paddingRight > 0)
                    canvas.drawRect(right - view.paddingRight, bottom - view.paddingBottom, right, bottom, paddingFillPaint)*/

                val offset = paddingBorderPaint.strokeWidth / 2f

                if (view.paddingTop > 0)
                    canvas.drawRect(left + offset, top + offset, right - offset, top + view.paddingTop - offset, paddingFillPaint)

                if (view.paddingLeft > 0)
                    canvas.drawRect(left + offset, top + offset, left + view.paddingLeft - offset, bottom - offset, paddingFillPaint)

                if (view.paddingBottom > 0)
                    canvas.drawRect(left + offset, bottom - view.paddingBottom + offset, right - offset, bottom - offset, paddingFillPaint)

                if (view.paddingRight > 0)
                    canvas.drawRect(right - view.paddingRight + offset, top + offset, right - offset, bottom - offset, paddingFillPaint)

                if (profile.paddingOptions.strokeStyle != StrokeStyle.NONE
                    && (view.paddingTop > 0 || view.paddingLeft > 0  || view.paddingBottom > 0 ||view.paddingRight > 0))
                    canvas.drawRect(left + view.paddingLeft - offset,
                        top + view.paddingTop - offset,
                        right - view.paddingRight + offset,
                        bottom - view.paddingBottom + offset,
                        paddingBorderPaint)


                //drawVerticalText(canvas!!, "$p", left + view.paddingLeft / 2f, (top + bottom) / 2f, textPaint)
            }

            if (profile.rulerOptions.enabled && profile.rulerOptions.strokeStyle != StrokeStyle.NONE)
            {
                val offset = rulerPaint.strokeWidth / 2f
                val top = top + offset
                val bottom = bottom - offset
                val left = left + offset
                val right = right - offset

                canvas.drawLine(0f, top, left, top, rulerPaint)
                canvas.drawLine(left, 0f, left, top, rulerPaint)
                canvas.drawLine(right, 0f, right, top, rulerPaint)
                canvas.drawLine(right, top, width.toFloat(), top, rulerPaint)
                canvas.drawLine(right, bottom, width.toFloat(), bottom, rulerPaint)
                canvas.drawLine(right, bottom, right, height.toFloat(), rulerPaint)
                canvas.drawLine(left, bottom, left, height.toFloat(), rulerPaint)
                canvas.drawLine(0f, bottom, left, bottom, rulerPaint)
            }

            if (profile.boundsOptions.enabled)
            {
                if (profile.boundsOptions.strokeStyle != StrokeStyle.NONE)
                {
                    val offset = boundsBorderPaint.strokeWidth / 2f
                    canvas.drawRect(rect.left + offset, rect.top + offset, rect.right - offset, rect.bottom - offset, boundsBorderPaint)
                }
                if (profile.boundsOptions.showCorners)
                {
                    cornerPaint.color = profile.boundsOptions.borderColor
                    cornerPaint.style = Paint.Style.FILL
                    drawCorners(canvas, cornerPaint)
                }
            }

            if (profile.marginDimensionsOptions.enabled)
            {
                if (view.layoutParams is ViewGroup.MarginLayoutParams)
                {
                    val params = view.layoutParams as ViewGroup.MarginLayoutParams

                    val unit = Dimens.Unit.from(profile.marginDimensionsOptions.unit)
                    val unitLabel = if (profile.marginDimensionsOptions.showUnit) "${unit.label.toLowerCase()}" else ""

                    val textColor = profile.marginDimensionsOptions.textColor
                    val backgroundColor = profile.marginDimensionsOptions.backgroundColor
                    val borderColor = profile.marginDimensionsOptions.borderColor

                    if (params.topMargin > 0)
                    {
                        val p = unit.fromPx(params.topMargin).toString()  + unitLabel
                        val r = drawText(canvas!!, p, left + view.width / 2f, top - params.topMargin / 2f, textColor, backgroundColor, borderColor, profile.marginDimensionsOptions.roundedCorners, true)

                        if (profile.marginDimensionsOptions.strokeStyle != StrokeStyle.NONE && r.height() < params.topMargin)
                        {
                            val paint = marginDimensionsPaint
                            val cx = r.centerX()
                            val sw = paint.strokeWidth / 2
                            canvas.drawLine(cx, r.top, cx, top - params.topMargin, paint)
                            canvas.drawLine(cx, r.bottom, cx, top, paint)

                            if ((params.topMargin - r.height()) / 2 > dp4)
                            {
                                canvas.drawLine(cx - dp4, top - params.topMargin + sw, cx + dp4, top - params.topMargin + sw, paint)
                                canvas.drawLine(cx - dp4, top - sw, cx + dp4, top - sw, paint)
                            }
                        }
                    }

                    if (params.leftMargin > 0)
                    {
                        val p = unit.fromPx(params.leftMargin).toString()  + unitLabel
                        val r = drawVerticalText(canvas!!, p, left - params.leftMargin / 2f, (top + bottom) / 2f, width.toFloat(), textColor, backgroundColor, borderColor, profile.marginDimensionsOptions.roundedCorners)

                        if (profile.marginDimensionsOptions.strokeStyle != StrokeStyle.NONE && r.width() < params.leftMargin)
                        {
                            val paint = marginDimensionsPaint
                            val cy = r.centerY()
                            val sw = paint.strokeWidth / 2
                            canvas.drawLine(r.left, cy, left - params.leftMargin, cy, paint)
                            canvas.drawLine(r.right, cy, left, cy, paint)

                            if ((params.leftMargin - r.width()) / 2 > dp4)
                            {
                                canvas.drawLine(left - params.leftMargin + sw, cy - dp4, left - params.leftMargin + sw, cy + dp4, paint)
                                canvas.drawLine(left - sw, cy - dp4, left - sw, cy + dp4, paint)
                            }
                        }
                    }

                    if (params.bottomMargin > 0)
                    {
                        val p = unit.fromPx(params.bottomMargin).toString()  + unitLabel
                        val r = drawText(canvas!!, p, left + view.width / 2f, bottom + params.bottomMargin / 2f, textColor, backgroundColor, borderColor, profile.marginDimensionsOptions.roundedCorners, true)

                        if (profile.marginDimensionsOptions.strokeStyle != StrokeStyle.NONE && r.height() < params.bottomMargin)
                        {
                            val paint = marginDimensionsPaint
                            val cx = r.centerX()
                            val sw = paint.strokeWidth / 2
                            canvas.drawLine(cx, r.top, cx, bottom, paint)
                            canvas.drawLine(cx, r.bottom, cx, bottom + params.bottomMargin, paint)

                            if ((params.bottomMargin - r.height()) / 2 > dp4)
                            {
                                canvas.drawLine(cx - dp4, bottom + sw, cx + dp4, bottom + sw, paint)
                                canvas.drawLine(cx - dp4, bottom + params.bottomMargin - sw, cx + dp4, bottom + params.bottomMargin - sw, paint)
                            }
                        }
                    }

                    if (params.rightMargin > 0)
                    {
                        val p = unit.fromPx(params.rightMargin).toString()  + unitLabel
                        val r = drawVerticalText(canvas!!, p, right + params.rightMargin / 2f, (top + bottom) / 2f, width.toFloat(), textColor, backgroundColor, borderColor, profile.marginDimensionsOptions.roundedCorners)

                        if (profile.marginDimensionsOptions.strokeStyle != StrokeStyle.NONE && r.width() < params.rightMargin)
                        {
                            val paint = marginDimensionsPaint
                            val cy = r.centerY()
                            val sw = paint.strokeWidth / 2
                            canvas.drawLine(r.left, cy, right, cy, paint)
                            canvas.drawLine(r.right, cy, right + params.rightMargin, cy, paint)

                            if ((params.rightMargin - r.width()) / 2 > dp4)
                            {
                                canvas.drawLine(right + sw, cy - dp4, right + sw, cy + dp4, paint)
                                canvas.drawLine(right + params.rightMargin - sw, cy - dp4, right + params.rightMargin - sw, cy + dp4, paint)
                            }
                        }
                    }
                }
            }

            if (profile.paddingDimensionsOptions.enabled)
            {
                val unit = Dimens.Unit.from(profile.paddingDimensionsOptions.unit)
                val unitLabel = if (profile.paddingDimensionsOptions.showUnit) "${unit.label.toLowerCase()}" else ""

                val textColor = profile.paddingDimensionsOptions.textColor
                val backgroundColor = profile.paddingDimensionsOptions.backgroundColor
                val borderColor = profile.paddingDimensionsOptions.borderColor

                if (view.paddingTop > 0)
                {
                    val p = unit.fromPx(view.paddingTop).toString()  + unitLabel
                    val r = drawText(canvas!!, p, left + view.width / 2f, top + view.paddingTop / 2f, textColor, backgroundColor, borderColor, profile.paddingDimensionsOptions.roundedCorners, true)

                    if (profile.paddingDimensionsOptions.strokeStyle != StrokeStyle.NONE && r.height() < view.paddingTop)
                    {
                        val paint = paddingDimensionsPaint
                        val cx = r.centerX()
                        val sw = paint.strokeWidth / 2
                        canvas.drawLine(cx, r.top, cx, top, paint)
                        canvas.drawLine(cx, r.bottom, cx, top + view.paddingTop, paint)

                        if ((view.paddingTop - r.height()) / 2 > dp4)
                        {
                            canvas.drawLine(cx - dp4, top + sw, cx + dp4, top + sw, paint)
                            canvas.drawLine(cx - dp4, top + view.paddingTop - sw, cx + dp4, top + view.paddingTop - sw, paint)
                        }
                    }
                }

                if (view.paddingLeft > 0)
                {
                    val p = unit.fromPx(view.paddingLeft).toString()  + unitLabel
                    val r = drawVerticalText(canvas!!, p, left + view.paddingLeft / 2f, (top + bottom) / 2f, width.toFloat(), textColor, backgroundColor, borderColor, profile.paddingDimensionsOptions.roundedCorners)

                    if (profile.paddingDimensionsOptions.strokeStyle != StrokeStyle.NONE && r.width() < view.paddingLeft)
                    {
                        val paint = paddingDimensionsPaint
                        val cy = r.centerY()
                        val sw = paint.strokeWidth / 2
                        canvas.drawLine(r.left, cy, left, cy, paint)
                        canvas.drawLine(r.right, cy, left + view.paddingLeft, cy, paint)

                        if ((view.paddingLeft - r.width()) / 2 > dp4)
                        {
                            canvas.drawLine(left + view.paddingLeft - sw, cy - dp4, left + view.paddingLeft - sw, cy + dp4, paint)
                            canvas.drawLine(left + sw, cy - dp4, left + sw, cy + dp4, paint)
                        }
                    }
                }

                if (view.paddingBottom > 0)
                {
                    val p = unit.fromPx(view.paddingBottom).toString()  + unitLabel
                    val r = drawText(canvas!!, p, left + view.width / 2f, bottom - view.paddingBottom / 2f, textColor, backgroundColor, borderColor, profile.paddingDimensionsOptions.roundedCorners, true)

                    if (profile.paddingDimensionsOptions.strokeStyle != StrokeStyle.NONE && r.height() < view.paddingBottom)
                    {
                        val paint = paddingDimensionsPaint
                        val cx = r.centerX()
                        val sw = paint.strokeWidth / 2
                        canvas.drawLine(cx, r.top, cx, bottom - view.paddingBottom, paint)
                        canvas.drawLine(cx, r.bottom, cx, bottom, paint)

                        if ((view.paddingBottom - r.height()) / 2 > dp4)
                        {
                            canvas.drawLine(cx - dp4, bottom - sw, cx + dp4, bottom - sw, paint)
                            canvas.drawLine(cx - dp4, bottom - view.paddingBottom + sw, cx + dp4, bottom - view.paddingBottom + sw, paint)
                        }
                    }
                }

                if (view.paddingRight > 0)
                {
                    val p = unit.fromPx(view.paddingRight).toString()  + unitLabel
                    val r = drawVerticalText(canvas!!, p, right - view.paddingRight / 2f, (top + bottom) / 2f, width.toFloat(), textColor, backgroundColor, borderColor, profile.paddingDimensionsOptions.roundedCorners)

                    if (profile.paddingDimensionsOptions.strokeStyle != StrokeStyle.NONE && r.width() < view.paddingRight)
                    {
                        val paint = paddingDimensionsPaint
                        val cy = r.centerY()
                        val sw = paint.strokeWidth / 2
                        canvas.drawLine(r.left, cy, right - view.paddingRight, cy, paint)
                        canvas.drawLine(r.right, cy, right, cy, paint)

                        if ((view.paddingRight - r.width()) / 2 > dp4)
                        {
                            canvas.drawLine(right - sw, cy - dp4, right - sw, cy + dp4, paint)
                            canvas.drawLine(right - view.paddingRight + sw, cy - dp4, right - view.paddingRight + sw, cy + dp4, paint)
                        }
                    }
                }
            }

            if (profile.sizeOptions.enabled)
            {
                // Draw size
                val unit = Dimens.Unit.from(profile.sizeOptions.unit)
                val w = unit.fromPx(view.width)
                val h = unit.fromPx(view.height)


                var str = "${w}x${h}"

                if (profile.sizeOptions.showUnit)
                    str += "${unit.label.toLowerCase()}"

                val x: Float = left + view.width / 2//(view.width - getTextWidth(textPaint, str)) / 2 - textBgFillingSpace
                val y: Float = top + view.height / 2//(view.height - getTextHeight(textPaint, str)) / 2 - textBgFillingSpace/2

                drawText(canvas!!, str, x, y, profile.sizeOptions.textColor, profile.sizeOptions.backgroundColor, profile.sizeOptions.borderColor, profile.sizeOptions.roundedCorners, true)
            }

            if (profile.positionOptions.enabled)
            {
                // Draw position
                val unit = Dimens.Unit.from(profile.positionOptions.unit)
                var xLabel = unit.fromPx(view.x.toInt())
                var yLabel = unit.fromPx(view.y.toInt())

                if (profile.positionOptions.relativeTo != RelativePosition.PARENT)
                {
                    view.getLocationOnScreen(point)

                    xLabel = unit.fromPx(point[0])
                    yLabel = unit.fromPx(point[1])

                    if (profile.positionOptions.relativeTo == RelativePosition.CONTENT_VIEW)
                    {
                        contentView.getLocationOnScreen(point)

                        xLabel -= unit.fromPx(point[0])
                        yLabel -= unit.fromPx(point[1])
                    }
                }

                var str = "x:$xLabel y:$yLabel"

                if (profile.positionOptions.showUnit)
                    str += " ${unit.label.toLowerCase()}"

                val x: Float = left
                val y: Float = top

                drawText(canvas!!, str, x, y,
                    profile.positionOptions.textColor,
                    profile.positionOptions.backgroundColor,
                    profile.positionOptions.borderColor,
                    profile.positionOptions.roundedCorners)

                //canvas.drawRect(left, top, left + cornerRadius + 1, top + cornerRadius + 1, Paint().apply { style = Paint.Style.FILL; color = Prefs.positionBgdColor })
            }
        }
    }

    private fun drawCorners(canvas:Canvas, paint:Paint)
    {
        val top = rect.top.toFloat()
        val bottom = rect.bottom.toFloat()
        val left = rect.left.toFloat()
        val right = rect.right.toFloat()

        //canvas.drawCircle(left, top, cornerRadius, cornerPaint)
        //canvas.drawCircle(right, top, cornerRadius, cornerPaint)
        //canvas.drawCircle(left, bottom, cornerRadius, cornerPaint)
        //canvas.drawCircle(right, bottom, cornerRadius, cornerPaint)

        cornerPath.reset()
        cornerPath.moveTo(left, top)
        cornerPath.lineTo(left + cornerRadius, top)
        cornerPath.lineTo(left, top + cornerRadius)
        cornerPath.close()
        canvas.drawPath(cornerPath, paint)

        cornerPath.reset()
        cornerPath.moveTo(right - cornerRadius, top)
        cornerPath.lineTo(right, top)
        cornerPath.lineTo(right, top + cornerRadius)
        cornerPath.close()
        canvas.drawPath(cornerPath, paint)

        cornerPath.reset()
        cornerPath.moveTo(left, bottom - cornerRadius)
        cornerPath.lineTo(left, bottom)
        cornerPath.lineTo(left + cornerRadius, bottom)
        cornerPath.close()
        canvas.drawPath(cornerPath, paint)

        cornerPath.reset()
        cornerPath.moveTo(right, bottom - cornerRadius)
        cornerPath.lineTo(right, bottom)
        cornerPath.lineTo(right - cornerRadius, bottom)
        cornerPath.close()
        canvas.drawPath(cornerPath, paint)
    }


    private val rect = Rect()
    private fun getRect(view: View): Rect
    {
        val location = IntArray(2)

        view.getLocationOnScreen(location)

        val thisLocation = IntArray(2)
        this.view.getLocationOnScreen(thisLocation)

        val width = view.width
        val height = view.height

        val left = location[0]
        val right = left + width
        val top = location[1] - thisLocation[1]

        val bottom = top + height

        rect.set(left, top, right, bottom)
        return rect
    }

    private val textBgFillingSpace = Dimens.dpF(4f)
    private val rectF = RectF()

    private fun drawText(canvas: Canvas, text: String, x: Float, y: Float, textColor:Int, bgdColor:Int, borderColor:Int, roundedCorners:Boolean = false, centered:Boolean = false):RectF
    {
        var left = x + cornerPaint.strokeWidth / 2f
        var top = y + cornerPaint.strokeWidth / 2f

        val tw = getTextWidth(textPaint, text)
        val th = textPaint.textSize//getTextHeight(textPaint, text)

        if (centered)
        {
            left -= tw / 2f + textBgFillingSpace
            top -= th / 2f + textBgFillingSpace/2f
        }

        var right = left + tw + textBgFillingSpace * 2 - cornerPaint.strokeWidth / 2f
        var bottom = top + th + textBgFillingSpace - cornerPaint.strokeWidth / 2f


        // ensure text in screen bound
        if (left < 0)
        {
            right -= left
            left = 0f
        }
        if (top < 0)
        {
            bottom -= top
            top = 0f
        }
        if (bottom > canvas.height)
        {
            val diff = top - bottom
            bottom = canvas.height.toFloat()
            top = bottom + diff
        }
        if (right > canvas.width)
        {
            val diff = left - right
            right = canvas.width.toFloat()
            left = right + diff
        }
        rectF.set(left, top, right, bottom)

        cornerPaint.color = bgdColor
        cornerPaint.style = Paint.Style.FILL

        if (roundedCorners)
            canvas.drawRoundRect(rectF, textCornerRadius, textCornerRadius, cornerPaint)
        else
            canvas.drawRect(rectF, cornerPaint)

        cornerPaint.color = borderColor
        cornerPaint.style = Paint.Style.STROKE

        if (roundedCorners)
            canvas.drawRoundRect(rectF, textCornerRadius, textCornerRadius, cornerPaint)
        else
            canvas.drawRect(rectF, cornerPaint)


        textPaint.color = textColor

        canvas.drawText(text, left + textBgFillingSpace, top + textPaint.textSize , textPaint)

        return rectF
    }

    private fun drawVerticalText(canvas: Canvas, text:String, x:Float, y:Float, width:Float, textColor:Int, bgdColor:Int, borderColor:Int, roundedCorners:Boolean = false):RectF
    {
        val charHeight = abs(textPaint.ascent()) + textPaint.descent()
        val spacing = Dimens.dpF(3)
        val th = charHeight * text.length + spacing

        val top = y - th / 2


        val tw = getTextWidth(textPaint, "x")
        val bw = tw + 2 * spacing
        var dx = x - bw / 2f// font is monospace, can use any char we want

        if (dx < 0f)
            dx = 0f

        if (dx > width)
            dx = width - bw

        val r = RectF(dx, top, dx + bw, y + th / 2)
        cornerPaint.color = bgdColor
        cornerPaint.style = Paint.Style.FILL
        if (roundedCorners)
            canvas.drawRoundRect(r, cornerRadius, cornerRadius, cornerPaint)
        else
            canvas.drawRect(r, cornerPaint)

        cornerPaint.color = borderColor
        cornerPaint.style = Paint.Style.STROKE
        if (roundedCorners)
            canvas.drawRoundRect(r, cornerRadius, cornerRadius, cornerPaint)
        else
            canvas.drawRect(r, cornerPaint)

        textPaint.color = textColor

        for (i in 0 until text.length)
        {
            val dy = if (i == 0) top + abs(textPaint.ascent()) else top + charHeight * (i+1) - textPaint.descent()
            canvas.drawText(text[i].toString(), dx + spacing, dy + spacing/2, textPaint)
        }

        return r
    }

    private val textRect = Rect()

    private fun getTextHeight(paint: Paint, text: String): Float
    {
        paint.getTextBounds(text, 0, text.length, textRect)
        return textRect.height().toFloat()
    }

    private fun getVerticalTextHeight(paint:Paint, text:CharSequence):Float
    {
        var h = 0f

        for (c in text)
        {
            h += getTextHeight(paint, c.toString())
            //Log.d("Overlay", "c $c $h")
        }

        return h
    }

    private fun getTextWidth(paint: Paint, text: String): Float
    {
        return paint.measureText(text)
    }

    private fun applyStrokeStyle(paint:Paint, strokeStyle:Int)
    {
        paint.pathEffect = when (strokeStyle)
        {
            StrokeStyle.DASHED -> dashPathEffect
            StrokeStyle.DOTS   -> dotPathEffect
            else               -> null
        }
    }

    private fun applyBackgroundStyle(paint:Paint, backgroundStyle:Int)
    {
        paint.shader = null

        if (backgroundStyle == BackgroundStyle.STRIPS)
        {
            paint.shader = LinearGradient(0f, 0f,
                Dimens.dpF(1) * 1.5f, Dimens.dpF(1) * 1.5f,
                intArrayOf(Color.TRANSPARENT, Color.TRANSPARENT, paint.color, paint.color),
                floatArrayOf(0f, .5f, .5f, 1f),
                Shader.TileMode.REPEAT)
        }
        else if (backgroundStyle == BackgroundStyle.NONE)
        {
            paint.color = Color.TRANSPARENT
        }
    }
}