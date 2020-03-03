package com.toolsfordevs.fast.plugins.overlay.ui

import android.content.Context
import android.view.View
import com.toolsfordevs.fast.core.ext.*
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.modules.androidx.ViewPager
import com.toolsfordevs.fast.modules.androidx.tabs.TabLayout
import com.toolsfordevs.fast.core.ui.widget.BottomSheetLayout
import com.toolsfordevs.fast.core.ui.widget.FastTabLayout
import com.toolsfordevs.fast.core.ui.widget.FastToolbar
import com.toolsfordevs.fast.core.ui.widget.adapter.FastViewPagerAdapter
import com.toolsfordevs.fast.plugins.overlay.ui.view.GridOptionsView
import com.toolsfordevs.fast.plugins.overlay.ui.view.GutterOptionsView
import com.toolsfordevs.fast.plugins.overlay.ui.view.ViewOptionsView
import com.toolsfordevs.fast.plugins.overlay.R


internal class OverlayPanel(context: Context) : BottomSheetLayout(context)
{
    private var lockModeCallback: (() -> Unit)? = null

    init
    {
        setCollapsedHeight(Dimens.dp(350))

        val vLayout = vLinearLayout(context)
        val toolbar = FastToolbar(context)

        val tabLayout = FastTabLayout(context)
        tabLayout.tabMode = TabLayout.MODE_FIXED

        val viewPager = ViewPager(context)

        tabLayout.setupWithViewPager(viewPager)

        viewPager.adapter = OverlayPagerAdapter(context)
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


        toolbar.addView(tabLayout, linearLayoutParamsWeM(1f))

        toolbar.createButton(R.drawable.fast_overlay_ic_lock, View.NO_ID, true).apply {
            isSelected = Prefs.lockedMode
            setOnClickListener {
                Prefs.lockedMode = !Prefs.lockedMode
                isSelected = !isSelected
                lockModeCallback?.invoke()
            }
        }

        vLayout.addView(toolbar, layoutParamsMW())
        vLayout.addView(viewPager, layoutParamsMM())
        addView(vLayout, layoutParamsMM())

        setDraggableView(tabLayout)
    }

    fun setOnLockModeChange(callback: () -> Unit)
    {
        lockModeCallback = callback
    }

    private class OverlayPagerAdapter(context: Context) : FastViewPagerAdapter()
    {
        val view1 = ViewOptionsView(context)
        val view2 = GridOptionsView(context)
        val view3 = GutterOptionsView(context)

        override fun getItem(position: Int): View
        {
            return when (position)
            {
                1    -> view2
                2    -> view3
                else -> view1
            }
        }

        override fun getCount(): Int
        {
            return 3
        }

        override fun getPageTitle(position: Int): CharSequence
        {
            return when (position)
            {
                1    -> "Grid"
                2    -> "Gutter"
                else -> "View"
            }
        }
    }
}