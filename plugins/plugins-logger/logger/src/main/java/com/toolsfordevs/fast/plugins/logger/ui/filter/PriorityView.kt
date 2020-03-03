package com.toolsfordevs.fast.plugins.logger.ui.filter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.toolsfordevs.fast.core.ext.*
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.LinearLayoutManager
import com.toolsfordevs.fast.modules.recyclerview.Renderer
import com.toolsfordevs.fast.modules.recyclerview.RendererAdapter
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.core.ui.ext.setMarginStart
import com.toolsfordevs.fast.core.ui.ext.setPaddingHorizontal
import com.toolsfordevs.fast.core.ui.image.MaskOptions
import com.toolsfordevs.fast.core.ui.image.PathHelper
import com.toolsfordevs.fast.core.ui.image.SuperImageView
import com.toolsfordevs.fast.modules.androidx.recyclerview.ext.vRecyclerView
import com.toolsfordevs.fast.plugins.logger.ui.Prefs
import com.toolsfordevs.fast.plugins.logger.model.Priority
import com.toolsfordevs.fast.plugins.logger.ui.prefs.FilterProfile


internal class PriorityView(context: Context) : FrameLayout(context), IFilterView
{
    private val adapter: RendererAdapter = RendererAdapter()

    init
    {
        adapter.addRenderer(::PriorityRenderer)

        val recyclerView = vRecyclerView(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setBackgroundColor(Color.WHITE)
        recyclerView.adapter = adapter

        addView(recyclerView, layoutParamsMM())
    }

    private lateinit var profile: FilterProfile

    override fun loadProfile(profile: FilterProfile)
    {
        this.profile = profile

        val a = BooleanArray(6)

        for (priority in profile.priorities) a[priority] = true

        val list = arrayListOf<PriorityItem>()
        list.add(PriorityItem(Priority.DEFAULT, a[Priority.DEFAULT.value]))
        list.add(PriorityItem(Priority.DEBUG, a[Priority.DEBUG.value]))
        list.add(PriorityItem(Priority.INFO, a[Priority.INFO.value]))
        list.add(PriorityItem(Priority.WARNING, a[Priority.WARNING.value]))
        list.add(PriorityItem(Priority.ERROR, a[Priority.ERROR.value]))
        list.add(PriorityItem(Priority.WTF, a[Priority.WTF.value]))

        adapter.clear()
        adapter.addAll(list)
    }

    private data class PriorityItem(val priority: Priority, var checked: Boolean)

    private inner class PriorityRenderer(parent: ViewGroup) : Renderer<PriorityItem>(PriorityView(parent.context))
    {
        val view = itemView as PriorityView

        override fun bind(data: PriorityItem, position: Int)
        {
            view.color.setImageDrawable(ColorDrawable(data.priority.color()))
            view.textview.text = data.priority.name
            view.checkbox.isChecked = data.checked

            view.checkbox.setOnClickListener {
                data.checked = view.checkbox.isChecked

                if (data.checked) profile.priorities.add(data.priority.value)
                else profile.priorities.remove(data.priority.value)

                Prefs.saveProfile(profile)
            }
        }
    }

    private class PriorityView(context: Context) : LinearLayout(context)
    {
        val color = SuperImageView(context)
        val textview = TextView(context)
        val checkbox = CheckBox(context)

        init
        {
            layoutParams = linearLayoutParamsMW().apply { gravity = Gravity.CENTER_VERTICAL }
            gravity = Gravity.CENTER_VERTICAL
            minimumHeight = Dimens.dp(56)

            val dp16 = Dimens.dp(16)
            val dp32 = Dimens.dp(32)

            setPaddingHorizontal(dp16)

            color.setMaskOptions(MaskOptions.Builder(PathHelper.circle(Dimens.dpF(32))).build())
            textview.setTextColor(MaterialColor.BLACK_87)

            addView(color, layoutParamsVV(dp32, dp32))
            addView(textview, linearLayoutParamsWeW(1f))
            addView(checkbox, layoutParamsWW())

            textview.setMarginStart(dp16)
        }
    }
}