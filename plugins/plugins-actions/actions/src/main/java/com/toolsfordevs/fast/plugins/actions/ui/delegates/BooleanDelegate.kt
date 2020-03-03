package com.toolsfordevs.fast.plugins.actions.ui.delegates

import android.view.View
import android.view.ViewGroup
import com.toolsfordevs.fast.core.ui.ext.setPaddingEnd
import com.toolsfordevs.fast.core.ui.widget.FastSwitch
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.plugins.actions.base.BooleanAction
import com.toolsfordevs.fast.plugins.actions.ui.ActionDelegate


internal class BooleanDelegate : ActionDelegate<Boolean, BooleanAction>()
{
    private lateinit var switch:FastSwitch

    override fun createView(parent: ViewGroup): View
    {
        switch = FastSwitch.create(parent.context, true)
        switch.setPaddingEnd(Dimens.dp(8))
        return switch
    }

    override fun bind(action: BooleanAction)
    {
        switch.isChecked = action.value ?: false
        switch.setOnClickListener {
            action.value = switch.isChecked
            onValueChange()
        }
    }

}