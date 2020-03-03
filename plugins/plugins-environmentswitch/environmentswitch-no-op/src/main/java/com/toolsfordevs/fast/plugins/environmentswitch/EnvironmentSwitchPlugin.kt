package com.toolsfordevs.fast.plugins.environmentswitch

import android.content.Context
import com.toolsfordevs.fast.core.annotation.Keep
import java.lang.ref.WeakReference

@Keep
class EnvironmentSwitchPlugin
{
    fun launch(context: Context)
    {
        // No-op
    }

    companion object
    {
        fun launch(context: Context)
        {
            // No-op
        }

        private val builder:ArrayList<WeakReference<Builder>> = arrayListOf()

        fun addBuilder(builder:Builder)
        {
            // No-op
        }

        fun removeBuilder(builder:Builder)
        {
            // No-op
        }
    }

    @Keep
    interface Builder
    {
        fun buildFastEnvironment(env:FastEnvironment)
    }
}