package com.toolsfordevs.fast.plugins.actions.ui.delegates

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.toolsfordevs.fast.core.ext.*
import com.toolsfordevs.fast.plugins.actions.base.Action
import com.toolsfordevs.fast.plugins.actions.ui.ActionDelegate
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.plugins.actions.base.ComboAction
import com.toolsfordevs.fast.plugins.actions.ui.DelegateManager


internal class MultiDelegate : ActionDelegate<ArrayList<Any?>, ComboAction>()
{
    private lateinit var layout: LinearLayout

    override fun createView(parent: ViewGroup): View
    {
        layout = vLinearLayout(parent.context)
        val dp8 = Dimens.dp(8)
        layout.setPadding(dp8, 0, dp8, 0)
        return layout
    }

    override fun bind(action: ComboAction)
    {
        val dp56 = Dimens.dp(56)

        for (i in action.actions.indices)
        {
            val actionItem = action.actions[i]

            val hLayout = hLinearLayout(layout.context, layoutParamsMW())
            hLayout.minimumHeight = dp56
            hLayout.gravity = Gravity.CENTER_VERTICAL

            val label = TextView(layout.context)
            label.setTextColor(Color.WHITE)
            label.gravity = Gravity.CENTER_VERTICAL
            label.text = actionItem.name
            hLayout.addView(label, linearLayoutParamsWeM(0.3f))

            val delegate = DelegateManager.getDelegateForAction(actionItem.action)
            val view = delegate.createView(hLayout)
            hLayout.addView(view, linearLayoutParamsWeW(0.7f))

            (delegate as ActionDelegate<*, Action<*>>).internalBind(actionItem.action as Action<Any>)
            delegate.listener = {
                //val list = arrayListOf<Any?>()

                action.value!![i] = actionItem.action.value
                onValueChange()
            }

            layout.addView(hLayout, layoutParamsMW())
        }
    }
}