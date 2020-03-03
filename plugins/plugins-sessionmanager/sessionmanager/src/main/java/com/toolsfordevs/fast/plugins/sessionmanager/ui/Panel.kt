package com.toolsfordevs.fast.plugins.sessionmanager.ui

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.toolsfordevs.fast.core.ext.*
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.core.ui.ext.setPaddingHorizontal
import com.toolsfordevs.fast.core.ui.ext.show
import com.toolsfordevs.fast.core.ui.widget.BottomSheetLayout
import com.toolsfordevs.fast.core.ui.widget.FastPanelToolbar
import com.toolsfordevs.fast.core.ui.widget.FastTabLayout
import com.toolsfordevs.fast.core.ui.widget.adapter.FastViewPagerAdapter
import com.toolsfordevs.fast.core.util.FastSort
import com.toolsfordevs.fast.modules.androidx.ViewPager
import com.toolsfordevs.fast.modules.androidx.tabs.TabLayout
import com.toolsfordevs.fast.plugins.sessionmanager.R
import com.toolsfordevs.fast.plugins.sessionmanager.Repository
import com.toolsfordevs.fast.plugins.sessionmanager.SessionManagerPlugin
import java.lang.ref.WeakReference

internal class Panel(context: Context) : BottomSheetLayout(context)
{
    private val repository = Repository()

    private val tabs:FastTabLayout = FastTabLayout(context).apply { tabMode = TabLayout.MODE_SCROLLABLE }
    private val viewPager:ViewPager = ViewPager(context)

    init
    {
        val layout = vLinearLayout(context)

        val toolbar = FastPanelToolbar(context)
        val title = TextView(context).apply {
            textSize = 16f
            text = "Session"
            setTextColor(MaterialColor.WHITE_100)
            gravity = Gravity.CENTER_VERTICAL
            setPaddingHorizontal(16.dp)
        }

        toolbar.buttonLayout.addView(title, 0, linearLayoutParamsWeW(1f).apply { gravity = Gravity.CENTER_VERTICAL })

        tabs.show(repository.getCategoryCount() > 1)

        tabs.setupWithViewPager(viewPager)
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

        toolbar.createButton(R.drawable.fast_plugin_sessionmanager_ic_refresh).setOnClickListener {
            refresh()
        }

        toolbar.createButton(R.drawable.fast_plugin_sessionmanager_ic_sort).makePopupMenu(listOf("Default", "A -> Z", "Z -> A")) { selectedIndex ->
            Prefs.setTabSortType(
                    repository.getCategories()[viewPager.currentItem], when (selectedIndex)
            {
                0    -> FastSort.DEFAULT
                1    -> FastSort.ALPHA_ASC
                2    -> FastSort.ALPHA_DESC
                else -> throw Exception("")
            }
            )

            (viewPager.adapter as ViewPagerAdapter).refresh()
        }

        toolbar.addView(tabs, layoutParamsMW())
        layout.addView(toolbar, layoutParamsMW())
        layout.addView(viewPager, layoutParamsMM())

        addView(layout, layoutParamsMM())
        setCollapsedHeight(500.dp)
    }

    private fun refresh()
    {
        SessionManagerPlugin.refreshData(repository)
        tabs.show(repository.getCategoryCount() > 1)
        viewPager.adapter = ViewPagerAdapter()
        viewPager.currentItem = Prefs.selectedTab
    }

    override fun onAttachedToWindow()
    {
        super.onAttachedToWindow()
        refresh()
    }

    private inner class ViewPagerAdapter : FastViewPagerAdapter()
    {
        private val views: Array<WeakReference<StateCategoryView>?> = arrayOfNulls(repository.getCategoryCount())

        override fun getCount(): Int
        {
            return views.size
        }

        override fun getItem(position: Int): View
        {
            return StateCategoryView(context, repository, repository.getCategories()[position]).also { views[position] = WeakReference(it) }
        }

        override fun getPageTitle(position: Int): CharSequence
        {
            return repository.getCategories()[position]
        }

        fun refresh()
        {
            for (i in 0 until count)
            {
                if (views.size > i)
                    views[i]?.get()?.refresh()
            }
        }
    }
}