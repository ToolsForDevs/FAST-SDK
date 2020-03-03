package com.toolsfordevs.fast.core.widget

import android.animation.ValueAnimator
import com.toolsfordevs.fast.core.FastManager
import com.toolsfordevs.fast.core.annotation.Keep

@Keep
class FastPanelDelegate : FastPanel
{
    private var modeChangeCallback:((mode: Int) -> Unit)? = null

    override var mode: Int = FastPanel.MODE_ON_TOP_OF
        set(value)
        {
            field = value
            modeChangeCallback?.invoke(value)
        }

    override var cancellable:Boolean = true
    override var canceledOnTouchOutside:Boolean = true

    private val dismissListeners = arrayListOf<FastPanel.OnDismissListener>()

    override fun setOnModeChangeCallback(callback: ((mode: Int) -> Unit)?)
    {
        modeChangeCallback = callback
    }

    override fun dismiss()
    {
       FastManager.onBackPressed()
    }

    override fun onDismiss()
    {
        for (listener in dismissListeners)
            listener.onDismiss(this)
    }

    override fun addOnDismissListener(listener: FastPanel.OnDismissListener)
    {
        dismissListeners.add(listener)
    }

    override fun removeOnDismissListener(listener: FastPanel.OnDismissListener)
    {
        dismissListeners.remove(listener)
    }

    override fun getEnterTransition(): ValueAnimator?
    {
        return null
    }

    override fun getExitTransition(): ValueAnimator?
    {
        return null
    }

    override fun onKeyStroke(key: Int)
    {

    }
}