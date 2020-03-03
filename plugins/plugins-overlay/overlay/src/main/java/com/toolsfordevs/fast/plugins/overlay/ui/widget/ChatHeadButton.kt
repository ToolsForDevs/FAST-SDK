package com.toolsfordevs.fast.plugins.overlay.ui.widget

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Point
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnticipateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.ImageButton
import com.toolsfordevs.fast.core.FastColor
import com.toolsfordevs.fast.core.ext.layoutParamsVV
import com.toolsfordevs.fast.core.util.AndroidVersion
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.core.util.ResUtils
import com.toolsfordevs.fast.core.widget.FastPanel
import com.toolsfordevs.fast.core.widget.FastPanelDelegate
import com.toolsfordevs.fast.core.ui.ext.animateX
import com.toolsfordevs.fast.plugins.overlay.R
import kotlin.math.abs


internal class ChatHeadButton(context: Context) : ImageButton(context), FastPanel by FastPanelDelegate()
{
    private var startX: Int = 0
    private var startY: Int = 0
    private var parentSize: Point = Point()


    init
    {
        imageTintList = ColorStateList.valueOf(Color.WHITE)
        setBackgroundResource(R.drawable.fast_resourcepicker_white_circle)

        if (AndroidVersion.isMinMarshmallow()) foreground = ResUtils.getDrawable(R.drawable.fast_selectable_item_background)

        backgroundTintList = ColorStateList.valueOf(FastColor.colorPrimary)
        elevation = Dimens.dpF(2)

        val dp56 = Dimens.dp(56)
        layoutParams = layoutParamsVV(dp56, dp56)

        var startTime: Long = 0
        var endTime: Long = 0

        setOnTouchListener { v, event ->

            when (event.action)
            {
                MotionEvent.ACTION_DOWN ->
                {
                    startTime = System.currentTimeMillis()

                    startX = event.rawX.toInt()
                    startY = event.rawY.toInt()

                    true
                }
                MotionEvent.ACTION_UP   ->
                {

                    //Get the difference between initial coordinate and current coordinate
                    val dx = event.rawX.toInt() - startX
                    val dy = event.rawY.toInt() - startY

                    //The check for x_diff <5 && y_diff< 5 because sometime elements moves a little while clicking.
                    //So that is click event.
                    if (abs(dx) < 5 && abs(dy) < 5)
                    {
                        endTime = System.currentTimeMillis()

                        //Also check the difference between start time and end time should be less than 300ms
                        if (endTime - startTime < 300)
                        {
                            clearAnimation()
                            performClick()
                        }

                    }
                    else
                    {
                        //reset position if user drags the floating view
                        anchorToSide()
                    }

                    true
                }
                MotionEvent.ACTION_MOVE ->
                {
                    val parent = parent as View
                    val p = IntArray(2)
                    parent.getLocationOnScreen(p)

                    val dx = abs(event.rawX - startX)
                    val dy = abs(event.rawY - startY)

                    // Prevent slight move when all we do is a fast click
                    if (dx > 10 && dy > 10)
                    {
                        x = event.rawX - p[0] - width / 2
                        y = event.rawY - p[1] - height / 2
                    }

                    true
                }
                else                    -> false
            }
        }
    }

    override fun onAttachedToWindow()
    {
        super.onAttachedToWindow()

        post {
            val parent = parent as View
            parentSize = Point(parent.width, parent.height)

            if (x == 0f && y == 0f)
            {
                val dp16 = Dimens.dp(16)
                val size = Dimens.dp(56)

                x = (parent.width - dp16 - size).toFloat()
                y = (parent.height - dp16 - size).toFloat()
            }
        }
    }

    /*  Reset position of Floating Widget view on dragging  */
    private fun anchorToSide()
    {
        clearAnimation()

        if (x <= parentSize.x / 2) animateX(Dimens.dpF(16), 300, OvershootInterpolator())
        else animateX((parent as View).width - Dimens.dpF(16) - width, 300, OvershootInterpolator())
    }

    override fun onDetachedFromWindow()
    {
        super.onDetachedFromWindow()
        onDismiss()
    }

    override fun getEnterTransition(): ValueAnimator?
    {
        return ObjectAnimator.ofFloat(this, "scale", 0f, 1f).also { it.interpolator = OvershootInterpolator() }
    }

    override fun getExitTransition(): ValueAnimator?
    {
        return ObjectAnimator.ofFloat(this, "scale", 1f, 0f).also { it.interpolator = AnticipateInterpolator() }
    }

    var scale: Float
        get() = scaleX
        set(value)
        {
            scaleX = value
            scaleY = value
        }
}