package com.toolsfordevs.fast.plugins.actions

import android.content.Context
import android.view.KeyEvent
import com.toolsfordevs.fast.core.FastManager
import com.toolsfordevs.fast.core.FastPlugin
import com.toolsfordevs.fast.core.annotation.FastIncludePlugin
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.plugins.actions.ui.ActionsPanel
import com.toolsfordevs.fast.plugins.actions.ui.Repository
import java.lang.ref.WeakReference

@FastIncludePlugin
class ActionsPlugin : FastPlugin("com.toolsfordevs.fast.plugins.actions", "Actions", R.drawable.fast_plugin_actions_plugin_icon)
{
    override fun launch(context: Context)
    {
        ActionsPlugin.launch(context)
    }

    /**
     * Display actions for the view passed in parameters
     */
    /*fun launch(activity:Activity, view:View)
    {
        //ActionsModel.launch(activity, view)
    }*/

    override fun getDefaultHotkey(): Int
    {
        return KeyEvent.KEYCODE_A
    }

    companion object
    {
        fun launch(context: Context)
        {
            FastManager.addView(ActionsPanel(context))
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

        internal fun refreshData(repository: Repository)
        {
            repository.clear()

            val publicRepo = FastActions(repository)

            for (builder in builder)
                builder.get()?.buildFastActions(publicRepo)
        }

        /*fun <T : Any> addActionForView(
            name: String,
            view: View,
            action: Action<T>,
            callback: (action: Action<T>) -> Unit)
        {
            ActionsRepository.addActionForView(name, view, action, callback)
        }

        fun <T : Any> addActionForViewType(
            name: String,
            viewClass: KClass<*>,
            action: Action<T>,
            callback: (action: Action<T>) -> Unit)
        {
            ActionsRepository.addActionForType(name, viewClass, action, callback)
        }*/
    }

    @Keep
    interface Builder
    {
        fun buildFastActions(actions:FastActions)
    }
}