package com.toolsfordevs.fast.core.ui.ext

import android.view.View
import android.view.ViewGroup
import com.toolsfordevs.fast.core.util.AndroidVersion
import com.toolsfordevs.fast.core.util.AttrUtil


fun View.setPaddingLeft(value: Int) = setPadding(value, paddingTop, paddingRight, paddingBottom)

fun View.setPaddingRight(value: Int) = setPadding(paddingLeft, paddingTop, value, paddingBottom)

fun View.setPaddingTop(value: Int) = setPaddingRelative(paddingStart, value, paddingEnd, paddingBottom)

fun View.setPaddingBottom(value: Int) = setPaddingRelative(paddingStart, paddingTop, paddingEnd, value)

fun View.setPaddingStart(value: Int) = setPaddingRelative(value, paddingTop, paddingEnd, paddingBottom)

fun View.setPaddingEnd(value: Int) = setPaddingRelative(paddingStart, paddingTop, value, paddingBottom)

fun View.setPaddingHorizontal(value: Int) = setPaddingRelative(value, paddingTop, value, paddingBottom)

fun View.setPaddingVertical(value: Int) = setPaddingRelative(paddingStart, value, paddingEnd, value)
fun View.setPadding(value: Int) = setPaddingRelative(value, value, value, value)

fun View.setHeight(value: Int)
{
    val lp = layoutParams
    lp?.let {
        lp.height = value
        layoutParams = lp
    }
}

fun View.setWidth(value: Int)
{
    val lp = layoutParams
    lp?.let {
        lp.width = value
        layoutParams = lp
    }
}

fun View.resize(width: Int, height: Int)
{
    val lp = layoutParams
    lp?.let {
        lp.width = width
        lp.height = height
        layoutParams = lp
    }
}

fun View.rippleForeground()
{
    if (AndroidVersion.isMinMarshmallow()) this.foreground = AttrUtil.getDrawable(context, android.R.attr.selectableItemBackgroundBorderless)
}

fun View.setMarginStart(value: Int)
{
    layoutParams?.let {
        if (it is ViewGroup.MarginLayoutParams)
        {
            it.marginStart = value
            layoutParams = it
        }
    }
}

fun View.setMarginEnd(value: Int)
{
    layoutParams?.let {
        if (it is ViewGroup.MarginLayoutParams)
        {
            it.marginEnd = value
            layoutParams = it
        }
    }
}

fun View.setMarginTop(value: Int)
{
    layoutParams?.let {
        if (it is ViewGroup.MarginLayoutParams)
        {
            it.topMargin = value
            layoutParams = it
        }
    }
}

fun View.setMarginBottom(value: Int)
{
    layoutParams?.let {
        if (it is ViewGroup.MarginLayoutParams)
        {
            it.bottomMargin = value
            layoutParams = it
        }
    }
}