package com.toolsfordevs.fast.core.ui.widget

import android.content.Context
import android.widget.LinearLayout
import com.toolsfordevs.fast.core.FastColor
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.core.ext.layoutParamsMM
import com.toolsfordevs.fast.core.ext.layoutParamsMW
import com.toolsfordevs.fast.core.ext.linearLayoutParamsMWe
import com.toolsfordevs.fast.core.ext.vLinearLayout
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.modules.androidx.ViewPager
import com.toolsfordevs.fast.modules.androidx.tabs.TabLayout
import com.toolsfordevs.fast.core.ui.widget.adapter.FastViewPagerAdapter
import kotlin.reflect.KMutableProperty0

@Keep
abstract class FastTabbedDialog(context:Context, val tabIndexPrefs: KMutableProperty0<Int>? = null) : FastDialog(context)
{
    private val adapter: FastViewPagerAdapter by lazy { buildViewPagerAdapter(context) }
    protected val layout:LinearLayout
    protected val tabLayout:TabLayout
    protected val viewPager:ViewPager

    init
    {
        layout = vLinearLayout(context)

        tabLayout = TabLayout(context)
        tabLayout.setTabTextColors(0xCCFFFFFF.toInt(), 0xFFFFFFFF.toInt())
        tabLayout.setBackgroundColor(FastColor.colorPrimary)
        tabLayout.setSelectedTabIndicatorColor(FastColor.colorAccent)
        //        tabLayout.setSelectedTabIndicatorColor(ColorUtil.darken(FastColor.colorPrimary, 0.35f))
        tabLayout.setSelectedTabIndicatorHeight(Dimens.dp(3))

        viewPager = ViewPager(context)
        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        tabLayout.setupWithViewPager(viewPager)
        viewPager.adapter = adapter

        tabIndexPrefs?.let { viewPager.currentItem = it.get() }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int)
            {

            }

            override fun onPageSelected(position: Int)
            {
                tabIndexPrefs?.set(position)
            }

            override fun onPageScrollStateChanged(state: Int)
            {

            }

        })

        layout.addView(tabLayout, layoutParamsMW())
        layout.addView(viewPager, linearLayoutParamsMWe(1f))

        addContentView(layout, layoutParamsMM())
    }

    abstract fun buildViewPagerAdapter(context: Context): FastViewPagerAdapter
}