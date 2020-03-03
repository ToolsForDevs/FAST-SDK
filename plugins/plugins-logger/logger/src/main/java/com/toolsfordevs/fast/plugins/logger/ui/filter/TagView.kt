package com.toolsfordevs.fast.plugins.logger.ui.filter

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.Menu
import android.view.ViewGroup
import android.widget.*
import com.toolsfordevs.fast.core.ext.*
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.LinearLayoutManager
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.RecyclerView
import com.toolsfordevs.fast.modules.recyclerview.Renderer
import com.toolsfordevs.fast.modules.recyclerview.RendererAdapter
import com.toolsfordevs.fast.core.ui.ext.setPaddingHorizontal
import com.toolsfordevs.fast.core.ui.widget.FloatingActionButton
import com.toolsfordevs.fast.modules.androidx.recyclerview.ext.vRecyclerView
import com.toolsfordevs.fast.plugins.logger.R
import com.toolsfordevs.fast.plugins.logger.ui.Prefs
import com.toolsfordevs.fast.plugins.logger.ui.prefs.FilterProfile
import com.toolsfordevs.fast.plugins.logger.ui.prefs.TagFilter
import com.toolsfordevs.fast.plugins.logger.ui.prefs.FilterType
import java.util.concurrent.FutureTask


internal class TagView(context: Context) : FrameLayout(context), IFilterView
{
    private val adapter: RendererAdapter = RendererAdapter()

    private lateinit var profile: FilterProfile
    private var runnable: FutureTask<Unit>? = null

    init
    {
        setBackgroundColor(Color.WHITE)

        val recyclerView = vRecyclerView(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setBackgroundColor(Color.WHITE)

        adapter.addRenderer(::FilterRenderer)
        recyclerView.adapter = adapter

        val button = FloatingActionButton(context)
        button.setImageResource(R.drawable.fast_logger_ic_plus)

        button.setOnClickListener {
            val item = TagFilter("", FilterType.STARTS_WITH)
            profile.tags.add(item)
            adapter.add(item, true)
        }

        addView(recyclerView, layoutParamsMM())

        val dp16 = Dimens.dp(16)
        val dp56 = Dimens.dp(56)
        addView(button, frameLayoutParamsVV(dp56, dp56).apply {
            gravity = Gravity.BOTTOM or Gravity.END
            bottomMargin = dp16
            marginEnd = dp16
        })
    }

    fun save()
    {
        /*val tags = arrayListOf<TagFilter>()

        for (filter in profile.tags)
        {
            Log.d("Prefs", "TagView tag filter # ${filter.filter} ${filter.type}")
            if (filter.filter.isNotEmpty()) tags.add(filter)
        }

        Log.d("Prefs", "TagView profile ${profile.name} ${profile.id} ${profile.tags.size}")

        val clone = FilterProfile(profile.id, profile.name, profile.priorities, tags)*/

        Prefs.saveProfile(profile)
    }

    override fun loadProfile(profile: FilterProfile)
    {
        this.profile = profile

        adapter.clear()
        adapter.addAll(profile.tags)
    }

    private inner class FilterRenderer(parent: ViewGroup) : Renderer<TagFilter>(FilterView(parent.context))
    {
        private val view = itemView as FilterView

        private var textWatcher:TextWatcher? = null

        override fun bind(data: TagFilter, position: Int)
        {
            textWatcher?.let {
                view.editText.removeTextChangedListener(textWatcher)
            }

            view.editText.setText(data.filter)
            view.spinner.setText(data.type.name.replace("_", " "))

            view.spinner.makePopupMenu(listOf("STARTS WITH", "CONTAINS", "ENDS WITH", "REGEXP")) { selectedIndex ->
                data.type = when(selectedIndex)
                {
                    0 -> FilterType.STARTS_WITH
                    1 -> FilterType.CONTAINS
                    2 -> FilterType.ENDS_WITH
                    3 -> FilterType.REGEXP
                    else                                   -> FilterType.STARTS_WITH
                }

                view.spinner.text = data.type.name.replace("_", " ")
                save()
            }
            
            textWatcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
                {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
                {}

                override fun afterTextChanged(s: Editable?)
                {
                    s?.let {
                        data.filter = it.toString()

                        // Delayed save
                        runnable?.cancel(false)
                        runnable = FutureTask { save()}

                        view.postDelayed(runnable, 500)
                    }
                }
            }

            view.editText.addTextChangedListener(textWatcher)

            view.button.setOnClickListener {
                profile.tags.remove(data)
                adapter.remove(data, true)
                save()
            }
        }
    }

    private class FilterView(context: Context) : LinearLayout(context)
    {
        val spinner = TextView(context)
        val editText = EditText(context)
        val button = ImageButton(context)

        init
        {
            layoutParams = layoutParamsMW()
            gravity = Gravity.CENTER_VERTICAL

            val dp56 = Dimens.dp(56)

            spinner.setPaddingHorizontal(Dimens.dp(8))
            spinner.compoundDrawablePadding = Dimens.dp(8)
            spinner.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.fast_logger_ic_menu_down, 0)
            spinner.setBackgroundResource(R.drawable.fast_selectable_item_background)
            spinner.gravity = Gravity.CENTER_VERTICAL

            button.setImageResource(R.drawable.fast_logger_ic_close)
            button.setBackgroundResource(R.drawable.fast_selectable_item_background)
            button.setBackgroundResource(R.drawable.fast_selectable_item_background_borderless)

            addView(spinner, layoutParamsWM())
            addView(editText, linearLayoutParamsWeW(1f))
            addView(button, layoutParamsVV(dp56, dp56))
        }
    }

}