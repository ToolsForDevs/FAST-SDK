package com.toolsfordevs.fast.plugins.logger.ui.prefs

import com.toolsfordevs.fast.plugins.logger.model.Priority
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet


internal class FilterProfile(val id: String, var name: String, var priorities: HashSet<Int>, var tags: ArrayList<TagFilter>)
{
    constructor(name: String) : this(UUID.randomUUID().toString(),
                                     name,
                                     hashSetOf(Priority.DEFAULT.value, Priority.DEBUG.value, Priority.INFO.value, Priority.WARNING.value, Priority.ERROR.value, Priority.WTF.value),
                                     arrayListOf())
}
