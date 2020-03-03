package com.toolsfordevs.fast.modules.restart

import android.content.Intent
import com.toolsfordevs.fast.core.AppInstance
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.core.prefs.SharedPrefsProvider
import java.io.File
import kotlin.system.exitProcess

@Keep
object FastRestartOptions
{
    fun killActivity()
    {
        AppInstance.activity?.finish()
    }

    fun restartActivityClearIntent()
    {
        val activity = AppInstance.activity!!
        activity.finish()
        activity.overridePendingTransition(0, 0)
        activity.startActivity(Intent(activity, activity.javaClass))
        activity.overridePendingTransition(0, 0)
    }

    fun restartActivitySameIntent()
    {
        val activity = AppInstance.activity!!
        activity.finish()
        activity.overridePendingTransition(0, 0)
        activity.startActivity(activity.intent)
        activity.overridePendingTransition(0, 0)
    }

    fun restartApp()
    {
        // Does it recreate the application object and run onCreate() ?
        val app = AppInstance.get()
        val packageName = app.packageName
        if (packageName != null)
        {
            val intent = app.packageManager.getLaunchIntentForPackage(packageName)
            AppInstance.activity?.finish()

            if (intent != null)
            {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                app.startActivity(intent)
                Runtime.getRuntime().exit(0)
//                killApp()
            }
        }
    }

    private fun clearData()
    {
        val cacheDirectory = AppInstance.get().cacheDir
        val applicationDirectory = File(cacheDirectory.parent!!)

        if (applicationDirectory.exists())
        {
            val fastFiles = SharedPrefsProvider.ignoredFiles
            val contents = arrayListOf<String?>()

            // Save FAST prefs in memory
            for (fileName in fastFiles)
            {
                val file = File(applicationDirectory, "shared_prefs" + File.separator + "$fileName.xml")
                if (file.exists())
                    contents.add(file.readText())
                else
                    contents.add(null) // avoid messing up the list indexes
            }

            // Delete data files
            val fileNames = applicationDirectory.list()!!
            for (fileName in fileNames)
            {
                if (fileName != "lib")
                    deleteFile(File(applicationDirectory, fileName))
            }

            // restore FAST prefs
            fastFiles.forEachIndexed { index, filename ->
                val content = contents[index]

                content?.let {
                    val file = File(applicationDirectory, "shared_prefs" + File.separator + "$filename.xml")
                    file.createNewFile()
                    file.writeText(it)
                }
            }
        }
    }

    fun clearDataAndKillApp()
    {
        clearData()
        killApp()
    }

    fun clearDataAndRestartApp()
    {
        clearData()
        restartApp()
    }

    fun deleteFile(file: File?): Boolean
    {
        var deletedAll = true
        if (file != null)
        {
            if (file.isDirectory)
            {
                val children = file.list()
                for (child in children)
                {
                    deletedAll = deleteFile(File(file, child)) && deletedAll
                }
            }
            else
            {
                deletedAll = file.delete()
            }
        }

        return deletedAll
    }

    fun killApp()
    {
        AppInstance.activity?.let {
            it.finishAffinity()
            exitProcess(0)
        }
    }
}