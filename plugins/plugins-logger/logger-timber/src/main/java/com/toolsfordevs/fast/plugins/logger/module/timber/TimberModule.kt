package com.toolsfordevs.fast.plugins.logger.module.timber

import android.content.Context
import android.util.Log
import com.toolsfordevs.fast.core.annotation.FastIncludeModule
import com.toolsfordevs.fast.plugins.logger.*
import com.toolsfordevs.fast.plugins.logger.modules.ext.LoggerModule
import timber.log.Timber

@FastIncludeModule
class TimberModule : LoggerModule("timber", "Timber")
{
    override fun initialize()
    {
        // do nothing
    }

    override fun onApplicationCreated(context: Context)
    {
        Timber.plant(FastTimberTree())
        LoggerPlugin.excludePackagesFromStacktrace("timber.log")
    }

    class FastTimberTree : Timber.Tree()
    {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?)
        {
            when (priority)
            {
                Log.VERBOSE -> flog(message, tag)
                Log.DEBUG -> flogD(message, tag)
                Log.INFO -> flogI(message, tag)
                Log.WARN -> flogW(message, tag)
                Log.ERROR -> flogE(message, tag)
                Log.ASSERT -> flogWTF(message, tag)
                else -> flog(message, tag)
            }
        }

    }

}