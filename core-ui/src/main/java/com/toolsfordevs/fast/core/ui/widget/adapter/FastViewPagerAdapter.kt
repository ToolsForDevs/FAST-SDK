package com.toolsfordevs.fast.core.ui.widget.adapter

import android.view.View
import android.view.ViewGroup
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.modules.androidx.PagerAdapter

@Keep
abstract class FastViewPagerAdapter : PagerAdapter()
{
    override fun instantiateItem(container: ViewGroup, position: Int): Any
    {
        val view = getItem(position)
        container.addView(view)
        return view
    }

    abstract fun getItem(position: Int): View

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any)
    {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean
    {
        return view == `object`
    }
}