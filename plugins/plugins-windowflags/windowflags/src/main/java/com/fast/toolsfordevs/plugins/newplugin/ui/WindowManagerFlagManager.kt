package com.fast.toolsfordevs.plugins.newplugin.ui

import android.app.Activity
import android.view.WindowManager
import com.fast.toolsfordevs.modules.codegenerator.CodeSnippet
import com.toolsfordevs.fast.core.AppInstance

internal class WindowManagerFlagManager
{
    val translucentNavigation = ItemWrapper(WindowFlag("FLAG_TRANSLUCENT_NAVIGATION", WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, "WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION"))
    val translucentStatusBar = ItemWrapper(WindowFlag("FLAG_TRANSLUCENT_STATUS", WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, "WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS"))

    val flags = listOf(ItemWrapper(WindowFlag("FLAG_ALLOW_LOCK_WHILE_SCREEN_ON", WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON, "WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON")),
                       ItemWrapper(WindowFlag("FLAG_ALT_FOCUSABLE_IM", WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM, "WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM")),
                       ItemWrapper(WindowFlag("FLAG_DIM_BEHIND", WindowManager.LayoutParams.FLAG_DIM_BEHIND, "WindowManager.LayoutParams.FLAG_DIM_BEHIND")),
                       ItemWrapper(WindowFlag("FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS", WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS, "WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS")),
                       ItemWrapper(WindowFlag("FLAG_FORCE_NOT_FULLSCREEN", WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, "WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN")),
                       ItemWrapper(WindowFlag("FLAG_FULLSCREEN", WindowManager.LayoutParams.FLAG_FULLSCREEN, "WindowManager.LayoutParams.FLAG_FULLSCREEN")),
                       ItemWrapper(WindowFlag("FLAG_HARDWARE_ACCELERATED", WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, "WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED")),
                       ItemWrapper(WindowFlag("FLAG_IGNORE_CHEEK_PRESSES", WindowManager.LayoutParams.FLAG_IGNORE_CHEEK_PRESSES, "WindowManager.LayoutParams.FLAG_IGNORE_CHEEK_PRESSES")),
                       ItemWrapper(WindowFlag("FLAG_KEEP_SCREEN_ON", WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, "WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON")),
                       ItemWrapper(WindowFlag("FLAG_LAYOUT_ATTACHED_IN_DECOR", WindowManager.LayoutParams.FLAG_LAYOUT_ATTACHED_IN_DECOR, "WindowManager.LayoutParams.FLAG_LAYOUT_ATTACHED_IN_DECOR")),
                       ItemWrapper(WindowFlag("FLAG_LAYOUT_INSET_DECOR", WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR, "WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR")),
                       ItemWrapper(WindowFlag("FLAG_LAYOUT_IN_OVERSCAN", WindowManager.LayoutParams.FLAG_LAYOUT_IN_OVERSCAN, "WindowManager.LayoutParams.FLAG_LAYOUT_IN_OVERSCAN")),
                       ItemWrapper(WindowFlag("FLAG_LAYOUT_IN_SCREEN", WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, "WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN")),
                       ItemWrapper(WindowFlag("FLAG_LAYOUT_NO_LIMITS", WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, "WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS")),
                       ItemWrapper(WindowFlag("FLAG_LOCAL_FOCUS_MODE", WindowManager.LayoutParams.FLAG_LOCAL_FOCUS_MODE, "WindowManager.LayoutParams.FLAG_LOCAL_FOCUS_MODE")),
                       ItemWrapper(WindowFlag("FLAG_NOT_FOCUSABLE", WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, "WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE")),
                       ItemWrapper(WindowFlag("FLAG_NOT_TOUCHABLE", WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, "WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE")),
                       ItemWrapper(WindowFlag("FLAG_NOT_TOUCH_MODAL", WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, "WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL")),
                       ItemWrapper(WindowFlag("FLAG_SCALED", WindowManager.LayoutParams.FLAG_SCALED, "WindowManager.LayoutParams.FLAG_SCALED")),
                       ItemWrapper(WindowFlag("FLAG_SECURE", WindowManager.LayoutParams.FLAG_SECURE, "WindowManager.LayoutParams.FLAG_SECURE")),
                       ItemWrapper(WindowFlag("FLAG_SHOW_WALLPAPER", WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER, "WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER")),
                       ItemWrapper(WindowFlag("FLAG_SPLIT_TOUCH", WindowManager.LayoutParams.FLAG_SPLIT_TOUCH, "WindowManager.LayoutParams.FLAG_SPLIT_TOUCH")),
                       translucentNavigation,
                       translucentStatusBar,
                       ItemWrapper(WindowFlag("FLAG_WATCH_OUTSIDE_TOUCH", WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, "WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH")))

    init
    {
        reInit()
    }

    fun reInit()
    {
        AppInstance.activity?.window?.let {
            val windowFlags = it.attributes.flags
            for (flag in flags)
            {
                if (windowFlags or flag.item.flag == windowFlags)
                    flag.checked = true
            }
        }
    }

    private fun setCheckedFlags(vararg flagIndexes:Int)
    {
        reset()

        for (flagIndex in flagIndexes)
            flags[flagIndex].checked = true

        applyFlags()
    }

    private fun reset()
    {
        for (flag in flags)
            flag.checked = false
    }

    fun applyFlags()
    {
        AppInstance.activity?.window?.let { window ->
            window.clearFlags(flags.map { it.item.flag }
                                  .fold(0, { acc, i -> acc or i }))

            window.addFlags(flags.filter { it.checked }
                                  .map { it.item.flag }
                                  .fold(0, { acc, i -> acc or i }))
        }
    }

    fun fillCodeSnippet(codeSnippet: CodeSnippet)
    {
        codeSnippet.addTitle("Set Window flags")

        var kotlinCode = "val activity:Activity = getActivity() // Get reference to current activity"
        var javaCode = "Activity activity = getActivity(); // Get reference to current activity"

        var kotlinFlags = "\n\nval flags = 0"
        var javaFlags = "\n\nint flags = 0;"

        var flagCount = 0
        for (flag in flags)
        {
            if (flag.checked)
            {
                if (flagCount == 0)
                {
                    kotlinFlags = "\n\n// Build bitwise value\nvar flags = ${flag.item.code}"
                    javaFlags = "\n\n// Build bitwise value\nint flags = ${flag.item.code}"
                }
                else
                {
                    kotlinFlags += "\nflags = flags or ${flag.item.code}"
                    javaFlags += "\n\t| ${flag.item.code}"
                }

                flagCount++
            }
        }

        if (flagCount > 0)
            javaFlags += ";"

        kotlinCode += kotlinFlags
        kotlinCode += "\n\nactivity?.window?.addFlags(flags)"

        javaCode += javaFlags
        javaCode += "\n\nactivity.getWindow().addFlags(flags);"

        codeSnippet.addSnippets(mapOf(
                CodeSnippet.Language.Kotlin to kotlinCode,
                CodeSnippet.Language.Java to javaCode))


    }
}