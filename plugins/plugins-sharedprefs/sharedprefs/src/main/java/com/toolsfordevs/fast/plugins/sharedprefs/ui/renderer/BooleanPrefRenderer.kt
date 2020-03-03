package com.toolsfordevs.fast.plugins.sharedprefs.ui.renderer

import android.view.ViewGroup
import com.toolsfordevs.fast.core.ui.widget.FastSwitch
import com.toolsfordevs.fast.plugins.sharedprefs.PrefManager
import com.toolsfordevs.fast.plugins.sharedprefs.ui.model.BooleanPref

internal class BooleanPrefRenderer(parent: ViewGroup) :
        PrefRenderer<BooleanPref, FastSwitch>(parent, FastSwitch.create(parent.context, true))
{
    override fun bind(data: BooleanPref, position: Int)
    {
        super.bind(data, position)

        dataView.isChecked = PrefManager.getSharedPreferences().getBoolean(data.name, false)

        dataView.setOnClickListener {
            //dataView.isChecked = !dataView.isChecked
            PrefManager.getSharedPreferences().edit().putBoolean(data.name, dataView.isChecked).apply()
        }
    }
}