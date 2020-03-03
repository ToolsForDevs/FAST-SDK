package com.toolsfordevs.fast.plugins.sessionmanager

import com.toolsfordevs.fast.core.data.BaseRepository
import com.toolsfordevs.fast.plugins.sessionmanager.model.StateItem

internal class Repository : BaseRepository<StateItem>()
{
    companion object
    {
        private const val CATEGORY_ALL = "All"
    }

    private val categories: ArrayList<String> = arrayListOf(CATEGORY_ALL)

    override fun add(item: StateItem)
    {
        super.add(item)

        if (categories.indexOf(item.category) == -1)
            item.category?.let { categories.add(it) }
    }

    fun getCategoryCount(): Int
    {
        return categories.size
    }

    fun getCategories():List<String>
    {
        return categories.toList().sorted()
    }

    fun getItemsForCategory(category:String):List<StateItem>
    {
        return if (category == CATEGORY_ALL)
            getData().toList()
        else
            getData().filter { it.category == category }
    }
}