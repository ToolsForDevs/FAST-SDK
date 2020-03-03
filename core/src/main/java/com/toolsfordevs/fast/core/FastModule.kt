package com.toolsfordevs.fast.core

import android.content.Context
import com.toolsfordevs.fast.core.annotation.Keep
import kotlin.reflect.KClass

@Keep
abstract class FastModule(val id: String, val name: String = "")
{
    private var isInit = false

    internal fun lazyInit()
    {
        if (!isInit)
        {
            initialize()
            isInit = true
        }
    }

    /**
     * Initialize stuff here that can be delayed (eg, not at application startup)
     * You can get a reference to a Context object with <code>AppInstance.get()</code>
     *
     * @see AppInstance.get
     */
    abstract fun initialize()

    /**
     * Initialize stuff here that must be right at application startup)
     */
    abstract fun onApplicationCreated(context: Context)

    companion object
    {
        fun <T:FastModule> getModules(moduleClass: KClass<T>):List<T>
        {
            return FastManager.getModulesOfType(moduleClass)
        }
    }
}