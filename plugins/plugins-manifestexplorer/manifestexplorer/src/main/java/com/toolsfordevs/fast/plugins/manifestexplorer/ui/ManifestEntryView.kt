package com.toolsfordevs.fast.plugins.manifestexplorer.ui

import android.content.Context
import android.view.Gravity
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.toolsfordevs.fast.core.ext.dp
import com.toolsfordevs.fast.core.ext.layoutParamsMW
import com.toolsfordevs.fast.core.ext.linearLayoutParamsVV
import com.toolsfordevs.fast.core.ext.linearLayoutParamsWeW
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.core.ui.ext.setPaddingStart
import com.toolsfordevs.fast.core.ui.ext.setTint
import com.toolsfordevs.fast.plugins.manifestexplorer.R

internal class ManifestEntryView(context:Context) : LinearLayout(context)
{
    val name: TextView = TextView(context).apply {
        textSize = 16f
        setTextColor(MaterialColor.BLACK_87)
    }

    val playButton = ImageButton(context).apply {
        setBackgroundResource(R.drawable.fast_selectable_item_background_borderless)
        setImageResource(R.drawable.fast_plugin_manifest_explorer_ic_play)
        setTint(MaterialColor.BLACK_87)
    }


    init
    {
        orientation = HORIZONTAL
        minimumHeight = 56.dp
        setPaddingStart(16.dp)
        isBaselineAligned = false

        setBackgroundResource(R.drawable.fast_selectable_item_background)

        addView(name, linearLayoutParamsWeW(1f).apply { gravity = Gravity.CENTER_VERTICAL })
        addView(playButton, linearLayoutParamsVV(56.dp, 56.dp).apply { gravity = Gravity.CENTER_VERTICAL })

        layoutParams = layoutParamsMW()
    }
}