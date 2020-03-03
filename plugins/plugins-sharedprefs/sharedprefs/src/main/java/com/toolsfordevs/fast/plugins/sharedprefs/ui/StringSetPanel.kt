package com.toolsfordevs.fast.plugins.sharedprefs.ui

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*
import com.toolsfordevs.fast.core.ext.*
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.core.ui.ext.setPadding
import com.toolsfordevs.fast.core.ui.widget.FastToolbar
import com.toolsfordevs.fast.core.widget.FastPanel
import com.toolsfordevs.fast.core.widget.FastPanelDelegate
import com.toolsfordevs.fast.modules.recyclerview.RendererAdapter
import com.toolsfordevs.fast.modules.recyclerview.ViewRenderer
import com.toolsfordevs.fast.core.util.FastSort
import com.toolsfordevs.fast.modules.androidx.recyclerview.ext.vRecyclerView
import com.toolsfordevs.fast.plugins.sharedprefs.PrefManager
import com.toolsfordevs.fast.plugins.sharedprefs.R

@SuppressLint("ViewConstructor")
internal class StringSetPanel(context: Context, key: String) : LinearLayout(context), FastPanel by FastPanelDelegate()
{
    private val adapter: RendererAdapter = RendererAdapter()

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

        addView(toolbar, layoutParamsMW())

        val recyclerView = vRecyclerView(context)
        addView(recyclerView, linearLayoutParamsMWe(1f))

        val set = PrefManager.getSharedPreferences().getStringSet(key, emptySet())!!

        val sortButton = toolbar.createButton(R.drawable.fast_plugin_sharedprefs_ic_sort)
        sortButton.makePopupMenu(listOf("Sort Default", "Sort A->Z", "Sort Z->A")) { selectedIndex ->
            when (selectedIndex)
            {
                0 -> displaySorted(set, FastSort.DEFAULT)
                1 -> displaySorted(set, FastSort.ALPHA_ASC)
                2 -> displaySorted(set, FastSort.ALPHA_DESC)
            }
        }

        adapter.addRenderer(String::class, ::StringRenderer)
        displaySorted(set, FastSort.DEFAULT)
        recyclerView.adapter = adapter


        val bottomBar = FastToolbar(context)

        val saveButton = makeButton("Save")
        val cancelButton = makeButton("Cancel")

        saveButton.setOnClickListener {
            val data = arrayListOf<String>()
            for (i in 0 until adapter.itemCount)
                data.add(adapter.getDataAtPosition(i) as String)

            PrefManager.getSharedPreferences().edit().putStringSet(key, data.toSet()).apply()
            dismiss()
        }

        cancelButton.setOnClickListener { dismiss() }

        bottomBar.addView(cancelButton, layoutParamsWM())
        bottomBar.addView(saveButton, layoutParamsWM())

        addView(bottomBar, layoutParamsMW())

        layoutParams = layoutParamsMM()
    }

    private fun displaySorted(set: MutableSet<String>, sort: String)
    {
        val list = set.toMutableList()

        if (sort == FastSort.ALPHA_ASC)
            list.sortWith(naturalOrder())
        else if (sort == FastSort.ALPHA_DESC)
            list.sortWith(reverseOrder())

        adapter.clear()
        adapter.addAll(list)
    }

    private fun makeButton(label: String): Button
    {
        return Button(context).apply {
            setBackgroundResource(R.drawable.fast_selectable_item_background)
            setTextColor(MaterialColor.WHITE_100)
            text = label
        }
    }

    private inner class StringRenderer(parent: ViewGroup) : ViewRenderer<String, LinearLayout>(LinearLayout(parent.context))
    {
        private val editText = EditText(parent.context).apply {
            setTextColor(MaterialColor.BLACK_87)
            setPadding(8.dp)
            background = null
            textSize = 14f
        }
        private val overflowButton = ImageButton(parent.context).apply {
            setImageResource(R.drawable.fast_plugin_sharedprefs_ic_overflow)
            setBackgroundResource(R.drawable.fast_selectable_item_background_borderless)
        }

        init
        {
            view.orientation = LinearLayout.HORIZONTAL

            view.addView(editText, linearLayoutParamsWeW(1f).apply { gravity = Gravity.CENTER_VERTICAL })
            view.addView(overflowButton, linearLayoutParamsVV(56.dp, 56.dp).apply { gravity = Gravity.TOP })

            view.layoutParams = layoutParamsMW()
        }

        override fun bind(data: String, position: Int)
        {
            editText.setText(data)
            overflowButton.makePopupMenu(listOf("Remove")) { selectedIndex -> adapter.remove(data) }

            // ToDo add TextWatcher to save new data when typing
        }
    }
}