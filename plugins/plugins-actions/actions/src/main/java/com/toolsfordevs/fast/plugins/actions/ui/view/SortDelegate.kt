package com.toolsfordevs.fast.plugins.actions.ui.view

import android.view.View
import com.toolsfordevs.fast.core.ext.makePopupMenu
import com.toolsfordevs.fast.plugins.actions.ui.ActionWrapper


internal class SortDelegate(button: View, callback: (sortType: Int) -> Unit)
{
    init
    {
        button.makePopupMenu(listOf("A -> Z", "Z -> A", "Last added first", "First added first")) { selectedIndex ->
            callback.invoke(
                    when (selectedIndex)
                    {
                        0    -> DESCENDING
                        1    -> ASCENDING
                        2    -> LIFO
                        3    -> FIFO
                        else -> DESCENDING
                    }
            )
        }
    }

    companion object
    {
        const val DESCENDING = 0
        const val ASCENDING = 1
        const val LIFO = 2
        const val FIFO = 3

        fun sort(sortType: Int, actions: List<ActionWrapper<*>>): List<ActionWrapper<*>>
        {
            return when (sortType)
            {
                DESCENDING -> actions.sortedBy { it.name }
                ASCENDING  -> actions.sortedBy { it.name }.reversed()
                LIFO       -> actions.reversed()
                FIFO       -> actions
                else       -> actions.reversed()
            }
        }
    }
}