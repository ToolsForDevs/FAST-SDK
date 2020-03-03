package com.toolsfordevs.fast.plugins.viewinspector.ui.renderer

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.toolsfordevs.fast.core.util.ResUtils
import com.toolsfordevs.fast.modules.resourcepicker.string.StringPickerDialog
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyRenderer
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.StringProperty
import com.toolsfordevs.fast.plugins.viewinspector.R


internal class StringRenderer(parent: ViewGroup) : ViewPropertyRenderer<StringProperty<*>>(parent,
    R.layout.fast_renderer_item_edit_text) {
    private val editText: EditText = itemView.findViewById(R.id.edit_text)
    private val button: View = itemView.findViewById(R.id.button)

    companion object {
        val map:HashMap<EditText, TextWatcher> = hashMapOf()
    }

    override fun bind(data: StringProperty<*>)
    {
        val textWatcher = map.get(editText)

        textWatcher?.let {
            editText.removeTextChangedListener(it)
        }

        editText.setText(data.getValue())

        val watcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                data.setValue(s.toString())
            }
        }
        map.put(editText, watcher)
        editText.addTextChangedListener(watcher)

        button.setOnClickListener {
            StringPickerDialog(itemView.context) { stringRes ->
                editText.setText(stringRes.resId)
                data.setValue(ResUtils.getString(stringRes.resId))
            }.show()
        }
    }
}