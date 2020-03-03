package com.toolsfordevs.fast.plugins.viewinspector.ui.renderer

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import com.toolsfordevs.fast.core.ext.frameLayoutParamsWW
import com.toolsfordevs.fast.core.ext.layoutParamsMW
import com.toolsfordevs.fast.core.ui.ext.setPaddingEnd
import com.toolsfordevs.fast.core.ui.widget.FastSwitch
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyRenderer
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.BooleanProperty


internal class BooleanRenderer(parent: ViewGroup) : ViewPropertyRenderer<BooleanProperty<*>>(BooleanRendererView(parent.context)) {
    private val fastSwitch: FastSwitch = (itemView as BooleanRendererView).fastSwitch

    override fun bind(data: BooleanProperty<*>)
    {
        fastSwitch.setOnClickListener(null)

        fastSwitch.isChecked = data.getValue() ?: false

        fastSwitch.setOnClickListener {
            data.setValue(fastSwitch.isChecked)
        }
    }
}

class BooleanRendererView(context: Context) : FrameLayout(context)
{
    val fastSwitch = FastSwitch.create(context)

    init
    {
        fastSwitch.setPaddingEnd(Dimens.dp(8))
        val p = frameLayoutParamsWW().apply { gravity = Gravity.END or Gravity.CENTER_VERTICAL }
        addView(fastSwitch, p)

        layoutParams = layoutParamsMW()
    }
}