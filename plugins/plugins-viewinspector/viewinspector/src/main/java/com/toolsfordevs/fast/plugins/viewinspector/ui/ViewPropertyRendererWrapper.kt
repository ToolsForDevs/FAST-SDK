package com.toolsfordevs.fast.plugins.viewinspector.ui

import android.content.Context
import android.graphics.Paint
import android.graphics.Rect
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.LinearLayout.VERTICAL
import android.widget.TextView
import com.toolsfordevs.fast.core.ext.frameLayoutParamsMM
import com.toolsfordevs.fast.core.ext.layoutParamsMV
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewProperty
import com.toolsfordevs.fast.modules.recyclerview.Renderer
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.core.ui.ext.hide
import com.toolsfordevs.fast.core.ui.ext.setPaddingStart
import com.toolsfordevs.fast.core.ui.ext.show
import com.toolsfordevs.fast.plugins.viewinspector.R
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyRenderer


internal class ViewPropertyRendererWrapper<T : ViewProperty<*, *>>(parent: ViewGroup, private val delegate: ViewPropertyRenderer<T>) :
    Renderer<T>(ViewPropertyLayout(parent.context))
{
    private val v = itemView as ViewPropertyLayout

    init
    {
        v.setContentView(delegate.itemView)
    }

    override fun bind(data: T, position: Int)
    {
        var name = data.name

        if (data.name.startsWith("get") || data.name.startsWith("set") || data.name.startsWith("has"))
            name = data.name.substring(3)
        else if (data.name.startsWith("is"))
            name = data.name.substring(2)

        if (v.name.width == 0)
        {
            v.name.post {
                var padding = v.apiLayout.width

                if (padding > 0)
                    padding += Dimens.dp(2)

                v.name.text = getLine(v.name.width - padding, v.name.paint, name.decapitalize())
            }
        }
        else
        {
            var padding = v.apiLayout.width

            if (padding > 0)
                padding += Dimens.dp(2)

            v.name.text = getLine(v.name.width - padding, v.name.paint, name.decapitalize())
        }

        if (data.isNew())
        {
            v.newApi.show()
            v.newApi.text = data.newApi
        }
        else
        {
            v.newApi.hide()
        }

        if (data.isDeprecated())
        {
            v.deprecatedApi.show()
            v.deprecatedApi.text = data.deprecatedApi
        }
        else
        {
            v.deprecatedApi.hide()
        }

        if (data.hasId)
        {
            v.starred.isSelected = FavoriteManager.isFavorite(data.view, data.id)

            v.starred.setOnClickListener { button ->
                FavoriteManager.toggleFavorite(data.view, data.id, button.isSelected)
                button.isSelected = !button.isSelected
            }

            v.starred.show()
        }
        else
        {
            v.starred.hide()
        }


//        val params = v.name.layoutParams as ViewGroup.MarginLayoutParams
//        params.marginEnd = if (v.deprecatedApi.isVisible() || v.newApi.isVisible()) Dimens.dp(16) else 0
//        v.name.layoutParams = params
        delegate.bind(data)
    }

    private fun getLine(width:Int, paint: Paint, text:String):String
    {
        var s:String = text

        val rect = Rect()
        paint.getTextBounds(s, 0, s.length, rect)

        while (width > 0 && rect.width() > width)
        {
            var index = -1

            val length = s.length - 1

            for (i in length downTo 0)
            {
                val char = s[i]

                if (char.isUpperCase())
                {
                    index = i
                    break
                }
            }
            if (index == -1 || index == 0)
                break

            s = s.substring(0, index)
            paint.getTextBounds(s, 0, s.length, rect)
        }

        if (s.length != text.length)
        {
            val substring = text.substring(s.length)
            s += "\n" + getLine(width, paint, substring)
        }

        return s
    }
}

private class ViewPropertyLayout(context: Context) : FrameLayout(context)
{
    private val size = Dimens.dp(16)
    val nameLayout:FrameLayout
    val name:TextView
    val apiLayout:LinearLayout
    val newApi:TextView
    val deprecatedApi:TextView
    val starred:ImageView
    private lateinit var contentView:View

    private val hDivider:View

    init
    {
        nameLayout = FrameLayout(context).apply {
            setPaddingStart(Dimens.dp(4))
            //setPaddingEnd(Dimens.dp(4))
//            setBackgroundColor(0xFFEEEEEE.toInt())
        }

        val vDivider = View(context).apply { setBackgroundColor(0x09000000.toInt()) }
        val params = frameLayoutParamsMM().apply {
            width = Dimens.dp(1)
            gravity = Gravity.END
        }
        nameLayout.addView(vDivider, params)

        name = TextView(context).apply {
            gravity = Gravity.CENTER_VERTICAL
            textSize = 10f
        }

        nameLayout.addView(name, MATCH_PARENT, MATCH_PARENT)

        apiLayout = LinearLayout(context).apply {
            orientation = VERTICAL
            setVerticalGravity(Gravity.CENTER_VERTICAL)
        }
        deprecatedApi = makeApiTextView().apply { setBackgroundResource(R.drawable.fast_renderer_deprecated_api) }
        newApi = makeApiTextView().apply { setBackgroundResource(R.drawable.fast_renderer_new_api) }


        apiLayout.addView(deprecatedApi, size, size)
        apiLayout.addView(newApi, size, size)

        starred = ImageView(context)
        starred.setImageResource(R.drawable.fast_plugin_view_inspector_ic_starred)
        apiLayout.addView(starred, size, size)

        nameLayout.addView(apiLayout, WRAP_CONTENT, MATCH_PARENT)
        (apiLayout.layoutParams as FrameLayout.LayoutParams).gravity = Gravity.END
        apiLayout.layoutParams = apiLayout.layoutParams

        addView(nameLayout, MATCH_PARENT, MATCH_PARENT)

        hDivider = View(context).apply { setBackgroundColor(0x09000000.toInt()) }
        addView(hDivider, layoutParamsMV(Dimens.dp(1)))

        layoutParams = LayoutParams(MATCH_PARENT, Dimens.dp(48))
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int)
    {
        val width = View.getDefaultSize(0, widthMeasureSpec)
        val height = View.getDefaultSize(0, heightMeasureSpec)

        val heightSize = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)

        val nameSize:Int = MeasureSpec.makeMeasureSpec((/*0.42*/0.3 * width).toInt(), MeasureSpec.EXACTLY)
        measureChild(nameLayout, nameSize, heightSize)
        val contentSize:Int = MeasureSpec.makeMeasureSpec((/*0.58*/0.7 * width).toInt(), MeasureSpec.EXACTLY)
        measureChild(contentView, contentSize, heightSize)

        measureChild(hDivider,
            MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(Dimens.dp(1), MeasureSpec.EXACTLY))

        setMeasuredDimension(width, height)
    }

    fun setContentView(view:View)
    {
        contentView = view
        addView(contentView, LayoutParams(MATCH_PARENT, MATCH_PARENT).apply { gravity = Gravity.END or Gravity.CENTER_VERTICAL })
    }

    private fun makeApiTextView():TextView
    {
        return TextView(context).apply {
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 8f)
            setTextColor(0xFFFFFFFF.toInt())
            hide()
            gravity = Gravity.CENTER
        }
    }
}