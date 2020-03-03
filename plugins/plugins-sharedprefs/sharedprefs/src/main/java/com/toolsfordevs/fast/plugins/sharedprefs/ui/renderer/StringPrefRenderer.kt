package com.toolsfordevs.fast.plugins.sharedprefs.ui.renderer

import android.text.TextUtils
import android.view.ViewGroup
import android.widget.TextView
import com.toolsfordevs.fast.core.FastManager
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.core.ui.ext.setPadding
import com.toolsfordevs.fast.core.ui.ext.setPaddingEnd
import com.toolsfordevs.fast.core.ui.ext.setPaddingStart
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.plugins.sharedprefs.PrefManager
import com.toolsfordevs.fast.plugins.sharedprefs.R
import com.toolsfordevs.fast.plugins.sharedprefs.ui.StringPanel
import com.toolsfordevs.fast.plugins.sharedprefs.ui.model.StringPref

internal class StringPrefRenderer(parent: ViewGroup) :
        PrefRenderer<StringPref, TextView>(parent, TextView(parent.context))
{
    init
    {
        dataView.setTextColor(MaterialColor.BLACK_87)
        dataView.maxLines = 10
        dataView.ellipsize = TextUtils.TruncateAt.END
        dataView.setPadding(Dimens.dp(8))
        dataView.setBackgroundResource(R.drawable.fast_selectable_item_background)
    }

    override fun bind(data: StringPref, position: Int)
    {
        super.bind(data, position)

        dataView.text = PrefManager.getSharedPreferences().getString(data.name, "")

        dataView.setOnClickListener { FastManager.addView(StringPanel(dataView.context, data.name)) }
    }
}