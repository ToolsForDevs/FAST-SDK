package com.toolsfordevs.fast.plugins.logger.ui

import com.toolsfordevs.fast.modules.recyclerview.RendererAdapter
import com.toolsfordevs.fast.plugins.logger.ui.items.LogEntryWrapper


internal class LogRendererAdapter : RendererAdapter()
{
    override fun getItemViewType(position: Int): Int
    {
        val item = data[position]

        val isLogEntryWrapper = item is LogEntryWrapper<*>

        val data = if (isLogEntryWrapper) (item as LogEntryWrapper<*>).formatter
        else item

        for (type in typeMap.keys.reversed())
        {
            if (type.isInstance(data))
                return typeMap.getValue(type)
        }

        return DEFAULT_TYPE
    }
}
