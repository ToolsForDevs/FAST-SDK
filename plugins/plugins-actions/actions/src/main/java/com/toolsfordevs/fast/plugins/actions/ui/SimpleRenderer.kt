package com.toolsfordevs.fast.plugins.actions.ui

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.toolsfordevs.fast.core.FastManager
import com.toolsfordevs.fast.core.ext.*
import com.toolsfordevs.fast.core.ui.ext.setPadding
import com.toolsfordevs.fast.modules.recyclerview.ViewRenderer
import com.toolsfordevs.fast.plugins.actions.R
import com.toolsfordevs.fast.plugins.actions.base.Action


internal class SimpleRenderer(parent: ViewGroup) :
        ViewRenderer<ActionWrapper<Any>, LinearLayout>(hLinearLayout(parent.context)), View.OnClickListener
{
    private val name: TextView = TextView(parent.context).apply {
        setPadding(16.dp)
        gravity = Gravity.CENTER_VERTICAL
    }
    private val icon: ImageButton = ImageButton(parent.context).apply {
        setImageResource(R.drawable.fast_actions_ic_starred)
        setBackgroundResource(R.drawable.fast_selectable_item_background_borderless)
    }

    init
    {
        view.addView(name, linearLayoutParamsWeW(1f))
        view.addView(icon, layoutParamsVM(56.dp))

        view.layoutParams = layoutParamsMV(56.dp)
        view.isBaselineAligned = false
        view.setBackgroundResource(R.drawable.fast_selectable_item_background)
    }

    override fun bind(data: ActionWrapper<Any>, position: Int)
    {
        name.text = data.name

        icon.isSelected = FavoriteManager.isFavorite(data.name)

        icon.setOnClickListener {
            icon.isSelected = !icon.isSelected
            FavoriteManager.toggleFavorite(data.name, !icon.isSelected)
        }

        itemView.setOnClickListener(this)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onClick(v: View?)
    {
        val wrapper: ActionWrapper<Any> = getItem(adapterPosition) as ActionWrapper<Any>

        if (wrapper.action is SimpleAction)
        {
            wrapper.callback.invoke(wrapper.action)

//            if (!isPinned)
//                dismiss()
        }
        else
        {
            val toolbar = ActionsToolbar(v!!.context)
            toolbar.setup(wrapper, DelegateManager.getDelegateForAction(wrapper.action) as ActionDelegate<*, Action<*>>)
            FastManager.addView(toolbar)
        }
    }
}