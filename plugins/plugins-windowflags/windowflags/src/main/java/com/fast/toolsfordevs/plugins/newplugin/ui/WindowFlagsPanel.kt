package com.fast.toolsfordevs.plugins.newplugin.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import com.fast.toolsfordevs.modules.codegenerator.CodeSnippet
import com.fast.toolsfordevs.modules.codegenerator.ui.CodeSnippetPanel
import com.fast.toolsfordevs.plugins.windowflags.R
import com.toolsfordevs.fast.core.AppInstance
import com.toolsfordevs.fast.core.FastManager
import com.toolsfordevs.fast.core.ext.*
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.core.ui.ext.setPaddingHorizontal
import com.toolsfordevs.fast.core.ui.ext.setPaddingStart
import com.toolsfordevs.fast.core.ui.widget.BottomSheetLayout
import com.toolsfordevs.fast.core.ui.widget.FastColorView
import com.toolsfordevs.fast.core.ui.widget.FastPanelToolbar
import com.toolsfordevs.fast.core.ui.widget.FastSpinner
import com.toolsfordevs.fast.core.util.AndroidVersion
import com.toolsfordevs.fast.core.util.ColorUtil
import com.toolsfordevs.fast.core.util.ResUtils
import com.toolsfordevs.fast.modules.androidx.recyclerview.ext.vRecyclerView
import com.toolsfordevs.fast.modules.recyclerview.RendererAdapter
import com.toolsfordevs.fast.modules.recyclerview.ViewRenderer
import com.toolsfordevs.fast.modules.resourcepicker.color.ColorPickerDialog
import com.toolsfordevs.fast.modules.subheader.Subheader
import com.toolsfordevs.fast.modules.subheader.SubheaderRenderer

internal class WindowFlagsPanel(context: Context) : BottomSheetLayout(context)
{
    private val adapter = RendererAdapter()
    private val uiSystemFlagManager = UISystemFlagManager()
    private val windowManagerFlagManager = WindowManagerFlagManager()

    init
    {
        val layout = vLinearLayout(context)

        val toolbar = FastPanelToolbar(context)

        toolbar.createButton(R.drawable.fast_module_code_generator_ic_code).setOnClickListener {
            FastManager.addView(CodeSnippetPanel(context, getCodeSnippet()))
        }
        val spinner = FastSpinner(context).apply {
            setLightTheme()
        }

        spinner.setAdapter(
                listOf(
                        "No preselection",
                        "Light status bar",
                        "Dark status bar",
                        "Lean back mode",
                        "Immersive mode",
                        "Immersive sticky mode"
                ), 0
        ) { selectedIndex ->
            when (selectedIndex)
            {
                1 -> uiSystemFlagManager.setStatusBarLight()
                2 -> uiSystemFlagManager.setStatusBarDark()
                3 -> uiSystemFlagManager.setLeanBack()
                4 -> uiSystemFlagManager.setImmersive()
                5 -> uiSystemFlagManager.setImmersiveSticky()
            }

            adapter.notifyDataSetChanged()
        }

        toolbar.buttonLayout.addView(spinner, 0, linearLayoutParamsWeM(1f))
        layout.addView(toolbar, layoutParamsMW())

        val recyclerView = vRecyclerView(context)
        recyclerView.isVerticalScrollBarEnabled = true

        adapter.addRenderer(ItemWrapper::class, ::FlagRenderer)
        adapter.addRenderer(CutoutMode::class, ::CutoutModeRenderer)
        adapter.addRenderer(ColorItem::class, ::ColorRenderer)
        adapter.addRenderer(Subheader::class, ::SubheaderRenderer)

        adapter.add(Subheader("Status bar"))
        uiSystemFlagManager.lightStatusBar?.let { adapter.add(it) }
        adapter.add(windowManagerFlagManager.translucentStatusBar)
        adapter.add(ColorItem("Status bar background color", true))

        adapter.add(Subheader("Nav bar"))
        uiSystemFlagManager.lightNavigationBar?.let { adapter.add(it) }
        adapter.add(windowManagerFlagManager.translucentNavigation)
        adapter.add(ColorItem("Nav bar background color", false))

        if (AndroidVersion.isMinPie())
        {
            adapter.add(Subheader("Cutout mode (notch)"))
            adapter.add(CutoutMode())
        }

        adapter.add(Subheader("View flags"))
        adapter.addAll(uiSystemFlagManager.flags.sortedBy { it.item.name })

        adapter.add(Subheader("WindowManager.LayoutParams flags"))
        adapter.addAll(windowManagerFlagManager.flags.sortedBy { it.item.name })

        recyclerView.adapter = adapter

        layout.addView(recyclerView, layoutParamsMM())

        addView(layout, layoutParamsMM())
        setCollapsedHeight(500.dp)
    }

    private val focusListener = OnFocusChangeListener { p0, p1 ->
        uiSystemFlagManager.reInit()
        windowManagerFlagManager.reInit()
        adapter.notifyDataSetChanged()
    }

    override fun onAttachedToWindow()
    {
        super.onAttachedToWindow()
        AppInstance.activity?.window?.decorView?.onFocusChangeListener = focusListener
    }

    override fun onDetachedFromWindow()
    {
        super.onDetachedFromWindow()
        AppInstance.activity?.window?.decorView?.onFocusChangeListener = null
    }

    private fun getCodeSnippet(): CodeSnippet
    {
        val codeSnippet = CodeSnippet()

        // Status bar
        codeSnippet.addTitle("Status bar customization")
        var kotlinCode = "val activity:Activity = getActivity() // Get reference to current activity"
        var javaCode = "Activity activity = getActivity(); // Get reference to current activity"

        uiSystemFlagManager.lightStatusBar?.let {
            if (it.checked)
            {
                kotlinCode += "\n\n// Set light status bar flag"
                javaCode += "\n\n// Set light status bar flag"

                kotlinCode += "\nactivity?.window?.addFlags(${it.item.code})"
                javaCode += "\nactivity.getWindow().addFlags(${it.item.code})"
            }
            else
            {
                kotlinCode += "\n\n// Remove light status bar flag"
                javaCode += "\n\n// Remove light status bar flag"

                kotlinCode += "\nactivity?.window?.clearFlags(${it.item.code})"
                javaCode += "\nactivity.getWindow().clearFlags(${it.item.code})"
            }
        }

        var translucentItem = windowManagerFlagManager.translucentStatusBar.item.code
        if (windowManagerFlagManager.translucentStatusBar.checked)
        {
            kotlinCode += "\n\n// Set translucent status bar flag"
            javaCode += "\n\n// Set translucent status bar flag"

            kotlinCode += "\nactivity?.window?.decorView?.systemUiVisibility?.let { it = it or $translucentItem}"
            javaCode += "\nView decorView = activity.getWindow().getDecorView();"
            javaCode += "\ndecorView.setSystemUIVisibility(decorView.getSystemUIVisibility() | $translucentItem)"
        }
        else
        {
            kotlinCode += "\n\n// Remove translucent status bar flag"
            javaCode += "\n\n// Remove translucent status bar flag"

            kotlinCode += "\nactivity?.window?.decorView?.systemUiVisibility?.let { it = it and $translucentItem.inv()}"
            javaCode += "\nView decorView = activity.getWindow().getDecorView();"
            javaCode += "\ndecorView.setSystemUIVisibility(decorView.getSystemUIVisibility() & ~$translucentItem);"

            val color = "0x" + ColorUtil.colorHex(AppInstance.activity?.window?.statusBarColor ?: 0)
            kotlinCode += "\n\n// Set status bar color"
            javaCode += "\n\n// Set status bar color"

            kotlinCode += "\nactivity?.window?.statusBarColor = $color.toInt()"
            javaCode += "\nactivity.getWindow().setStatusBarColor($color.toInt());"
        }

        codeSnippet.addSnippets(
                mapOf(
                        CodeSnippet.Language.Kotlin to kotlinCode,
                        CodeSnippet.Language.Java to javaCode
                )
        )

        // Nav bar
        codeSnippet.addTitle("Navigation bar customization")
        kotlinCode = "val activity:Activity = getActivity() // Get reference to current activity"
        javaCode = "Activity activity = getActivity(); // Get reference to current activity"

        uiSystemFlagManager.lightNavigationBar?.let {
            if (it.checked)
            {
                kotlinCode += "\n\n// Set light nav bar flag"
                javaCode += "\n\n// Set light nav bar flag"

                kotlinCode += "\nactivity?.window?.addFlags(${it.item.code})"
                javaCode += "\nactivity.getWindow().addFlags(${it.item.code})"
            }
            else
            {
                kotlinCode += "\n\n// Remove light nav bar flag"
                javaCode += "\n\n// Remove light nav bar flag"

                kotlinCode += "\nactivity?.window?.clearFlags(${it.item.code})"
                javaCode += "\nactivity.getWindow().clearFlags(${it.item.code})"
            }
        }

        translucentItem = windowManagerFlagManager.translucentNavigation.item.code
        if (windowManagerFlagManager.translucentNavigation.checked)
        {
            kotlinCode += "\n\n// Set translucent nav bar flag"
            javaCode += "\n\n// Set translucent nav bar flag"

            kotlinCode += "\nactivity?.window?.decorView?.systemUiVisibility?.let { it = it or $translucentItem}"
            javaCode += "\nView decorView = activity.getWindow().getDecorView();"
            javaCode += "\ndecorView.setSystemUIVisibility(decorView.getSystemUIVisibility() | $translucentItem)"
        }
        else
        {
            kotlinCode += "\n\n// Remove translucent nav bar flag"
            javaCode += "\n\n// Remove translucent nav bar flag"

            kotlinCode += "\nactivity?.window?.decorView?.systemUiVisibility?.let { it = it and $translucentItem.inv()}"
            javaCode += "\nView decorView = activity.getWindow().getDecorView();"
            javaCode += "\ndecorView.setSystemUIVisibility(decorView.getSystemUIVisibility() & ~$translucentItem);"

            val color = "0x" + ColorUtil.colorHex(AppInstance.activity?.window?.navigationBarColor ?: 0)
            kotlinCode += "\n\n// Set nav bar color"
            javaCode += "\n\n// Set nav bar color"

            kotlinCode += "\nactivity?.window?.navigationBarColor = $color.toInt()"
            javaCode += "\nactivity.getWindow().setNavigationBarColor($color.toInt());"
        }

        codeSnippet.addSnippets(
                mapOf(
                        CodeSnippet.Language.Kotlin to kotlinCode,
                        CodeSnippet.Language.Java to javaCode
                )
        )

        if (AndroidVersion.isMinPie())
        {
            // Cutout mode
            codeSnippet.addTitle("Notch management")
            kotlinCode = "val activity:Activity = getActivity() // Get reference to current activity"
            javaCode = "Activity activity = getActivity(); // Get reference to current activity"

            val mode = when (AppInstance.activity?.window?.attributes?.layoutInDisplayCutoutMode)
            {
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT     -> "WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT"
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES -> "WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES"
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER       -> "WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER"
                else                                                                 -> "WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT"
            }
            kotlinCode += "\n\nactivity?.window?.attributes?.layoutInDisplayCutoutMode = $mode"
            javaCode += "\n\nactivity.getWindow().getAttributes().layoutInDisplayCutoutMode = $mode;"

            codeSnippet.addSnippets(
                    mapOf(
                            CodeSnippet.Language.Kotlin to kotlinCode,
                            CodeSnippet.Language.Java to javaCode
                    )
            )
        }

        uiSystemFlagManager.fillCodeSnippet(codeSnippet)
        windowManagerFlagManager.fillCodeSnippet(codeSnippet)

        return codeSnippet
    }

    private data class ColorItem(val name: String, val statusBar: Boolean)

    private inner class ColorRenderer(parent: ViewGroup) : ViewRenderer<ColorItem, LinearLayout>(hLinearLayout(parent.context)), OnClickListener
    {
        val name = TextView(parent.context).apply {
            setTextColor(MaterialColor.BLACK_87)
        }

        val color = FastColorView(context).apply {
            showTransparentGrid = true
        }

        init
        {
            view.setBackgroundResource(R.drawable.fast_selectable_item_background)
            view.setPaddingHorizontal(16.dp)
            view.minimumHeight = 56.dp

            view.addView(name, linearLayoutParamsWeW(1f).apply { gravity = Gravity.CENTER_VERTICAL })
            view.addView(color, linearLayoutParamsVV(32.dp, 32.dp).apply { gravity = Gravity.CENTER_VERTICAL })

            view.layoutParams = layoutParamsMW()

            color.setOnClickListener(this)
        }

        override fun bind(data: ColorItem, position: Int)
        {
            name.text = data.name
            color.setColor(getColor(data))
        }

        override fun onClick(v: View?)
        {
            ColorPickerDialog(context, { colorResource ->
                val colorInt = colorResource.value ?: ResUtils.getColor(colorResource.resId)

                color.setColor(colorInt)
                setNavColor(getItem(adapterPosition) as ColorItem, colorInt)

            }, getColor(getItem(adapterPosition) as ColorItem)).show()
        }

        private fun getColor(data: ColorItem): Int
        {
            return AppInstance.activity?.window?.let {
                if (data.statusBar)
                    it.statusBarColor
                else
                    it.navigationBarColor
            } ?: 0
        }

        private fun setNavColor(data: ColorItem, color: Int)
        {
            AppInstance.activity?.window?.let {
                if (data.statusBar)
                    it.statusBarColor = color
                else
                    it.navigationBarColor = color
            }
        }

    }

    private inner class FlagRenderer(parent: ViewGroup) : ViewRenderer<ItemWrapper<WindowFlag>, LinearLayout>(hLinearLayout(parent.context))
    {
        val name = TextView(parent.context).apply {
            setTextColor(MaterialColor.BLACK_87)
        }

        val checkBox = CheckBox(context)

        init
        {
            view.setBackgroundResource(R.drawable.fast_selectable_item_background)
            view.setPaddingHorizontal(16.dp)
            view.minimumHeight = 56.dp

            view.addView(name, linearLayoutParamsWeW(1f).apply { gravity = Gravity.CENTER_VERTICAL })
            view.addView(checkBox, linearLayoutParamsWW().apply { gravity = Gravity.CENTER_VERTICAL })

            view.layoutParams = layoutParamsMW()
        }

        override fun bind(data: ItemWrapper<WindowFlag>, position: Int)
        {
            name.text = data.item.name

            checkBox.setOnCheckedChangeListener(null)

            checkBox.isChecked = data.checked

            checkBox.setOnCheckedChangeListener { view, isChecked ->
                data.checked = isChecked
                uiSystemFlagManager.applyFlags()
                windowManagerFlagManager.applyFlags()
            }
        }
    }

    private class CutoutMode

    private inner class CutoutModeRenderer(parent: ViewGroup) : ViewRenderer<CutoutMode, FastSpinner>(FastSpinner(parent.context))
    {
        init
        {
            view.setPaddingHorizontal(16.dp)
            view.minimumHeight = 56.dp

            view.layoutParams = layoutParamsMW()
        }

        @SuppressLint("NewApi")
        override fun bind(data: CutoutMode, position: Int)
        {
            view.setAdapter(
                    listOf(
                            "CUTOUT_MODE_DEFAULT",
                            "CUTOUT_MODE_SHORT_EDGES",
                            "CUTOUT_MODE_NEVER"
                    ), AppInstance.activity?.window?.attributes?.layoutInDisplayCutoutMode ?: 0
            ) { selectedIndex ->
                AppInstance.activity?.window?.attributes?.layoutInDisplayCutoutMode = when (selectedIndex)
                {
                    0    -> WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT
                    1    -> WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
                    2    -> WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER
                    else -> WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT
                }
            }
        }

    }
}