package com.toolsfordevs.fast.modules.androidx.recyclerview.ext

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateInterpolator
import android.view.animation.OvershootInterpolator
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.FastRecyclerView
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.LinearLayoutManager
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.RecyclerView

fun hRecyclerView(context: Context, layoutParams: ViewGroup.LayoutParams? = null): FastRecyclerView
{
    return FastRecyclerView(context).apply {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        setHasFixedSize(true)
        layoutParams?.let {
            this.layoutParams = layoutParams
        }
    }
}

fun vRecyclerView(context: Context, layoutParams: ViewGroup.LayoutParams? = null, enableScrollbars: Boolean = true): RecyclerView
{
    val recyclerView = FastRecyclerView(context)

    return recyclerView.apply {
        layoutManager = LinearLayoutManager(context)
        setHasFixedSize(true)
        scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        layoutParams?.let {
            this.layoutParams = layoutParams
        }
    }
}

fun RecyclerView.pairWithFab(fab: View)
{
//    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
//    {
    var showAnimationRunning = false
    var hideAnimationRunning = false

    addOnScrollListener(object : RecyclerView.OnScrollListener()
                        {

                            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int)
                            {
                                super.onScrolled(recyclerView, dx, dy)

                                // 2 is to avoid showing/clearing on unwanted moves
                                if (dy > 2)
                                {
                                    if (!hideAnimationRunning && fab.scaleX > 0f)
                                    {
                                        fab.animate().scaleX(0f).scaleY(0f).setInterpolator(AnticipateInterpolator())
                                            .withStartAction { hideAnimationRunning = true }
                                            .withEndAction {
                                                hideAnimationRunning = false
                                                fab.visibility = View.GONE
                                            }
                                    }
                                }
                                else if (dy < -2)
                                {
                                    if (!showAnimationRunning && fab.scaleX < 1f)
                                    {
                                        fab.animate().scaleX(1f).scaleY(1f).setInterpolator(OvershootInterpolator())
                                            .withStartAction {
                                                showAnimationRunning = true
                                                fab.visibility = View.VISIBLE
                                            }
                                            .withEndAction { showAnimationRunning = false }
                                    }
                                }
                            }
                        })
//    }
}