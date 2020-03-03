package com.toolsfordevs.fast.modules.resources

import android.content.Context
import android.util.Log
import com.toolsfordevs.fast.core.AppInstance
import com.toolsfordevs.fast.core.annotation.Keep
import java.util.Collections.unmodifiableList

@Keep
object ResourceManager
{
    private val resourceMap = HashMap<String, List<TypedResource>>()

    val colors:List<ColorResource>
        get() = getResourcesOfType(TypedResource.COLOR)

    val dimens:List<DimensionResource>
        get() = getResourcesOfType(TypedResource.DIMEN)

    val drawables:List<DrawableResource>
        get() = getResourcesOfType(TypedResource.DRAWABLE)

    val strings:List<StringResource>
        get() = getResourcesOfType(TypedResource.STRING)

    /**
     * Use constants from TypedResources
     */
    fun <T:TypedResource> getResourcesOfType(type: String): List<T>
    {
        var list = resourceMap.get(type)

        if (list == null)
        {
            val resourceNames = getResourceList(AppInstance.get(), type)
            list = resourceNames.map { buildResourceObject(type, it) }
            resourceMap.put(type, list)
        }

        return list as List<T>
    }

    private fun buildResourceObject(type: String, name: String): TypedResource
    {
        return when (type)
        {
            TypedResource.COLOR    -> ColorResource(name)
            TypedResource.DIMEN    -> DimensionResource(name)
            TypedResource.DRAWABLE -> DrawableResource(name)
            TypedResource.STRING   -> StringResource(name)
            else                   -> throw java.lang.IllegalArgumentException("can build resource object of type $type")
        }
    }

    private val TAG = javaClass.simpleName
    private val mClassCache: HashMap<String, Class<*>> = hashMapOf()

    private fun getResourceClass(context: Context, suffix: String): Class<*>?
    {
        if (mClassCache.containsKey(suffix))
        {
            return mClassCache.get(suffix)
        }
        else
        {
            try
            {
                val rClassBase = Class.forName((AppInstance.packageName ?: context.applicationContext.packageName) + ".R")
                val subClassTable = rClassBase.declaredClasses

                for (subClass in subClassTable)
                {
                    if (subClass?.canonicalName?.endsWith(suffix) == true)
                    {
                        Log.d(TAG, "getResourceClass() : found class ${subClass.canonicalName}")
                        mClassCache.put(suffix, subClass)
                        return subClass
                    }
                }

            }
            catch (e: ClassNotFoundException)
            {
                Log.e(TAG, "getResourceClass() ClassNotFoundException: " + e.message, e)
            }

//            Log.e(TAG, "getResourceClass() Unable to find Sublass: $suffix")

            return null
        }
    }

    fun getResourceList(context: Context, type: String): List<String>
    {
        val list = arrayListOf<String>()
        val resourceClass = getResourceClass(context, ".R.$type")

        if (resourceClass != null)
        {
            val resourceArray = resourceClass.fields

            for (field in resourceArray)
            {
                try
                {
                    if (Int::class.javaPrimitiveType == field.type)
                    {
                        // Don't add FAST resources
                        if (!field.name.startsWith("fast_"))
                            list.add(field.name)
                    }
                }
                catch (e: IllegalArgumentException)
                {
                    Log.e(TAG, "getResourceList() Error: " + e.message, e)
                }
            }
        }

        list.sort()
        return unmodifiableList(list)
    }
}