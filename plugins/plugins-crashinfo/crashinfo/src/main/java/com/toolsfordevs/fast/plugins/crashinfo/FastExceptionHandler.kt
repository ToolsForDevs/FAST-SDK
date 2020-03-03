package com.toolsfordevs.fast.plugins.crashinfo

import android.util.Log
import com.toolsfordevs.fast.core.AppInstance
import com.toolsfordevs.fast.plugins.crashinfo.ui.CrashInfoDialog


internal class FastExceptionHandler : Thread.UncaughtExceptionHandler
{
    override fun uncaughtException(t: Thread?, e: Throwable?)
    {
        Log.d("FastExceptionHandler", "crash ! $t")
        Log.d("FastExceptionHandler", "crash ! $e")

        e?.printStackTrace()

        e?.let {
            val activity = AppInstance.activity

            if (activity != null)
            {
                // Force main thread as exception may have occurred on background thread
                activity.runOnUiThread {
                    CrashInfoDialog(activity, it).show()
                }
            }
            else
            {
                throw it
            }
        }
    }
}