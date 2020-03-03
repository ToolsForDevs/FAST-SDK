package com.toolsfordevs.fast.plugins.sharedprefs.ui.renderer

import android.view.Gravity
import android.view.ViewGroup
import com.toolsfordevs.fast.core.ui.widget.FastNumberStepper
import com.toolsfordevs.fast.plugins.sharedprefs.PrefManager
import com.toolsfordevs.fast.plugins.sharedprefs.ui.model.LongPref

internal class LongPrefRenderer(parent: ViewGroup) :
        PrefRenderer<LongPref, FastNumberStepper>(parent, FastNumberStepper(parent.context))
{
    init
    {
        dataView.gravity = Gravity.END
    }
    override fun bind(data: LongPref, position: Int)
    {
        super.bind(data, position)

        dataView.setValue(PrefManager.getSharedPreferences().getLong(data.name, 0L))
        dataView.setOnChangeCallback { value ->
            PrefManager.getSharedPreferences().edit().putLong(data.name, value.toLong()).apply()
        }
    }
}