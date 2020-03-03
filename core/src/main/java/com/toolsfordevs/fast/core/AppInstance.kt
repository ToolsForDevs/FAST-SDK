package com.toolsfordevs.fast.core

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.toolsfordevs.fast.core.activity.CustomWindowCallback
import com.toolsfordevs.fast.core.annotation.Keep
import java.lang.ref.WeakReference

@Keep
object AppInstance
    : Application.ActivityLifecycleCallbacks
{
    var packageName:String? = null

    fun getApplicationId():String
    {
        return packageName ?: get().packageName
    }

    private lateinit var ref: WeakReference<Application>

    private var _activity: WeakReference<Activity>? = null

    //@get:Keep
    val activity: Activity?
        get() = _activity?.get()

    //@Keep
    fun get(): Application = ref.get()!!

    internal fun attach(app: Application)
    {
        ref = WeakReference(app)

        app.registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityPaused(activity: Activity?)
    {

    }

    override fun onActivityResumed(activity: Activity?)
    {
        // Getting back to the activity
        // The only true way to know the current activity is the good one
        activity?.let {
            _activity = WeakReference(it)

            // Must do it here because appcompat may change window's callback
            // with setSupportActionbar in onCreate(), which is called after onActivityCreated
            if (it.window.callback !is CustomWindowCallback)
                it.window.callback = CustomWindowCallback(it.window.callback)
        }
    }

    override fun onActivityStarted(activity: Activity?)
    {

    }

    override fun onActivityDestroyed(activity: Activity?)
    {
        FastManager.onActivityDestroyed(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?)
    {

    }

    override fun onActivityStopped(activity: Activity?)
    {

    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?)
    {
        // An activity created will be displayed immediately after
        // We might need a reference to the activity depending on the code on our activity onCreate() method
        activity?.let {
            _activity = WeakReference(it)

            // Setup custom callback to manage back button
            //it.window.callback = CustomWindowCallback(it.window.callback)

            FastManager.onActivityCreated(activity)
        }


    }
}