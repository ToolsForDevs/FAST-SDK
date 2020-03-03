package com.toolsfordevs.fast.plugins.logger.ui

import android.content.Context
import android.graphics.*
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import com.toolsfordevs.fast.core.ext.*
import com.toolsfordevs.fast.modules.recyclerview.Renderer
import com.toolsfordevs.fast.core.ui.ext.*
import com.toolsfordevs.fast.core.util.*
import com.toolsfordevs.fast.plugins.logger.R
import com.toolsfordevs.fast.plugins.logger.modules.ext.LogRenderer
import com.toolsfordevs.fast.plugins.logger.ui.items.LogEntryWrapper
import com.toolsfordevs.fast.modules.formatters.FastFormatter
import java.util.*


internal class LogRendererWrapper<T : FastFormatter>(parent: ViewGroup, val delegate: LogRenderer<T>) :
        Renderer<LogEntryWrapper<T>>(LogEntryLayout(parent.context))
{
    companion object
    {
        var showTime: Boolean = true
        var showTag: Boolean = true

        var callback: ((position: Int, newFormatter: FastFormatter) -> Unit)? = null
    }

    private val v = itemView as LogEntryLayout

    init
    {
        v.addContentView(delegate.itemView)
    }

    override fun bind(data: LogEntryWrapper<T>, position: Int)
    {
        val logEntry = data.logEntry

        val isLast = data.session?.getEntries()?.last() == data.logEntry

        v.priorityView.setBackgroundColor(logEntry.priority.color())
        v.priorityView.setMarginBottom(Dimens.dp(if (isLast) 0 else 1))

        if (AndroidVersion.isMinApi(23))
            v.foreground = ResUtils.getDrawable(R.drawable.fast_selectable_item_background)

        if (data.session != null)
        {
            v.sessionLineView.setColor(data.session.color)
            v.sessionLineView.show()
        }
        else
        {
            v.sessionLineView.hide()
        }

        val cal = Calendar.getInstance()
        cal.timeInMillis = logEntry.timestamp

        val hours = cal.get(Calendar.HOUR_OF_DAY)
        val minutes = cal.get(Calendar.MINUTE)
        val seconds = cal.get(Calendar.SECOND)
        val milliseconds = cal.get(Calendar.MILLISECOND)

        val sHours = if (hours < 10) "0$hours" else "$hours"
        val sMinutes = if (minutes < 10) "0$minutes" else "$minutes"
        val sSeconds = if (seconds < 10) "0$seconds" else "$seconds"
        val sMilliseconds = when
        {
            milliseconds < 10  -> "00$milliseconds"
            milliseconds < 100 -> "0$milliseconds"
            else               -> "$milliseconds"
        }

        v.time.text = "$sHours:$sMinutes:$sSeconds.$sMilliseconds"
        v.tag.text = logEntry.tag ?: "NO_TAG"

        v.time.show(showTime)
        v.tag.show(showTag)

        delegate.bind(logEntry.data, data.formatter)

        refreshMethodCallState(data)
        refreshStackTraceState(data)

        v.setOnClickListener { view ->
            val popup = PopupMenu(view.context, view, Gravity.END)
            popup.inflate(R.menu.fast_logcat_menu_log)

            popup.menu.add("Util").isEnabled = false
            popup.menu.add("Copy Raw").isCheckable = false
            popup.menu.add("Copy Formatted").isCheckable = false

            val groupId = View.generateViewId()

            if (data.compatibleFormatters.size > 1)
            {
                popup.menu.add("Format as").isEnabled = false
                //val submenu = popup.menu.addSubMenu("Switch Formatter")

                data.compatibleFormatters.forEachIndexed { index, formatter ->
                    val item = popup.menu.add(
                            groupId,
                            0,
                            index,
                            formatter.name
                    )

                    if (data.formatter::class == formatter::class)
                        item.isChecked = true
                }

                popup.menu.setGroupCheckable(
                        groupId,
                        true,
                        true
                )
            }

            when
            {
                data.showMethodCall -> popup.menu.findItem(R.id.fast_logger_menu_item_method_call).isChecked = true
                data.showStackTrace -> popup.menu.findItem(R.id.fast_logger_menu_item_stack_trace).isChecked = true
                else                -> popup.menu.findItem(R.id.fast_logger_menu_item_hide).isChecked = true
            }

            //popup.menu.setGroupDividerEnabled(true)
            popup.setOnMenuItemClickListener { menuItem ->

                if (menuItem.title == "Copy Raw")
                {
                    CopyUtil.copy("Log entry", data.logEntry.data?.toString() ?: "null")
                }
                else if (menuItem.title == "Copy Formatted")
                {
                    CopyUtil.copy("Log entry", data.formatter.formatToString(data.logEntry.data))
                }
                else if (menuItem.groupId == groupId)
                {
                    if (!menuItem.isChecked)
                    {
                        menuItem.isChecked = true // Show selection, even if briefly
                        callback?.invoke(
                                adapterPosition,
                                data.compatibleFormatters.get(menuItem.order)
                        )
                    }
                }
                else
                {
                    menuItem.isChecked = !menuItem.isChecked

                    data.showMethodCall =
                            popup.menu.findItem(R.id.fast_logger_menu_item_method_call).isChecked
                    data.showStackTrace =
                            popup.menu.findItem(R.id.fast_logger_menu_item_stack_trace).isChecked
                    refreshStackTraceState(data)
                    refreshMethodCallState(data)
                }

                true
            }

            popup.show()
        }
    }

    private fun refreshMethodCallState(data: LogEntryWrapper<T>)
    {
        v.showMethodCall(data.logEntry.stackTrace, data.showMethodCall)
    }

    private fun refreshStackTraceState(data: LogEntryWrapper<T>)
    {
        v.showStackTrace(data.logEntry.stackTrace, data.showStackTrace)
    }
}

private class LogEntryLayout(context: Context) : LinearLayout(context)
{
    lateinit var contentView: View

    val priorityView: View
    val dataLayout: LinearLayout
    val sessionLineView: SessionLineView
    val time: TextView
    val tag: TextView

    private var methodCallLayout: MethodCallItem? = null
    private var stackTraceLayout: LinearLayout? = null

    private val dp8 = Dimens.dp(8)

    init
    {
        orientation = HORIZONTAL
        setBackgroundResource(R.drawable.fast_selectable_item_background)

        priorityView = View(context)
        addView(priorityView, layoutParamsVM(Dimens.dp(4)))
//        priorityView.setMarginTop(Dimens.dp(1))
        priorityView.setMarginBottom(Dimens.dp(1))

        sessionLineView = SessionLineView(context)
        addView(sessionLineView, linearLayoutParamsVM(Dimens.dp(8)))

        dataLayout = vLinearLayout(context)
        dataLayout.setPaddingStart(dp8)

        val dp4 = Dimens.dp(4)

        val headerLayout = hLinearLayout(context, layoutParamsMW()).apply {
            setPaddingEnd(dp8)
            minimumHeight = 1 // px
        }

        time = TextView(context).apply {
            setTextColor(ResUtils.getColor(R.color.fast_md_black_54))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 8f)
            setTypeface(Typeface.MONOSPACE, Typeface.NORMAL)
            setPaddingEnd(dp8)

        }
        headerLayout.addView(time, linearLayoutParamsWW().apply {
            topMargin = dp4
        })

        tag = TextView(context).apply {
            setTextColor(ResUtils.getColor(R.color.fast_md_black_54))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 8f)
            setTypeface(Typeface.MONOSPACE, Typeface.NORMAL)
            setLines(1)
        }
        headerLayout.addView(tag, linearLayoutParamsWW().apply {
            topMargin = dp4
        })

        dataLayout.addView(headerLayout, layoutParamsMW())
        addView(dataLayout, layoutParamsMW())

        layoutParams = layoutParamsMW()
    }

    fun addContentView(view: View)
    {
        contentView = view
        dataLayout.addView(
                contentView,
                layoutParamsMM()/*.apply { gravity = Gravity.END or Gravity.CENTER_VERTICAL }*/
        )
    }

    fun showMethodCall(data: Array<StackTraceElement>, show: Boolean)
    {
        if (show)
        {
            if (methodCallLayout == null)
            {
                methodCallLayout = MethodCallItem(context)
                dataLayout.addView(methodCallLayout, layoutParamsMW())
            }

            var element: StackTraceElement? = null

            for (e in data)
            {
                if (!isFiltered(e.className))
                {
                    element = e
                    break
                }
            }

            element?.let {

                val line = if (!element.isNativeMethod) "${element.lineNumber}" else "C++"
                var s = "${element.className} : ${element.methodName} : $line"

                val item = methodCallLayout!!

//                if (showFilename)
                s += "\n> ${element.fileName}"

                item.classname.text = s

                item.show()
            }
        }
        else
        {
            methodCallLayout?.hide()
        }
    }

    fun showStackTrace(data: Array<StackTraceElement>, show: Boolean)
    {
        if (show)
        {
            if (stackTraceLayout == null)
            {
                stackTraceLayout = vLinearLayout(context, layoutParamsMW())
                dataLayout.addView(stackTraceLayout)
            }

            val layout = stackTraceLayout!!

            var i = 0
            val childCount = layout.childCount
            for (stackTraceElement in data)
            {
                // ToDo maybe just "com.awesome.fast" ?
                // Filter stacktrace

                if (isFiltered(stackTraceElement.className))
                    continue

                val item: StackTraceItem

                if (childCount > i)
                {
                    item = layout.getChildAt(i) as StackTraceItem
                }
                else
                {
                    item = StackTraceItem(context)
                    layout.addView(item, layoutParamsMW())
                }

                item.post { setClassname(item, stackTraceElement.className) }

                item.method.text = stackTraceElement.methodName
                item.line.text =
                        if (!stackTraceElement.isNativeMethod) "${stackTraceElement.lineNumber}" else "C++"

                i++
            }

            if (i < childCount)
            {
                for (j in i until childCount)
                    layout.removeViewAt(i)
            }

            layout.show()
        }
        else
        {
            stackTraceLayout?.hide()
        }
    }

    private fun isFiltered(classname: String): Boolean
    {
        for (excludedStacktracePackage in Prefs.excludedStacktracePackages)
        {
            if (classname.startsWith(excludedStacktracePackage))
                return true
        }

        for (excludedStacktraceClass in Prefs.excludedStacktraceClasses)
        {
            if (classname == excludedStacktraceClass)
                return true
        }

        return false
    }

    private fun setClassname(item: StackTraceItem, classname: String)
    {
        val text = getLine(item.classname.width, item.classname.paint, classname)
        item.classname.text = text
    }

    private fun getLine(width: Int, paint: Paint, text: String): String
    {
        var s: String = text

        val rect = Rect()
        paint.getTextBounds(s, 0, s.length, rect)

        while (rect.width() + 1 > width)
        {
            val index = s.lastIndexOf(".")

            if (index == -1)
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

    class MethodCallItem(context: Context) : LinearLayout(context)
    {
        val dp8 = Dimens.dp(4)

        val classname: TextView = TextView(context).apply {
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 8f)
            setTextColor(Color.BLACK)
            setTypeface(Typeface.MONOSPACE, Typeface.NORMAL)
            setPadding(0, dp8, 0, dp8)
        }


        init
        {
            orientation = HORIZONTAL
//            setBackgroundResource(R.color.md_black_12)

            val view = View(context)
            view.setBackgroundResource(R.color.fast_md_black_12)
            addView(view, linearLayoutParamsVM(dp8).apply { marginEnd = dp8 })

            addView(classname, layoutParamsMW())
        }
    }

    class StackTraceItem(context: Context) : LinearLayout(context)
    {
        val classname: TextView = makeTextView(6f)
        val method: TextView = makeTextView(3f)
        val line: TextView = makeTextView(1f)

        val dp8 = Dimens.dp(8)
        val dp4 = Dimens.dp(4)

        init
        {
            orientation = HORIZONTAL

            val view = View(context)
            view.setBackgroundResource(R.color.fast_md_black_12)
            addView(view, linearLayoutParamsVM(dp4).apply { marginEnd = dp4 })

            addView(classname)
            addView(makeDivider())
            addView(method)
            addView(makeDivider())
            addView(line)
        }

        fun makeTextView(weight: Float): TextView
        {
            val textView = TextView(context)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8f)
            textView.setTextColor(Color.BLACK)
            textView.setTypeface(Typeface.MONOSPACE, Typeface.NORMAL)
            textView.setPadding(0, 0, 0, dp8)
            textView.layoutParams = linearLayoutParamsWeW(weight)

            return textView
        }

        fun makeDivider(): View
        {
            return View(context).apply {
                setBackgroundResource(R.color.fast_md_black_12)
                val layoutParams = linearLayoutParamsVM(Dimens.dp(1))
                layoutParams.marginStart = dp4
                layoutParams.marginEnd = dp4
                this.layoutParams = layoutParams
            }
        }
    }
}

private class SessionLineView(context: Context) : View(context)
{
    private val paint = Paint().apply {
        isAntiAlias = true
        color = Color.WHITE
        style = Paint.Style.FILL
        strokeWidth = Dimens.dpF(2)
    }

    private val lineX = Dimens.dpF(6)


    fun setColor(color: Int)
    {
        paint.color =
                if (ColorUtil.isColorDark(color)) ColorUtil.lighten(color, 0.5f)
                else ColorUtil.darken(
                        color,
                        0.5f
                )
    }

    override fun onDraw(canvas: Canvas?)
    {
        super.onDraw(canvas)

        canvas?.drawLine(lineX, 0f, lineX, height.toFloat(), paint)
    }
}