package com.toolsfordevs.fast.modules.restart

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.core.ext.*
import com.toolsfordevs.fast.core.ui.KeyboardShorcutManager
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.core.ui.ext.hide
import com.toolsfordevs.fast.core.ui.ext.setPaddingHorizontal
import com.toolsfordevs.fast.core.ui.ext.show
import com.toolsfordevs.fast.core.ui.widget.BottomSheetLayout
import com.toolsfordevs.fast.core.ui.widget.FastKeyboardShortcutView
import com.toolsfordevs.fast.core.ui.widget.FastPanelToolbar
import com.toolsfordevs.fast.modules.androidx.recyclerview.ext.vRecyclerView
import com.toolsfordevs.fast.modules.recyclerview.RendererAdapter
import com.toolsfordevs.fast.modules.recyclerview.ViewRenderer
import kotlin.math.min

@SuppressLint("ViewConstructor")
@Keep
class FastRestartPanel(context: Context, customTitle: String? = null, customOptions: List<CustomRestartItem>? = null) : BottomSheetLayout(context)
{
    private val adapter = RendererAdapter()
    private val listeners = arrayListOf<OnItemClickListener>()

    var showKeyboardShortcuts = true
        set(value)
        {
            field = value
            adapter.notifyDataSetChanged()
        }

    init
    {
        val layout = vLinearLayout(context)

        val toolbar = FastPanelToolbar(context)

        val title = TextView(context).apply {
            setTextColor(MaterialColor.WHITE_87)
            textSize = 16f
            text = customTitle ?: "Restart"
            gravity = Gravity.CENTER_VERTICAL
            setPaddingHorizontal(16.dp)
        }

        toolbar.buttonLayout.hide()
        toolbar.addView(title, layoutParamsMV(56.dp))
        layout.addView(toolbar, layoutParamsMW())


        val recyclerView = vRecyclerView(context)
        recyclerView.isVerticalScrollBarEnabled = true
        recyclerView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY

        adapter.addRenderer(RestartItem::class, ::RestartRenderer)
        addItem("Kill activity")
        addItem("Restart activity (no intent data)")
        addItem("Restart activity (same intent data)")
        addItem("Kill app")
        addItem("Restart app")
        addItem("Clear data & kill app")
        addItem("Clear data & restart app")

        customOptions?.let {
            for (item in it)
                addItem(item.name, item.callback)
        }

        recyclerView.adapter = adapter

        layout.addView(recyclerView, layoutParamsMM())

        addView(layout, layoutParamsMM())
        setCollapsedHeight(min(9, adapter.itemCount + 1) * 56.dp + 12.dp)
    }

    private fun addItem(name: String, callback: (() -> Unit)? = null)
    {
        val key = KeyboardShorcutManager.lowercaseKeys[adapter.itemCount]
        adapter.add(RestartItem(name, key.name, key.keyCode, callback))
    }

    override fun onKeyStroke(key: Int)
    {
        when (key)
        {
            KeyEvent.KEYCODE_A ->
            {
                dismiss()
                FastRestartOptions.killActivity()
            }
            KeyEvent.KEYCODE_B ->
            {
                dismiss()
                FastRestartOptions.restartActivityClearIntent()
            }
            KeyEvent.KEYCODE_C ->
            {
                dismiss()
                FastRestartOptions.restartActivitySameIntent()
            }
            KeyEvent.KEYCODE_D ->
            {
                dismiss()
                FastRestartOptions.killApp()
            }
            KeyEvent.KEYCODE_E ->
            {
                dismiss()
                FastRestartOptions.restartApp()
            }
            KeyEvent.KEYCODE_F ->
            {
                dismiss()
                FastRestartOptions.clearDataAndKillApp()
            }
            KeyEvent.KEYCODE_G ->
            {
                dismiss()
                FastRestartOptions.clearDataAndRestartApp()
            }
            else               ->
            {
                val item = adapter.getItems().firstOrNull { (it as RestartItem).hotKeyValue == key } as RestartItem?

                item?.let {
                    dismiss()
                    it.callback?.invoke()
                }
            }
        }
    }

    override fun dismiss()
    {
        for (listener in listeners)
            listener.onItemClick()

        super.dismiss()
    }

    fun addOnItemClickListener(listener: OnItemClickListener)
    {
        listeners.add(listener)
    }

    fun removeOnItemClickListener(listener: OnItemClickListener)
    {
        listeners.remove(listener)
    }

    private data class RestartItem(val name: String, val hotKeyLabel: String, val hotKeyValue: Int, val callback: (() -> Unit)? = null)

    @Keep
    data class CustomRestartItem(val name: String, val callback: () -> Unit)

    @Keep
    interface OnItemClickListener {
        fun onItemClick()
    }

    private inner class RestartRenderer(parent: ViewGroup) : ViewRenderer<RestartItem, LinearLayout>(hLinearLayout(parent.context)), OnClickListener
    {
        val name = TextView(parent.context).apply {
            setTextColor(MaterialColor.BLACK_87)
        }

        val hotKey = FastKeyboardShortcutView(parent.context)

        init
        {
            view.setBackgroundResource(R.drawable.fast_selectable_item_background)
            view.setPaddingHorizontal(16.dp)
            view.minimumHeight = 56.dp

            view.addView(name, linearLayoutParamsWeW(1f).apply { gravity = Gravity.CENTER_VERTICAL })
            view.addView(hotKey, linearLayoutParamsWW().apply { gravity = Gravity.CENTER_VERTICAL })

            view.setOnClickListener(this)
            view.layoutParams = layoutParamsMW()
        }

        override fun bind(data: RestartItem, position: Int)
        {
            name.text = data.name
            hotKey.text = data.hotKeyLabel

            hotKey.show(showKeyboardShortcuts)
        }

        override fun onClick(v: View?)
        {
            val item = getItem(adapterPosition) as RestartItem
            onKeyStroke(item.hotKeyValue)
        }
    }
}