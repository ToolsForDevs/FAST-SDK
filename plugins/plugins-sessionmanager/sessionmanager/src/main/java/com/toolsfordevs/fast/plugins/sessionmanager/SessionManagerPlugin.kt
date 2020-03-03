package com.toolsfordevs.fast.plugins.sessionmanager

import android.content.Context
import android.view.KeyEvent
import com.toolsfordevs.fast.plugins.sessionmanager.ui.Panel
import com.toolsfordevs.fast.core.FastManager
import com.toolsfordevs.fast.core.FastPlugin
import com.toolsfordevs.fast.core.annotation.FastIncludePlugin
import com.toolsfordevs.fast.core.annotation.Keep
import java.lang.ref.WeakReference

@FastIncludePlugin
class SessionManagerPlugin : FastPlugin("com.toolsfordevs.fast.plugins.sessionmanager", "Session Manager", R.drawable.fast_plugin_sessionmanage_ic_plugin)
{
    override fun getDefaultHotkey(): Int
    {
        return KeyEvent.KEYCODE_B
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

        internal fun refreshData(repository: Repository)
        {
            repository.clear()

            val session = FastSession(repository)

            for (listener in builders)
            {
                listener.get()?.buildFastSession(session)
            }
        }

        private val builders:ArrayList<WeakReference<Builder>> = arrayListOf()

        fun addBuilder(builder:Builder)
        {
            builders.add(WeakReference(builder))
        }

        fun removeBuilder(builder:Builder)
        {
            for (ref in builders)
            {
                if (builder == ref.get())
                {
                    builders.remove(ref)
                    break
                }
            }
        }
    }

    @Keep
    interface Builder
    {
        fun buildFastSession(session:FastSession)
    }
}