package com.toolsfordevs.fast.modules.resourcepicker


import android.graphics.Color
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.DefaultItemAnimator
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.GridLayoutManager
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.LinearLayoutManager
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.RecyclerView
import com.toolsfordevs.fast.modules.recyclerview.ExpandableRenderer
import com.toolsfordevs.fast.modules.recyclerview.Renderer
import com.toolsfordevs.fast.modules.recyclerview.decoration.GridDivider
import com.toolsfordevs.fast.modules.subheader.Subheader
import com.toolsfordevs.fast.core.util.ColorUtil
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.modules.recyclerview.ExpandableRendererAdapter
import com.toolsfordevs.fast.core.ui.ext.setTint
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty0


internal abstract class SwitchableViewDelegate(private val button: ImageButton,
                                      private val recyclerView: RecyclerView,
                                      selectionPref: KMutableProperty0<Boolean>?,
                                      val clazz: KClass<*>)
{
    private var gridMode = true

    private val listAdapter = ExpandableRendererAdapter()
    private val gridAdapter = ExpandableRendererAdapter()

    private val linearManager = LinearLayoutManager(button.context)
    private val gridManager = GridLayoutManager(button.context, 3)

    var itemTextColor = Color.WHITE
    var itemBackgroundColor = Color.WHITE
    var itemDrawableState: IntArray? = null

    private var divider: GridDivider = GridDivider(Dimens.dp(2), true)

    init
    {
        divider.excludeDataItemsOfType(Subheader::class)

        gridManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup()
        {
            override fun getSpanSize(position: Int): Int
            {
                val data = gridAdapter.getDataAtPosition(position)

                data?.let {
                    if (it is Subheader) return gridManager.spanCount
                }

                return 1
            }
        }

        listAdapter.addRenderer(Subheader::class, ::SubheaderRenderer, true)
        gridAdapter.addRenderer(Subheader::class, ::SubheaderRenderer, true)

        val a: DefaultItemAnimator = recyclerView.itemAnimator as DefaultItemAnimator
        a.supportsChangeAnimations = false

        button.setOnClickListener {
            if (gridMode)
            {
                setListMode()
                selectionPref?.set(false)
            }
            else
            {
                setGridMode()
                selectionPref?.set(true)
            }

        }

        if (selectionPref == null || selectionPref.get())
            setGridMode()
        else
            setListMode()
    }

    fun setSpanCount(span:Int)
    {
        gridManager.spanCount = span
    }

    abstract fun buildListRenderer(): (parent: ViewGroup) -> Renderer<*>
    abstract fun buildGridRenderer(): (parent: ViewGroup) -> Renderer<*>


    private fun setListMode()
    {
        gridMode = false

        button.setImageResource(R.drawable.fast_resourcepicker_ic_view_grid)

        if (recyclerView.itemDecorationCount > 0)
            recyclerView.removeItemDecorationAt(0)
        recyclerView.layoutManager = linearManager
        recyclerView.adapter = listAdapter
    }

    private fun setGridMode()
    {
        gridMode = true

        button.setImageResource(R.drawable.fast_resourcepicker_ic_view_list)

        recyclerView.addItemDecoration(divider)
        recyclerView.layoutManager = gridManager
        recyclerView.adapter = gridAdapter
    }

    fun setData(data: List<Any>)
    {
        listAdapter.addRenderer(clazz, buildListRenderer())
        gridAdapter.addRenderer(clazz, buildGridRenderer())

        listAdapter.clear()
        listAdapter.addAll(data, !gridMode)

        gridAdapter.clear()
        gridAdapter.addAll(data, gridMode)
    }

    fun setColorTheme(color: Int)
    {
        val isColorDark = ColorUtil.isColorDark(color)

        // two different methods produce different results
        val recyclerViewBgdColor = if (isColorDark) ColorUtil.lighten/*Color*/(color, 0.2f) else ColorUtil.darken/*Color*/(color, 0.1f)

        val textColor = if (isColorDark) 0xFFFFFFFF.toInt() else 0xFF000000.toInt()

        recyclerView.setBackgroundColor(recyclerViewBgdColor)
        this.itemTextColor = textColor
        itemBackgroundColor = color
        SubheaderRenderer.color = textColor

        if (gridMode) gridAdapter.notifyDataSetChanged()
        else listAdapter.notifyDataSetChanged()
    }

    fun setDrawableStates(states: IntArray?)
    {
        itemDrawableState = states

        if (gridMode) gridAdapter.notifyDataSetChanged()
        else listAdapter.notifyDataSetChanged()
    }

    class SubheaderRenderer(parent: ViewGroup) : ExpandableRenderer<Subheader>(parent, R.layout.fast_resourcepicker_item_group_subheader2)
    {
        companion object
        {
            var color = Color.BLACK
        }

        private val text: TextView = itemView.findViewById(R.id.text)
        private val icon: ImageButton = itemView.findViewById(R.id.icon)

        override fun bind(data: Subheader, position: Int)
        {
            text.text = data.title
            icon.rotation = if (isExpanded()) 0f else 180f

            text.setTextColor(color)
            icon.setTint(color)

            icon.setOnClickListener {
                icon.animate().rotation(if (isExpanded()) 180f else 0f).start()
                toggle()
            }
        }
    }
}