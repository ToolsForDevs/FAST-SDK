package com.toolsfordevs.fast.plugins.environmentswitch.ui.renderer

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.toolsfordevs.fast.core.ext.*
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.core.ui.ext.setPaddingStart
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.modules.recyclerview.ViewRenderer
import com.toolsfordevs.fast.plugins.actions.base.actions.Action

internal abstract class EnvRenderer<T : Action<*>, V : View>(
    parent: ViewGroup,
    protected val dataView: V
) :
        ViewRenderer<T, LinearLayout>(LinearLayout(parent.context))
{
    private val textView = TextView(parent.context).apply {
        setTextColor(MaterialColor.BLACK_87)
        textSize = 12f
        setPaddingStart(Dimens.dp(8))
        gravity = Gravity.CENTER_VERTICAL
    }

    init
    {
        view.orientation = LinearLayout.HORIZONTAL
        view.gravity = Gravity.CENTER_VERTICAL

        view.addView(textView, linearLayoutParamsWeV(0.5f, 48.dp).apply { gravity = Gravity.TOP })
        view.addView(View(parent.context).apply { setBackgroundColor(MaterialColor.BLACK_12) }, layoutParamsVM(Dimens.dp(1)))

        view.addView(dataView, linearLayoutParamsWeW(1f).apply { gravity = Gravity.CENTER_VERTICAL })

        view.layoutParams = layoutParamsMW()
        view.minimumHeight = Dimens.dp(48)
    }

    override fun bind(data: T, position: Int)
    {
        textView.text = data.name
    }
}