package com.toolsfordevs.fast.modules.resourcepicker.dimens

import android.content.Context
import android.graphics.Color
import android.text.InputType
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import com.toolsfordevs.fast.core.FastColor
import com.toolsfordevs.fast.core.ext.*
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.modules.resourcepicker.R
import com.toolsfordevs.fast.modules.resourcepicker.ResourcePickerView
import com.toolsfordevs.fast.modules.resources.DimensionResource
import com.toolsfordevs.fast.modules.subheader.Subheader
import com.toolsfordevs.fast.core.ui.ext.hide
import com.toolsfordevs.fast.core.ui.widget.FastSpinner
import com.toolsfordevs.fast.core.util.ColorUtil
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.modules.resourcepicker.Prefs
import com.toolsfordevs.fast.modules.resources.ColorResource


internal class CustomDimensionsPicker(context: Context) : FrameLayout(context)
{
    private var listener: ((dimensionRes: DimensionResource) -> Unit)? = null
    var dimensionPx: Int = Dimens.dp(16)
        set(value)
        {
            field = value
            setInitialValue()
        }

    private var selectedUnit = Prefs.dimensionPickerSelectedUnit
        set(value)
        {
            field = value
            Prefs.dimensionPickerSelectedUnit = value
        }

    private val editText:EditText

    init
    {
        setBackgroundColor(0xFFFFFFFF.toInt())

        val dp56 = Dimens.dp(56)

        val layout = hLinearLayout(context)

        editText = EditText(context)
        editText.inputType = InputType.TYPE_CLASS_NUMBER
        editText.gravity = Gravity.END
        setInitialValue()

        val spinner = FastSpinner(context)
        val index = when (selectedUnit)
        {
            TypedValue.COMPLEX_UNIT_DIP -> 0
            TypedValue.COMPLEX_UNIT_SP  -> 1
            TypedValue.COMPLEX_UNIT_PX  -> 2
            TypedValue.COMPLEX_UNIT_IN  -> 3
            TypedValue.COMPLEX_UNIT_MM  -> 4
            TypedValue.COMPLEX_UNIT_PT  -> 5
            else                        -> 0
        }
        spinner.setAdapter(listOf("DP", "SP", "PX", "IN", "MM", "PT"), index) { index ->
            selectedUnit = when (index)
            {
                0    -> TypedValue.COMPLEX_UNIT_DIP
                1    -> TypedValue.COMPLEX_UNIT_SP
                2    -> TypedValue.COMPLEX_UNIT_PX
                3    -> TypedValue.COMPLEX_UNIT_IN
                4    -> TypedValue.COMPLEX_UNIT_MM
                5    -> TypedValue.COMPLEX_UNIT_PT
                else -> TypedValue.COMPLEX_UNIT_DIP
            }
        }


        layout.addView(editText, layoutParamsWV(dp56))
        layout.addView(spinner, layoutParamsWV(dp56))

        addView(layout, frameLayoutParamsWW().apply { gravity = Gravity.CENTER })

        val buttonBar = hLinearLayout(context).apply {
            setBackgroundColor(FastColor.colorPrimary)
            gravity = Gravity.CENTER
        }

        val okButton = Button(context).apply {
            gravity = Gravity.CENTER
            setBackgroundResource(R.drawable.fast_selectable_item_background)
            setTextColor(Color.WHITE)
            text = "OK"
        }

        okButton.setOnClickListener {
            val parsedValue = editText.text.toString().toFloatOrNull()
            val value = Dimens.toPx(parsedValue ?: 0f, selectedUnit)
            listener?.invoke(DimensionResource("", value = value))
        }

        buttonBar.addView(okButton, layoutParamsMM())
        addView(buttonBar, frameLayoutParamsMV(dp56).apply { gravity = Gravity.BOTTOM })
    }

    private fun setInitialValue()
    {
        editText.setText(Dimens.pxToDimension(dimensionPx.toFloat(), selectedUnit).toInt().toString())
    }

    fun setOnDimensionSelectedListener(callback: (dimensionRes: DimensionResource) -> Unit)
    {
        listener = callback
    }
}