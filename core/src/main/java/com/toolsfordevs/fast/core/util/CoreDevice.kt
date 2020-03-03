package com.toolsfordevs.fast.core.util

import android.content.res.Configuration
import com.toolsfordevs.fast.core.AppInstance
import com.toolsfordevs.fast.core.annotation.Keep

/**
 * Return various metrics on the device screen
 * The values are always up to date, even after a configuration change like a screen rotation
 */
@Keep
object CoreDevice
{
    private val metrics
        get() = AppInstance.get().resources.displayMetrics

    private val configuration
        get() = AppInstance.get().resources.configuration

    val screenWidthPx
        get() = metrics.widthPixels

    val screenHeightPx
        get() = metrics.heightPixels

    /**
     * returns the current display dpi, either 120 (ldpi), 160(mdpi), 240 (hdpi), 320(xhdpi), 480(xxhdpi) or 640(xxxhdpi)
     */
    val dpi
        get() = metrics.densityDpi

    /**
     * Returns the dpi factor of the screen. 1 for mdpi, 1.5 for hdpi, 2 for xhdpi, 3 for xxhdpi, 4 for xxxhdpi
     * The factor is base on the mdpi (160) reference
     */
    val dpiFactor
        get() = metrics.density

    val exactScreenWidthDp
        get() = metrics.xdpi

    val exactScreenHeightDp
        get() = metrics.ydpi

    /**
     * return the current width value from the resources folder. One of mdpi/hdpi/xhdpi/xxhdpi/xxhdpi corresponding values
     */
    val screenWidthDp
        get() = configuration.screenWidthDp

    /**
     * * return the current height value from the resources folder. One of mdpi/hdpi/xhdpi/xxhdpi/xxhdpi corresponding values
     */
    val screenHeightDp
        get() = configuration.screenHeightDp

    val smallestScreenWidthDp
        get() = configuration.smallestScreenWidthDp

    private val orientation
        get() = configuration.orientation

    val isPortrait
        get() = orientation == Configuration.ORIENTATION_PORTRAIT

    val isLandscape
        get() = orientation == Configuration.ORIENTATION_LANDSCAPE

    override fun toString():String
    {
        return "portrait $isPortrait, landscape $isLandscape\nscreenWidthDp $screenWidthDp, screenHeightDp $screenHeightDp\nexactScreenWidthDp $exactScreenWidthDp, exactScreenHeightDp $exactScreenHeightDp\nscrenWidthPx $screenWidthPx, screenHeightPx $screenHeightPx\ndpi $dpi, dpiFactor $dpiFactor"
    }
}