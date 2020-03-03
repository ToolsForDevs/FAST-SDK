package com.toolsfordevs.fast.modules.recyclerview

import com.toolsfordevs.fast.core.annotation.Keep

@Keep
interface DataAdapter
{
    fun getDataAtPosition(position:Int):Any?
}