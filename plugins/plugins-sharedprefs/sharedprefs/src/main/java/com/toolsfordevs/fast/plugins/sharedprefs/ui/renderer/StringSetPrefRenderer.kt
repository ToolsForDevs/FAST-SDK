package com.toolsfordevs.fast.plugins.sharedprefs.ui.renderer

import android.content.Context
import android.graphics.Typeface
import android.text.TextUtils
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.toolsfordevs.fast.core.FastManager
import com.toolsfordevs.fast.core.ext.dp
import com.toolsfordevs.fast.core.ext.makePopupMenu
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.core.ui.ext.setPadding
import com.toolsfordevs.fast.core.util.ResUtils
import com.toolsfordevs.fast.core.util.FastSort
import com.toolsfordevs.fast.plugins.sharedprefs.PrefManager
import com.toolsfordevs.fast.plugins.sharedprefs.R
import com.toolsfordevs.fast.plugins.sharedprefs.ui.StringSetPanel
import com.toolsfordevs.fast.plugins.sharedprefs.ui.model.StringSetPref
import java.util.*

internal class StringSetPrefRenderer(parent: ViewGroup) :
        PrefRenderer<StringSetPref, LinearLayout>(parent, LinearLayout(parent.context))
{
    private val viewPool = arrayListOf<TextView>()

    private val moreItem:TextView by lazy { createTextView(parent.context).apply { typeface = Typeface.create(Typeface.DEFAULT, Typeface.ITALIC) } }

    private val max = 10

    init
    {
        dataView.orientation = LinearLayout.VERTICAL
        dataView.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
        dataView.dividerDrawable = ResUtils.getDrawable(R.drawable.fast_plugin_sharedprefs_fast_sharedprefs_stringset_divider)
        dataView.setBackgroundResource(R.drawable.fast_selectable_item_background)
    }

    override fun bind(data: StringSetPref, position: Int)
    {
        super.bind(data, position)

        val set = PrefManager.getSharedPreferences().getStringSet(data.name, emptySet())!!

        if (viewPool.size > set.size)
        {
            val start = viewPool.size - 1
            val diff = viewPool.size - set.size

            for (i in (start - diff) until start)
            {
                dataView.removeView(viewPool[i])
            }
        }

        if (set.size > viewPool.size)
        {
            for (i in 0 until set.size - viewPool.size)
            {
                if (viewPool.size == max)
                    break

                createTextView(dataView.context).also {
                    viewPool.add(it)
                    dataView.addView(it)
                }
            }
        }

        displaySorted(set, FastSort.DEFAULT)

        if (set.size > 10)
        {
            if (moreItem.parent == null)
                dataView.addView(moreItem)

            val diff = set.size - max
            moreItem.text = "+ $diff more. Click to view all"
        }
        else if (moreItem.parent!= null)
        {
            dataView.removeView(moreItem)
        }

        dataView.setOnClickListener { FastManager.addView(StringSetPanel(dataView.context, data.name)) }

        overflowButton.makePopupMenu(listOf("Delete entry", "Sort Default", "Sort A->Z", "Sort Z->A")) { selectedIndex ->
            when (selectedIndex)
            {
                0 -> PrefManager.deleteKey(data.name)
                1 -> displaySorted(set, FastSort.DEFAULT)
                2 -> displaySorted(set, FastSort.ALPHA_ASC)
                3 -> displaySorted(set, FastSort.ALPHA_DESC)
            }
        }
    }

    private fun displaySorted(set: MutableSet<String>, sort: String)
    {
        val list = set.toMutableList()

        if (sort == FastSort.ALPHA_ASC)
            list.sortBy { it.toLowerCase(Locale.getDefault()) }
        else if (sort == FastSort.ALPHA_DESC)
            list.sortByDescending { it.toLowerCase(Locale.getDefault()) }

        list.forEachIndexed { index, s -> if (index < max) viewPool[index].text = s }
    }

    private fun createTextView(context: Context): TextView
    {
        return TextView(context).apply {
            setTextColor(MaterialColor.BLACK_87)
            maxLines = 5
            ellipsize = TextUtils.TruncateAt.END
            setPadding(8.dp)
        }
    }
}