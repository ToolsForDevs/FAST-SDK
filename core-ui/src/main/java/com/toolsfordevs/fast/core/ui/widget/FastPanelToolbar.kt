package com.toolsfordevs.fast.core.ui.widget

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageButton
import android.widget.LinearLayout
import com.toolsfordevs.fast.core.FastColor
import com.toolsfordevs.fast.core.ext.*
import com.toolsfordevs.fast.core.ui.R
import com.toolsfordevs.fast.core.widget.FastPanel
import com.toolsfordevs.fast.core.widget.FastPanelDelegate

class FastPanelToolbar(context: Context) : LinearLayout(context), FastPanel by FastPanelDelegate()
{
    val handle = View(context)
    val buttonBar:LinearLayout = hLinearLayout(context).apply { gravity = Gravity.CENTER_VERTICAL }
    val buttonLayout:LinearLayout = hLinearLayout(context)

    init
    {
        orientation = VERTICAL
        setBackgroundColor(FastColor.colorPrimary)

        handle.setBackgroundResource(R.drawable.fast_core_panel_toolbar_handle)
        addView(handle, linearLayoutParamsVV(24.dp, 2.dp).apply {
            gravity = Gravity.CENTER_HORIZONTAL
            topMargin = 4.dp
            bottomMargin = 4.dp
        })

        buttonLayout.addView(buttonBar, layoutParamsWV(56.dp).apply {
            gravity = Gravity.CENTER_VERTICAL
        })

        addView(buttonLayout, layoutParamsMV(56.dp))
    }

    fun createButton(drawableRes:Int, idRes:Int = View.NO_ID, addAsChild:Boolean = true): ImageButton
    {
        val button = ImageButton(context)
        button.layoutParams = LayoutParams(56.dp, 56.dp)
        button.setBackgroundResource(R.drawable.fast_selectable_item_background)
        button.setImageResource(drawableRes)
        button.id = idRes

        if (addAsChild)
        {
            buttonBar.addView(button)
            refreshButtonBar()
        }

        return button
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int)
    {
        super.onSizeChanged(w, h, oldw, oldh)
        refreshButtonBar()
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

    private val arrowButton:ImageButton = ImageButton(context).apply {
        // setImageResource(R.drawable.ic_arrow)
    }

    private fun refreshButtonBar()
    {
        // ToDo check that all buttons can be contained in the button bar
        // if not, add an arrow button (">") that will scroll horizontally like
        // the OneNote app

        if (buttonBar.childCount * 56.dp > buttonBar.width)
        {
            /*buttonLayout.addView(arrowButton) // check that it isn’t already added
            arrowButton.setOnClickListener {
                 scroll button bar
                 animate arrow icon 180°
            }*/
        }

    }
}