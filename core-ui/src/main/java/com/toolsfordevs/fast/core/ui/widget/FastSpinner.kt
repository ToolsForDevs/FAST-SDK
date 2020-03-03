package com.toolsfordevs.fast.core.ui.widget

import android.app.AlertDialog
import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.TextView
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.core.ext.layoutParamsWV
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.core.ui.R
import com.toolsfordevs.fast.core.ui.ext.setPaddingStart

@Suppress("NAME_SHADOWING")
@Keep
class FastSpinner(context: Context) : TextView(context)
{
    init
    {
        setTextColor(MaterialColor.BLACK_100)
        gravity = Gravity.CENTER_VERTICAL
        textSize = 16f
        setPaddingStart(Dimens.dp(8))
        setBackgroundResource(R.drawable.fast_selectable_item_background)
        setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.fast_core_ic_menu_down, 0)
        compoundDrawablePadding = Dimens.dp(8)

        layoutParams = layoutParamsWV(Dimens.dp(48))
    }

    fun setLightTheme()
    {
        setTextColor(MaterialColor.WHITE_100)
        setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.fast_core_ic_menu_down_light, 0)
    }

    fun setAdapter(labels: List<String>, selectedIndex: Int, callback: (selectedIndex: Int) -> Unit)
    {
        maxLines = 1
        ellipsize = TextUtils.TruncateAt.END

        if (selectedIndex >= 0 && labels.isNotEmpty())
        {
            text = if (selectedIndex in labels.indices) labels[selectedIndex] else labels[0]

            setOnClickListener { v ->
                val popup = PopupMenu(v.context, v)

                val items = arrayListOf<MenuItem>()

                for (label in labels) items.add(popup.menu.add(label))

                popup.setOnMenuItemClickListener { item ->
                    callback(items.indexOf(item))
                    text = item.title
                    true
                }

                popup.show()
            }
        }
        else
        {
            text = ""
            setOnClickListener(null)
        }
    }

    fun setAdapter(labels: List<String>, selectedIndexes: List<Int>, callback: (selectedIndexes: List<Int>) -> Unit)
    {
        maxLines = Int.MAX_VALUE
        ellipsize = null

        if (labels.isNotEmpty())
        {
            val selectedLabels = selectedIndexes.map { labels[it] }
            text = if (selectedLabels.isNotEmpty()) selectedLabels.joinToString("\n") else "None"

            val tmpSelectedIndexes = arrayListOf<Int>()
            tmpSelectedIndexes.addAll(selectedIndexes)

            setOnClickListener {
                val indices = BooleanArray(labels.size)

                for (i in indices.indices)
                    indices[i] = tmpSelectedIndexes.contains(i)

                AlertDialog.Builder(context)
                    .setMultiChoiceItems(labels.toTypedArray(), indices) { dialog, which, isChecked -> indices[which] = isChecked }
                    .setPositiveButton("OK") { dialog, which ->

                        tmpSelectedIndexes.clear()
                        for (i in indices.indices)
                        {
                            if (indices[i])
                                tmpSelectedIndexes.add(i)
                        }

                        callback(tmpSelectedIndexes)
                        val selectedLabels = tmpSelectedIndexes.map { labels[it] }
                        text = if (selectedLabels.isNotEmpty()) selectedLabels.joinToString("\n") else "None"
                    }
                    .setCancelable(true)
                    .show()
            }
        }
        else
        {
            text = ""
            setOnClickListener(null)
        }
    }
}