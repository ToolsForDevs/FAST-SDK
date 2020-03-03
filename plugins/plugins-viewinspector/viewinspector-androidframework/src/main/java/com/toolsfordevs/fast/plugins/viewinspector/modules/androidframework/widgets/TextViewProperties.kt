package com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.widgets

import android.annotation.SuppressLint
import android.os.Build
import android.text.InputType
import android.text.Layout
import android.text.TextUtils
import android.text.method.SingleLineTransformationMethod
import android.text.util.Linkify
import android.util.ArrayMap
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.toolsfordevs.fast.core.util.AndroidVersion
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.*
import com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.properties.GravityProperty


@Suppress("DEPRECATION")
internal class TextViewProperties : ViewPropertyHolder(TextView::class.java)
{
    init
    {
        add(PropertyCategory.COMMON, StringProperty(TextView::getText, TextView::setText))
        add(PropertyCategory.COMMON, TextSizeProperty())
        // ToDo TextPicker Select font and style (normal, bold, italic, bold italic) or weight
        // add(PropertyCategory.COMMON, TextStyleProperty(""))
        add(PropertyCategory.COMMON, ColorStateListProperty(TextView::getTextColors, TextView::setTextColor))
        add(PropertyCategory.COMMON, GravityProperty(TextView::getGravity, TextView::setGravity))
        add(PropertyCategory.COMMON, StringProperty(TextView::getHint, TextView::setHint))
        add(PropertyCategory.COMMON, ColorProperty(TextView::getCurrentHintTextColor, TextView::setHintTextColor))
        add(PropertyCategory.COMMON, EllipsizeProperty())
        add(PropertyCategory.COMMON, SingleLineProperty())
        // ToDo
        // Text font
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) add(PropertyCategory.COMMON,
            BooleanProperty(TextView::isAllCaps, TextView::setAllCaps).apply { newApi = "28" })

        add(PropertyCategory.COMMON, AutoLinkProperty())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) add(PropertyCategory.COMMON, BreakStrategyProperty().apply { newApi = "23" })

        add(PropertyCategory.COMMON, DimensionIntRangeProperty(TextView::getCompoundDrawablePadding, TextView::setCompoundDrawablePadding, max = screenWidth))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            add(PropertyCategory.COMMON, ColorStateListProperty(TextView::getCompoundDrawableTintList, TextView::setCompoundDrawableTintList).apply {
                newApi = "23"
            })
            add(PropertyCategory.COMMON,
                ColorTintModeProperty(TextView::getCompoundDrawableTintMode, TextView::setCompoundDrawableTintMode).apply { newApi = "23" })
        }


        add(PropertyCategory.COMMON, DrawableProperty(TextView::compoundDrawableLeft).apply { name = "compoundDrawableLeft" })
        add(PropertyCategory.COMMON, DrawableProperty(TextView::compoundDrawableTop).apply { name = "compoundDrawableTop" })
        add(PropertyCategory.COMMON, DrawableProperty(TextView::compoundDrawableRight).apply { name = "compoundDrawableRight" })
        add(PropertyCategory.COMMON, DrawableProperty(TextView::compoundDrawableBottom).apply { name = "compoundDrawableBottom" })
        add(PropertyCategory.COMMON, BooleanProperty(TextView::isCursorVisible, TextView::setCursorVisible))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) add(PropertyCategory.COMMON,
            BooleanProperty(TextView::isElegantTextHeight, TextView::setElegantTextHeight).apply { newApi = "28" })

        add(PropertyCategory.COMMON, StringProperty(TextView::getError, TextView::setError))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) add(PropertyCategory.COMMON,
            BooleanProperty(TextView::isFallbackLineSpacing, TextView::setFallbackLineSpacing).apply { newApi = "28" })

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) // AppCompatTextView
            add(PropertyCategory.COMMON,
                DimensionIntRangeProperty(TextView::getFirstBaselineToTopHeight, TextView::setFirstBaselineToTopHeight, max = screenHeight).apply {
                    newApi = "28"
                })

        add(PropertyCategory.COMMON, StringProperty(TextView::getFontFeatureSettings) { fontFeatureSettings = it.toString() })

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) add(PropertyCategory.COMMON,
            StringProperty(TextView::getFontVariationSettings) { fontVariationSettings = it as String? }.apply { newApi = "26" })

        add(PropertyCategory.COMMON, BooleanProperty(TextView::getFreezesText, TextView::setFreezesText))
        add(PropertyCategory.COMMON, ColorProperty(TextView::getHighlightColor, TextView::setHighlightColor))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) add(PropertyCategory.COMMON, HyphenationProperty().apply { newApi = "23" })

        add(PropertyCategory.COMMON, ImeOptionsProperty(TextView::getImeOptions, TextView::setImeOptions))
        add(PropertyCategory.COMMON, BooleanProperty(TextView::getIncludeFontPadding, TextView::setIncludeFontPadding))
        add(PropertyCategory.COMMON, InputTypeProperty())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) add(PropertyCategory.COMMON, JustificationModeProperty().apply { newApi = "26" })

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) add(PropertyCategory.COMMON,
            DimensionIntRangeProperty(TextView::getLastBaselineToBottomHeight, TextView::setLastBaselineToBottomHeight, max = screenHeight).apply {
                newApi = "28"
            })

        add(PropertyCategory.COMMON, FloatRangeProperty(TextView::getLetterSpacing, TextView::setLetterSpacing, 0f, 5f, 2))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) add(PropertyCategory.COMMON,
            IntRangeProperty(TextView::getLineHeight, TextView::setLineHeight, 0, 300).apply { newApi = "28" })

        add(PropertyCategory.COMMON, LineSpacingExtraProperty())
        add(PropertyCategory.COMMON, LineSpacingMultiplierProperty())
        add(PropertyCategory.COMMON, ColorStateListProperty(TextView::getLinkTextColors, TextView::setLinkTextColor))
        add(PropertyCategory.COMMON, BooleanProperty(TextView::getLinksClickable, TextView::setLinksClickable))
        add(PropertyCategory.COMMON, IntRangeProperty(TextView::getMarqueeRepeatLimit, TextView::setMarqueeRepeatLimit, -1, 50))
        add(PropertyCategory.COMMON, DimensionIntRangeProperty(TextView::getMaxHeight, TextView::setMaxHeight, max = screenHeight))
        add(PropertyCategory.COMMON, IntRangeProperty(TextView::getMaxLines, TextView::setMaxLines, -1, 50))
        add(PropertyCategory.COMMON, DimensionIntRangeProperty(TextView::getMaxWidth, TextView::setMaxWidth, max = screenWidth))
        add(PropertyCategory.COMMON, DimensionIntRangeProperty(TextView::getMinHeight, TextView::setMinHeight, max = screenHeight))
        add(PropertyCategory.COMMON, IntRangeProperty(TextView::getMinLines, TextView::setMinLines, -1, 50))
        add(PropertyCategory.COMMON, DimensionIntRangeProperty(TextView::getMinWidth, TextView::setMinWidth, max = screenWidth))
        add(PropertyCategory.COMMON, BooleanProperty(TextView::isSelected, TextView::setSelected))
        add(PropertyCategory.COMMON, BooleanProperty(TextView::getShowSoftInputOnFocus, TextView::setShowSoftInputOnFocus))

        // Android Q
        //add(PropertyCategory.COMMON, DrawableProperty("Text Cursor Drawable", DrawableDelegate(view, TextView::getTextCursorDrawable, TextView::setTextCursorDrawable))
        add(PropertyCategory.COMMON, BooleanProperty(TextView::isTextSelectable, TextView::setTextIsSelectable))
        add(PropertyCategory.COMMON, FloatRangeProperty(TextView::getTextScaleX, TextView::setTextScaleX, 0f, 20f, 2))

        // Android Q
        //add(PropertyCategory.COMMON, DrawableProperty("Text Select Handle", DrawableDelegate(view, TextView::getTextSelectHandle, TextView::setTextSelectHandle))
        //add(PropertyCategory.COMMON, DrawableProperty("Text Select Handle Left", DrawableDelegate(view, TextView::getTextSelectHandleLeft, TextView::setTextSelectHandleLeft))
        //add(PropertyCategory.COMMON, DrawableProperty("Text Select Handle Right", DrawableDelegate(view, TextView::getTextSelectHandleRight, TextView::setTextSelectHandleRight))
    }

    inner class TextSizeProperty : DimensionFloatRangeProperty<TextView>(TextView::textSizeP, min = sp(8f), max = sp(112f))
    {
        init
        {
            name = "getTextSize"
            scaledUnit = TypedValue.COMPLEX_UNIT_SP

        }
    }


/*class TextStyleProperty():SingleChoiceProperty<TextView, Typeface>(TextView::getTypeface, TextView::setTypeface)
{
    override fun defineKeyValues(map: ArrayMap<Int, String>) {
        map.put(Typeface.NORMAL, "NORMAL")
        map.put(Typeface.ITALIC, "ITALIC")
        map.put(Typeface.BOLD, "BOLD")
        map.put(Typeface.BOLD_ITALIC, "BOLD ITALIC")
    }
}*/
/*

class TextStyleDelegate(view:TextView) : MultiValueDelegate<TextView>(view)
{
    override fun getLabels(): List<String> {
        return arrayListOf("Normal", "Bold", "Italic", "Bold Italic")
    }

    override fun getSelectedIndex(): Int {
        val typeface = view.typeface

        if (typeface.isBold && typeface.isItalic)
            return 3

        if (typeface.isItalic)
            return 2

        if (typeface.isBold)
            return 1

        return 0
    }

    override fun setSelectedIndex(index: Int) {
        view.typeface = Typeface.create(view.typeface, when (index) {
            0 -> Typeface.NORMAL
            1 -> Typeface.BOLD
            2 -> Typeface.ITALIC
            3 -> Typeface.BOLD_ITALIC
            else -> Typeface.NORMAL
        })
    }
}
*/

    // ToDo check behavior
    class ImeOptionsProperty<T : View>(getter: (T.() -> Int?)? = null, setter: (T.(Int) -> Unit)? = null) : BitwiseMultiChoiceProperty<T>(getter, setter)
    {
        override fun defineKeyValues(map: ArrayMap<Int, String>)
        {
            map.put(0, "NORMAL")
            map.put(EditorInfo.IME_ACTION_DONE, "IME ACTION DONE")
            map.put(EditorInfo.IME_ACTION_GO, "IME ACTION GO")
            map.put(EditorInfo.IME_ACTION_NEXT, "IME ACTION NEXT")
            map.put(EditorInfo.IME_ACTION_NONE, "IME ACTION NONE")
            map.put(EditorInfo.IME_ACTION_PREVIOUS, "IME ACTION PREVIOUS")
            map.put(EditorInfo.IME_ACTION_SEARCH, "IME ACTION SEARCH")
            map.put(EditorInfo.IME_ACTION_SEND, "IME ACTION SEND")
            map.put(EditorInfo.IME_FLAG_FORCE_ASCII, "IME FLAG FORCE ASCII")
            map.put(EditorInfo.IME_FLAG_NAVIGATE_NEXT, "IME FLAG NAVIGATE NEXT ")
            map.put(EditorInfo.IME_FLAG_NAVIGATE_PREVIOUS, "IME FLAG NAVIGATE PREVIOUS ")
            map.put(EditorInfo.IME_FLAG_NO_ACCESSORY_ACTION, "IME FLAG NO ACCESSORY ACTION")
            map.put(EditorInfo.IME_FLAG_NO_ENTER_ACTION, "IME FLAG NO ENTER ACTION")
            map.put(EditorInfo.IME_FLAG_NO_EXTRACT_UI, "IME FLAG NO EXTRACT UI")
            map.put(EditorInfo.IME_FLAG_NO_FULLSCREEN, "IME FLAG NO FULLSCREEN")

            if (AndroidVersion.isMinOreo())
                map.put(EditorInfo.IME_FLAG_NO_PERSONALIZED_LEARNING, "IME FLAG NO PERSONALIZED LEARNING (NEW API 26)")
        }
    }

    private class AutoLinkProperty : BitwiseMultiChoiceProperty<TextView>(TextView::getAutoLinkMask, TextView::setAutoLinkMask)
    {
        override fun defineKeyValues(map: ArrayMap<Int, String>)
        {
            map.put(0, "NONE")
            map.put(Linkify.EMAIL_ADDRESSES, "EMAIL ADDRESSES")
            map.put(Linkify.MAP_ADDRESSES, "MAP ADDRESSES (DEPRECATED API 28)")
            map.put(Linkify.PHONE_NUMBERS, "PHONE NUMBERS")
            map.put(Linkify.WEB_URLS, "WEB URLS")
            map.put(Linkify.ALL, "ALL")
        }
    }

    private class EllipsizeProperty : SingleChoiceProperty<TextView, TextUtils.TruncateAt>(TextView::getEllipsize, TextView::setEllipsize)
    {
        override fun defineKeyValues(map: ArrayMap<TextUtils.TruncateAt, String>)
        {
            map.put(null, "NONE")
            map.put(TextUtils.TruncateAt.START, "START")
            map.put(TextUtils.TruncateAt.MIDDLE, "MIDDLE")
            map.put(TextUtils.TruncateAt.MARQUEE, "MARQUEE")
            map.put(TextUtils.TruncateAt.END, "END")
        }
    }

    class InputTypeProperty : BitwiseMultiChoiceProperty<TextView>(TextView::getInputType, TextView::setInputType)
    {
        override fun defineKeyValues(map: ArrayMap<Int, String>)
        {
            map.put(0, "NONE")
            map.put(InputType.TYPE_DATETIME_VARIATION_DATE, "TYPE_DATETIME_VARIATION_DATE")
            map.put(InputType.TYPE_DATETIME_VARIATION_NORMAL, "TYPE_DATETIME_VARIATION_NORMAL")
            map.put(InputType.TYPE_DATETIME_VARIATION_TIME, "TYPE_DATETIME_VARIATION_TIME")
            map.put(InputType.TYPE_NULL, "TYPE_NULL")
            map.put(InputType.TYPE_NUMBER_FLAG_DECIMAL, "TYPE_NUMBER_FLAG_DECIMAL")
            map.put(InputType.TYPE_NUMBER_FLAG_SIGNED, "TYPE_NUMBER_FLAG_SIGNED")
            map.put(InputType.TYPE_NUMBER_VARIATION_NORMAL, "TYPE_NUMBER_VARIATION_NORMAL")
            map.put(InputType.TYPE_NUMBER_VARIATION_PASSWORD, "TYPE_NUMBER_VARIATION_PASSWORD")
            map.put(InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE, "TYPE_TEXT_FLAG_AUTO_COMPLETE")
            map.put(InputType.TYPE_TEXT_FLAG_AUTO_CORRECT, "TYPE_TEXT_FLAG_AUTO_CORRECT")
            map.put(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS, "TYPE_TEXT_FLAG_CAP_CHARACTERS")
            map.put(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES, "TYPE_TEXT_FLAG_CAP_SENTENCES")
            map.put(InputType.TYPE_TEXT_FLAG_CAP_WORDS, "TYPE_TEXT_FLAG_CAP_WORDS")
            map.put(InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE, "TYPE_TEXT_FLAG_IME_MULTI_LINE")
            map.put(InputType.TYPE_TEXT_FLAG_MULTI_LINE, "TYPE_TEXT_FLAG_MULTI_LINE")
            map.put(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS, "TYPE_TEXT_FLAG_NO_SUGGESTIONS")
            map.put(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS, "TYPE_TEXT_VARIATION_EMAIL_ADDRESS")
            map.put(InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT, "TYPE_TEXT_VARIATION_EMAIL_SUBJECT")
            map.put(InputType.TYPE_TEXT_VARIATION_FILTER, "TYPE_TEXT_VARIATION_FILTER")
            map.put(InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE, "TYPE_TEXT_VARIATION_LONG_MESSAGE")
            map.put(InputType.TYPE_TEXT_VARIATION_NORMAL, "TYPE_TEXT_VARIATION_NORMAL")
            map.put(InputType.TYPE_TEXT_VARIATION_PASSWORD, "TYPE_TEXT_VARIATION_PASSWORD")
            map.put(InputType.TYPE_TEXT_VARIATION_PERSON_NAME, "TYPE_TEXT_VARIATION_PERSON_NAME")
            map.put(InputType.TYPE_TEXT_VARIATION_PHONETIC, "TYPE_TEXT_VARIATION_PHONETIC")
            map.put(InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS, "TYPE_TEXT_VARIATION_POSTAL_ADDRESS")
            map.put(InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE, "TYPE_TEXT_VARIATION_SHORT_MESSAGE")
            map.put(InputType.TYPE_TEXT_VARIATION_URI, "TYPE_TEXT_VARIATION_URI")
            map.put(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD, "TYPE_TEXT_VARIATION_VISIBLE_PASSWORD")
            map.put(InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT, "TYPE_TEXT_VARIATION_WEB_EDIT_TEXT")
            map.put(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS, "TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS")
            map.put(InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD, "TYPE_TEXT_VARIATION_WEB_PASSWORD")
        }
    }

    private class SingleLineProperty : BooleanProperty<TextView>(TextView::singleLine)
    {
        init
        {
            name = "singleLine"
        }
    }

    private class LineSpacingExtraProperty : FloatRangeProperty<TextView>(TextView::getLineSpacingExtra, max = 200f, decimalScale = 0)
    {
        override fun setValue(value: Float?): Boolean
        {
            view.setLineSpacing(value ?: 0f, view.lineSpacingMultiplier)
            return true
        }
    }

    private class LineSpacingMultiplierProperty : FloatRangeProperty<TextView>(TextView::getLineSpacingExtra, max = 5f)
    {
        init
        {
            name = "lineSpacingMultiplier"
        }
        override fun setValue(value: Float?): Boolean
        {
            view.setLineSpacing(view.lineSpacingExtra, value ?: 1f)
            return true
        }
    }

    @SuppressLint("NewApi")
    private class HyphenationProperty : SingleChoiceProperty<TextView, Int>(TextView::getHyphenationFrequency, TextView::setHyphenationFrequency)
    {
        override fun defineKeyValues(map: ArrayMap<Int, String>)
        {
            map.put(Layout.HYPHENATION_FREQUENCY_NONE, "NONE")
            map.put(Layout.HYPHENATION_FREQUENCY_NORMAL, "NORMAL")
            map.put(Layout.HYPHENATION_FREQUENCY_FULL, "FULL")
        }
    }

    @SuppressLint("NewApi")
    private class JustificationModeProperty : SingleChoiceProperty<TextView, Int>(TextView::getJustificationMode, TextView::setJustificationMode)
    {
        override fun defineKeyValues(map: ArrayMap<Int, String>)
        {
            map.put(Layout.JUSTIFICATION_MODE_NONE, "NONE")
            map.put(Layout.JUSTIFICATION_MODE_INTER_WORD, "INTER WORD")
        }
    }

    @SuppressLint("NewApi")
    private class BreakStrategyProperty : SingleChoiceProperty<TextView, Int>(TextView::getBreakStrategy, TextView::setBreakStrategy)
    {
        override fun defineKeyValues(map: ArrayMap<Int, String>)
        {
            map.put(Layout.BREAK_STRATEGY_HIGH_QUALITY, "HIGH QUALITY")
            map.put(Layout.BREAK_STRATEGY_SIMPLE, "SIMPLE")
            map.put(Layout.BREAK_STRATEGY_BALANCED, "BALANCED")
        }
    }
}

internal var TextView.compoundDrawableLeft
    get() = this.compoundDrawables.get(0)
    set(value)
    {
        setCompoundDrawablesWithIntrinsicBounds(value, compoundDrawableTop, compoundDrawableRight, compoundDrawableBottom)
    }
internal var TextView.compoundDrawableTop
    get() = this.compoundDrawables.get(1)
    set(value)
    {
        setCompoundDrawablesWithIntrinsicBounds(compoundDrawableLeft, value, compoundDrawableRight, compoundDrawableBottom)
    }
internal var TextView.compoundDrawableRight
    get() = this.compoundDrawables.get(2)
    set(value)
    {
        setCompoundDrawablesWithIntrinsicBounds(compoundDrawableLeft, compoundDrawableTop, value, compoundDrawableBottom)
    }
internal var TextView.compoundDrawableBottom
    get() = this.compoundDrawables.get(3)
    set(value)
    {
        setCompoundDrawablesWithIntrinsicBounds(compoundDrawableLeft, compoundDrawableTop, compoundDrawableRight, value)
    }

internal var TextView.textSizeP: Float
    get() = getTextSize()
    set(value)
    {
        setTextSize(TypedValue.COMPLEX_UNIT_PX, value)
    }

internal var TextView.singleLine
    get() = lineCount == 1 && transformationMethod == SingleLineTransformationMethod.getInstance()
    set(value) = setSingleLine(value)