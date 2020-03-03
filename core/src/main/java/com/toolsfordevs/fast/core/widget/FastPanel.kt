package com.toolsfordevs.fast.core.widget

import android.animation.ValueAnimator
import com.toolsfordevs.fast.core.annotation.Keep

@Keep
interface FastPanel
{
    companion object
    {
        const val MODE_ON_TOP_OF = 0
        const val MODE_BELOW = 1
    }

    var mode:Int
    var cancellable:Boolean
    var canceledOnTouchOutside:Boolean

    fun setOnModeChangeCallback(callback: ((mode: Int) -> Unit)?)

    fun dismiss()
    fun onDismiss()
    fun addOnDismissListener(listener: OnDismissListener)
    fun removeOnDismissListener(listener: OnDismissListener)

    fun getEnterTransition(): ValueAnimator?
    fun getExitTransition():ValueAnimator?

    fun onKeyStroke(key:Int)


    interface OnDismissListener
    {
        fun onDismiss(panel: FastPanel)
    }
}