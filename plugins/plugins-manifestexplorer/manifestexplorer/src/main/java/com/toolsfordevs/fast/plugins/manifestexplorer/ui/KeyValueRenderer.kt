package com.toolsfordevs.fast.plugins.manifestexplorer.ui

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.toolsfordevs.fast.core.ext.*
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.core.ui.ext.setPaddingHorizontal
import com.toolsfordevs.fast.core.util.CopyUtil
import com.toolsfordevs.fast.modules.recyclerview.ViewRenderer
import com.toolsfordevs.fast.plugins.manifestexplorer.R

internal class KeyValueRenderer(parent: ViewGroup) : ViewRenderer<KeyValue, LinearLayout>(hLinearLayout(parent.context)), View.OnClickListener
{
    private val key = TextView(parent.context).apply {
        setTextColor(MaterialColor.BLACK_87)
        textSize = 12f
        setPaddingHorizontal(8.dp)
        gravity = Gravity.CENTER_VERTICAL
    }

    private val value = TextView(parent.context).apply {
        setTextColor(MaterialColor.BLACK_87)
        textSize = 12f
        setPaddingHorizontal(8.dp)
        gravity = Gravity.CENTER_VERTICAL
    }

    init
    {
        view.gravity = Gravity.CENTER_VERTICAL

        view.addView(key, linearLayoutParamsWeV(0.5f, 48.dp).apply { gravity = Gravity.TOP })
        view.addView(View(parent.context).apply { setBackgroundColor(MaterialColor.BLACK_12) }, layoutParamsVM(1.dp))
        view.addView(value, linearLayoutParamsWeW(1f).apply { gravity = Gravity.CENTER_VERTICAL })

        view.layoutParams = layoutParamsMW()
        view.minimumHeight = 48.dp
        
        view.setBackgroundResource(R.drawable.fast_selectable_item_background)
        view.setOnClickListener(this)
    }

    override fun bind(data: KeyValue, position: Int)
    {
        key.text = data.key
        value.text = data.value ?: "null"
    }

    override fun onClick(v: View?)
    {
        val data = adapterDelegate!!.getItem(adapterPosition) as KeyValue
        
        data.value?.let { CopyUtil.copy(data.key, it) }
    }
}

data class KeyValue(val key:String, val value:String?)