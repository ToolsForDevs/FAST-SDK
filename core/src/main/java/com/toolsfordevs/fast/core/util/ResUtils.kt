package com.toolsfordevs.fast.core.util

import android.content.Context
import android.content.res.AssetManager
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.TypedValue
import com.toolsfordevs.fast.core.AppInstance
import com.toolsfordevs.fast.core.annotation.Keep
import java.util.*

@Keep
object ResUtils {
    private val context: Context
        get() = AppInstance.get()

    private val res: Resources
        get() = context.resources

    /**
     * @param resId @DimenRes
     */
    fun getDimension(/*@DimenRes*/ resId: Int): Float {
        return res.getDimension(resId)
    }

    /**
     * @param resId @DimenRes
     */
    fun getDimension(context:Context, /*@DimenRes*/ resId: Int): Float {
        return context.resources.getDimension(resId)
    }

    /**
     * @param resId @DimenRes
     */
    fun getDimensionPxSize(/*@DimenRes*/ resId: Int): Int {
        return res.getDimensionPixelSize(resId)
    }

    /**
     * @param resId @DimenRes
     */
    fun getDimensionPxSize(context:Context, /*@DimenRes*/ resId: Int): Int {
        return context.resources.getDimensionPixelSize(resId)
    }

    /**
     * @param resId @BoolRes
     */
    fun getBoolean(/*@BoolRes*/ resId: Int): Boolean {
        return res.getBoolean(resId)
    }

    fun getBoolean(context:Context, /*@BoolRes*/ resId: Int): Boolean {
        return context.resources.getBoolean(resId)
    }

    /**
     * @param resId @BoolRes
     */
    fun getString(/*@StringRes*/ resId: Int): String {
        return res.getString(resId)
    }

    /**
     * @param resId @StringRes
     */
    fun getString(context:Context, /*@StringRes*/ resId: Int): String {
        return context.resources.getString(resId)
    }

    /**
     * @param resId @StringRes
     */
    fun getString(/*@StringRes*/ resId: Int, vararg args: Any): String {
        return res.getString(resId, *args)
    }

    /**
     * @param resId @StringRes
     */
    fun getString(context:Context, /*@StringRes*/ resId: Int, vararg args: Any): String {
        return context.resources.getString(resId, *args)
    }

    private fun getLocalizedResources(context: Context, locale: Locale):Resources
    {
        var conf = Configuration(context.resources.configuration).apply { setLocale(locale) }
        return context.createConfigurationContext(conf).resources
    }

    fun getLocalizedString(context:Context, locale:Locale, resId:Int):String
    {
        return getLocalizedResources(context, locale).getString(resId)
    }

    fun getLocalizedString(locale:Locale, resId:Int):String
    {
        return getLocalizedString(AppInstance.get(), locale, resId)
    }

    /**
     * @param resId @ArrayRes
     */
    fun getStringArray(/*@ArrayRes*/ resId: Int): Array<String> {
        return res.getStringArray(resId)
    }

    /**
     * @param resId @ArrayRes
     */
    fun getStringArray(context:Context, /*@ArrayRes*/ resId: Int): Array<String> {
        return context.resources.getStringArray(resId)
    }

    /**
     * @param resId @ArrayRes
     */
    fun getIntArray(/*@ArrayRes*/ resId: Int): IntArray {
        return res.getIntArray(resId)
    }

    /**
     * @param resId @ArrayRes
     */
    fun getIntArray(context:Context, /*@ArrayRes*/ resId: Int): IntArray {
        return context.resources.getIntArray(resId)
    }

    /**
     * @param resId @ColorRes
     */
    fun getColor(/*@ColorRes*/ resId: Int, context:Context? = null): Int {
        return if (Build.VERSION.SDK_INT >= 23)
            (context ?: ResUtils.context).getColor(resId)
        else
            (context ?: ResUtils.context).getResources().getColor(resId)
    }

    /**
     * @param resId @ColorRes
     */
    fun getColorStateList(/*@ColorRes*/ resId: Int): ColorStateList? {
        return getColorStateList(context, resId)
    }

    /**
     * @param resId @ColorRes
     */
    fun getColorStateList(context: Context, /*@ColorRes*/ resId: Int): ColorStateList? {
        return if (Build.VERSION.SDK_INT >= 23)
            context.getColorStateList(resId)
        else
            context.resources.getColorStateList(resId)
    }

    /**
     * @param resId @DrawableRes
     */
    fun getDrawable(/*@DrawableRes*/ resId: Int): Drawable? {
        return context.getDrawable(resId)
    }

    /**
     * @param resId @DrawableRes
     */
    fun getDrawable(context:Context, /*@DrawableRes*/ resId: Int): Drawable? {
        return context.getDrawable(resId)
    }

    /**
     * @param resId @DrawableRes
     * @param colo @ColorInt
     */
    fun getTintedDrawable(/*@DrawableRes*/ resId: Int, /*@ColorInt*/ color:Int): Drawable? {
        val drawable = getDrawable(resId)

        drawable?.setTint(color)

        return drawable
    }

    /**
     * @param resId @DrawableRes
     * @param colo @ColorInt
     */
    fun getTintedDrawable(context:Context, /*@DrawableRes*/ resId: Int, /*@ColorInt*/ color:Int): Drawable? {
        val drawable = getDrawable(context, resId)

        drawable?.setTint(color)

        return drawable
    }

    fun getAssetManager(): AssetManager {
        return res.assets
    }

    fun getDrawableByName(name: String): Int {
        return res.getIdentifier(name, "drawable", context.packageName)
    }

    fun getDrawableByName(context:Context, name: String): Int {
        return context.resources.getIdentifier(name, "drawable", context.packageName)
    }

    fun getStringByName(name: String): String {
        return getString(res.getIdentifier(name, "string", context.packageName))
    }

    fun getStringByName(context:Context, name: String): String {
        return getString(context.resources.getIdentifier(name, "string", context.packageName))
    }

    fun getDpInPx(dpSize: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpSize.toFloat(), res.displayMetrics).toInt()
    }

    fun getSpInPx(spSize: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spSize.toFloat(), res.displayMetrics).toInt()
    }

    fun getDimensionForDimensionUnit(dimensionUnit:Int, resId:Int):Float
    {
        val dimens = res.getDimensionPixelSize(resId)
        return TypedValue.applyDimension(dimensionUnit, dimens.toFloat(), res.displayMetrics)
    }

    fun getDrawableResourceName(resId:Int):String
    {
        return getResourceName("drawable", resId)
    }

    fun getStringResourceName(resId:Int):String
    {
        return getResourceName("string", resId)
    }

    fun getThemeResourceName(resId:Int):String
    {
        return getResourceName("style", resId)
    }

    private fun getResourceName(resourceType:String, resId:Int):String
    {
        if (resId == 0)
            return "0"

        return "R.$resourceType." + res.getResourceEntryName(resId) + " ($resId)"
    }
}
