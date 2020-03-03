package com.toolsfordevs.fast.plugins.overlay.ui.view

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.graphics.drawable.shapes.RoundRectShape
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.toolsfordevs.fast.core.ext.*
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.core.util.ResUtils
import com.toolsfordevs.fast.modules.androidx.recyclerview.ext.pairWithFab
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.LinearLayoutManager
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.RecyclerView
import com.toolsfordevs.fast.modules.recyclerview.Renderer
import com.toolsfordevs.fast.modules.recyclerview.RendererAdapter
import com.toolsfordevs.fast.modules.recyclerview.decoration.Divider
import com.toolsfordevs.fast.modules.resourcepicker.color.ColorPickerDialog
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.core.ui.ext.*
import com.toolsfordevs.fast.core.ui.widget.*
import com.toolsfordevs.fast.modules.androidx.recyclerview.ext.vRecyclerView
import com.toolsfordevs.fast.plugins.overlay.ui.Prefs
import com.toolsfordevs.fast.plugins.overlay.R
import com.toolsfordevs.fast.plugins.overlay.ui.model.Direction
import com.toolsfordevs.fast.plugins.overlay.ui.model.GutterOptions
import com.toolsfordevs.fast.plugins.overlay.ui.widget.GutterPreview


internal class GutterOptionsView(context: Context) : FrameLayout(context)
{
    private val adapter:RendererAdapter

    init
    {
        setBackgroundColor(0xFFDDDDDD.toInt())

        val recyclerView = vRecyclerView(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setPaddingVertical(Dimens.dp(16))
        recyclerView.clipToPadding = false
        recyclerView.addItemDecoration(Divider().apply { setDividerSizeDp(16) })


        adapter = RendererAdapter()
        adapter.addRenderer(::GutterOptionsRenderer)
        adapter.addAll(Prefs.currentProfile.gutters)
        recyclerView.adapter = adapter

        addView(recyclerView, layoutParamsMM())

        val button = FloatingActionButton(context)
        button.setImageResource(R.drawable.fast_overlay_ic_plus)
        button.setOnClickListener {
            val newOption = GutterOptions()
            Prefs.currentProfile.gutters.add(newOption)
            //Prefs.saveGutterOption(newOption)
            Prefs.saveCurrentProfile()
            adapter.add(newOption)

            if ((parent.parent.parent as BottomSheetLayout).isExpanded())
                recyclerView.smoothScrollToPosition(adapter.itemCount - 1)
        }

        recyclerView.pairWithFab(button)

        val dp16 = Dimens.dp(16)
        val dp56 = Dimens.dp(56)
        addView(button, frameLayoutParamsVV(dp56, dp56).apply {
            marginEnd = dp16
            bottomMargin = dp16
            gravity = Gravity.BOTTOM or Gravity.END
        })
    }

    fun remove(item: GutterOptions)
    {
        adapter.remove(item)
        Prefs.currentProfile.gutters.remove(item)
        Prefs.saveCurrentProfile()
    }

    inner class GutterOptionsRenderer(parent: ViewGroup) : Renderer<GutterOptions>(
        GutterOptionsItem(parent.context))
    {
        private val view = itemView as GutterOptionsItem

        private var textWatcher:TextWatcher? = null

        override fun bind(data: GutterOptions, position: Int)
        {
            view.name.removeTextChangedListener(textWatcher)
            view.offset.removeTextChangedListener(textWatcher)
            view.strokeStyle.setOnClickListener(null)
            view.offset.setOnClickListener(null)

            refreshPreview(data)
            view.name.setText(data.name)

            view.enabled.isChecked = data.enabled

            setEnabled(data.enabled)
            view.enabled.setOnClickListener {
                setEnabled(view.enabled.isChecked)
                data.enabled = view.enabled.isChecked
                save(data)
            }

            view.overflow.setOnClickListener {
                PopupMenu(view.overflow.context, view.overflow).apply {
                    menu.add("Delete")
                    setOnMenuItemClickListener {
                        //Prefs.removeGutterOption(data)
                        remove(data)
                        true
                    }
                }.show()
            }

            view.itemsLayout.show(data.expanded)

            view.header.setOnClickListener {
                view.itemsLayout.toggleVisibility()
                data.expanded = view.itemsLayout.isVisible()
                save(data)
            }

            view.color.setColor(data.color)
            view.color.setOnClickListener {
                ColorPickerDialog(view.color.context, { colorResource ->
                    val color = colorResource.value ?: ResUtils.getColor(colorResource.resId)
                    view.color.setColor(color)
                    data.color = color
                    refreshPreview(data)
                    save(data)

                }, data.color).show()
            }

            view.direction.setAdapter(listOf("VERTICAL", "HORIZONTAL"), data.direction) { direction ->
                data.direction = direction
                save(data)
                refreshStartFrom(data)
                refreshPreview(data)
            }
            view.strokeStyle.setAdapter(listOf("SOLID", "DASHED", "DOTS"), data.strokeStyle) { dimension ->
                data.strokeStyle = dimension
                save(data)
                refreshPreview(data)
            }

            view.offset.setText("${data.offset}")

            val dimensions = listOf(Dimens.PX, Dimens.DP).map { it.label }
            view.offsetUnit.setAdapter(dimensions, data.offsetUnit) { dimension ->
                data.offsetUnit = dimension
                save(data)
            }

            refreshStartFrom(data)

            textWatcher = object : BaseTextWatcher()
            {
                override fun afterTextChanged(s: Editable?)
                {
                    data.name = view.name.text.toString()

                    setToZeroIfEmpty(view.offset)

                    data.offset = Integer.parseInt(view.offset.text.toString())
                    refreshPreview(data)
                    save(data)
                }

            }
            view.name.addTextChangedListener(textWatcher)
            view.offset.addTextChangedListener(textWatcher)
        }

        private fun refreshStartFrom(data: GutterOptions)
        {
            val labels = if (data.direction == Direction.VERTICAL) listOf("START", "CENTER", "END") else listOf("TOP", "CENTER", "BOTTOM")
            view.startFrom.setAdapter(labels, data.startFrom) { direction -> data.startFrom = direction }
        }

        private fun setToZeroIfEmpty(vararg editTexts:EditText)
        {
            for (editText in editTexts)
            {
                if (editText.text.isEmpty())
                {
                    editText.setText("0")
                    editText.setSelection(1)
                }
            }
        }

        private fun refreshPreview(data: GutterOptions)
        {
            view.preview.refresh(data.color, data.direction, data.strokeStyle)
        }

        private fun setEnabled(enabled:Boolean)
        {
            view.itemsLayout.alpha = if (enabled) 1f else 0.5f

            view.preview.alpha = if (enabled) 1f else 0.5f
            view.name.isEnabled = enabled

            for (i in 0 until view.itemsLayout.childCount)
            {
                val child = view.itemsLayout.getChildAt(i) as ViewGroup

                for (j in 0 until child.childCount)
                    child.getChildAt(j).isEnabled = enabled
            }
        }

        private fun save(data: GutterOptions)
        {
            //Prefs.saveGutterOption(data)
            Prefs.saveCurrentProfile()
        }
    }

    class GutterOptionsItem(context: Context) : LinearLayout(context)
    {
        val preview: GutterPreview =
                GutterPreview(context)
        val name: EditText = EditText(context)
        val enabled: FastSwitch = FastSwitch.create(context, true)
        val overflow:ImageButton = ImageButton(context)
        val color = FastColorView(context)
        val direction = FastSpinner(context)
        val strokeStyle = FastSpinner(context)
        val offset = EditText(context).apply { inputType = InputType.TYPE_NUMBER_FLAG_SIGNED }
        val offsetUnit = FastSpinner(context)
        val startFrom = FastSpinner(context)

        val header: LinearLayout
        val itemsLayout: LinearLayout

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
            name.background = null

            overflow.setImageResource(R.drawable.fast_overlay_ic_overflow)
            overflow.setBackgroundResource(R.drawable.fast_selectable_item_background)

            val rad = Dimens.dpF(4)
            val r: RectShape = RoundRectShape(floatArrayOf(rad, rad, rad, rad, rad, rad, rad, rad), null, null)
            background = ShapeDrawable(r).apply { paint.color = Color.WHITE }

            header = hLinearLayout(context)
            header.gravity = Gravity.CENTER_VERTICAL
            header.setPaddingStart(Dimens.dp(16))
            header.addView(preview, layoutParamsVV(Dimens.dp(24), Dimens.dp(24)))
            header.addView(name, linearLayoutParamsWeW(1f))
            header.addView(enabled, layoutParamsWW())
            header.addView(overflow, layoutParamsVV(Dimens.dp(56), Dimens.dp(56)))
            this.addView(header, layoutParamsMV(Dimens.dp(56)))
            this.addView(View(context).apply { setBackgroundColor(MaterialColor.BLACK_12) }, layoutParamsMV(1))

            itemsLayout = vLinearLayout(context)
            itemsLayout.setPaddingHorizontal(Dimens.dp(16))
            this.addView(itemsLayout, layoutParamsMW())

            val dp32 = Dimens.dp(24)
            color.layoutParams = layoutParamsVV(dp32, dp32)

            styleEditTexts(offset)

            addItem("Direction", direction)
            addItem("Color", color)
            addItem("Stroke style", strokeStyle)
            addItem("Offset", offset, offsetUnit)
            addItem("Start from", startFrom)
        }

        fun addItem(name: String, vararg views: View)
        {
            val layout = hLinearLayout(context)
            layout.gravity = Gravity.CENTER_VERTICAL

            val textView = TextView(context)
            textView.setTextColor(MaterialColor.BLACK_87)
            textView.text = name
            layout.addView(textView, linearLayoutParamsWeW(1f))

            for (view in views) layout.addView(view, view.layoutParams ?: layoutParamsWM())

            itemsLayout.addView(layout, layoutParamsMV(Dimens.dp(48)))
        }

        private fun styleEditTexts(vararg editTexts: EditText)
        {
            for (editText in editTexts)
            {
                editText.background = null
                editText.minWidth = Dimens.dp(96)
                editText.gravity = Gravity.END or Gravity.CENTER_VERTICAL
                editText.textSize = 14f
                editText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
            }
        }
    }

    abstract class BaseTextWatcher : TextWatcher
    {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
        {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
        {

        }
    }
}