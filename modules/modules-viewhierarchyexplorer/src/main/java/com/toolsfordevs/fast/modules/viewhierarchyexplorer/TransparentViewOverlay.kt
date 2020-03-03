package com.toolsfordevs.fast.modules.viewhierarchyexplorer

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.core.ext.layoutParamsMM

@Keep
open class TransparentViewOverlay(context: Context) : View(context)
{
    var onClickCallback:((x:Int, y:Int)->Unit)? = null

    var isLocked:Boolean = true

    init {
        layoutParams = layoutParamsMM()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean
    {

        if (isLocked && event?.action == MotionEvent.ACTION_DOWN)
        {
            val arr = IntArray(2)

            getLocationOnScreen(arr)

            onClickCallback?.invoke(event.rawX.toInt(), event.rawY.toInt())
            return true
        }

        return false
    }


}