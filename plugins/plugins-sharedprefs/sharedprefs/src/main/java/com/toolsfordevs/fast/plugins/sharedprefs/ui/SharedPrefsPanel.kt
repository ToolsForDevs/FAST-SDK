package com.toolsfordevs.fast.plugins.sharedprefs.ui

import android.content.Context
import android.content.SharedPreferences
import android.view.KeyEvent
import com.toolsfordevs.fast.core.ext.*
import com.toolsfordevs.fast.core.prefs.SharedPrefsProvider
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.core.ui.widget.BottomSheetLayout
import com.toolsfordevs.fast.core.ui.widget.FastPanelToolbar
import com.toolsfordevs.fast.core.ui.widget.FastSpinner
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.core.util.FastSort
import com.toolsfordevs.fast.core.widget.FastPanel
import com.toolsfordevs.fast.modules.androidx.recyclerview.ext.vRecyclerView
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.LinearLayoutManager
import com.toolsfordevs.fast.modules.recyclerview.RendererAdapter
import com.toolsfordevs.fast.modules.recyclerview.decoration.Divider
import com.toolsfordevs.fast.plugins.sharedprefs.PrefManager
import com.toolsfordevs.fast.plugins.sharedprefs.Prefs
import com.toolsfordevs.fast.plugins.sharedprefs.R
import com.toolsfordevs.fast.plugins.sharedprefs.ui.model.*
import com.toolsfordevs.fast.plugins.sharedprefs.ui.renderer.*
import java.io.File
import java.util.Collections.emptyList


internal class SharedPrefsPanel(context: Context) : BottomSheetLayout(context)
{
    private val spinner:FastSpinner = FastSpinner(context)
    private val adapter: RendererAdapter

    private val callback: (SharedPreferences, String) -> Unit = { sharedPreferences, key ->
        refreshPreferences()
    }

    init
    {
        mode = FastPanel.MODE_ON_TOP_OF
        val layout = vLinearLayout(context, layoutParamsMM())

        val toolbar = FastPanelToolbar(context)

        spinner.setLightTheme()

        refreshSpinner()

        toolbar.buttonLayout.addView(spinner, 0, linearLayoutParamsWeM(1f).apply { marginEnd = Dimens.dp(16) })

        val filterButton = toolbar.createButton(R.drawable.fast_plugin_sharedprefs_ic_filter)
        val sortButton = toolbar.createButton(R.drawable.fast_plugin_sharedprefs_ic_sort)
        val overflowButton = toolbar.createButton(R.drawable.fast_plugin_sharedprefs_ic_overflow_light)

        sortButton.makePopupMenu(listOf("Default", "A -> Z", "Z -> A")) { selectedIndex ->
            Prefs.mainSorting = when (selectedIndex) {
                0 -> FastSort.DEFAULT
                1 -> FastSort.ALPHA_ASC
                2 -> FastSort.ALPHA_DESC
                else -> FastSort.DEFAULT
            }

            refreshPreferences()
        }
        overflowButton.makePopupMenu(listOf("Add entry", "Refresh", "Delete file")) { selectedIndex ->
            when (selectedIndex)
            {
//                0 ->
                1 -> refreshPreferences()
                2 -> {
                    PrefManager.deleteCurrentFile()
                    refreshSpinner()
                    refreshPreferences()
                }
            }
        }

        layout.addView(toolbar, layoutParamsMW())

        val recyclerView = vRecyclerView(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = RendererAdapter()
        adapter.mode = RendererAdapter.MODE_INSTANCE_OF
        adapter.addRenderer(BooleanPref::class, ::BooleanPrefRenderer)
        adapter.addRenderer(IntPref::class, ::IntPrefRenderer)
        adapter.addRenderer(FloatPref::class, ::FloatPrefRenderer)
        adapter.addRenderer(LongPref::class, ::LongPrefRenderer)
        adapter.addRenderer(StringPref::class, ::StringPrefRenderer)
        adapter.addRenderer(StringSetPref::class, ::StringSetPrefRenderer)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(Divider().apply {
            setDividerSizeDp(1)
            showLast = true
            setColor(MaterialColor.BLACK_12)
        })

        layout.addView(recyclerView, layoutParamsMM())
        addView(layout, layoutParamsMM())

        setCollapsedHeight(Dimens.dp(400))
        setDraggableView(toolbar)
    }

    private fun refreshSpinner()
    {
        var index = -1
        val files = getPreferenceFiles().sorted()

        Prefs.lastSelectedPreferenceFile?.let { index = files.indexOf(it) }

        if (index == -1)
        {
            // User removed the pref file somehow
            index = 0
            Prefs.lastSelectedPreferenceFile = if (files.isNotEmpty()) files[0] else null
        }

        spinner.setAdapter(files, index) { i ->
            run {
                Prefs.lastSelectedPreferenceFile = files[i]
                refreshPreferences()
            }
        }
    }

    private fun refreshPreferences()
    {
        adapter.clear()

        var data = PrefManager.getSharedPreferences().all.map {
            when (it.value)
            {
                is Boolean    -> BooleanPref(it.key)
                is Int        -> IntPref(it.key)
                is Float      -> FloatPref(it.key)
                is Long       -> LongPref(it.key)
                is String     -> StringPref(it.key)
                is HashSet<*> -> StringSetPref(it.key)
                else          -> throw Exception("SharedPrefsPlugin error : cannot determine type for ${it.key} => ${it.value}")
            }
        }

        data = when (Prefs.mainSorting)
        {
            FastSort.ALPHA_ASC  -> data.sortedWith(naturalOrder())
            FastSort.ALPHA_DESC -> data.sortedWith(reverseOrder())
            else                -> data
        }

        adapter.addAll(data)
    }

    private fun getPreferenceFiles(): List<String>
    {
        val prefsDirectory = File(context.applicationInfo.dataDir, "shared_prefs")

        if (prefsDirectory.exists() && prefsDirectory.isDirectory)
            return prefsDirectory.list()?.filter { it.endsWith(".xml") }?.
                map { it.substring(0, it.lastIndexOf(".")) }?.
                filter { !SharedPrefsProvider.ignoredFiles.contains(it) } ?: emptyList()

        return emptyList()
    }

    override fun onKeyStroke(key: Int)
    {
        if (key == KeyEvent.KEYCODE_T)
            toggle()
    }

    override fun onAttachedToWindow()
    {
        super.onAttachedToWindow()
        PrefManager.getSharedPreferences().registerOnSharedPreferenceChangeListener(callback)
        refreshPreferences()
    }

    override fun onDetachedFromWindow()
    {
        super.onDetachedFromWindow()
        PrefManager.getSharedPreferences().unregisterOnSharedPreferenceChangeListener(callback)
    }
}