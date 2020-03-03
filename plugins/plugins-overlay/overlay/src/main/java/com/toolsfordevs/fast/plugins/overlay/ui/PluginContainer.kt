package com.toolsfordevs.fast.plugins.overlay.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.view.KeyEvent
import android.view.View
import android.widget.FrameLayout
import com.toolsfordevs.fast.core.FastManager
import com.toolsfordevs.fast.core.activity.OnBackPressedCallback
import com.toolsfordevs.fast.core.ext.layoutParamsMM
import com.toolsfordevs.fast.core.widget.FastPanel
import com.toolsfordevs.fast.core.widget.FastPanelDelegate
import com.toolsfordevs.fast.modules.viewhierarchyexplorer.ViewHierarchyExplorer
import com.toolsfordevs.fast.plugins.overlay.R
import com.toolsfordevs.fast.plugins.overlay.ui.overlay.DelegateViewOverlay
import com.toolsfordevs.fast.plugins.overlay.ui.widget.ChatHeadButton


internal class PluginContainer(context: Context) : FrameLayout(context), FastPanel by FastPanelDelegate()
{
    private val button = ChatHeadButton(context)
    private val panel: OverlayPanel = OverlayPanel(context) //by lazy { OverlayPanel(context) }

    private val explorer:ViewHierarchyExplorer by lazy { ViewHierarchyExplorer() }
    private val overlay: DelegateViewOverlay by lazy { DelegateViewOverlay(context) }

    private val backPressedCallback = object : OnBackPressedCallback(false)
    {
        override fun handleOnBackPressed()
        {
            closeSettings()
        }
    }

    init
    {
        layoutParams = layoutParamsMM()

        button.setImageResource(R.drawable.fast_overlay_ic_tune)
        button.setOnClickListener {
            openSettings()
        }

        addView(button)
        button.post { button.getEnterTransition()!!.start() }

        overlay.isLocked = Prefs.lockedMode
        panel.setOnLockModeChange { overlay.isLocked = Prefs.lockedMode }

        backPressedCallback.enabled = false
    }

    private fun openSettings()
    {
        closeThenShow(button, panel)
        backPressedCallback.enabled = true
    }

    private fun closeSettings()
    {
        closeThenShow(panel, button)
        backPressedCallback.enabled = false
    }

    private fun closeThenShow(closePanel: FastPanel, openPanel: FastPanel)
    {
        closePanel.getExitTransition()!!.apply {
            addListener(object : AnimatorListenerAdapter()
            {
                override fun onAnimationEnd(animation: Animator?)
                {
                    animation?.removeAllListeners()
                    removeView(closePanel as View)

                    addView(openPanel as View)
                    openPanel.post { openPanel.getEnterTransition()!!.start() }
                }
            })
        }.start()
    }

    override fun onAttachedToWindow()
    {
        super.onAttachedToWindow()
        FastManager.addCallback(backPressedCallback)

        overlay.onClickCallback = { x, y ->
            val v = explorer.getViewAt((context as Activity).window.decorView, x, y)

            if (v != null)
                overlay.onViewClicked(v)
        }

        FastManager.addOverlay(overlay)
    }

    override fun onDetachedFromWindow()
    {
        super.onDetachedFromWindow()
        FastManager.removeCallback(backPressedCallback)
        FastManager.removeOverlay(overlay)
    }

    override fun onKeyStroke(key: Int)
    {
        if (key == KeyEvent.KEYCODE_S)
        {
            openSettings()
        }
        else if (key == KeyEvent.KEYCODE_DEL)
        {
            if (panel.parent != null)
                closeSettings()

        }
        else if (panel.parent != null)
        {
            //panel.onKeyStroke(key)
        }
    }
}