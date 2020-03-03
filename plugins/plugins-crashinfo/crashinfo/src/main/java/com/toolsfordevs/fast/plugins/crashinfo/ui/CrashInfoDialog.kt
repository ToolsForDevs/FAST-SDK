package com.toolsfordevs.fast.plugins.crashinfo.ui

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.toolsfordevs.fast.core.AppInstance
import com.toolsfordevs.fast.core.FastColor
import com.toolsfordevs.fast.core.FastManager
import com.toolsfordevs.fast.core.ext.*
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.LinearLayoutManager
import com.toolsfordevs.fast.modules.recyclerview.Renderer
import com.toolsfordevs.fast.modules.recyclerview.RendererAdapter
import com.toolsfordevs.fast.modules.recyclerview.decoration.Divider
import com.toolsfordevs.fast.core.ui.widget.FastDialog
import com.toolsfordevs.fast.core.widget.FastPanel
import com.toolsfordevs.fast.modules.androidx.recyclerview.ext.vRecyclerView
import com.toolsfordevs.fast.modules.restart.FastRestartPanel
import com.toolsfordevs.fast.plugins.crashinfo.R

internal class CrashInfoDialog(context: Context, throwable: Throwable) : FastDialog(context)
{
    init
    {
        val adapter = RendererAdapter()

        val layout = vLinearLayout(context, layoutParamsMM()).apply { setBackgroundColor(Color.WHITE) }

        val dp8 = Dimens.dp(8)
        val dp16 = Dimens.dp(16)
        val dp56 = Dimens.dp(56)

        val header = hLinearLayout(context)
        header.setBackgroundColor(FastColor.colorPrimary)
        header.gravity = Gravity.CENTER_VERTICAL

        val title = TextView(context)
        title.text = "Oops, it's a crash!"
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
        title.setPadding(dp8, 0, dp8, 0)
        title.setTextColor(Color.WHITE)
        header.addView(title, linearLayoutParamsWeW(1f))

        val fileButton = ImageButton(context)
        fileButton.setBackgroundResource(R.drawable.fast_selectable_item_background)
        fileButton.isSelected = Prefs.showFilename
        fileButton.setImageResource(R.drawable.fast_crashinfo_ic_file)
        fileButton.setOnClickListener {
            Prefs.showFilename = !Prefs.showFilename
            fileButton.isSelected = Prefs.showFilename
            adapter.notifyDataSetChanged()
        }
        header.addView(fileButton, layoutParamsVV(dp56, dp56))

        val foldButton = ImageButton(context)
        foldButton.setBackgroundResource(R.drawable.fast_selectable_item_background)
        foldButton.isSelected = Prefs.showFullClassname
        foldButton.setImageResource(R.drawable.fast_crashinfo_ic_fold)
        foldButton.setOnClickListener {
            Prefs.showFullClassname = !Prefs.showFullClassname
            foldButton.isSelected = Prefs.showFullClassname
            adapter.notifyDataSetChanged()
        }

        header.addView(foldButton, layoutParamsVV(dp56, dp56))

        layout.addView(header, layoutParamsMV(dp56))

        val exception = TextView(context)
        exception.setTextColor(0XFFFF0000.toInt())
        exception.setPadding(dp8, dp16, dp8, dp16)
        exception.typeface = Typeface.MONOSPACE
        exception.gravity = Gravity.CENTER_HORIZONTAL
        exception.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
        exception.text = throwable.toString().split(":", limit = 2).joinToString("\n")
        layout.addView(exception, layoutParamsMW())

        val divider = View(context)
        divider.setBackgroundResource(R.color.fast_md_black_12)
        layout.addView(divider, layoutParamsMV(Dimens.dp(1)))

        adapter.addRenderer(::StackTraceRenderer)
        adapter.addAll(throwable.stackTrace.toList())

        val recyclerView = vRecyclerView(context)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(Divider().apply {
            setDividerSizeDp(1)
            setColorRes(R.color.fast_md_black_12)
        })
        recyclerView.adapter = adapter

        layout.addView(recyclerView, layoutParamsMM())

        addContentView(layout, layoutParamsMM())
    }

    override fun onBackPressed()
    {
        val view = FastRestartPanel(context, "What do you want to do now?", listOf(FastRestartPanel.CustomRestartItem("Ignore and continue") {}))
        view.showKeyboardShortcuts = false

        val dialog = AlertDialog.Builder(AppInstance.activity)
            .setView(view)
            .show()

        view.addOnItemClickListener(object : FastRestartPanel.OnItemClickListener
        {
            override fun onItemClick()
            {
                dialog.dismiss()
                dismiss()
                view.removeOnItemClickListener(this)
            }
        })
    }

    class StackTraceRenderer(parent: ViewGroup) :
            Renderer<StackTraceElement>(parent, R.layout.fast_crashinfo_item_stacktrace)
    {
        private val classname: TextView = itemView.findViewById(R.id.classname)
        private val method: TextView = itemView.findViewById(R.id.method)
        private val line: TextView = itemView.findViewById(R.id.line)

        override fun bind(data: StackTraceElement, position: Int)
        {
            if (classname.width > 0)
                refreshClassname(data)
            else
                classname.post { refreshClassname(data) }

            method.text = data.methodName
            line.text = if (!data.isNativeMethod) "${data.lineNumber}" else "C++"

            classname.setTypeface(Typeface.MONOSPACE, if (position == 0) Typeface.BOLD else Typeface.NORMAL)
            method.setTypeface(Typeface.MONOSPACE, if (position == 0) Typeface.BOLD else Typeface.NORMAL)
            line.setTypeface(Typeface.MONOSPACE, if (position == 0) Typeface.BOLD else Typeface.NORMAL)
        }

        private fun refreshClassname(data: StackTraceElement)
        {
            val name = if (Prefs.showFullClassname) data.className
            else data.className.substring(data.className.lastIndexOf(".") + 1)

            setClassname(name, data.fileName)
        }

        private fun setClassname(name: String, filename: String?)
        {
            var text = getLine(classname.width, classname.paint, name)

            if (Prefs.showFilename)
                text += "\n> " + (filename ?: "Unknown")

            classname.text = text
        }

        private fun getLine(width: Int, paint: Paint, text: String): String
        {
            var s: String = text

            val rect = Rect()
            paint.getTextBounds(s, 0, s.length, rect)

            while (rect.width() > width)
            {
                val index = s.lastIndexOf(".")

                if (index <= 0)
                    break

                s = s.substring(0, index)
                paint.getTextBounds(s, 0, s.length, rect)
            }

            if (s.length != text.length)
            {
                val substring = text.substring(s.length)
                s += "\n\t" + getLine(width, paint, substring)
            }

            return s
        }

    }
}