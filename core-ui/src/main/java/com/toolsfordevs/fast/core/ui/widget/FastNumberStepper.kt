package com.toolsfordevs.fast.core.ui.widget

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.Gravity
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.core.ext.layoutParamsVM
import com.toolsfordevs.fast.core.ext.layoutParamsWM
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.core.ui.R
import com.toolsfordevs.fast.core.util.AttrUtil
import com.toolsfordevs.fast.core.util.Dimens

@Keep
class FastNumberStepper(context: Context) : LinearLayout(context)
{
    private val editText = EditText(context)

    private lateinit var value:Number

    private var callback:((Number)->Unit)? = null

    private val watcher:TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?)
        {
            value = when(value)
            {
                is Int -> s.toString().toInt()
                is Float -> s.toString().toFloat()
                is Long -> s.toString().toLong()
                else -> throw Exception("Could not parse text ${s?.toString()} to a Number" )
            }

            callback?.invoke(value)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
        {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
        {

        }

    }

    init
    {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL or Gravity.END

        val minusButton = ImageButton(context).apply {
            setImageResource(R.drawable.fast_core_ic_minus)
            background = AttrUtil.getDrawable(context, android.R.attr.selectableItemBackgroundBorderless)
        }
        val plusButton = ImageButton(context).apply {
            setImageResource(R.drawable.fast_core_ic_plus)
            background = AttrUtil.getDrawable(context, android.R.attr.selectableItemBackgroundBorderless)
            scaleType = ImageView.ScaleType.CENTER
        }

        minusButton.setOnClickListener { add(-1) }
        plusButton.setOnClickListener { add(1) }

        editText.setTextColor(MaterialColor.BLACK_87)
        editText.background = null
        editText.gravity = Gravity.CENTER_VERTICAL
        editText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED

        val dp56 = Dimens.dp(56)
        addView(minusButton, layoutParamsVM(dp56))
        addView(editText, layoutParamsWM())
        addView(plusButton, layoutParamsVM(dp56))
    }

    fun setValue(value:Number)
    {
        this.value = value
        editText.setText(value.toString())

        if (value is Float)
            editText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        else
            editText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
    }

    fun setOnChangeCallback(callback:((Number)->Unit)?)
    {
        this.callback = callback
    }

    private fun add(delta:Int)
    {
        value = when (value)
        {
            is Int -> value as Int + delta
            is Float -> value as Float + delta.toFloat()
            is Long -> value as Long + delta.toLong()
            else -> throw Exception("Value has an unknown Number type")
        }

        editText.setText(value.toString())
        callback?.invoke(value)
    }

    override fun onAttachedToWindow()
    {
        super.onAttachedToWindow()
        editText.addTextChangedListener(watcher)
    }

    override fun onDetachedFromWindow()
    {
        super.onDetachedFromWindow()
        editText.removeTextChangedListener(watcher)
    }
}