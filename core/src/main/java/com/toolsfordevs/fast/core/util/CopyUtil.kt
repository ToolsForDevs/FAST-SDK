package com.toolsfordevs.fast.core.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import com.toolsfordevs.fast.core.AppInstance

object CopyUtil
{
    fun copy(textToCopy:String)
    {
        copy(textToCopy, textToCopy)
    }

    fun copy(label:String, textToCopy: String)
    {
        val clipboard: ClipboardManager = AppInstance.get().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("FAST value to copy", textToCopy)
        clipboard.setPrimaryClip(clip)

        Toast.makeText(AppInstance.get(), "$label copied to clipboard!", Toast.LENGTH_SHORT).show()
    }
}