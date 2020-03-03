package com.toolsfordevs.fast.plugins.actions.ui.delegates

import android.view.View
import android.view.ViewGroup
import com.toolsfordevs.fast.core.ext.layoutParamsMM
import com.toolsfordevs.fast.core.ui.ext.setPaddingEnd
import com.toolsfordevs.fast.core.ui.widget.FastSpinner
import com.toolsfordevs.fast.plugins.actions.base.SingleChoiceAction
import com.toolsfordevs.fast.plugins.actions.ui.ActionDelegate
import com.toolsfordevs.fast.core.util.Dimens


internal class SingleChoiceDelegate<T:Any> : ActionDelegate<T, SingleChoiceAction<T>>()
{
    private lateinit var spinner: FastSpinner

    override fun createView(parent: ViewGroup): View
    {
        spinner = FastSpinner(parent.context).apply {
            layoutParams = layoutParamsMM()
            setLightTheme()
            setPaddingEnd(Dimens.dp(16))
            textSize = 16f
        }
        return spinner
    }

    override fun bind(action: SingleChoiceAction<T>)
    {
        val map = action.choices()

        var label = "MAKE YOUR SELECTION"

        action.value?.let {
            label = map[it]!!
        }

        val index = if (action.value != null) map.indexOfKey(action.value) else 0

        spinner.setAdapter(map.values.toList(), index) { index ->
            action.value = map.keyAt(index)
            onValueChange()
        }

        spinner.text = label
    }
}