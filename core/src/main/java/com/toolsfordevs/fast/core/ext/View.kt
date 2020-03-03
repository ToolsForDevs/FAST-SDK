package com.toolsfordevs.fast.core.ext

import android.view.View
import android.widget.PopupMenu

fun View.makePopupMenu(labels: List<String>, callback: ((selectedIndex:Int) -> Unit)? = null)
{
    setOnClickListener { v ->
        val popup = PopupMenu(v.context, v)

        val items = labels.map { popup.menu.add(it) }

        popup.setOnMenuItemClickListener { item ->
            callback?.invoke(items.indexOf(item))
            true
        }

        popup.show()
    }
}

fun View.makeCheckablePopupMenu(labels: List<String>, checked:List<Boolean>, callback: ((selectedIndex:Int) -> Unit)? = null)
{
    setOnClickListener { v ->
        val popup = PopupMenu(v.context, v)

        val items = labels.mapIndexed { index, label -> popup.menu.add(label).apply { isChecked = checked[index] } }

        popup.setOnMenuItemClickListener { item ->
            item.isChecked = !item.isChecked
            callback?.invoke(items.indexOf(item))
            true
        }

        popup.show()
    }
}