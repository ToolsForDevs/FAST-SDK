package com.toolsfordevs.fast.plugins.sessionmanager

import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.plugins.sessionmanager.model.StateItem

@Keep
class FastSession internal constructor(private val repository:Repository)
{
    fun add(category: String, name: String, value: String)
    {
        repository.add(StateItem(category, name, value))
    }
}