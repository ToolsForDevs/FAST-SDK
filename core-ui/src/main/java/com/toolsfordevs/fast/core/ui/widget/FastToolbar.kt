package com.toolsfordevs.fast.core.ui.widget

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import com.toolsfordevs.fast.core.FastColor
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.core.ui.R
import com.toolsfordevs.fast.core.widget.FastPanel
import com.toolsfordevs.fast.core.widget.FastPanelDelegate

@Keep
open class FastToolbar(context: Context) : LinearLayout(context), FastPanel by FastPanelDelegate()
{
    private val dp56 = Dimens.dp(56)
    override var mode: Int = FastPanel.MODE_ON_TOP_OF

    init
    {
        orientation = HORIZONTAL
        setBackgroundColor(FastColor.colorPrimary)
        gravity = Gravity.END or Gravity.CENTER_VERTICAL
        layoutParams = FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply { gravity = Gravity.BOTTOM }
        minimumHeight = dp56
    }

    fun createButton(drawableRes:Int, idRes:Int = View.NO_ID, addAsChild:Boolean = true): ImageButton
    {
        val button = ImageButton(context)
        button.layoutParams = LayoutParams(dp56, dp56)
        button.setBackgroundResource(R.drawable.fast_selectable_item_background)
        button.setImageResource(drawableRes)
        button.id = idRes

        if (addAsChild)
            addView(button)

        return button
    }

    override fun getEnterTransition(): ValueAnimator?
    {
        return ObjectAnimator.ofFloat(this, "translationY", height.toFloat(), 0f).apply {
            interpolator = DecelerateInterpolator(2f)
        }
    }

    override fun getExitTransition(): ValueAnimator?
    {
        return ObjectAnimator.ofFloat(this, "translationY", translationY, height.toFloat()). apply {
            interpolator = DecelerateInterpolator(2f)
        }
    }

    override fun onDetachedFromWindow()
    {
        super.onDetachedFromWindow()
        onDismiss()
    }
}