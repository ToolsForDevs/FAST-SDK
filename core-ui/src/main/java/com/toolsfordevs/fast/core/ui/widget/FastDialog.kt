package com.toolsfordevs.fast.core.ui.widget

import android.app.Dialog
import android.content.Context
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.core.ui.R

@Keep
open class FastDialog(context:Context) : Dialog(context, R.style.Fast_FullscreenDialogTheme)
{
    override fun onStart()
    {
        super.onStart()
        window?.decorView?.setPadding(0, 0, 0, 0)
        window?.decorView?.setBackgroundResource(0)
    }
}