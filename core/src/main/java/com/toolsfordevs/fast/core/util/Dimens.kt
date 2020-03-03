package com.toolsfordevs.fast.core.util

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import com.toolsfordevs.fast.core.AppInstance
import com.toolsfordevs.fast.core.annotation.Keep


@Keep
object Dimens
{
    private val context: Context
        get() = AppInstance.get()

    private val res: Resources
        get() = context.resources

    private fun convert(unit:Int, value:Float):Float = TypedValue.applyDimension(unit, value, res.displayMetrics)

    /**
     * Return the value in pixels corresponding to the passed parameter in dp
     * @param value the value, in dp, to convert in pixels
     * @Return the converted value in pixels, casted to Int
     */
    @JvmStatic
    fun dp(value:Int):Int = convert(TypedValue.COMPLEX_UNIT_DIP, value.toFloat()).toInt()
    /**
     * Return the value in pixels corresponding to the passed parameter in dp
     * @param value the value, in dp, to convert in pixels
     * @Return the converted value in pixels, casted to Int
     */
    fun dp(value:Float):Int = convert(TypedValue.COMPLEX_UNIT_DIP, value).toInt()
    /**
     * Return the value in pixels corresponding to the passed parameter in dp. Same as the <code>dp</code>, except that
     * it returns a Float instead of an Int
     * @param value the value, in dp, to convert in pixels
     * @Return the converted value in pixels, casted to Float
     */
    fun dpF(value:Int):Float = convert(TypedValue.COMPLEX_UNIT_DIP, value.toFloat())
    /**
     * Return the value in pixels corresponding to the passed parameter in dp. Same as the <code>dp</code>, except that
     * it returns a Float instead of an Int
     * @param value the value, in dp, to convert in pixels
     * @Return the converted value in pixels, casted to Float
     */
    fun dpF(value:Float):Float = convert(TypedValue.COMPLEX_UNIT_DIP, value)

    /**
     * Return the value in pixels corresponding to the passed parameter in sp
     * @param value the value, in sp, to convert in pixels
     * @Return the converted value in pixels, casted to Int
     */
    @JvmStatic
    fun sp(value: Int): Int = convert(TypedValue.COMPLEX_UNIT_SP, value.toFloat()).toInt()
    /**
     * Return the value in pixels corresponding to the passed parameter in sp. Same as the <code>sp</code>, except that
     * it returns a Float instead of an Int
     * @param value the value, in sp, to convert in pixels
     * @Return the converted value in pixels, casted to Float
     */
    fun spF(value: Int): Float = convert(TypedValue.COMPLEX_UNIT_SP, value.toFloat())
    fun spF(value: Float): Float = convert(TypedValue.COMPLEX_UNIT_SP, value)

    fun pxToDp(value: Int): Int = (value / res.displayMetrics.density).toInt()
    fun pxToDpF(value: Int): Float = value / res.displayMetrics.density

    fun pxToSp(value: Int): Int = (value / res.displayMetrics.scaledDensity).toInt()
    fun pxToSpF(value: Int): Float = value / res.displayMetrics.scaledDensity

    fun pxToDimension(value:Float, dimensionUnit:Int):Float
    {
        val metrics = res.displayMetrics

        return when (dimensionUnit)
        {
            TypedValue.COMPLEX_UNIT_PX  -> value
            TypedValue.COMPLEX_UNIT_DIP -> value / metrics.density
            TypedValue.COMPLEX_UNIT_SP  -> value / metrics.scaledDensity
            TypedValue.COMPLEX_UNIT_PT  -> value / metrics.xdpi * (1.0f / 72)
            TypedValue.COMPLEX_UNIT_IN  -> value / metrics.xdpi
            TypedValue.COMPLEX_UNIT_MM  -> value / metrics.xdpi * (1.0f / 25.4f)
            else -> 0f
        }
    }

    fun toPx(value:Float, unit:Int):Float = convert(unit, value)
    fun fromPx(value:Float, unit:Int):Float = pxToDimension(value, unit)

    data class Unit internal constructor(val unit:Int, val label:String)
    {
        fun toPx(value:Float):Float = toPx(value, unit)
        fun toPx(value:Int):Int = toPx(value.toFloat(), unit).toInt()
        fun fromPx(pxValue:Int):Int = pxToDimension(pxValue.toFloat(), unit).toInt()
        fun fromPx(pxValue:Float):Float = pxToDimension(pxValue, unit)

        companion object
        {
            fun from(unit:Int):Unit
            {
                return when (unit)
                {
                    PX.unit -> PX
                    DP.unit -> DP
                    SP.unit -> SP
                    PT.unit -> PT
                    IN.unit -> PX
                    MM.unit -> PX
                    else -> throw Exception("No such unit for value $unit")
                }
            }
        }
    }

    val PX = Unit(TypedValue.COMPLEX_UNIT_PX, "PX")
    val DP = Unit(TypedValue.COMPLEX_UNIT_DIP, "DP")
    val SP = Unit(TypedValue.COMPLEX_UNIT_SP, "SP")
    val PT = Unit(TypedValue.COMPLEX_UNIT_PT, "PT")
    val IN = Unit(TypedValue.COMPLEX_UNIT_IN, "IN")
    val MM = Unit(TypedValue.COMPLEX_UNIT_MM, "MM")
}