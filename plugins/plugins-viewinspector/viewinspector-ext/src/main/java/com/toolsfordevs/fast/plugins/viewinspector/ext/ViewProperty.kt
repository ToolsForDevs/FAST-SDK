package com.toolsfordevs.fast.plugins.viewinspector.ext

import android.util.TypedValue
import android.view.View
import com.toolsfordevs.fast.core.annotation.Keep
import kotlin.reflect.KFunction

@Keep
open class ViewProperty<T: View, R>(val getter: (T.() -> R?)? = null, val setter: (T.(R) -> Unit)? = null, var name:String = "", var newApi:String? = null, var deprecatedApi:String? = null, var scaledUnit:Int = TypedValue.COMPLEX_UNIT_DIP)
{
    companion object
    {
        private const val NO_NAME = "unknown"
        private const val NO_ID = "NO_ID"
    }

    lateinit var view:T

    val defaultValue:R? = null

    // var needInvalidate:Boolean ?

    var usePixels = false

    var subsituteId:String = ""
        protected set

    val unit:Int
        get() = if (usePixels) TypedValue.COMPLEX_UNIT_PX else scaledUnit

    init {
        if (name.isBlank())
        {
            if (setter != null)
            {
                try
                {
                    name = (setter as KFunction<R?>).name
                }
                catch (e: Exception)
                {

                }
            }

            if (name.isBlank() && getter != null)
            {
                try
                {
                    name = (getter as KFunction<R?>).name
                }
                catch (e: Exception)
                {

                }
            }

            if (name.isBlank())
                name = NO_NAME
        }
    }

    val id:String
        get() = if (name != NO_NAME || getter != null || setter != null)
                    this::class.java.simpleName + " $name $getter $setter $subsituteId"
                else if (subsituteId.isNotBlank())
                    subsituteId
                else
                    NO_ID

    val hasId:Boolean
        get() = id != NO_ID

    val hasName:Boolean
        get() = name != NO_NAME


    fun isDeprecated():Boolean = !deprecatedApi.isNullOrBlank()
    fun isNew():Boolean = !newApi.isNullOrBlank()

    open fun getValue(): R? = getter?.invoke(view)
    open fun setValue(value: R?):Boolean
    {
        if (value == null)
        {
            try
            {
                (setter as (T.(R?) -> Unit)).invoke(view, value)
            }
            catch (e:Exception)
            {
                return false
            }
        }
        else
        {
            setter?.invoke(view, value)
        }

        return true
    }

    open fun hasValue():Boolean
    {
        return getter != null
    }

    fun assignView(view:View)
    {
        this.view = view as T
    }
}