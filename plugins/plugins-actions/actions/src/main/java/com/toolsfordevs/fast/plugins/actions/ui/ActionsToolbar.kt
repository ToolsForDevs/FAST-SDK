package com.toolsfordevs.fast.plugins.actions.ui

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.toolsfordevs.fast.core.ext.hLinearLayout
import com.toolsfordevs.fast.core.ext.layoutParamsMW
import com.toolsfordevs.fast.core.ext.linearLayoutParamsMM
import com.toolsfordevs.fast.core.ext.linearLayoutParamsWeW
import com.toolsfordevs.fast.core.ui.widget.FastToolbar
import com.toolsfordevs.fast.plugins.actions.base.Action
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.plugins.actions.R


internal class ActionsToolbar(context: Context) : FastToolbar(context)
{
    private lateinit var autoFireButton: ImageButton
    private lateinit var actionWrapper: ActionWrapper<Any>

    init {
        orientation  = VERTICAL
        layoutParams.height = LayoutParams.WRAP_CONTENT
        gravity = Gravity.CENTER
    }

    fun setup(actionWrapper: ActionWrapper<Any>, delegate: ActionDelegate<*, Action<*>>)
    {
        this.actionWrapper = actionWrapper

        val layout = hLinearLayout(context, layoutParamsMW()).apply {
            gravity = Gravity.CENTER_VERTICAL
        }

        val dp8 = Dimens.dp(8)

        val text = TextView(context)
        text.setTextColor(Color.WHITE)
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
        text.text = actionWrapper.name
        text.setPadding(dp8, dp8, dp8, dp8)

        layout.addView(text, linearLayoutParamsWeW(1f))

        autoFireButton = createButton(R.drawable.fast_actions_ic_fire, View.NO_ID, false)
        autoFireButton.contentDescription = "Automatically fire callback on value change"
        autoFireButton.imageAlpha = if (Prefs.autoFireChanges) 0xFF else 0x88
        autoFireButton.setOnClickListener {
            toggleAutoFire()
        }
        autoFireButton.setOnLongClickListener {
            Toast.makeText(context, "Automatically fire callback on value change", Toast.LENGTH_SHORT).show()
            true
        }

        val fireButton = createButton(R.drawable.fast_actions_ic_play, View.NO_ID, false)
        autoFireButton.contentDescription = "Fire callback"
        fireButton.setOnClickListener {
            play()
        }

        layout.addView(autoFireButton)
        layout.addView(fireButton)
        addView(layout)

        val v = delegate.createView(this)
        v.minimumHeight = Dimens.dp(56)
        if (v.layoutParams == null)
            v.layoutParams = linearLayoutParamsMM()

        actionWrapper.action.reset()
        delegate.internalBind(actionWrapper.action)
        delegate.listener = { if (Prefs.autoFireChanges) actionWrapper.callback(actionWrapper.action.value) }

        addView(v)
    }

    private fun toggleAutoFire()
    {
        Prefs.autoFireChanges = !Prefs.autoFireChanges
        autoFireButton.imageAlpha = if (Prefs.autoFireChanges) 0xFF else 0x88
    }

    private fun play()
    {
        actionWrapper.callback(actionWrapper.action.value)
    }

    override fun onKeyStroke(key: Int)
    {
        if (key == KeyEvent.KEYCODE_A)
        {
            toggleAutoFire()
        }
        else if (key == KeyEvent.KEYCODE_P)
        {
            play()
        }
    }
}