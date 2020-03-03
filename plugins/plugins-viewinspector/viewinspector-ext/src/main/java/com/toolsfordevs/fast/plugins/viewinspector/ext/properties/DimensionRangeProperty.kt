package com.toolsfordevs.fast.plugins.viewinspector.ext.properties

import android.util.TypedValue
import android.view.View
import com.toolsfordevs.fast.core.annotation.Keep
import kotlin.reflect.KMutableProperty1

abstract class DimensionRangeProperty<T: View, R:Number>(getter: (T.() -> R?)? = null, setter: (T.(R) -> Unit)? = null, min:R, max:R, scaledUnit:Int = TypedValue.COMPLEX_UNIT_DIP) : NumberRangeProperty<T, R>(getter, setter, min, max)
{
    constructor(property: KMutableProperty1<T, R>, min:R, max:R, scaledUnit:Int = TypedValue.COMPLEX_UNIT_DIP) : this(property.getter, property.setter, min, max, scaledUnit)

    init {
        this.scaledUnit = scaledUnit
    }
}

@Keep
open class DimensionIntRangeProperty<T: View>(getter: (T.() -> Int?)? = null, setter: (T.(Int) -> Unit)? = null, min:Int = 0, max:Int = 100, scaledUnit:Int = TypedValue.COMPLEX_UNIT_DIP) : DimensionRangeProperty<T, Int>(getter,
    setter as (T.(Number) -> Unit)?, min, max)
{
    constructor(property: KMutableProperty1<T, Int>, min:Int, max:Int, scaledUnit:Int = TypedValue.COMPLEX_UNIT_DIP) : this(property.getter, property.setter, min, max, scaledUnit)

    init {
        this.scaledUnit = scaledUnit
    }
}

@Keep
open class DimensionFloatRangeProperty<T: View>(getter: (T.() -> Float?)? = null, setter: (T.(Float) -> Unit)? = null, min:Float = 0f, max:Float = 100f, scaledUnit:Int = TypedValue.COMPLEX_UNIT_DIP) : DimensionRangeProperty<T, Float>(getter,
    setter as (T.(Number) -> Unit)?, min, max)
{
    constructor(property: KMutableProperty1<T, Float>, min:Float, max:Float, scaledUnit:Int = TypedValue.COMPLEX_UNIT_DIP) : this(property.getter, property.setter, min, max, scaledUnit)

    init {
        this.scaledUnit = scaledUnit
    }
}

