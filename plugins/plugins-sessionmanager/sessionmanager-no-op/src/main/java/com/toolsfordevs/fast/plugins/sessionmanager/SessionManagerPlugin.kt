package com.toolsfordevs.fast.plugins.sessionmanager

import android.content.Context
import com.toolsfordevs.fast.core.annotation.Keep

@Keep
class SessionManagerPlugin
{
    fun launch(context: Context)
    {
        // No op
    }

    companion object
    {
        fun launch(context: Context)
        {
            // No op
        }

        fun addBuilder(builder:Builder)
        {
            // No op
        }

        fun removeBuilder(builder:Builder)
        {
            // No op
        }
    }

    @Keep
    interface Builder
    {
        fun buildFastSession(session:FastSession)
    }
}