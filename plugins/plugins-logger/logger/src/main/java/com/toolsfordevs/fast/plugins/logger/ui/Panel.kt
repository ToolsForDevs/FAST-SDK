package com.toolsfordevs.fast.plugins.logger.ui

import android.content.Context
import android.graphics.Color
import android.view.*
import android.widget.ImageButton
import android.widget.PopupMenu
import com.toolsfordevs.fast.core.FastColor
import com.toolsfordevs.fast.core.ext.*
import com.toolsfordevs.fast.core.util.AttrUtil
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.core.widget.FastPanel
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.LinearLayoutManager
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.LinearSmoothScroller
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.RecyclerView
import com.toolsfordevs.fast.modules.recyclerview.RendererAdapter
import com.toolsfordevs.fast.modules.recyclerview.decoration.Divider
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.core.ui.ext.hide
import com.toolsfordevs.fast.core.ui.widget.BottomSheetLayout
import com.toolsfordevs.fast.modules.androidx.recyclerview.ext.vRecyclerView
import com.toolsfordevs.fast.plugins.logger.R
import com.toolsfordevs.fast.modules.formatters.FastFormatter
import com.toolsfordevs.fast.modules.formatters.FastFormatterStore
import com.toolsfordevs.fast.plugins.logger.Store
import com.toolsfordevs.fast.plugins.logger.modules.ext.LoggerModule
import com.toolsfordevs.fast.plugins.logger.ui.filter.FilterDialog
import com.toolsfordevs.fast.plugins.logger.formatter.IntrospectionFormatter
import com.toolsfordevs.fast.plugins.logger.formatter.StringFormatter
import com.toolsfordevs.fast.plugins.logger.ui.items.LogSessionEndWrapper
import com.toolsfordevs.fast.plugins.logger.ui.items.LogSessionStartWrapper
import com.toolsfordevs.fast.plugins.logger.model.LogEntry
import com.toolsfordevs.fast.plugins.logger.ui.items.LogEntryWrapper
import com.toolsfordevs.fast.plugins.logger.model.LogSession
import com.toolsfordevs.fast.plugins.logger.ui.renderer.DefaultRenderer
import com.toolsfordevs.fast.plugins.logger.ui.renderer.LogSessionEndRenderer
import com.toolsfordevs.fast.plugins.logger.ui.renderer.LogSessionStartRenderer


internal class Panel(context: Context) : BottomSheetLayout(context)
{
    private var pinButton: ImageButton

    private var isPinned = Prefs.pinPanel
        set(value)
        {
            Prefs.pinPanel = value
            pinButton.setImageResource(if (value) R.drawable.fast_logger_ic_pin_on else R.drawable.fast_logger_ic_pin_off)
            field = value
        }

    private var opacityButton: ImageButton

    private var isSemiTransparent = Prefs.panelOpacity
        set(value)
        {
            Prefs.panelOpacity = value
            //opacityButton.setImageResource(if (value) R.drawable.fast_logcat_ic_pin_on else R.drawable.fast_logcat_ic_pin_off)
            setBackgroundColor(if (value) Color.WHITE else 0x88FFFFFF.toInt())
            field = value
        }

    // Seriously, Google ? No way to programmatically set scrollbar visible ?
    private val recyclerView: RecyclerView = vRecyclerView(context)
    private val layoutManager: LinearLayoutManager = LinearLayoutManager(context)
    private val adapter: LogRendererAdapter = LogRendererAdapter()

    private val logListener = object : Store.LogRepositoryListener
    {
        override fun onLogEntryAdded(logEntry: LogEntry, session: LogSession?)
        {
            adapter.add(getWrapperForEntry(logEntry, session))

            if (Prefs.scrollToEnd) recyclerView.scrollToPosition(adapter.itemCount - 1)
        }

        override fun onSessionStarted(session: LogSession)
        {
            adapter.add(LogSessionStartWrapper(session))

            if (Prefs.scrollToEnd) recyclerView.scrollToPosition(adapter.itemCount - 1)
        }

        override fun onSessionEnded(session: LogSession)
        {
            adapter.add(LogSessionEndWrapper(session))

            if (Prefs.scrollToEnd) recyclerView.scrollToPosition(adapter.itemCount - 1)
        }
    }

    private val header = hLinearLayout(context)
    private val dp56 = Dimens.dp(56)

    init
    {
        mode = FastPanel.MODE_ON_TOP_OF
        val layout = vLinearLayout(context, layoutParamsMM())

        header.setBackgroundColor(FastColor.colorPrimary)
        header.gravity = Gravity.CENTER_VERTICAL or Gravity.END

        // clear button
        createButton(R.drawable.fast_logger_ic_clear) {
            Store.clear()
            adapter.clear()
        }

        // filter button
        createButton(R.drawable.fast_logger_ic_filter) { button ->

            val profiles = Prefs.filterProfiles
            if (profiles.isEmpty())
            {
                FilterDialog(context).show()
            }
            else
            {
                val popup = PopupMenu(context, button)
                val items = arrayListOf<MenuItem>()

                val groupId = View.generateViewId()

                for (profile in profiles) items.add(popup.menu.add(groupId, 0, Menu.NONE, profile.name).apply {
                    isChecked = Prefs.isCurrentProfile(profile)
                })

                popup.menu.setGroupCheckable(groupId, true, false)

                items.add(popup.menu.add("Manage profiles"))

                popup.setOnMenuItemClickListener { menuItem ->

                    val index = items.indexOf(menuItem)

                    if (index == items.size - 1)
                    {
                        FilterDialog(context).also { it.setOnDismissListener { fillAdapter() } }.show()
                    }
                    else
                    {
                        menuItem.isChecked = !menuItem.isChecked
                        Prefs.setAsCurrentProfile(profiles[index], menuItem.isChecked)
                        fillAdapter()
                    }


                    true
                }

                popup.show()
            }
        }

        opacityButton = createButton(R.drawable.fast_logger_ic_opacity) { isSemiTransparent = !isSemiTransparent }

        // toggleMode button
        createButton(R.drawable.fast_logger_ic_layout_bottom_sheet) { button ->
            mode = if (mode == FastPanel.MODE_ON_TOP_OF) FastPanel.MODE_BELOW else FastPanel.MODE_ON_TOP_OF

            (button as ImageButton).setImageResource(if (mode == FastPanel.MODE_ON_TOP_OF) R.drawable.fast_logger_ic_layout_bottom_sheet
                                                     else R.drawable.fast_logger_ic_layout_below_content)
        }

        pinButton = createButton(if (isPinned) R.drawable.fast_logger_ic_pin_on
                                 else R.drawable.fast_logger_ic_pin_off) { isPinned = !isPinned }
        pinButton.hide()


        // overflow button
        createButton(R.drawable.fast_logger_ic_dots_vertical).makeCheckablePopupMenu(listOf("Scroll to end", "Show time", "Show tag"), listOf(Prefs.scrollToEnd, Prefs.showTime, Prefs.showTag)) { index ->
            when (index) {
                0 -> Prefs.scrollToEnd = !Prefs.scrollToEnd
                1 ->
                {
                    Prefs.showTime = !Prefs.showTime
                    LogRendererWrapper.showTime = Prefs.showTime
                    adapter.notifyDataSetChanged()
                }
                2 -> {
                    Prefs.showTag = !Prefs.showTag
                    LogRendererWrapper.showTag = Prefs.showTag
                    adapter.notifyDataSetChanged()
                }
            }
        }

        layout.addView(header , layoutParamsMV(dp56))

        adapter.mode = RendererAdapter.MODE_INSTANCE_OF

        // If no renderer available, use default renderer and formatter
        adapter.addRenderer(FastFormatter::class) { parent: ViewGroup -> LogRendererWrapper(parent, DefaultRenderer(parent)) }

        adapter.addRenderer(::LogSessionStartRenderer)
        adapter.addRenderer(::LogSessionEndRenderer)

        val renderers = LoggerModule.getRenderers()

        for (pair in renderers)
        {
            adapter.addRenderer(pair.first) { parent: ViewGroup ->
                LogRendererWrapper(parent, pair.second(parent))
            }
        }

        layoutManager.stackFromEnd = true
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = null
        recyclerView.adapter = adapter

        val divider = Divider().apply {
            setDividerSizeDp(1)
            setColor(MaterialColor.BLACK_12)
            showOnlyBetweenDataOfType(LogEntryWrapper::class)
            useInsets = false
        }
        recyclerView.addItemDecoration(divider)

        layout.addView(recyclerView, layoutParamsMM())

        addView(layout, layoutParamsMM())

        LogRendererWrapper.showTime = Prefs.showTime
        LogRendererWrapper.showTag = Prefs.showTag

        setCollapsedHeight(Dimens.dp(400))
        setDraggableView(header)
    }

    private fun createButton(iconResId: Int, clickListener: ((view: View) -> Unit)? = null): ImageButton
    {
        val button = ImageButton(context)
        button.background = AttrUtil.getDrawable(context, android.R.attr.selectableItemBackground)
        button.setImageResource(iconResId)
        button.setOnClickListener(clickListener)
        header.addView(button, layoutParamsVV(dp56, dp56))
        return button
    }

    override fun onAttachedToWindow()
    {
        super.onAttachedToWindow()
        fillAdapter()

        LogRendererWrapper.callback = { position, formatter ->
            val item: LogEntryWrapper<*> = adapter.getDataAtPosition(position) as LogEntryWrapper<*>

            val entry = item.logEntry
            val session = item.session

            val newEntry = LogEntry(entry.data, entry.tag, entry.priority, formatter)
            newEntry.timestamp = entry.timestamp
            newEntry.stackTrace = entry.stackTrace

            adapter.remove(item)
            adapter.add(getWrapperForEntry(newEntry, session), position)
        }

        Store.addListener(logListener)
    }

    override fun onDetachedFromWindow()
    {
        super.onDetachedFromWindow()
        Store.removeListener(logListener)
        LogRendererWrapper.callback = null
    }

    private fun fillAdapter()
    {
        adapter.clear()

        val logs = Store.getLogs()

        val wrappers = arrayListOf<Any>()

        for (item in logs)
        {
            if (item is LogSession)
            {
                wrappers.add(LogSessionStartWrapper(item))

                for (entry in item.getEntries()) wrappers.add(getWrapperForEntry(entry, item))

                if (item.hasEnded) wrappers.add(LogSessionEndWrapper(item))
            }
            else
            {
                val entry = item as LogEntry
                wrappers.add(getWrapperForEntry(entry))
            }
        }

        adapter.addAll(wrappers)

        if (Prefs.scrollToEnd && adapter.itemCount > 0)
        {
            recyclerView.post {
                layoutManager.startSmoothScroll(object : LinearSmoothScroller(context)
                                                {
                                                    override fun getVerticalSnapPreference(): Int =
                                                            SNAP_TO_END
                                                }.apply { targetPosition = adapter.itemCount - 1 })
            }
        }
    }

    private fun getWrapperForEntry(entry: LogEntry, session: LogSession? = null): LogEntryWrapper<*>
    {
        val formatters = arrayListOf<FastFormatter>()

        var formatter: FastFormatter? = null

        if (entry.data == null || entry.displayAsRawString || entry.data is String) formatter =
                StringFormatter()

        if (entry.introspectionReport) formatter = IntrospectionFormatter()

        if (entry.formatter != null) formatter = entry.formatter

        if (entry.data != null)
        {
            val all = FastFormatterStore.getFormatters()
            for (f in all)
                if (f.canFormatData(entry.data)) formatters.add(f)
        }

        if (formatters.isNotEmpty() && formatter == null) formatter = formatters.get(0)

        if (formatters.isNotEmpty()) formatters.add(StringFormatter())

        return LogEntryWrapper(entry, session, formatter ?: StringFormatter(), formatters)
    }
}