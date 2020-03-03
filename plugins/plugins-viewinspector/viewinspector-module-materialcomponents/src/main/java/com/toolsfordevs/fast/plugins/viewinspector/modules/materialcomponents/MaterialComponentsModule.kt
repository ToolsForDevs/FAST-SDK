package com.toolsfordevs.fast.plugins.viewinspector.modules.materialcomponents

import android.content.Context
import com.toolsfordevs.fast.core.annotation.FastIncludeModule
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewInspectorModule
import com.toolsfordevs.fast.plugins.viewinspector.modules.materialcomponents.widgets.*

@FastIncludeModule
class MaterialComponentsModule : ViewInspectorModule("MaterialComponents")
{
    override fun initialize()
    {
        addWidget(AppBarLayoutProperties()) // super = LinearLayout
        addWidget(CollapsingToolbarLayoutProperties()) // super = FrameLayout
        addWidget(MaterialToolbarProperties()) // super = androidx.appcompat.Toolbar
        addWidget(BottomAppBarProperties()) // super = androidx.appcompat.Toolbar
        addWidget(BottomNavigationViewProperties()) // super = FrameLayout
        addWidget(NavigationViewProperties()) // super = ScrimInsetsFrameLayout : FrameLayout
        addWidget(MaterialButtonProperties()) // super = androidx.appcompat.AppCompatButton
        addWidget(FloatingActionButtonProperties()) // super = VisibilityAwareButton : ImageButton
        addWidget(ExtendedFloatingActionButtonProperties()) // super = MaterialButton
        addWidget(MaterialButtonToggleGroupProperties()) // super = RelativeLayout
        addWidget(MaterialCardViewProperties()) // super = androidx.cardview.widget.CardView
        addWidget(MaterialCheckBoxProperties()) // super = androidx.appcompat.AppCompatCheckBox
        addWidget(MaterialRadioButtonProperties()) // super = androidx.appcompat.AppCompatRadioButton
        addWidget(SwitchMaterialProperties()) // super = androidx.appcompat.widget.SwitchCompat
        addWidget(TabItemProperties()) // super = View
        addWidget(TabLayoutProperties()) // super = HorizontalScrollView
        addWidget(TextInputEditTextProperties()) // super = androidx.appcompat.widget.AppCompatEditText
        addWidget(TextInputLayoutProperties()) // super = LinearLayout
        addWidget(TransformationChildCardProperties()) // super = com.google.android.material.circularreveal.cardview.CircularRevealCardView : MaterialCardView
        addWidget(TransformationChildLayoutProperties()) // super = com.google.android.material.circularreveal.cardview.CircularRevealFrameLayout : FrameLayout
    }

    override fun onApplicationCreated(context: Context)
    {
        // do nothing
    }
}