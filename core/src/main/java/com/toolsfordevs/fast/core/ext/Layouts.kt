package com.toolsfordevs.fast.core.ext

import android.content.Context
import android.view.ViewGroup.LayoutParams
import android.widget.FrameLayout
import android.widget.LinearLayout


fun hLinearLayout(context: Context, layoutParams: LayoutParams? = null): LinearLayout
{
    return LinearLayout(context).apply {
        orientation = LinearLayout.HORIZONTAL
        layoutParams?.let {
            this.layoutParams = layoutParams
        }
    }
}

fun vLinearLayout(context: Context, layoutParams: LayoutParams? = null): LinearLayout
{
    return LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        layoutParams?.let {
            this.layoutParams = layoutParams
        }
    }
}

fun frameLayout(context: Context, layoutParams: LayoutParams? = null): FrameLayout
{
    return FrameLayout(context).apply {
        layoutParams?.let {
            this.layoutParams = layoutParams
        }
    }
}