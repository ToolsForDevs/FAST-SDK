package com.toolsfordevs.fast.plugins.manifestexplorer.ui

import android.content.Context
import android.view.View
import com.toolsfordevs.fast.core.ext.dp
import com.toolsfordevs.fast.core.ext.layoutParamsMM
import com.toolsfordevs.fast.core.ext.layoutParamsMW
import com.toolsfordevs.fast.core.ext.vLinearLayout
import com.toolsfordevs.fast.core.ui.ext.hide
import com.toolsfordevs.fast.core.ui.widget.BottomSheetLayout
import com.toolsfordevs.fast.core.ui.widget.FastPanelToolbar
import com.toolsfordevs.fast.core.ui.widget.FastTabLayout
import com.toolsfordevs.fast.core.ui.widget.adapter.FastViewPagerAdapter
import com.toolsfordevs.fast.modules.androidx.ViewPager
import com.toolsfordevs.fast.modules.androidx.tabs.TabLayout

internal class ManifestExplorerPanel(context: Context) : BottomSheetLayout(context)
{
    init
    {
        val layout = vLinearLayout(context)

        val toolbar = FastPanelToolbar(context)
        toolbar.buttonLayout.hide()

        val tabs = FastTabLayout(context)
        val viewPager = ViewPager(context)

        tabs.tabMode = TabLayout.MODE_SCROLLABLE
        tabs.setupWithViewPager(viewPager)
        val pagerAdapter = PagerAdapter(context)
        viewPager.adapter = pagerAdapter

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

        toolbar.addView(tabs)
        layout.addView(toolbar, layoutParamsMW())
        layout.addView(viewPager, layoutParamsMM())

        addView(layout, layoutParamsMM())
        setCollapsedHeight(500.dp)
    }

    private class PagerAdapter(context: Context) : FastViewPagerAdapter()
    {
        private val view1 = ApplicationView(context)
        private val view2 = PackageView(context)
        private val view3 = ActivitiesView(context)
        private val view4 = ConfigurationsView(context)
        private val view5 = FeaturesView(context)
        private val view6 = PermissionsView(context)
        private val view7 = ProvidersView(context)
        private val view8 = ReceiversView(context)
        private val view9 = RequestedPermissionsView(context)
        private val view10 = ServicesView(context)

        override fun getCount(): Int
        {
            return 10
        }

        override fun getItem(position: Int): View
        {
            return when (position)
            {
                0    -> view1
                1    -> view2
                2    -> view3
                3    -> view4
                4    -> view5
                5    -> view6
                6    -> view7
                7    -> view8
                8    -> view9
                9    -> view10
                else -> throw Exception()
            }
        }

        override fun getPageTitle(position: Int): CharSequence
        {
            return when (position)
            {
                0    -> "Application"
                1    -> "Package"
                2    -> "Activities"
                3    -> "Configurations"
                4    -> "Features"
                5    -> "Permissions"
                6    -> "Providers"
                7    -> "Receivers"
                8    -> "Req Permissions"
                9    -> "Services"
                else -> throw Exception()
            }
        }
    }
}