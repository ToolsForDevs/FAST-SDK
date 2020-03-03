package com.fast.toolsfordevs.modules.codegenerator.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import com.fast.toolsfordevs.modules.codegenerator.CodeSnippet
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.core.ext.layoutParamsMM
import com.toolsfordevs.fast.core.ui.widget.BottomSheetLayout

@SuppressLint("ViewConstructor")
@Keep
class CodeSnippetPanel(context: Context, codeSnippet: CodeSnippet) : BottomSheetLayout(context)
{
    init
    {
        val codeSnippetView = CodeSnippetView(context, codeSnippet)
        addView(codeSnippetView, layoutParamsMM())
    }

    override fun onAttachedToWindow()
    {
        setCollapsedHeight((parent as View).height)
        super.onAttachedToWindow()
    }
}