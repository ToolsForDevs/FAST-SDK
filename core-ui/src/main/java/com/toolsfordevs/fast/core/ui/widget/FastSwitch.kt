package com.toolsfordevs.fast.core.ui.widget

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.widget.Switch
import com.toolsfordevs.fast.core.FastColor
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.core.ui.R
import com.toolsfordevs.fast.modules.androidx.appcompat.widget.SwitchCompat
import com.toolsfordevs.fast.modules.androidx.core.graphics.ColorUtils

@Keep
class FastSwitch : SwitchCompat
{
    constructor(context: Context) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init
    {
        // apply default style
        applyTint(this)
    }

    companion object
    {
        private fun applyTint(view: FastSwitch, accentTint: Boolean = false)
        {
            val color = if (accentTint) FastColor.colorAccent else FastColor.colorPrimary
            val alphaColor = ColorUtils.setAlphaComponent(color, 0x4C) //30%
            val disabledColor = ColorUtils.setAlphaComponent(color, 0x19) //10%

            val thumbColorStateList = ColorStateList(arrayOf(intArrayOf(-android.R.attr.state_enabled), // Disabled
                                                             intArrayOf(android.R.attr.state_enabled,
                                                                        -android.R.attr.state_activated,
                                                                        -android.R.attr.state_checked), // Normal
                                                             intArrayOf(android.R.attr.state_enabled, android.R.attr.state_activated),
                                                             intArrayOf(android.R.attr.state_enabled, android.R.attr.state_checked)
                                                            ),                             // Normal
                                                     intArrayOf(0xFFBDBDBD.toInt(), // Dark
                                                                0xFFF1F1F1.toInt(),
                                                                color,
                                                                color))        // Default


            val trackColorStateList = ColorStateList(
                arrayOf(intArrayOf(-android.R.attr.state_enabled), // Disabled
                        intArrayOf(android.R.attr.state_enabled, -android.R.attr.state_activated, -android.R.attr.state_checked), // Normal
                        intArrayOf(android.R.attr.state_enabled, android.R.attr.state_activated),
                        intArrayOf(android.R.attr.state_enabled, android.R.attr.state_checked)
                       ),                                  // normal
                intArrayOf(disabledColor,
                           0x4C000000,
                           alphaColor,
                           alphaColor)
                                                    )

            view.trackTintList = trackColorStateList
            view.thumbTintList = thumbColorStateList
        }

        fun create(context: Context, accentTint: Boolean = false): FastSwitch
        {
            return FastSwitch(context).apply { applyTint(this, accentTint) }
        }
    }
}