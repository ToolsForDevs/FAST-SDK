package com.toolsfordevs.fast.core

import android.content.Context
import android.util.Log
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.core.prefs.IntPref

@Keep
abstract class FastPlugin(val id:String, val name:String, val icon:Int = 0, val availableInLaunchBar:Boolean = true)
{
    val modules:List<FastModule> = arrayListOf()

    internal var enabled = true
    private var _hotkey by IntPref("PLUGIN_${id}_HOTKEY", -1)

    // ToDo is it used somewhere ?
    fun addModule(module:FastModule)
    {
        (modules as ArrayList).add(module)
    }

    fun disableModule()
    {

    }

    /**
     * Provide a keyboard key in order to quickly launch this plugin
     * Use a constant from the KeyEvent class
     * For example KeyEvent.KEYCODE_A if you want to set "A" as the hotkey to launch the plugin
     * Please use an uppercase key, as lowercase keys are reserved for the panels
     * Use KeyEvent.KEYCODE_UNKNOWN if you don't want to (or can't) launch the plugin via a hotkey
     */
    abstract fun getDefaultHotkey():Int

//    abstract fun onApplicationCreated(context: Context)
    fun launch()
    {
        try
        {
            launch(AppInstance.activity ?: AppInstance.get())
        }
        catch(e: Exception)
        {
            Log.w("Fast", "can't launch Fast because current activity is null and no context has been provided. Please use Fast.launch(context) instead.")
        }
    }

    abstract fun launch(context: Context)

    internal fun getHotkey():Int
    {
        return if (_hotkey == -1) getDefaultHotkey() else _hotkey
    }

    /**
     * Replace the default key for launching this plugin.
     *
     * @param Use a constant from [android.view.KeyEvent]
     * @see android.view.KeyEvent
     */
    fun setHotkey(keyCode:Int)
    {
        _hotkey = keyCode
    }
}