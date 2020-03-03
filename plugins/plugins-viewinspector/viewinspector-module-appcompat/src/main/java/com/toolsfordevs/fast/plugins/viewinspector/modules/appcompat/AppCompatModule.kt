package com.toolsfordevs.fast.plugins.viewinspector.modules.appcompat

import android.content.Context
import com.toolsfordevs.fast.core.annotation.FastIncludeModule
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewInspectorModule
import com.toolsfordevs.fast.plugins.viewinspector.modules.appcompat.widgets.*

@FastIncludeModule
class AppCompatModule : ViewInspectorModule("AndroidFramework")
{
    override fun initialize()
    {
        // ViewGroups first
        addWidget(LinearLayoutCompatProperties())

        addWidget(AppCompatAutoCompleteTextViewProperties()) // super = AutoCompleteTextView
        addWidget(AppCompatButtonProperties()) // super = Button
        addWidget(AppCompatCheckboxProperties()) // super = Checkbox
        addWidget(AppCompatCheckedTextViewProperties()) // super = CheckedTextView
        addWidget(AppCompatEditTextProperties()) // super = EditText
        addWidget(AppCompatImageButtonProperties()) // super = ImageButton
        addWidget(AppCompatImageViewProperties()) // super = ImageView
        addWidget(AppCompatMultiAutoCompleteTextViewProperties()) // super = MultiAutoCompleteTextView
        addWidget(AppCompatRadioButtonProperties()) // super = RadioButton
        addWidget(AppCompatRatingBarProperties()) // super = RatingBar
        addWidget(AppCompatSeekBarProperties()) // super = SeekBar
        addWidget(AppCompatSpinnerProperties()) // super = Spinner
        addWidget(AppCompatTextViewProperties()) // super = TextView
        // addWidget(AppCompatToggleButtonProperties()) // super = ToggleButton, for some reason, AppCompatToggleButton can't be found in the appcompat package...
        addWidget(SearchViewProperties()) // super = LinearLayoutCompat
        addWidget(SwitchCompatProperties()) // super = CompoundButton
        addWidget(ToolbarProperties()) // super = ViewGroup

        // androidx.emoji.widget
        // Emoji widgets (utton, textview, edittext, ...)

        // androidx.gridlayout.widget
        // GridLayout

        // androidx.percentlayout.widget
        // PercentFrameLayout

        // ViewPager2
    }

    override fun onApplicationCreated(context: Context)
    {
        // do nothing
    }
}