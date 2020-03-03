package com.toolsfordevs.fast.plugins.sessionmanager.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.toolsfordevs.fast.core.ext.*
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.core.ui.ext.setPaddingHorizontal
import com.toolsfordevs.fast.core.util.CopyUtil
import com.toolsfordevs.fast.core.util.FastSort
import com.toolsfordevs.fast.modules.androidx.recyclerview.ext.vRecyclerView
import com.toolsfordevs.fast.modules.recyclerview.RendererAdapter
import com.toolsfordevs.fast.modules.recyclerview.ViewRenderer
import com.toolsfordevs.fast.modules.recyclerview.decoration.Divider
import com.toolsfordevs.fast.plugins.sessionmanager.R
import com.toolsfordevs.fast.plugins.sessionmanager.Repository
import com.toolsfordevs.fast.plugins.sessionmanager.model.StateItem
import java.util.*

@SuppressLint("ViewConstructor")
internal class StateCategoryView(context: Context, private val repository: Repository, private val category: String) : FrameLayout(context)
{
    private val adapter = RendererAdapter()

    init
    {
        val recyclerView = vRecyclerView(context)
        recyclerView.isVerticalScrollBarEnabled = true
        recyclerView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        recyclerView.addItemDecoration(Divider().apply {
            setDividerSize(1.dp)
            setColor(MaterialColor.BLACK_12)
            showLast = true
        })

        adapter.addRenderer(StateItem::class, ::StateRenderer)
        recyclerView.adapter = adapter
        refresh()

        addView(recyclerView, layoutParamsMM())
        layoutParams = layoutParamsMM()
    }

    fun refresh()
    {
        adapter.clear()

        val sortType = Prefs.getTabSortType(category)
        val list = repository.getItemsForCategory(category)

        adapter.addAll(when (sortType)
                       {
                           FastSort.DEFAULT    -> list
                           FastSort.ALPHA_ASC  -> list.sortedBy { it.name.toLowerCase(Locale.getDefault()) }
                           FastSort.ALPHA_DESC -> list.sortedByDescending { it.name.toLowerCase(Locale.getDefault()) }
                           else                -> throw Exception("")
                       })
    }

    private inner class StateRenderer(parent: ViewGroup) : ViewRenderer<StateItem, LinearLayout>(hLinearLayout(parent.context)), OnClickListener
    {
        val name = TextView(parent.context).apply {
            setTextColor(MaterialColor.BLACK_87)
            textSize = 12f
            setPaddingHorizontal(8.dp)
            gravity = Gravity.CENTER_VERTICAL
        }

        val value = TextView(parent.context).apply {
            setTextColor(MaterialColor.BLACK_87)
            textSize = 12f
            setPaddingHorizontal(8.dp)
            gravity = Gravity.CENTER_VERTICAL
        }

        init
        {
            view.gravity = Gravity.CENTER_VERTICAL
            view.setBackgroundResource(R.drawable.fast_selectable_item_background)
            view.minimumHeight = 56.dp

            view.addView(name, linearLayoutParamsWeW(0.5f).apply { gravity = Gravity.CENTER_VERTICAL })
            view.addView(View(parent.context).apply { setBackgroundColor(MaterialColor.BLACK_12) }, layoutParamsVM(1.dp))
            view.addView(value, linearLayoutParamsWeW(1f).apply { gravity = Gravity.CENTER_VERTICAL })

            view.setOnClickListener(this)
            view.layoutParams = layoutParamsMW()
        }

        override fun bind(data: StateItem, position: Int)
        {
            name.text = data.name
            value.text = data.value
        }

        override fun onClick(v: View?)
        {
            CopyUtil.copy(name.text.toString(), value.text.toString())
        }
    }
}