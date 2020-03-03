package com.toolsfordevs.fast.modules.formatters

import com.toolsfordevs.fast.core.annotation.Keep

@Keep
interface FastFormatter
{
    val name:String

    fun canFormatData(data:Any?):Boolean

    /**
     * Return a string representation of the data
     */
    fun formatToString(data:Any?):String

}
