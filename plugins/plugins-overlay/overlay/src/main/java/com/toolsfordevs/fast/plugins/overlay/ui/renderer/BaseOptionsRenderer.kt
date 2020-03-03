package com.toolsfordevs.fast.plugins.overlay.ui.renderer

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.graphics.drawable.shapes.RoundRectShape
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.toolsfordevs.fast.core.ext.*
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.core.util.ResUtils
import com.toolsfordevs.fast.modules.recyclerview.Renderer
import com.toolsfordevs.fast.modules.resourcepicker.color.ColorPickerDialog
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.core.ui.ext.*
import com.toolsfordevs.fast.core.ui.widget.FastColorView
import com.toolsfordevs.fast.core.ui.widget.FastSpinner
import com.toolsfordevs.fast.core.ui.widget.FastSwitch
import com.toolsfordevs.fast.plugins.overlay.ui.Prefs
import com.toolsfordevs.fast.plugins.overlay.ui.model.IOptions
import com.toolsfordevs.fast.plugins.overlay.ui.widget.BackgroundPreview
import kotlin.reflect.KMutableProperty0


internal open class BaseOptionsRenderer<T: IOptions, R: BaseOptionsView>(view: R) : Renderer<T>(view)
{
    val view:R = itemView as R
    val preview: View = (itemView as BaseOptionsView).preview
    val name: TextView = (itemView as R).name
    val enabled: FastSwitch = (itemView as R).enabled
    val header = (itemView as BaseOptionsView).header
    val itemsLayout: LinearLayout = (itemView as BaseOptionsView).itemsLayout
    var colorTest:Int = 0

    override fun bind(data: T, position: Int)
    {
        name.text = data.name.toUpperCase()

        setEnabled(data.enabled)
        enabled.isChecked = data.enabled

        enabled.setOnClickListener {
            setEnabled(enabled.isChecked)
            data.enabled = enabled.isChecked
            save()
        }

        itemsLayout.show(data.expanded)

        header.setOnClickListener {
            itemsLayout.toggleVisibility()
            data.expanded = itemsLayout.isVisible()
            save()
        }
    }

    private fun setEnabled(enabled:Boolean)
    {
        itemsLayout.alpha = if (enabled) 1f else 0.5f

        preview.alpha = if (enabled) 1f else 0.5f
        name.alpha = if (enabled) 1f else 0.5f

        for (i in 0 until itemsLayout.childCount)
        {
            val child = itemsLayout.getChildAt(i) as ViewGroup

            for (j in 0 until child.childCount)
                child.getChildAt(j).isEnabled = enabled
        }
    }

    fun bindColor(colorPref:KMutableProperty0<Int>, view: FastColorView)
    {
        view.setColor(colorPref.get())

        view.setOnClickListener {
            ColorPickerDialog(view.context, { colorResource ->
                val color = colorResource.value ?: ResUtils.getColor(colorResource.resId)
                view.setColor(color)
                colorPref.set(color)
                save() // anticipate profiles
                preview.invalidate()
            }, colorPref.get()).show()
        }
    }

    fun bindBoolean(booleanPref:KMutableProperty0<Boolean>, view: FastSwitch)
    {
        view.isChecked = booleanPref.get()

        view.setOnClickListener {
            booleanPref.set(view.isChecked)
            save()
        }
    }

    fun bindUnit(intPref:KMutableProperty0<Int>, view: FastSpinner)
    {
        bindSpinner(listOf(Dimens.PX.label, Dimens.DP.label), intPref, view)
    }

    fun bindStrokeStyle(intPref:KMutableProperty0<Int>, view: FastSpinner)
    {
        bindSpinner(listOf("SOLID", "DASHED", "DOTS", "NONE"), intPref, view)
    }

    fun bindBackgroundStyle(intPref:KMutableProperty0<Int>, view: FastSpinner)
    {
        bindSpinner(listOf("NONE", "SOLID", "STRIPES"/*, "GRID"*/), intPref, view)
    }

    fun bindSpinner(labels:List<String>, intPref:KMutableProperty0<Int>, view: FastSpinner)
    {
        view.setAdapter(labels, intPref.get()) { index -> intPref.set(index); save() }
    }

    fun save()
    {
        Prefs.saveCurrentProfile()
    }
}

open class BaseOptionsView(context: Context) : LinearLayout(context)
{
    val preview: View by lazy { makePreview() }
    val name: TextView = TextView(context)
    val enabled: FastSwitch = FastSwitch.create(context, true)

    val header:LinearLayout
    val itemsLayout:LinearLayout

    init
    {
        orientation = VERTICAL
        layoutParams = layoutParamsMW().apply {
            marginStart = Dimens.dp(16)
            marginEnd = Dimens.dp(16)
        }
        elevation = Dimens.dpF(2)

        name.textSize = 14f
        name.setTextColor(MaterialColor.BLACK_100)
        name.typeface = Typeface.DEFAULT_BOLD
        name.setPaddingStart(Dimens.dp(16))

        val rad = Dimens.dpF(4)
        val r: RectShape = RoundRectShape(floatArrayOf(rad, rad, rad, rad, rad, rad, rad, rad), null, null)
        background = ShapeDrawable(r).apply { paint.color = Color.WHITE }

        header = hLinearLayout(context)
        header.gravity = Gravity.CENTER_VERTICAL
        header.setPaddingHorizontal(Dimens.dp(16))
        header.addView(preview, layoutParamsVV(Dimens.dp(24), Dimens.dp(24)))
        header.addView(name, linearLayoutParamsWeW(1f))
        header.addView(enabled, layoutParamsWW())
        this.addView(header, layoutParamsMV(Dimens.dp(56)))
        this.addView(View(context).apply { setBackgroundColor(MaterialColor.BLACK_12) }, layoutParamsMV(1))

        itemsLayout = vLinearLayout(context)
        itemsLayout.setPaddingHorizontal(Dimens.dp(16))
        this.addView(itemsLayout, layoutParamsMW())
    }

    open protected fun makePreview():View
    {
        return BackgroundPreview(context)
    }

    fun makeColorButton(): FastColorView
    {
        val dp24 = Dimens.dp(24)
        return  FastColorView(context).apply { layoutParams = layoutParamsVV(dp24, dp24) }
    }

    fun makeSpinner(): FastSpinner
    {
        return FastSpinner(context)
    }

    fun makeSwitch(): FastSwitch
    {
        return FastSwitch.create(context)
    }

    fun addItem(name:String, view: View)
    {
        val layout = hLinearLayout(context)
        layout.gravity = Gravity.CENTER_VERTICAL

        val textView = TextView(context)
        textView.setTextColor(MaterialColor.BLACK_87)
        textView.text = name
        layout.addView(textView, linearLayoutParamsWeW(1f))

        layout.addView(view)
        itemsLayout.addView(layout, layoutParamsMV(Dimens.dp(48)))
    }
}