package com.toolsfordevs.fast.plugins.actions.ui.delegates

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.toolsfordevs.fast.core.ext.hLinearLayout
import com.toolsfordevs.fast.core.ext.layoutParamsVV
import com.toolsfordevs.fast.core.ext.linearLayoutParamsWeM
import com.toolsfordevs.fast.core.ui.ext.setMarginEnd
import com.toolsfordevs.fast.core.ui.ext.setPadding
import com.toolsfordevs.fast.core.ui.ext.setPaddingEnd
import com.toolsfordevs.fast.core.ui.ext.setPaddingStart
import com.toolsfordevs.fast.core.util.ColorUtil
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.core.util.ResUtils
import com.toolsfordevs.fast.modules.resourcepicker.color.ColorPickerDialog
import com.toolsfordevs.fast.plugins.actions.R
import com.toolsfordevs.fast.plugins.actions.base.ColorAction
import com.toolsfordevs.fast.plugins.actions.ui.ActionDelegate


internal class ColorDelegate : ActionDelegate<Int, ColorAction>()
{
    private lateinit var layout:LinearLayout
    private lateinit var textview:TextView
    private lateinit var colorView:View
    private lateinit var icon: ImageView

    override fun createView(parent: ViewGroup): View
    {
        layout = hLinearLayout(parent.context)
        layout.setPaddingStart(Dimens.dp(8))
        layout.gravity = Gravity.CENTER_VERTICAL
        layout.setBackgroundResource(R.drawable.fast_selectable_item_background)
        layout.descendantFocusability = ViewGroup.FOCUS_BEFORE_DESCENDANTS

        textview = TextView(parent.context)
        textview.setTextColor(Color.WHITE)
        textview.setPaddingEnd(Dimens.dp(8))
        textview.setText("NO COLOR SELECTED")
        textview.textSize = 14f
        textview.gravity = Gravity.CENTER_VERTICAL
        layout.addView(textview, linearLayoutParamsWeM(1f))

        var size = Dimens.dp(32)
        colorView = View(parent.context)
        layout.addView(colorView, layoutParamsVV(size, size))
        colorView.setMarginEnd(Dimens.dp(12))

        size = Dimens.dp(56)
        icon = ImageView(parent.context)
        icon.setPadding(Dimens.dp(12))
        icon.setImageResource(R.drawable.fast_actions_ic_r)
        icon.setBackgroundResource(0)
        layout.addView(icon, layoutParamsVV(size, size))

        return layout
    }

    override fun bind(action: ColorAction)
    {
        action.value?.let {
            refresh(it)
        }

        layout.setOnClickListener {
            ColorPickerDialog(icon.context, { colorResource ->
                val v = colorResource.value ?: ResUtils.getColor(colorResource.resId)
                refresh(v)
                action.value = v
                onValueChange()
            }).show()
        }
    }

    private fun refresh(color:Int)
    {
        textview.setText("#" + ColorUtil.colorHex(color))
        colorView.setBackgroundColor(color)
    }
}