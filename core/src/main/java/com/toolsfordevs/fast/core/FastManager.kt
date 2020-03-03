package com.toolsfordevs.fast.core

import android.app.Activity
import android.util.Log
import android.view.View
import com.toolsfordevs.fast.core.activity.OnBackPressedCallback
import com.toolsfordevs.fast.core.activity.OnBackPressedDispatcher
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.core.internal.FastModuleProvider
import com.toolsfordevs.fast.core.internal.FastPluginProvider
import com.toolsfordevs.fast.core.internal.ViewManager
import java.lang.ref.WeakReference
import kotlin.reflect.KClass

/**
 * Facade for common operations, hide all the complex implemented stuff
 * @hide
 */
@Keep
object FastManager
{
    internal var enabled = false

    private val map = ArrayList<LifecycleManagers>()

    private val managers: LifecycleManagers
        get() = getOrThrow()

    private val backDispatcher: OnBackPressedDispatcher
        get() = managers.backDispatcher

    private val viewManager: ViewManager
        get() = managers.viewManager

    internal fun onActivityCreated(activity: Activity)
    {
        val viewManager = ViewManager(activity)
        map.add(LifecycleManagers(activity, OnBackPressedDispatcher(), viewManager))
        viewManager.setup()
    }

    internal fun onActivityDestroyed(activity: Activity?)
    {
        activity?.let {
            val manager = map.firstOrNull { it.isFromActivity(activity) }
            manager?.let { map.remove(it) }
        }
    }

    private fun getOrThrow(): LifecycleManagers
    {
        val activity = AppInstance.activity
            ?: throw Exception("No activity available, where from/when are you calling this method ?")

        return map.firstOrNull { it.isFromActivity(activity) }
            ?: throw Exception("Didn't find any instance for activity $activity")
    }

    internal fun open()
    {
        val mainPlugin = FastPluginProvider.plugins.firstOrNull { it.id == "com.toolsfordevs.fast.launcher"}
        // Launch main plugin (which is the plugin launcher) if available (should be)
        mainPlugin?.launch(AppInstance.activity ?: AppInstance.get())
    }


    /*#########################################################################
    *
    * OnBackPressedDispatcher Facade methods
    *
    #########################################################################*/

    internal fun onBackPressed(): Boolean
    {
        return backDispatcher.onBackPressed()
    }

    @Keep
    fun addCallback(callback: OnBackPressedCallback)
    {
        if (enabled)
            backDispatcher.addCallback(callback)
    }

    /**
     * Convenience method.
     * You should use callback.remove() instead as this is what this method does
     */
    @Keep
    fun removeCallback(callback: OnBackPressedCallback)
    {
        if (enabled)
        {
            // Activity may have been destroyed
            try
            {
                backDispatcher.removeCallback(callback)
            }
            catch (e: Exception)
            {

            }
        }
    }

    /*#########################################################################
    *
    * ViewManager Facade methods
    *
    #########################################################################*/

    @Keep
    fun addOverlay(overlay: View)
    {
        if (enabled)
            viewManager.showOverlay(overlay)
    }

    @Keep
    fun removeOverlay(overlay: View)
    {
        if (enabled)
        {
            // Activity may have been destroyed
            try
            {
                viewManager.removeOverlay(overlay)
            }
            catch (e: Exception)
            {

            }
        }
    }

    @Keep
    fun addView(plugin: View)
    {
        if (enabled)
            viewManager.addView(plugin)
    }

    internal fun closeAllViews()
    {
        viewManager.closeAll()
    }

    internal fun dispatchKeyStrokeToCurrentView(key:Int)
    {
        viewManager.onKeyStroke(key)
    }

    @Keep
    fun getPlugins():List<FastPlugin>
    {
        @Suppress("UNCHECKED_CAST")
        return FastPluginProvider.plugins.clone() as List<FastPlugin>
    }

    @Keep
    internal fun <T:FastModule> getModulesOfType(type: KClass<T>):List<T>
    {
        return FastModuleProvider.getModulesOfType(type)
    }
}

private class LifecycleManagers(
    activity: Activity,
    val backDispatcher: OnBackPressedDispatcher,
    val viewManager: ViewManager
)
{
    val activity = WeakReference(activity)
    fun isFromActivity(activity: Activity): Boolean = this.activity.get() == activity
}