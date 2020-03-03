package com.toolsfordevs.fast.plugins.sharedprefs.ui.renderer

import android.view.Gravity
import android.view.ViewGroup
import com.toolsfordevs.fast.core.ui.widget.FastNumberStepper
import com.toolsfordevs.fast.plugins.sharedprefs.PrefManager
import com.toolsfordevs.fast.plugins.sharedprefs.ui.model.FloatPref

internal class FloatPrefRenderer(parent: ViewGroup) :
        PrefRenderer<FloatPref, FastNumberStepper>(parent, FastNumberStepper(parent.context))
{
    init
    {
        dataView.gravity = Gravity.END
    }
    override fun bind(data: FloatPref, position: Int)
    {
        super.bind(data, position)

        dataView.setValue(PrefManager.getSharedPreferences().getFloat(data.name, 0f))
        dataView.setOnChangeCallback { value ->
            PrefManager.getSharedPreferences().edit().putFloat(data.name, value.toFloat()).apply()
        }
    }
}