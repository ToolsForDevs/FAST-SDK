package com.toolsfordevs.fast.plugins.logger.ui.filter

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.toolsfordevs.fast.core.ext.*
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.modules.androidx.tabs.TabLayout
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.core.ui.ext.*
import com.toolsfordevs.fast.core.ui.widget.FastTabbedDialog
import com.toolsfordevs.fast.core.ui.widget.FastToolbar
import com.toolsfordevs.fast.core.ui.widget.FloatingActionButton
import com.toolsfordevs.fast.core.ui.widget.adapter.FastViewPagerAdapter
import com.toolsfordevs.fast.plugins.logger.R
import com.toolsfordevs.fast.plugins.logger.ui.Prefs
import com.toolsfordevs.fast.plugins.logger.ui.prefs.FilterProfile
import com.toolsfordevs.fast.plugins.logger.ui.prefs.TagFilter


internal class FilterDialog(private val callerContext: Context) : FastTabbedDialog(callerContext, Prefs::filterSelectedTab)
{
    private val spinner: TextView
    private var currentProfile: FilterProfile? = null

    private val toolbar: FastToolbar
    private var emptyStateLayout: LinearLayout? = null

    init
    {
        layout.setBackgroundColor(Color.WHITE)
        layout.gravity = Gravity.CENTER
        tabLayout.tabMode = TabLayout.MODE_FIXED

        toolbar = FastToolbar(callerContext)

        val dp16 = Dimens.dp(16)
        spinner = TextView(callerContext)
        spinner.setPaddingStart(dp16)
        spinner.setPaddingEnd(dp16)
        spinner.gravity = Gravity.CENTER_VERTICAL
        spinner.compoundDrawablePadding = dp16
        spinner.setTextColor(Color.WHITE)
        spinner.setBackgroundResource(R.drawable.fast_selectable_item_background)
        spinner.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.fast_logger_ic_menu_down_white, 0)

        spinner.setOnClickListener {
            val popup = PopupMenu(callerContext, spinner)

            val items = arrayListOf<MenuItem>()

            for (filterProfile in Prefs.filterProfiles) items.add(popup.menu.add(filterProfile.name))

            items.add(popup.menu.add("Add new profile"))

            popup.setOnMenuItemClickListener { menuItem ->

                val index = items.indexOf(menuItem)

                if (index == items.size - 1)
                    onNewProfile()
                else
                    load(Prefs.filterProfiles[index])

                true
            }

            popup.show()
        }

        toolbar.addView(spinner, layoutParamsWM())

        toolbar.addView(Space(callerContext), linearLayoutParamsWeM(1f))

        val duplicateButton = toolbar.createButton(R.drawable.fast_logger_ic_duplicate)
        duplicateButton.setOnClickListener { onNewProfile(currentProfile!!) }

        val deleteButton = toolbar.createButton(R.drawable.fast_logger_ic_delete)
        deleteButton.setOnClickListener {
            AlertDialog.Builder(callerContext)
                .setTitle("Delete ${currentProfile?.name}")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", { dialog, which -> delete() })
                .setNegativeButton("No", null)
                .show()
        }

        layout.addView(toolbar, layoutParamsMW())

        refresh()
    }

    private fun refresh()
    {
        when
        {
            Prefs.filterProfiles.isEmpty()     -> showEmptyState()
            Prefs.currentProfiles.isNotEmpty() -> load(Prefs.currentProfiles.get(0))
            else                               -> load(Prefs.filterProfiles.get(0))
        }
    }

    private fun load(profile: FilterProfile)
    {
        currentProfile = profile

        spinner.text = profile.name

        showDataState()

        (viewPager.adapter as FilterAdapter).loadProfile(profile)
        // update views from pageradapter
    }

    private fun delete()
    {
        Prefs.removeProfile(currentProfile!!)
        refresh()
    }

    private fun showEmptyState()
    {
        tabLayout.hide()
        viewPager.hide()
        toolbar.hide()

        emptyStateLayout?.show()

        if (emptyStateLayout == null)
        {
            emptyStateLayout = vLinearLayout(callerContext)
            val text = TextView(callerContext)
            text.setTextColor(MaterialColor.BLACK_87)
            text.text = "No profile yet. Make one!"

            val button = FloatingActionButton(callerContext)
            button.setImageResource(R.drawable.fast_logger_ic_plus)

            button.setOnClickListener {
                onNewProfile()
            }

            emptyStateLayout!!.gravity = Gravity.CENTER
            emptyStateLayout!!.addView(text, layoutParamsWM())
            emptyStateLayout!!.addView(button)

            text.setMarginBottom(Dimens.dp(16))

            layout.addView(emptyStateLayout, linearLayoutParamsWW().apply { gravity = Gravity.CENTER })
        }
    }

    private fun showDataState()
    {
        emptyStateLayout?.hide()

        tabLayout.show()
        viewPager.show()
        toolbar.show()
    }

    private fun onNewProfile(profileToDuplicate:FilterProfile? = null)
    {
        val editText = EditText(callerContext)
        editText.hint = "Profile name"
        editText.layoutParams = layoutParamsMW()

        val textWatcher: TextWatcher

        val title = if (profileToDuplicate != null) "Duplicate ${profileToDuplicate.name}" else "New profile"

        val dialog = AlertDialog.Builder(callerContext).setTitle(title).setView(editText).setNegativeButton("Cancel", null)
            .setPositiveButton("OK") { dialog, which ->
                val profile = FilterProfile(editText.text.toString())

                profileToDuplicate?.let {
                    profile.priorities = it.priorities.clone() as HashSet<Int>
                    profile.tags = it.tags.clone() as ArrayList<TagFilter>
                }

                val profiles = Prefs.filterProfiles.toMutableList()
                profiles.add(profile)
                Prefs.filterProfiles = profiles

                load(profile)
            }.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false

        editText.setMarginTop(Dimens.dp(16))
        editText.setMarginStart(Dimens.dp(20))
        editText.setMarginEnd(Dimens.dp(20))

        textWatcher = object : TextWatcher
        {
            override fun afterTextChanged(s: Editable?)
            {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = editText.text.isNotEmpty()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
            {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
            }

        }

        editText.addTextChangedListener(textWatcher)
    }

    override fun buildViewPagerAdapter(context: Context): FastViewPagerAdapter
    {
        return FilterAdapter(context)
    }

    class FilterAdapter(context: Context) : FastViewPagerAdapter()
    {
        private val view1 = PriorityView(context)
        private val view2 = TagView(context)

        fun loadProfile(profile:FilterProfile)
        {
            (view1 as IFilterView).loadProfile(profile)
            (view2 as IFilterView).loadProfile(profile)
        }

        override fun getItem(position: Int): View
        {
            return when (position)
            {
                1    -> view2
                else -> view1
            }
        }

        override fun getCount(): Int
        {
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence
        {
            return when (position)
            {
                1    -> "Tag"
                else -> "Priority"
            }
        }
    }
}