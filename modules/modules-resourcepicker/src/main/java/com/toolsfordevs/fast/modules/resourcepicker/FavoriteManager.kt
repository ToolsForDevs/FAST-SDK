package com.toolsfordevs.fast.modules.resourcepicker

import android.util.Log
import com.toolsfordevs.fast.modules.resources.*
import kotlin.reflect.KMutableProperty0


internal object FavoriteManager
{
    private val listeners = arrayListOf<() -> Unit>()

    fun toggleResource(resource: ColorResource) = toggleResource(Prefs::favoriteColors, resource)
    fun toggleResource(resource: DimensionResource) = toggleResource(Prefs::favoriteDimensions, resource)
    fun toggleResource(resource: DrawableResource) = toggleResource(Prefs::favoriteDrawables, resource)
    fun toggleResource(resource: StringResource) = toggleResource(Prefs::favoriteStrings, resource)

    private fun <T : TypedResource> toggleResource(prefVar: KMutableProperty0<ArrayList<T>>, resource: T)
    {
        val resources = prefVar.get()

        val found = resources.firstOrNull { it.name == resource.name }

        if (found != null)
            resources.remove(found)
        else
            resources.add(resource)

        prefVar.set(resources)

        for (listener in listeners)
            listener.invoke()
    }

    private fun <T : TypedResource> isStarred(prefVar: KMutableProperty0<ArrayList<T>>, resource: T):Boolean
    {
        return prefVar.get().firstOrNull { it.name == resource.name } != null
    }

    fun isFavorite(resource:ColorResource):Boolean = isStarred(Prefs::favoriteColors, resource)
    fun isFavorite(resource:DimensionResource):Boolean = isStarred(Prefs::favoriteDimensions, resource)
    fun isFavorite(resource:DrawableResource):Boolean = isStarred(Prefs::favoriteDrawables, resource)
    fun isFavorite(resource:StringResource):Boolean = isStarred(Prefs::favoriteStrings, resource)

    fun addOnFavoriteChangeCallback(callback: () -> Unit)
    {
        listeners.add(callback)
    }

    fun removeOnFavoriteChangeCallback(callback:() -> Unit)
    {
        listeners.remove(callback)
    }
}