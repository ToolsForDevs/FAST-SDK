package com.toolsfordevs.fast.modules.resourcepicker

import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.SeekBar
import com.toolsfordevs.fast.core.FastColor
import com.toolsfordevs.fast.core.util.Dimens


internal class BackgroundColorDelegate(val button: View, val callback:(color: Int) -> Unit)
{
    private var popup:PopupWindow? = null

    private var dismissTime = 0L

    init
    {
        button.setOnClickListener { button ->

            if (popup == null)
                makePopup()

            if (popup!!.isShowing)
                popup!!.dismiss()
            // If we click a second time on the button to dismiss the popup
            // the popup will first dismiss (because we touched outside the popup
            // then the button click listener will fire
            // so we put a timing safeguard to prevent re displaying the popup when
            // we in fact want to hide the popup on the second button click
            else if (System.currentTimeMillis() - dismissTime > 100L)
                popup!!.showAsDropDown(button)
        }
    }

    private fun makePopup()
    {
        val colorSeekBar = CustomSeekBar(button.context)
        val params = FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.gravity = Gravity.CENTER_VERTICAL
        colorSeekBar.layoutParams = params

        val frameLayout = FrameLayout(button.context)
        frameLayout.addView(colorSeekBar)

        colorSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean)
            {
                val color = colorSeekBar.getColor()
                colorSeekBar.thumbTintList = ColorStateList.valueOf(color)
                callback(color)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?)
            {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?)
            {

            }

        })

        popup = PopupWindow(frameLayout, ViewGroup.LayoutParams.MATCH_PARENT, Dimens.dp(56))
        with (popup!!)
        {
            isOutsideTouchable = true
            setBackgroundDrawable(ColorDrawable(FastColor.colorAccent))
            setOnDismissListener {
                dismissTime = System.currentTimeMillis()
            }
        }
    }
}