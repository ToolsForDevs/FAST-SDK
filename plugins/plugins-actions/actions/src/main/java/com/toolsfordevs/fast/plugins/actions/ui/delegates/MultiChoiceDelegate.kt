package com.toolsfordevs.fast.plugins.actions.ui.delegates

import android.app.AlertDialog
import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.toolsfordevs.fast.core.ui.R
import com.toolsfordevs.fast.plugins.actions.base.MultiChoiceAction
import com.toolsfordevs.fast.plugins.actions.ui.ActionDelegate
import com.toolsfordevs.fast.core.util.AttrUtil
import com.toolsfordevs.fast.core.util.Dimens


internal class MultiChoiceDelegate<T : Any> : ActionDelegate<List<T>, MultiChoiceAction<T>>()
{
    private lateinit var textView: TextView

    override fun createView(parent: ViewGroup): View
    {
        val dp8 = Dimens.dp(8)
        textView = TextView(parent.context).apply {
            setPadding(dp8, 0, dp8 * 2, 0)
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            setTextColor(Color.WHITE)
            setBackgroundResource(AttrUtil.getResourceId(parent.context, android.R.attr.selectableItemBackground))
            setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.fast_core_ic_menu_down_light, 0)
            compoundDrawablePadding = Dimens.dp(8)
            gravity = Gravity.CENTER_VERTICAL
        }
        return textView
    }

    override fun bind(action: MultiChoiceAction<T>)
    {
        val map = action.choices()
        val indices = BooleanArray(map.size) //Nothing selected for now

        val selectedValues = action.value

        var label = "MAKE YOUR SELECTION"

        selectedValues?.let {
            val labels = arrayListOf<String>()

            for (i in 0 until indices.size)
            {
                indices[i] = it.contains(map.keyAt(i))

                if (indices[i]) labels.add(map.valueAt(i))
            }

            if (labels.isNotEmpty()) label = labels.joinToString(", ")
        }

        textView.text = label

        textView.setOnClickListener {
            AlertDialog.Builder(textView.context)
                .setMultiChoiceItems(map.values.toTypedArray(), indices) { dialog, which, isChecked -> indices[which] = isChecked }
                .setPositiveButton("OK") { dialog, which ->
                    val list = arrayListOf<T>()
                    val labels = arrayListOf<String>()

                    for (i in 0 until indices.size)
                    {
                        if (indices[i])
                        {
                            list.add(map.keyAt(i))
                            labels.add(map.valueAt(i))
                        }
                    }

                    textView.text = if (labels.isNotEmpty()) labels.joinToString(", ") else "NOTHING SELECTED"
                    action.value = list
                    onValueChange()
                }.setCancelable(true).show()
        }
    }

}