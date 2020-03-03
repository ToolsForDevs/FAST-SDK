package com.toolsfordevs.fast.core.util

object FastSort
{
    const val DEFAULT = "default"
    const val ALPHA_DESC = "alpha_desc"
    const val ALPHA_ASC = "alpha_asc"
    const val SIZE_DESC = "size_desc"
    const val SIZE_ASC = "size_asc"
    const val DATE_DESC = "date_desc"
    const val DATE_ASC = "date_asc"

    fun <T:Comparable<T>> compare(o1:T, o2:T, fastSort:String):Int
    {
        val coef = if (fastSort in listOf(ALPHA_DESC, SIZE_DESC, DATE_DESC)) -1 else 1
        return o1.compareTo(o2) * coef
    }
}