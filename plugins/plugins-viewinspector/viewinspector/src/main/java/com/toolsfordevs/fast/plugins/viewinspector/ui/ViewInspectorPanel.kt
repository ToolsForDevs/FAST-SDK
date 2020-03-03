package com.toolsfordevs.fast.plugins.viewinspector.ui

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.View
import android.view.View.OnLayoutChangeListener
import android.view.ViewGroup
import android.widget.*
import com.toolsfordevs.fast.core.FastColor
import com.toolsfordevs.fast.core.FastManager
import com.toolsfordevs.fast.core.UnitConverter
import com.toolsfordevs.fast.core.ext.inflate
import com.toolsfordevs.fast.core.ui.ext.animateY
import com.toolsfordevs.fast.core.ui.ext.invisible
import com.toolsfordevs.fast.core.ui.ext.setTint
import com.toolsfordevs.fast.core.ui.widget.BottomSheetLayout
import com.toolsfordevs.fast.core.ui.widget.adapter.FastViewPagerAdapter
import com.toolsfordevs.fast.core.util.ColorUtil
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.core.util.ViewUtil
import com.toolsfordevs.fast.core.widget.FastPanel
import com.toolsfordevs.fast.modules.androidx.ViewPager
import com.toolsfordevs.fast.modules.viewhierarchyexplorer.TransparentViewOverlay
import com.toolsfordevs.fast.modules.viewhierarchyexplorer.ViewHierarchyExplorer
import com.toolsfordevs.fast.plugins.viewinspector.R
import com.toolsfordevs.fast.plugins.viewinspector.ui.view.FavoriteView
import com.toolsfordevs.fast.plugins.viewinspector.ui.view.LayoutView
import com.toolsfordevs.fast.plugins.viewinspector.ui.view.PropertiesView


internal class ViewInspectorPanel(context: Context) : BottomSheetLayout(context)
{
    private var header: View
    private var name: TextView
    private var viewInfo: ViewGroup
    private var id: TextView
    private var size: TextView
    private var viewPager: ViewPager

    private val buttonBar: LinearLayout
    private val pagerButton: ImageButton
    private val opacityButton: ImageButton
    private val unitButton: Button
    private val visibilityButton: ImageButton
    private val lockModeButton: ImageButton
    private val toggleModeButton: ImageButton
    private val headerVisibilityButton: ImageButton

    private val explorer: ViewHierarchyExplorer by lazy { ViewHierarchyExplorer() }
    private val overlay = TransparentViewOverlay(context)

    private var pagerAdapter: ViewPagerAdapter

    override var mode: Int = Prefs.mode
        set(value)
        {
            super.mode = value
            toggleModeButton.isSelected = value == FastPanel.MODE_BELOW

            field = value
        }

    private var lockMode: Boolean = true
        set (value)
        {
            field = value
            lockModeButton.isSelected = value
            overlay.isLocked = value
        }

    private var usePx = Prefs.usePx
        set(value)
        {
            field = value
            Prefs.usePx = value
            pagerAdapter.usePixels(usePx)
            refresh()
        }

    private var isSemiTransparent = Prefs.panelOpacity
        set(value)
        {
            Prefs.panelOpacity = value
            //opacityButton.setImageResource(if (value) R.drawable.fast_logcat_ic_pin_on else R.drawable.fast_logcat_ic_pin_off)
            alpha = if (value) 0.5f else 1f
            field = value
        }

    private var isPanelVisible = true
        set(value)
        {
            viewInfo.invisible(!value)
            viewPager.invisible(!value)
            pagerButton.invisible(!value)
            unitButton.invisible(!value)
            //opacityButton.invisible(!value)
            visibilityButton.setTint(if (!value) 0xFF000000.toInt() else 0xFFFFFFFF.toInt())
            visibilityButton.alpha = if (!value) 0.2f else 1f
            headerVisibilityButton.invisible(!value)
            toggleModeButton.invisible(!value)
            buttonBar.setBackgroundColor(if (!value) 0x00000000 else buttonBarBackground)

            field = value
        }

    private var isHeaderVisible = Prefs.showHeader
        set(value)
        {
            Prefs.showHeader = value

            viewInfo.animateY(if (value) Dimens.dpF(68) else 0f, 300)
            headerVisibilityButton.animate().rotation(if (value) 180f else 0f).setDuration(300).start()

            field = value
        }

    private val unit
        get() = if (usePx) TypedValue.COMPLEX_UNIT_PX else TypedValue.COMPLEX_UNIT_DIP

    private val buttonBarBackground = ColorUtil.darken(FastColor.colorPrimary, .10f)

    private lateinit var dataView: View

    init
    {
        setCollapsedHeight(Dimens.dp(350))
        setBackgroundColor(Color.TRANSPARENT)

        context.inflate(R.layout.fast_view_inspector, this, true)

        viewPager = findViewById(R.id.view_pager)

        header = findViewById(R.id.header)
        name = findViewById(R.id.name)
        viewInfo = findViewById(R.id.view_info)
        id = findViewById(R.id.id)
        size = findViewById(R.id.size)

        buttonBar = findViewById(R.id.button_bar)
        pagerButton = findViewById(R.id.pager_button)
        opacityButton = findViewById(R.id.alpha_button)
        unitButton = findViewById(R.id.button_unit)
        visibilityButton = findViewById(R.id.visibility_button)
        lockModeButton = findViewById(R.id.lock_mode_button)
        toggleModeButton = findViewById(R.id.toggle_mode_button)
        headerVisibilityButton = findViewById(R.id.size_button)

        pagerButton.setOnClickListener { showPagerMenu() }
        unitButton.setOnClickListener { usePx = !usePx }
        opacityButton.setOnClickListener { isSemiTransparent = !isSemiTransparent }
        visibilityButton.setOnClickListener { isPanelVisible = !isPanelVisible }
        lockModeButton.setOnClickListener { lockMode = !lockMode }
        toggleModeButton.setOnClickListener {
            mode = if (mode == FastPanel.MODE_ON_TOP_OF) FastPanel.MODE_BELOW else FastPanel.MODE_ON_TOP_OF
        }
        headerVisibilityButton.setOnClickListener { isHeaderVisible = !isHeaderVisible }

        setDraggableView(header)
        buttonBar.setBackgroundColor(buttonBarBackground)

        pagerAdapter = ViewPagerAdapter()
        viewPager.adapter = pagerAdapter

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener
        {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int)
            {
            }

            override fun onPageSelected(position: Int)
            {
                Prefs.viewPagerIndex = position
                pagerButton.setImageResource(if (position == 0) R.drawable.fast_view_inspector_ic_layout
                                            else if (position == 1) R.drawable.fast_view_inspector_ic_list
                                            else R.drawable.fast_plugin_view_inspector_ic_star)
            }

            override fun onPageScrollStateChanged(state: Int)
            {
            }
        })

        viewPager.setCurrentItem(Prefs.viewPagerIndex, false)

        // Init button states
        unitButton.text = if (usePx) "PX" else "DP"

        if (isHeaderVisible)
        {
            viewInfo.y = Dimens.dpF(68)
            headerVisibilityButton.rotation = 180f
        }

        toggleModeButton.isSelected = mode == FastPanel.MODE_BELOW
        lockModeButton.isSelected = lockMode
    }

    override fun onAttachedToWindow()
    {
        super.onAttachedToWindow()

        overlay.onClickCallback = { x, y ->
            val v = explorer.getViewAt((context as Activity).window.decorView, x, y)

            if (v != null)
                setView(v)
        }

        FastManager.addOverlay(overlay)
    }

    override fun onDetachedFromWindow()
    {
        super.onDetachedFromWindow()

        FastManager.removeOverlay(overlay)
    }

    private val layoutChangeListener: OnLayoutChangeListener =
            OnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom -> refresh() }

    fun setView(view: View)
    {
        if (::dataView.isInitialized)
        {
            dataView.removeOnLayoutChangeListener(layoutChangeListener)
        }

        dataView = view

        name.text = dataView::class.java.simpleName
        id.text = ViewUtil.getIdString(dataView)

        refresh()

        dataView.addOnLayoutChangeListener(layoutChangeListener)

        pagerAdapter.setView(view)
    }

    private fun refresh()
    {
        unitButton.text = if (usePx) "PX" else "DP"

        val width = UnitConverter.getScaledValue(dataView.width, unit)
        val height = UnitConverter.getScaledValue(dataView.height, unit)
        val unitLabel = if (usePx) "px" else "dp"

        val text = "$width x $height $unitLabel"

        size.text = text
    }

    override fun onExpanded()
    {
        super.onExpanded()
        toggleModeButton.isEnabled = false
        toggleModeButton.imageAlpha = 128 //0.5
    }

    override fun onCollapsed()
    {
        super.onCollapsed()
        toggleModeButton.isEnabled = true
        toggleModeButton.imageAlpha = 255 //1
    }

    private fun showPagerMenu()
    {
        val menu = PopupMenu(context, pagerButton)
        val layout = menu.menu.add("Layout")
        layout.setIcon(R.drawable.fast_view_inspector_ic_layout)
        val properties = menu.menu.add("Properties")
        properties.setIcon(R.drawable.fast_view_inspector_ic_list_dark)
        val favorites = menu.menu.add("Favorites")

        menu.setOnMenuItemClickListener { item ->
            var index = 0
            if (item == properties)
                index = 1
            else
                index = 2

            viewPager.setCurrentItem(index, true)
            true
        }
        menu.show()
    }

    private inner class ViewPagerAdapter : FastViewPagerAdapter()
    {
        private val fragment1 = LayoutView(context)
        private val fragment2 = PropertiesView(context)
        private val fragment3 = FavoriteView(context)

        init {
            fragment1.usePixels = usePx
            fragment2.usePixels = usePx
            fragment3.usePixels = usePx
        }

        fun setView(view: View)
        {
            fragment1.setView(view)
            fragment2.setView(view)
            fragment3.setView(view)
        }

        fun usePixels(usePx: Boolean)
        {
            fragment1.usePixels = usePx
            fragment2.usePixels = usePx
            fragment3.usePixels = usePx
        }

        override fun getItem(position: Int): View
        {
            return when (position)
            {
                0 -> fragment1
                1 -> fragment2
                else -> fragment3
            }
        }

        override fun getCount(): Int
        {
            return 3
        }

        override fun getPageTitle(position: Int): CharSequence?
        {
            return when (position)
            {
                0 -> "Layout"
                1 -> "Properties"
                else -> "Favorites"
            }
        }
    }
}