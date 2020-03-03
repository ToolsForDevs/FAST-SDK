package com.toolsfordevs.fast.core.ext

import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout

/**############################################################################
 *
 * ViewGroup
 *
 ############################################################################*/

/**
 * Create a ViewGroup.LayoutParams with MATCH_PARENT for width and height
 */
fun layoutParamsMM(): ViewGroup.MarginLayoutParams =
    ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT, ViewGroup.MarginLayoutParams.MATCH_PARENT)
/**
 * Create a ViewGroup.MarginLayoutParams with WRAP_CONTENT for width and MATCH_PARENT for height
 */
fun layoutParamsMW(): ViewGroup.MarginLayoutParams =
    ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT)
/**
 * Create a ViewGroup.MarginLayoutParams with MATCH_PARENT for width and WRAP_CONTENT for height
 */
fun layoutParamsWM(): ViewGroup.MarginLayoutParams =
    ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.MATCH_PARENT)
/**
 * Create a ViewGroup.MarginLayoutParams with WRAP_CONTENT for width and height
 */
fun layoutParamsWW(): ViewGroup.MarginLayoutParams =
    ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT)

fun layoutParamsVV(width:Int, height:Int): ViewGroup.MarginLayoutParams =
    ViewGroup.MarginLayoutParams(width, height)

fun layoutParamsMV(height:Int): ViewGroup.MarginLayoutParams =
    ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT, height)

fun layoutParamsWV(height:Int): ViewGroup.MarginLayoutParams =
    ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, height)

fun layoutParamsVM(width:Int): ViewGroup.MarginLayoutParams =
    ViewGroup.MarginLayoutParams(width, ViewGroup.MarginLayoutParams.MATCH_PARENT)

fun layoutParamsVW(width:Int): ViewGroup.MarginLayoutParams =
    ViewGroup.MarginLayoutParams(width, ViewGroup.MarginLayoutParams.WRAP_CONTENT)
/**############################################################################
 *
 * FrameLayout
 *
############################################################################*/

/**
 * Create a FrameLayout.LayoutParams with MATCH_PARENT for width and height
 */
fun frameLayoutParamsMM(): FrameLayout.LayoutParams =
    FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
/**
 * Create a FrameLayout.LayoutParams with WRAP_CONTENT for width and MATCH_PARENT for height
 */
fun frameLayoutParamsMW(): FrameLayout.LayoutParams =
    FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
/**
 * Create a FrameLayout.LayoutParams with MATCH_PARENT for width and MATCH_PARENT for height
 */
fun frameLayoutParamsMV(height:Int): FrameLayout.LayoutParams =
        FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height)
/**
 * Create a FrameLayout.LayoutParams with MATCH_PARENT for width and WRAP_CONTENT for height
 */
fun frameLayoutParamsWM(): FrameLayout.LayoutParams =
    FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT)
/**
 * Create a FrameLayout.LayoutParams with WRAP_CONTENT for width and height
 */
fun frameLayoutParamsWW(): FrameLayout.LayoutParams =
    FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)

fun frameLayoutParamsVV(width:Int, height:Int): FrameLayout.LayoutParams =
        FrameLayout.LayoutParams(width, height)
/**############################################################################
 *
 * LinearLayout
 *
############################################################################*/

/**
 * Create a LinearLayout.LayoutParams with MATCH_PARENT for width and height
 */
fun linearLayoutParamsMM(): LinearLayout.LayoutParams =
    LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
/**
 * Create a LinearLayout.LayoutParams with WRAP_CONTENT for width and MATCH_PARENT for height
 */
fun linearLayoutParamsMW(): LinearLayout.LayoutParams =
    LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
/**
 * Create a LinearLayout.LayoutParams with MATCH_PARENT for width and WRAP_CONTENT for height
 */
fun linearLayoutParamsWM(): LinearLayout.LayoutParams =
    LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT)
/**
 * Create a LinearLayout.LayoutParams with WRAP_CONTENT for width and height
 */
fun linearLayoutParamsWW(): LinearLayout.LayoutParams =
    LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)

fun linearLayoutParamsWeM(weight:Float): LinearLayout.LayoutParams {
    return LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, weight)
}

fun linearLayoutParamsWeW(weight:Float): LinearLayout.LayoutParams {
    return LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, weight)
}

fun linearLayoutParamsWeV(weight:Float, height:Int): LinearLayout.LayoutParams {
    return LinearLayout.LayoutParams(0, height, weight)
}

fun linearLayoutParamsMWe(weight:Float): LinearLayout.LayoutParams {
    return LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, weight)
}

fun linearLayoutParamsWWe(weight:Float): LinearLayout.LayoutParams {
    return LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0, weight)
}

fun linearLayoutParamsMV(height:Int): LinearLayout.LayoutParams =
    LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height)

fun linearLayoutParamsWV(height:Int): LinearLayout.LayoutParams =
    LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, height)

fun linearLayoutParamsVM(width:Int): LinearLayout.LayoutParams =
    LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT)

fun linearLayoutParamsVW(width:Int): LinearLayout.LayoutParams =
    LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT)

fun linearLayoutParamsVV(width:Int, height:Int): LinearLayout.LayoutParams =
    LinearLayout.LayoutParams(width, height)