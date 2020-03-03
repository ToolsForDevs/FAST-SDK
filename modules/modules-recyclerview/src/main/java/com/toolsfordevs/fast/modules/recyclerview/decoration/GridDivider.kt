package com.toolsfordevs.fast.modules.recyclerview.decoration

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.ShapeDrawable
import android.view.View
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.GridLayoutManager
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.RecyclerView
import com.toolsfordevs.fast.modules.recyclerview.DataAdapter
import kotlin.reflect.KClass

@Keep
class GridDivider(val spacingPx: Int, private val includeEdge: Boolean, val color:Int = 0x00000000) :
    RecyclerView.ItemDecoration()
{

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State)
    {
        val position = parent.getChildAdapterPosition(view) // item position
        var spanCount = 1
        var spanSize = 1

        if (parent.layoutManager is GridLayoutManager)
        {
            spanCount = (parent.layoutManager as GridLayoutManager).spanCount
            spanSize = (parent.layoutManager as GridLayoutManager).spanSizeLookup.getSpanSize(position)
        }

        if (!shouldDrawDivider(view, position, parent))
            return
        val column = getRealColumn(position, parent) // item column

        if (includeEdge)
        {
            outRect.left = spacingPx - column * spacingPx / spanCount
            outRect.right = (column + 1) * spacingPx / spanCount

            if (getLine(position, parent) == 0)
            { // top edge
                outRect.top = spacingPx
            }
            outRect.bottom = spacingPx // item bottom
        }
        else
        {
            outRect.left = column * spacingPx / spanCount
            outRect.right = if (column + spanSize == spanCount) 0 else spacingPx - (column + 1) * spacingPx / spanCount

            if (getLine(position, parent) > 0)
            {
                outRect.top = spacingPx // item top
            }
        }
    }

    private fun getRealColumn(position: Int, parent: RecyclerView): Int
    {
        val layoutManager = parent.layoutManager

        if (layoutManager is GridLayoutManager)
        {
            val spanSizeLookup = layoutManager.spanSizeLookup
            return spanSizeLookup.getSpanIndex(position, layoutManager.spanCount)
        }

        return 0
    }

    private fun getLine(position: Int, parent: RecyclerView): Int
    {
        val layoutManager = parent.layoutManager

        if (layoutManager is GridLayoutManager)
        {
            val spanSizeLookup = layoutManager.spanSizeLookup
            return spanSizeLookup.getSpanGroupIndex(position, layoutManager.spanCount)
        }

        return position
    }

    private var exemptedViewTypes: ArrayList<KClass<*>> = arrayListOf()
    private var exemptedDataTypes: ArrayList<KClass<*>> = arrayListOf()

    fun excludeViewItemsOfType(type: KClass<*>)
    {
        exemptedViewTypes.add(type)
    }

    fun excludeDataItemsOfType(type: KClass<*>)
    {
        exemptedDataTypes.add(type)
    }

    private fun shouldDrawDivider(view: View): Boolean
    {
        exemptedViewTypes.forEach { if (it.java.isInstance(view)) return false }

        return true
    }

    private fun shouldDrawDivider(view: View, position: Int, parent: RecyclerView): Boolean
    {
        exemptedViewTypes.forEach { if (it.java.isInstance(view)) return false }

        parent.adapter?.let { adapter ->
            if (adapter is DataAdapter)
            {
                val data = adapter.getDataAtPosition(position)

                exemptedDataTypes.forEach { if (it.java.isInstance(data)) return false }
            }
        }

        return true
    }


    private val mBounds = Rect()
    private val drawable = ShapeDrawable().apply {
        intrinsicWidth = spacingPx
        intrinsicHeight = spacingPx
        paint.color = color
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State)
    {
        super.onDrawOver(c, parent, state)
        drawVertical(c, parent)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State)
    {
        if (parent.layoutManager == null || drawable == null)
            return

//        if (orientation == Divider.VERTICAL)
//            drawVertical(c, parent)
//        else
//        drawHorizontal(c, parent)
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView)
    {
        canvas.save()
        val left: Int
        val right: Int

        /*if (parent.clipToPadding)
        {
            left = parent.paddingLeft// + startMargin
            right = parent.width - parent.paddingRight// - endMargin
            canvas.clipRect(
                left, parent.paddingTop, right,
                parent.height - parent.paddingBottom
            )
        }
        else
        {
            left = 0//startMargin
            right = parent.width - 0//endMargin
        }*/

        val childCount = parent.childCount
        for (i in 0 until childCount)
        {
            val child = parent.getChildAt(i)

            parent.getDecoratedBoundsWithMargins(child, drawable.bounds)

            drawable.bounds.bottom = drawable.bounds.top + spacingPx
//            drawable.bounds.top -= spacingPx

            if (shouldDrawDivider(child))
                drawable.draw(canvas)


            val position = parent.getChildAdapterPosition(child) // item position
            val column = getRealColumn(position, parent) // item column
            var spanCount = 1
            var spanSize = 1

            if (parent.layoutManager is GridLayoutManager)
            {
                spanCount = (parent.layoutManager as GridLayoutManager).spanCount
                spanSize = (parent.layoutManager as GridLayoutManager).spanSizeLookup.getSpanSize(position)
            }

            parent.getDecoratedBoundsWithMargins(child, drawable.bounds)
            drawable.bounds.right = drawable.bounds.left + column * spacingPx / spanCount
            if (shouldDrawDivider(child))
                drawable.draw(canvas)

            parent.getDecoratedBoundsWithMargins(child, drawable.bounds)
            drawable.bounds.left = drawable.bounds.right - if (column + spanSize == spanCount) 0 else spacingPx - (column + 1) * spacingPx / spanCount
            if (shouldDrawDivider(child))
                drawable.draw(canvas)

//            if (!shouldDrawDividerForView(child, parent))
//                continue

//            parent.getDecoratedBoundsWithMargins(child, mBounds)
//            val bottom = mBounds.bottom + Math.round(child.translationY)
//            val top = bottom - drawable!!.intrinsicHeight
//            drawable!!.setBounds(left, top, right, bottom)
//            drawable!!.draw(canvas)
        }
        canvas.restore()
    }

    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView)
    {
        canvas.save()
        val top: Int
        val bottom: Int

        if (parent.clipToPadding)
        {
            top = parent.paddingTop
            bottom = parent.height - parent.paddingBottom
            canvas.clipRect(
                parent.paddingLeft, top,
                parent.width - parent.paddingRight, bottom
            )
        }
        else
        {
            top = 0
            bottom = parent.height
        }

        val childCount = parent.childCount
        for (i in 0 until childCount)
        {
            val child = parent.getChildAt(i)

//            if (!shouldDrawDividerForView(child, parent))
//                continue

            parent.layoutManager!!.getDecoratedBoundsWithMargins(child, mBounds)
            val right = mBounds.right + Math.round(child.translationX)
            val left = right - drawable!!.intrinsicWidth
            drawable!!.setBounds(left, top, right, bottom)
            drawable!!.draw(canvas)
        }
        canvas.restore()
    }
}