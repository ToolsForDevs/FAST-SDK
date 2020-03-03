package com.fast.toolsfordevs.modules.codegenerator.ui

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.fast.toolsfordevs.modules.codegenerator.CodeSnippet
import com.toolsfordevs.fast.core.AppInstance
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.core.ext.layoutParamsMM

@SuppressLint("SetJavaScriptEnabled", "ViewConstructor")
@Keep
class CodeSnippetView(context: Context, private val codeSnippet: CodeSnippet) : WebView(context)
{
    init
    {
        layoutParams = layoutParamsMM()

        settings.javaScriptEnabled = true
        addJavascriptInterface(CopyInterface(), "CopyInterface")
        addJavascriptInterface(LanguageInterface(), "LanguageInterface")
    }

    override fun onAttachedToWindow()
    {
        super.onAttachedToWindow()

        loadDataWithBaseURL(null, codeSnippet.renderHTML(), "text/html", "UTF-8", null)
    }

    private class CopyInterface
    {
        @JavascriptInterface
        fun copy(text: String)
        {
            val clipboard: ClipboardManager = AppInstance.get().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Code", text)
            clipboard.setPrimaryClip(clip)

        }
    }

    private class LanguageInterface
    {
        @JavascriptInterface
        fun onSelectLanguage(languageId:String)
        {
            Prefs.lastSelectedLanguage = languageId
        }
    }
}