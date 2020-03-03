package com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework

import android.content.Context
import com.toolsfordevs.fast.core.annotation.FastIncludeModule
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewInspectorModule
import com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.widgets.*

@FastIncludeModule
class AndroidFrameworkModule : ViewInspectorModule("AndroidFramework")
{
    override fun initialize()
    {
        addWidget(ViewProperties())

        // ViewGroups
        addWidget(ViewGroupProperties())
        addWidget(FrameLayoutProperties())
        addWidget(ScrollViewProperties()) // super = FrameLayout
        addWidget(HorizontalScrollViewProperties()) // super = FrameLayout
        addWidget(LinearLayoutProperties())
        addWidget(RelativeLayoutProperties())
//        addWidget(AbsoluteLayoutProperties())
//        addWidget(GridLayoutProperties())
//        addWidget(TableLayoutProperties())

        addWidget(TextViewProperties())
        addWidget(EditTextProperties()) // super = TextView
        addWidget(AutoCompleteTextViewProperties()) // super = EditText
        // addWidget(MultiAutoCompleteTextViewProperties()) // super = AutoCompleteTextView

        addWidget(ButtonProperties()) // super = TextView
        addWidget(CompoundButtonProperties()) // super = Button
        addWidget(ToggleButonProperties()) // super = CompoundButton
        addWidget(CheckboxProperties()) // super = CompoundButton
        addWidget(RadioButtonProperties()) // super = CompoundButton
        addWidget(SwitchProperties()) // super = CompoundButton

        addWidget(ImageViewProperties())
        addWidget(ImageButtonProperties()) // super = ImageView

        addWidget(ProgressBarProperties())
        addWidget(AbsSeekBarProperties()) // super = ProgressBar
        addWidget(SeekBarProperties()) // super = AbsSeekBar
        addWidget(RatingBarProperties()) // super = AbsSeekBar

        addWidget(RadioGroupProperties()) // super = LinearLayout
        addWidget(SearchViewProperties()) // super = LinearLayout

        addWidget(AdapterViewProperties())
        addWidget(AbsSpinnerProperties()) // super = AdapterView
        addWidget(SpinnerProperties()) // super = AbsSpinner

        addWidget(ToolbarProperties())

        // AbsListView
        // ActionMenuView : A few things
        // AdapterViewAnimator
        // AdapterViewFlipper
        // AnalogClock
        // CalendarView
        // CheckedTextView
        // DatePicker
        // DigitalClock
        // ExpandableListView // super = listview
        // Gallery
        // GridView
        // ImageSwitcher
        // ListView
        // MediaController
        // MultiAutoCompleteTextView
        // NumberPicker
        // QuickContactBadge
        // SlidingDrawer
        // Space
        // StackView
        // TabHost
        // TableRow
        // TabWidget
        // TextClock
        // TextSwitcher
        // TimePicker
        // TwoLineListItem
        // VideoView
        // ViewAnimator
        // ViewFlipper
        // ViewSwitcher
        // WebView : A few things
        // ZoomButton



        // AppCompatButton : A few things
        // AppCompatEditText : A few things
        // AppCompatRadioButton : A few things
        // AppCompatSeekBar : nothing
        // AppCompatRatingBar : nothing
        // AppCompatMultiAutoCompleteTextView : A few things
        // AppCompatAutoCompleteTextView : A few things
        // AppCompatCheckedTextView : A few things
        // AppCompatImageButton : A few things
        // AppCompatImageView : A few things
        // AppCompatToggleButton : Nothing
        // RecyclerView : A few things
        // ViewPager : A few things
        // NestedScrollView : A few things
        // FAB : More than a few things
        // CardView : More than a few things
        // Chip : Way too many things
        // Chip Group : Way too many things
        // AppBarLayout
        // BottomAppBar
        // NavigationView
        // BottomNavigationView
        // TabLayout
        // TabItem
        // MapView
    }

    override fun onApplicationCreated(context: Context)
    {
        // do nothing
    }
}

