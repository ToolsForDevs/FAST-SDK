package com.fast.toolsfordevs.plugins.newplugin.ui

import android.annotation.SuppressLint
import android.view.View
import com.fast.toolsfordevs.modules.codegenerator.CodeSnippet
import com.toolsfordevs.fast.core.AppInstance
import com.toolsfordevs.fast.core.util.AndroidVersion

internal class UISystemFlagManager
{
    val lightNavigationBar: ItemWrapper<WindowFlag>? = if (AndroidVersion.isMinOreo())
        ItemWrapper(WindowFlag("SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR", View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR, "View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR"))
    else
        null


    val lightStatusBar: ItemWrapper<WindowFlag>? = if (AndroidVersion.isMinMarshmallow())
        ItemWrapper(WindowFlag("SYSTEM_UI_FLAG_LIGHT_STATUS_BAR", View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR, "View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR"))
    else
        null

    @SuppressLint("InlinedApi")
    val flags = arrayListOf(
            ItemWrapper(WindowFlag("SYSTEM_UI_FLAG_FULLSCREEN", View.SYSTEM_UI_FLAG_FULLSCREEN, "View.SYSTEM_UI_FLAG_FULLSCREEN")),                             // 0
            ItemWrapper(WindowFlag("SYSTEM_UI_FLAG_HIDE_NAVIGATION", View.SYSTEM_UI_FLAG_HIDE_NAVIGATION, "View.SYSTEM_UI_FLAG_HIDE_NAVIGATION")),                   // 1
            ItemWrapper(WindowFlag("SYSTEM_UI_FLAG_IMMERSIVE", View.SYSTEM_UI_FLAG_IMMERSIVE, "View.SYSTEM_UI_FLAG_IMMERSIVE")),                               // 2
            ItemWrapper(WindowFlag("SYSTEM_UI_FLAG_IMMERSIVE_STICKY", View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY, "View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY")),                 // 3
            ItemWrapper(WindowFlag("SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN", View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN, "View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN")),               // 4
            ItemWrapper(WindowFlag("SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION", View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION, "View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION")),     // 5
            ItemWrapper(WindowFlag("SYSTEM_UI_FLAG_LAYOUT_STABLE", View.SYSTEM_UI_FLAG_LAYOUT_STABLE, "View.SYSTEM_UI_FLAG_LAYOUT_STABLE")),                       // 6
            ItemWrapper(WindowFlag("SYSTEM_UI_FLAG_LOW_PROFILE", View.SYSTEM_UI_FLAG_LOW_PROFILE, "View.SYSTEM_UI_FLAG_LOW_PROFILE")),                           // 7
            ItemWrapper(WindowFlag("SYSTEM_UI_FLAG_VISIBLE", View.SYSTEM_UI_FLAG_VISIBLE, "View.SYSTEM_UI_FLAG_VISIBLE"))                                    // 8
    ).apply {
        lightNavigationBar?.let { add(it) }
        lightStatusBar?.let { add(it) }
    }

    init {
        reInit()
    }

    fun reInit()
    {
        AppInstance.activity?.window?.decorView?.systemUiVisibility?.let {
            for (flag in flags)
            {
                if (it or flag.item.flag == it)
                    flag.checked = true
            }
        }
    }

    fun setStatusBarDark()
    {
        // SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        // SYSTEM_UI_FLAG_LAYOUT_STABLE
        setCheckedFlags(4, 6)
    }

    fun setStatusBarLight()
    {
        // SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        // SYSTEM_UI_FLAG_LAYOUT_STABLE
        // SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        setCheckedFlags(4, 6, 10)
    }

    fun setLeanBack()
    {
        // SYSTEM_UI_FLAG_FULLSCREEN
        // SYSTEM_UI_FLAG_HIDE_NAVIGATION
        // SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        // SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        // SYSTEM_UI_FLAG_LAYOUT_STABLE
        setCheckedFlags(0, 1, 4, 5, 6)


        /*
        View.SYSTEM_UI_FLAG_IMMERSIVE
            // Set the content to appear under the system bars so that the
            // content doesn't resize when the system bars hide and show.
            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            // Hide the nav bar and status bar
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_FULLSCREEN
         */
    }

    fun setImmersive()
    {
        // SYSTEM_UI_FLAG_FULLSCREEN
        // SYSTEM_UI_FLAG_HIDE_NAVIGATION
        // SYSTEM_UI_FLAG_IMMERSIVE
        setCheckedFlags(0, 1, 2, 4, 5, 6)
    }

    fun setImmersiveSticky()
    {
        // SYSTEM_UI_FLAG_FULLSCREEN
        // SYSTEM_UI_FLAG_HIDE_NAVIGATION
        // SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        setCheckedFlags(0, 1, 3, 4, 5, 6)
    }

    private fun setCheckedFlags(vararg flagIndexes: Int)
    {
        reset()

        var count = 0
        for (flagIndex in flagIndexes)
        {
            // Quickly check that we can manage the last two items
            // that are api level restricted
            if (flagIndex < flags.size)
            {
                flags[flagIndex].checked = true
                count++
            }
        }

        applyFlags()
    }

    private fun reset()
    {
        for (flag in flags)
            flag.checked = false
    }

    fun applyFlags()
    {
        var visibility = 0

        for (flag in flags)
        {
            if (flag.checked)
                visibility = visibility or flag.item.flag
        }

        AppInstance.activity?.window?.decorView?.systemUiVisibility = visibility
    }

    fun fillCodeSnippet(codeSnippet: CodeSnippet)
    {
        codeSnippet.addTitle("Set systemUIVisibility")

        var kotlinCode = "val activity:Activity = getActivity() // Get reference to current activity"
        var javaCode = "Activity activity = getActivity(); // Get reference to current activity"

        var kotlinVisibility = "\n\nval visibility = 0"
        var javaVisibility = "\n\nint visibility = 0;"

        var flagCount = 0
        for (flag in flags)
        {
            if (flag.checked)
            {
                if (flagCount == 0)
                {
                    kotlinVisibility = "\n\n// Build bitwise value\nvar visibility = ${flag.item.code}"
                    javaVisibility = "\n\n// Build bitwise value\nint visibility = ${flag.item.code}"
                }
                else
                {
                    kotlinVisibility += "\nvisibility = visibility or ${flag.item.code}"
                    javaVisibility += "\n\t| ${flag.item.code}"
                }

                flagCount++
            }
        }

        if (flagCount > 0)
            javaVisibility += ";"

        kotlinCode += kotlinVisibility
        kotlinCode += "\n\nactivity?.window?.decorView?.systemUiVisibility = visibility"

        javaCode += javaVisibility
        javaCode += "\n\nactivity.getWindow().getDecorView().setSystemUiVisibility(visibility);"

        codeSnippet.addSnippets(mapOf(CodeSnippet.Language.Kotlin to kotlinCode,
                                      CodeSnippet.Language.Java to javaCode))


    }
}

data class WindowFlag(val name: String, val flag: Int, val code:String)

class ItemWrapper<T>(val item: T)
{
    var checked = false
}
