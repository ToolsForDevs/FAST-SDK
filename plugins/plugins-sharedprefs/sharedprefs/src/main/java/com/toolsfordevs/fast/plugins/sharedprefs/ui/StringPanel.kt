package com.toolsfordevs.fast.plugins.sharedprefs.ui

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.widget.*
import com.toolsfordevs.fast.core.ext.*
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.core.ui.widget.FastToolbar
import com.toolsfordevs.fast.core.widget.FastPanel
import com.toolsfordevs.fast.core.widget.FastPanelDelegate
import com.toolsfordevs.fast.plugins.sharedprefs.PrefManager
import com.toolsfordevs.fast.plugins.sharedprefs.R

@SuppressLint("ViewConstructor")
internal class StringPanel(context: Context, key:String) : LinearLayout(context), FastPanel by FastPanelDelegate()
{
    init
    {
        orientation = VERTICAL
        setBackgroundColor(MaterialColor.WHITE_100)

        val toolbar = FastToolbar(context)

        val title = TextView(context).apply {
            setTextColor(MaterialColor.WHITE_100)
            text = "Edit $key"
            ellipsize = TextUtils.TruncateAt.MARQUEE
            gravity = Gravity.CENTER_VERTICAL
        }

        toolbar.addView(title, linearLayoutParamsWeM(1f).apply { leftMargin = 16.dp })

        val overflowButton = toolbar.createButton(R.drawable.fast_plugin_sharedprefs_ic_overflow_light)
        overflowButton.makePopupMenu(listOf("RAW (default)", "JSON", "XML"))

        addView(toolbar, layoutParamsMW())

        val scrollView = ScrollView(context).apply {
            isFillViewport = true
        }

        val editText = EditText(context).apply {
            gravity = Gravity.TOP
            textSize = 12f
            background = null
        }
        
       editText.setText(PrefManager.getSharedPreferences().getString(key, ""))

        scrollView.addView(editText, linearLayoutParamsMM())
        addView(scrollView, linearLayoutParamsMWe(1f))

        val bottomBar = FastToolbar(context)

        val saveButton = makeButton("Save")
        val cancelButton = makeButton("Cancel")

        saveButton.setOnClickListener {
            PrefManager.getSharedPreferences().edit().putString(key, editText.text.toString()).apply()
            dismiss()
        }

        cancelButton.setOnClickListener { dismiss() }

        bottomBar.addView(cancelButton, layoutParamsWM())
        bottomBar.addView(saveButton, layoutParamsWM())

        addView(bottomBar, layoutParamsMW())

        layoutParams = layoutParamsMM()
    }

    private fun makeButton(label:String):Button
    {
        return Button(context).apply {
            setBackgroundResource(R.drawable.fast_selectable_item_background)
            setTextColor(MaterialColor.WHITE_100)
            text = label
        }
    }
}