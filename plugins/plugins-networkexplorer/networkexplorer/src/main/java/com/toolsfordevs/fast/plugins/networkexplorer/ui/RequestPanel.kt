package com.toolsfordevs.fast.plugins.networkexplorer.ui

import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.toolsfordevs.fast.core.ext.*
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.core.ui.ext.setPaddingEnd
import com.toolsfordevs.fast.core.ui.ext.setPaddingStart
import com.toolsfordevs.fast.core.ui.widget.BottomSheetLayout
import com.toolsfordevs.fast.core.ui.widget.FastPanelToolbar
import com.toolsfordevs.fast.core.ui.widget.FastTabLayout
import com.toolsfordevs.fast.core.ui.widget.adapter.FastViewPagerAdapter
import com.toolsfordevs.fast.modules.androidx.ViewPager
import com.toolsfordevs.fast.plugins.networkexplorer.core.model.Request

internal class RequestPanel(context: Context, request: Request) : BottomSheetLayout(context)
{
    init
    {
        val toolbar = FastPanelToolbar(context)
        val title = TextView(context).apply {
            setTextColor(MaterialColor.WHITE_87)
            textSize = 16f
            setPaddingStart(16.dp)
            setPaddingEnd(16.dp)
            text = "${request.method} ${request.endpoint}"
            maxLines = 1
            ellipsize = TextUtils.TruncateAt.END
        }
        toolbar.buttonLayout.addView(title, linearLayoutParamsWW().apply { gravity = Gravity.CENTER_VERTICAL })

        val tabLayout = FastTabLayout(context)
        val viewPager = ViewPager(context)

        tabLayout.setupWithViewPager(viewPager)
        viewPager.adapter = ViewPagerAdapter(context, request)

        viewPager.currentItem = Prefs.selectedTab
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int)
            {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int)
            {

            }

            override fun onPageSelected(page: Int)
            {
                Prefs.selectedTab = page
            }

        })

        val layout = vLinearLayout(context)

        layout.addView(toolbar, layoutParamsMW())
        layout.addView(tabLayout, layoutParamsMV(56.dp))
        layout.addView(viewPager, layoutParamsMM())
        addView(layout, layoutParamsMM())

        setDraggableView(toolbar)
        setCollapsedHeight(400.dp)
    }

    private class ViewPagerAdapter(context:Context, request: Request) : FastViewPagerAdapter()
    {
        private val requestView = RequestView(context, request)
        private val responseView = ResponseView(context, request.response!!)

        override fun getItem(position: Int): View
        {
            return when (position)
            {
                0    -> requestView
                else -> responseView
            } as View
        }

        override fun getCount(): Int
        {
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence
        {
            return when (position)
            {
                0    -> "Request"
                else -> "Response"
            }
        }
    }
}