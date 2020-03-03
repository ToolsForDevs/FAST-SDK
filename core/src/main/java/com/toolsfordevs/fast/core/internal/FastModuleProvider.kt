package com.toolsfordevs.fast.core.internal

import android.util.Log
import com.toolsfordevs.fast.core.FastModule
import kotlin.reflect.KClass


internal object FastModuleProvider
{
    val modules = arrayListOf<FastModule>()

    fun <T:FastModule> getModulesOfType(type: KClass<T>):List<T>
    {
        Log.d("FastModuleProvider", "modules ${modules.size}")
        return modules.filter {
            val isInstance = type.isInstance(it)

            if (isInstance)
                it.lazyInit()

            isInstance
        } as List<T>
    }
}