package com.toolsfordevs.fast.plugins.actions

import android.content.Context
import com.toolsfordevs.fast.core.annotation.Keep

@Keep
class ActionsPlugin
{
    fun launch(context: Context)
    {
        // No op
    }

    /**
     * Display actions for the view passed in parameters
     */
    /*fun launch(activity:Activity, view:View)
    {
        //ActionsModel.launch(activity, view)
    }*/

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