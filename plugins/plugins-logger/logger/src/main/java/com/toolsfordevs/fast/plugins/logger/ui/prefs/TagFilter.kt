package com.toolsfordevs.fast.plugins.logger.ui.prefs


internal data class TagFilter(var filter: String, var type: FilterType)
{
    fun check(tag: String): Boolean
    {
        return when (type)
        {
            FilterType.STARTS_WITH -> tag.toLowerCase().startsWith(filter.toLowerCase())
            FilterType.CONTAINS    -> tag.toLowerCase().contains(filter.toLowerCase())
            FilterType.ENDS_WITH   -> tag.toLowerCase().endsWith(filter.toLowerCase())
            FilterType.REGEXP      -> tag.toLowerCase().contains(Regex(filter.toLowerCase()))
        }
    }
}
