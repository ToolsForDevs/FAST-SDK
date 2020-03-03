package com.toolsfordevs.fast.modules.resourcepicker

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import com.toolsfordevs.fast.core.FastColor
import com.toolsfordevs.fast.core.ext.hLinearLayout
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.core.util.ResUtils
import com.toolsfordevs.fast.modules.androidx.recyclerview.ext.vRecyclerView
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.RecyclerView


internal abstract class ResourcePickerView(context: Context) : LinearLayout(context)
{
    protected var recyclerView: RecyclerView
    protected var buttonBar: LinearLayout

    protected val dp56 = Dimens.dp(56)

    init
    {
        orientation = VERTICAL

        recyclerView = vRecyclerView(context)
        recyclerView.setHasFixedSize(true)
//        recyclerView.setBackgroundColor(0xFFEFEFEF.toInt())
        recyclerView.setBackgroundColor(0xFFFFFFFF.toInt())
        recyclerView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, 0)
            .apply { weight = 1f }
        addView(recyclerView)

        buttonBar = hLinearLayout(context).apply {
            setBackgroundColor(FastColor.colorPrimary)
            gravity = Gravity.END or Gravity.CENTER_VERTICAL
        }

        addView(buttonBar, LayoutParams.MATCH_PARENT, Dimens.dp(56))
    }

    protected fun addButton(drawableRes: Int, idRes: Int = View.NO_ID): ImageButton
    {
        val button = ImageButton(context)
        button.layoutParams = LayoutParams(dp56, dp56)
        button.background = ResUtils.getDrawable(context, R.drawable.fast_selectable_item_background)
        button.setImageResource(drawableRes)
        button.id = idRes
        buttonBar.addView(button)
        return button
    }

    protected fun addButton(text:String, idRes:Int = View.NO_ID): Button
    {
        val button = Button(context)
        button.layoutParams = LayoutParams(dp56, dp56)
        button.background = ResUtils.getDrawable(context, R.drawable.fast_selectable_item_background)
        button.setText(text)
        button.setTextColor(Color.WHITE)
        button.textSize = 16f
//        button.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        button.id = idRes
        buttonBar.addView(button)
        return button
    }

    fun setOnResourceSelectedListener(callback: () -> Unit)
    {

    }
}