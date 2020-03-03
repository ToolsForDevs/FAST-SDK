package com.toolsfordevs.fast.plugins.viewinspector.modules.appcompat.widgets

import android.text.InputType
import android.util.ArrayMap
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import com.toolsfordevs.fast.core.util.AndroidVersion
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.*


internal class SearchViewProperties: ViewPropertyHolder(SearchView::class.java)
{
    init
    {
        add(PropertyCategory.COMMON,
            BooleanProperty(SearchView::isIconified, SearchView::setIconified))
        add(PropertyCategory.COMMON,
            BooleanProperty(SearchView::isIconfiedByDefault,
                SearchView::setIconifiedByDefault))
        add(PropertyCategory.COMMON, ImeOptionsProperty(SearchView::getImeOptions, SearchView::setImeOptions))
        add(PropertyCategory.COMMON, InputTypeProperty())
        add(PropertyCategory.COMMON,
            DimensionIntRangeProperty(SearchView::getMaxWidth,
                SearchView::setMaxWidth,
                max = screenWidth))
        add(PropertyCategory.COMMON,
            StringProperty(SearchView::getQueryHint, SearchView::setQueryHint))
        add(PropertyCategory.COMMON,
            BooleanProperty(SearchView::isQueryRefinementEnabled,
                SearchView::setQueryRefinementEnabled))
        add(PropertyCategory.COMMON,
            BooleanProperty(SearchView::isSubmitButtonEnabled,
                SearchView::setSubmitButtonEnabled))

    }

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
}