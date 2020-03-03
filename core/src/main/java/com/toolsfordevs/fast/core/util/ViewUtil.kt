package com.toolsfordevs.fast.core.util

import android.content.res.Resources
import android.view.View
import com.toolsfordevs.fast.core.AppInstance
import com.toolsfordevs.fast.core.annotation.Keep

@Keep
object ViewUtil
{
    fun getIdString(view: View): String
    {
        val out = StringBuilder()
        val id = view.getId()
        if (id != View.NO_ID && !isViewIdGenerated(id))
        {
            try
            {
                val resources = AppInstance.get().resources
                val pkgName: String

                when (id and -0x1000000)
                {
                    0x7f000000 -> pkgName = "app"
                    0x01000000 -> pkgName = "android"
                    else -> pkgName = resources.getResourcePackageName(id)
                }

                val typename = resources.getResourceTypeName(id)
                val entryName = resources.getResourceEntryName(id)
                out.append("@")
                out.append(pkgName)
                out.append(":")
                out.append(typename)
                out.append("/")
                out.append(entryName)
            }
            catch (e: Resources.NotFoundException)
            {
                e.printStackTrace()
                out.append(Integer.toHexString(id))
            }

        }
        else
        {
            out.append("NO_ID")
        }
        return out.toString()
    }

    // see View.java
    // generateViewId(), isViewIdGenerated()
    private fun isViewIdGenerated(id: Int): Boolean
    {
        return id and -0x1000000 == 0 && id and 0x00FFFFFF != 0
    }
}