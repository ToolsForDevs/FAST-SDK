package com.toolsfordevs.fast.plugins.environmentswitch

import android.content.Context
import android.view.KeyEvent
import com.toolsfordevs.fast.plugins.environmentswitch.ui.Panel
import com.toolsfordevs.fast.core.FastManager
import com.toolsfordevs.fast.core.FastPlugin
import com.toolsfordevs.fast.core.annotation.FastIncludePlugin
import com.toolsfordevs.fast.core.annotation.Keep
import java.lang.ref.WeakReference

@FastIncludePlugin
class EnvironmentSwitchPlugin : FastPlugin("com.toolsfordevs.fast.plugins.environmentswitch", "Environment Switch", R.drawable.plugin_environmentswitch_ic_plugin)
{
    override fun getDefaultHotkey(): Int
    {
        return KeyEvent.KEYCODE_E
    }

    override fun launch(context: Context)
    {
        Companion.launch(context)
    }

    companion object
    {
        fun launch(context: Context)
        {
            FastManager.addView(Panel(context))
        }

        private val builder:ArrayList<WeakReference<Builder>> = arrayListOf()

        fun addBuilder(builder:Builder)
        {
            this.builder.add(WeakReference(builder))
        }

        fun removeBuilder(builder:Builder)
        {
            for (ref in this.builder)
            {
                if (builder == ref.get())
                {
                    this.builder.remove(ref)
                    break
                }
            }
        }

        internal fun refreshData(store: Store)
        {
            store.clear()

            val env = FastEnvironment(store)

            for (builder in builder)
                builder.get()?.buildFastEnvironment(env)
        }
    }

    @Keep
    interface Builder
    {
        fun buildFastEnvironment(env:FastEnvironment)
    }
}