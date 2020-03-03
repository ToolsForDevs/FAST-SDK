package com.toolsfordevs.fast.plugins.environmentswitch.ui.renderer

import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import android.widget.EditText
import com.toolsfordevs.fast.plugins.actions.base.actions.StringAction


internal class StringRenderer(parent: ViewGroup) : EnvRenderer<StringAction, EditText>(parent,
    EditText(parent.context)
) {

    companion object {
        val map:HashMap<EditText, TextWatcher> = hashMapOf()
    }

    override fun bind(data: StringAction, position:Int)
    {
        super.bind(data, position)

        val textWatcher = map[dataView]

        textWatcher?.let {
            dataView.removeTextChangedListener(it)
        }

        dataView.setText(data.value?.value ?: "")

        val watcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                data.value?.value = s.toString()
                data.callback(data.value?.value)
            }
        }

        map[dataView] = watcher
        dataView.addTextChangedListener(watcher)
    }
}