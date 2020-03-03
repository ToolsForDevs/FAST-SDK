package com.toolsfordevs.fast

import android.content.Context
import com.toolsfordevs.fast.core.AppInstance
import com.toolsfordevs.fast.core.FastManager
import com.toolsfordevs.fast.core.annotation.Keep

@Keep
object Fast
{
    @JvmStatic fun launch()
    {
        launch(AppInstance.activity ?: AppInstance.get())
    }

    @JvmStatic fun launch(context : Context)
    {
        FastManager.addView(FastPluginToolbar(context))
    }

    @JvmStatic fun setPackageName(packageName:String)
    {
        AppInstance.packageName = packageName
    }

    // ToDo Disable open via shake
}