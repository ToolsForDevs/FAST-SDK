package com.toolsfordevs.fast

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.TextUtils
import android.view.Gravity
import android.view.ViewGroup
import android.widget.EdgeEffect
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.toolsfordevs.fast.core.FastColor
import com.toolsfordevs.fast.core.FastManager
import com.toolsfordevs.fast.core.FastPlugin
import com.toolsfordevs.fast.core.ext.layoutParamsMM
import com.toolsfordevs.fast.core.ext.layoutParamsMW
import com.toolsfordevs.fast.core.ext.layoutParamsVV
import com.toolsfordevs.fast.core.ext.vLinearLayout
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.GridLayoutManager
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.RecyclerView
import com.toolsfordevs.fast.modules.recyclerview.Renderer
import com.toolsfordevs.fast.modules.recyclerview.RendererAdapter
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.core.ui.ext.setPadding
import com.toolsfordevs.fast.core.ui.ext.setPaddingHorizontal
import com.toolsfordevs.fast.core.ui.ext.setPaddingTop
import com.toolsfordevs.fast.core.ui.ext.setPaddingVertical
import com.toolsfordevs.fast.core.ui.widget.BottomSheetLayout


internal class FastPluginToolbar(context: Context) : BottomSheetLayout(context)
{
    init {
        setCollapsedHeight(Dimens.dp(400))
        setBackgroundColor(FastColor.colorPrimary)

        val layout = vLinearLayout(context)

        val title = TextView(context)
        title.text = "FAST"
        title.textSize = 24f
        title.typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
        title.letterSpacing = 0.15f
        title.setTextColor(MaterialColor.WHITE_87)
        title.setPadding(Dimens.dp(16))
        title.gravity = Gravity.CENTER
        layout.addView(title, layoutParamsMW())

        val recyclerView = RecyclerView(context)
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        recyclerView.edgeEffectFactory = object : RecyclerView.EdgeEffectFactory() {
            override fun createEdgeEffect(view: RecyclerView, direction: Int): EdgeEffect {
                return EdgeEffect(view.context).apply { setColor(MaterialColor.BLACK_38) }
            }
        }

        val adapter = RendererAdapter()
        adapter.mode = RendererAdapter.MODE_INSTANCE_OF
        adapter.addRenderer(::PluginRenderer)

        for (plugin in FastManager.getPlugins().sortedBy { it.name })
        {
            if (plugin.availableInLaunchBar)
            {
                adapter.add(plugin)
                //val button = createButton(plugin.icon, View.NO_ID)
                //button.setOnClickListener { plugin.launch(context as Activity) }
            }
        }

        recyclerView.adapter = adapter
        layout.addView(recyclerView, layoutParamsMM())
        addView(layout, layoutParamsMM())

        setDraggableView(title)
    }

    private class PluginRenderer(parent: ViewGroup) : Renderer<FastPlugin>(Item(parent.context))
    {
        override fun bind(data: FastPlugin, position: Int)
        {
            val item = itemView as Item
            item.icon.setImageResource(data.icon)
            item.label.text = data.name

            item.setOnClickListener { data.launch(item.context as Activity) }
        }
    }

    private class Item(context:Context) : LinearLayout(context)
    {
        val icon = ImageView(context)
        val label = TextView(context)

        init
        {
            orientation = VERTICAL
            gravity = Gravity.CENTER
            setPaddingVertical(Dimens.dp(16))
            setPaddingHorizontal(Dimens.dp(8))

            val size = Dimens.dp(32)
            addView(icon, layoutParamsVV(size, size))

            label.textSize = 14f
            label.setTextColor(Color.WHITE)
            label.setSingleLine()
            label.ellipsize = TextUtils.TruncateAt.END
            label.gravity = Gravity.CENTER
            label.setPaddingTop(Dimens.dp(16))
            addView(label, layoutParamsMW())

            setBackgroundResource(R.drawable.fast_selectable_item_background)
        }
    }
}