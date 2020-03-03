package com.toolsfordevs.fast.core.internal

import android.app.Application
import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.toolsfordevs.fast.core.AppInstance
import com.toolsfordevs.fast.core.FastManager
import com.toolsfordevs.fast.core.FastModule
import com.toolsfordevs.fast.core.FastPlugin
import com.toolsfordevs.fast.core.util.SensorDetector
import java.util.*


internal class FastProvider : ContentProvider()
{
    override fun onCreate(): Boolean
    {
        // Plugins are relying on this
        AppInstance.attach(context!!.applicationContext as Application)

        val pluginLoader = ServiceLoader.load(FastPlugin::class.java, FastPlugin::class.java.classLoader)

        for (plugin in pluginLoader)
        {
            FastPluginProvider.plugins.add(plugin)
        }

        val moduleLoader = ServiceLoader.load(FastModule::class.java, FastModule::class.java.classLoader)

        for (module in moduleLoader)
        {
            module.onApplicationCreated(context!!.applicationContext)
            FastModuleProvider.modules.add(module)
        }

        Log.i("FAST SDK", "######################################")
        Log.i("FAST SDK", "##                                  ##")
        Log.i("FAST SDK", "## SDK Initialized.                 ##")
        Log.i("FAST SDK", "## Found ${FastPluginProvider.plugins.size} plugin(s) and ${FastModuleProvider.modules.size} modules. ##")
        Log.i("FAST SDK", "##                                  ##")
        Log.i("FAST SDK", "######################################")

        SensorDetector { FastManager.open() }

        FastManager.enabled = true

        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri?
    {
        throw Exception("FastProvider don't do anything but initialize some variables")
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor?
    {
        throw Exception("FastProvider don't do anything but initialize some variables")
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int
    {
        throw Exception("FastProvider don't do anything but initialize some variables")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int
    {
        throw Exception("FastProvider don't do anything but initialize some variables")
    }

    override fun getType(uri: Uri): String?
    {
        throw Exception("FastProvider don't do anything but initialize some variables")
    }
}