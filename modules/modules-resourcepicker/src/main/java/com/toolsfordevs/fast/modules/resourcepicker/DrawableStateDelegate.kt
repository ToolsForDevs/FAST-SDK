package com.toolsfordevs.fast.modules.resourcepicker

import android.app.AlertDialog
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.toolsfordevs.fast.core.ui.ext.inflate


internal class DrawableStateDelegate(val button: View, val callback:(states: IntArray?) -> Unit)
{
    private val states = arrayListOf(
        DrawableState("ACCELERATED", android.R.attr.state_accelerated),
        DrawableState("ACTIVATED", android.R.attr.state_activated),
        DrawableState("ACTIVE", android.R.attr.state_active),
        DrawableState("CHECKED", android.R.attr.state_checked),
        DrawableState("DRAG CAN ACCEPT", android.R.attr.state_drag_can_accept),
        DrawableState("DRAG HOVERED", android.R.attr.state_drag_hovered),
        DrawableState("ENABLED", android.R.attr.state_enabled),
        DrawableState("FIRST", android.R.attr.state_first),
        DrawableState("FOCUSED", android.R.attr.state_focused),
        DrawableState("HOVERED", android.R.attr.state_hovered),
        DrawableState("LAST", android.R.attr.state_last),
        DrawableState("MIDDLE", android.R.attr.state_middle),
        DrawableState("PRESSED", android.R.attr.state_pressed),
        DrawableState("SELECTED", android.R.attr.state_selected),
        DrawableState("SINGLE", android.R.attr.state_single),
        DrawableState("WINDOW FOCUSED", android.R.attr.state_window_focused)
    )

    init
    {
        button.setOnClickListener {
            AlertDialog.Builder(button.context)
                .setAdapter(StateAdapter(states), null)
                .setPositiveButton("OK") { dialog, which ->
                    callback(generateStates()) }
                .setCancelable(true)
                .show()
        }
    }

    private fun generateStates():IntArray?
    {
        val list = arrayListOf<Int>()
        for (state in states)
        {
            if (state.selected)
                list.add(if (state.enabled) state.attrResId else -state.attrResId)
        }

        if (list.isEmpty())
            return null

        val states = IntArray(list.size)

        list.forEachIndexed { index, value -> states.set(index, value) }

        return states
    }

    data class DrawableState(val name:String, val attrResId:Int, var selected:Boolean = false, var enabled:Boolean = true)

    private class StateAdapter(val states:List<DrawableState>) : BaseAdapter()
    {
        override fun getItem(position: Int): Any
        {
            return states.get(position)
        }

        override fun getItemId(position: Int): Long
        {
            return position.toLong()
        }

        override fun getCount(): Int
        {
            return states.size
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
        {
            var view = convertView

            if (convertView == null)
                view = parent.inflate(R.layout.fast_resourcepicker_item_drawable_state)

            val state:DrawableState = getItem(position) as DrawableState

            val checkbox = view!!.findViewById<CheckBox>(R.id.checkbox)
            val name = view.findViewById<TextView>(R.id.name)
            val switch = view.findViewById<Switch>(R.id.switch_view)

            checkbox.setOnCheckedChangeListener(null)
            switch.setOnCheckedChangeListener(null)

            checkbox.isChecked = state.selected
            name.text = state.name
            switch.isChecked = state.enabled

            switch.isEnabled = state.selected

            checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
                state.selected = isChecked
                switch.isEnabled = state.selected
            }
            switch.setOnCheckedChangeListener { buttonView, isChecked -> state.enabled = isChecked }

            return view
        }

    }
}