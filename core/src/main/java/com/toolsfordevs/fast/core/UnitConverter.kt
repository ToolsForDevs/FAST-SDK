package com.toolsfordevs.fast.core

import android.util.TypedValue
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.core.util.Dimens

@Keep
class UnitConverter
{
    companion object
    {
        // ToDo remove and replace with Dimens unit function
        fun getScaledValue(value:Int, unit:Int):Int
        {
            return when(unit) {
                TypedValue.COMPLEX_UNIT_DIP -> Dimens.pxToDp(value)
                TypedValue.COMPLEX_UNIT_SP -> Dimens.pxToSp(value)
                else -> value
            }
        }

        // ToDo remove and replace with Dimens unit function
        fun getPxValue(value:Int, unit:Int):Int
        {
            return when(unit) {
                TypedValue.COMPLEX_UNIT_DIP -> Dimens.dp(value)
                TypedValue.COMPLEX_UNIT_SP -> Dimens.sp(value)
                else -> value
            }
        }
    }
}