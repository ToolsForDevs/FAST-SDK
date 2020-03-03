package com.toolsfordevs.fast.plugins.actions.ui.delegates

import android.content.res.ColorStateList
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.toolsfordevs.fast.plugins.actions.base.StringAction
import com.toolsfordevs.fast.plugins.actions.ui.ActionDelegate
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.core.ui.ext.setPaddingBottom


internal class StringDelegate : ActionDelegate<String, StringAction>()
{
    private lateinit var editText:EditText

    override fun createView(parent: ViewGroup): View
    {
        editText = EditText(parent.context)
        editText.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
        editText.setTextColor(Color.WHITE)
        editText.setPaddingBottom(Dimens.dp(8))
        return editText
    }

    override fun bind(action: StringAction)
    {
        val dp8 = Dimens.dp(8)
        (editText.layoutParams as ViewGroup.MarginLayoutParams).marginStart = dp8
        (editText.layoutParams as ViewGroup.MarginLayoutParams).marginEnd = dp8

        editText.setText(action.value ?: "")

        editText.addTextChangedListener(object : TextWatcher
        {
            override fun afterTextChanged(s: Editable?)
            {
                action.value = s?.toString()
                onValueChange()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
            {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {

            }
        })
    }
}