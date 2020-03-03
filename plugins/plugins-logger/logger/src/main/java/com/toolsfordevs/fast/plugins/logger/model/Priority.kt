package com.toolsfordevs.fast.plugins.logger.model

import com.toolsfordevs.fast.plugins.logger.ui.Prefs


internal enum class Priority(val value:Int)
{
    DEFAULT(0) { override fun color() = Prefs.colorDefault },
    DEBUG(1)  { override fun color() = Prefs.colorDebug },
    INFO(2) { override fun color() = Prefs.colorInfo },
    WARNING(3) { override fun color() = Prefs.colorWarning },
    ERROR(4) { override fun color() = Prefs.colorError },
    WTF(5) { override fun color() = Prefs.colorWTF };

    abstract fun color(): Int
}