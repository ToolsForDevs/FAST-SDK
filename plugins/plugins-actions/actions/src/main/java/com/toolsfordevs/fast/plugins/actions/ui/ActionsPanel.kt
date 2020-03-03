package com.toolsfordevs.fast.plugins.actions.ui

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.ImageButton
import android.widget.Space
import com.toolsfordevs.fast.core.ext.*
import com.toolsfordevs.fast.core.ui.ext.hide
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.modules.androidx.ViewPager
import com.toolsfordevs.fast.modules.androidx.tabs.TabLayout
import com.toolsfordevs.fast.core.ui.widget.BottomSheetLayout
import com.toolsfordevs.fast.core.ui.widget.FastTabLayout
import com.toolsfordevs.fast.core.ui.widget.FastToolbar
import com.toolsfordevs.fast.core.ui.widget.adapter.FastViewPagerAdapter
import com.toolsfordevs.fast.plugins.actions.ActionsPlugin
import com.toolsfordevs.fast.plugins.actions.R
import com.toolsfordevs.fast.plugins.actions.ui.view.ActionsView
import com.toolsfordevs.fast.plugins.actions.ui.view.FavoriteView
import com.toolsfordevs.fast.plugins.actions.ui.view.ISortable
import com.toolsfordevs.fast.plugins.actions.ui.view.SortDelegate


internal class ActionsPanel(context: Context) : BottomSheetLayout(context)
{
    private val repository = Repository()

    private var pinButton: ImageButton

    private var isPinned = Prefs.pinPanel
        set(value)
        {
            Prefs.pinPanel = value
            pinButton.setImageResource(if (value) R.drawable.fast_actions_ic_pin_on else R.drawable.fast_actions_ic_pin_off)
            field = value
        }

    init
    {
        ActionsPlugin.refreshData(repository)
        setCollapsedHeight(Dimens.dp(350))
        // mode = MODE_BELOW_PARENT

        val layout = vLinearLayout(context)

        val toolbar = FastToolbar(context).apply { gravity = Gravity.END }

        val tabLayout = FastTabLayout(context)

        toolbar.addView(tabLayout, layoutParamsWM())
        toolbar.addView(Space(context), linearLayoutParamsWeM(1f))

        val sortButton = toolbar.createButton(R.drawable.fast_actions_ic_sort)
        pinButton = toolbar.createButton(if (isPinned) R.drawable.fast_actions_ic_pin_on else R.drawable.fast_actions_ic_pin_off)
        pinButton.setOnClickListener { isPinned = !isPinned }
        pinButton.hide()

        val viewPager = ViewPager(context)
        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        tabLayout.setupWithViewPager(viewPager)

        viewPager.setBackgroundColor(Color.WHITE)
        val adapter = ActionPagerAdapter(context, repository)
        viewPager.adapter = adapter

        viewPager.currentItem = Prefs.selectedTab

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener
        {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int)
            {

            }

            override fun onPageSelected(position: Int)
            {
                Prefs.selectedTab = position
            }

            override fun onPageScrollStateChanged(state: Int)
            {

            }

        })

        layout.addView(toolbar, layoutParamsMW())
        layout.addView(viewPager, layoutParamsMM())

        addView(layout, layoutParamsMM())
        setDraggableView(toolbar)

        SortDelegate(sortButton) { sortType -> adapter.setSortType(viewPager.currentItem, sortType) }
    }

    override fun onKeyStroke(key: Int)
    {
        if (key == KeyEvent.KEYCODE_P)
        {
            isPinned = !isPinned
        }
    }

    class ActionPagerAdapter(context: Context, repository: Repository) : FastViewPagerAdapter()
    {
        private val view1 = ActionsView(context, repository)
        private val view2 = FavoriteView(context, repository)

        override fun getItem(position: Int): View
        {
            return when (position)
            {
                0    -> view1
                else -> view2
            }
        }

        fun setSortType(position: Int, sortType: Int)
        {
            (getItem(position) as ISortable).sortData(sortType)
        }

        override fun getCount(): Int
        {
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence
        {
            return when (position)
            {
                0    -> "Actions"
                else -> "Starred"
            }
        }
    }
}