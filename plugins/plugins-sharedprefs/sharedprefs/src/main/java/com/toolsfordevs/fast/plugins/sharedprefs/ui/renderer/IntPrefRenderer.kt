package com.toolsfordevs.fast.plugins.sharedprefs.ui.renderer

import android.view.Gravity
import android.view.ViewGroup
import com.toolsfordevs.fast.core.ui.widget.FastNumberStepper
import com.toolsfordevs.fast.plugins.sharedprefs.PrefManager
import com.toolsfordevs.fast.plugins.sharedprefs.ui.model.IntPref

internal class IntPrefRenderer(parent: ViewGroup) :
        PrefRenderer<IntPref, FastNumberStepper>(parent, FastNumberStepper(parent.context))
{
    init
    {
        dataView.gravity = Gravity.END
    }

    override fun bind(data: IntPref, position: Int)
    {
        super.bind(data, position)

        dataView.setValue(PrefManager.getSharedPreferences().getInt(data.name, 0))
        dataView.setOnChangeCallback { value ->
            PrefManager.getSharedPreferences().edit().putInt(data.name, value.toInt()).apply()
        }
    }
}