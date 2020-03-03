package com.toolsfordevs.fast.plugins.viewinspector.modules.materialcomponents.widgets

import android.util.ArrayMap
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.*
import com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.properties.IdProperty
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode


internal class BottomNavigationViewProperties : ViewPropertyHolder(BottomNavigationView::class.java)
{
    init
    {
        // setElevation is in View
        add(PropertyCategory.COMMON, DrawableProperty(BottomNavigationView::getItemBackground, BottomNavigationView::setItemBackground))
        add(PropertyCategory.COMMON,
            BooleanProperty(BottomNavigationView::isItemHorizontalTranslationEnabled, BottomNavigationView::setItemHorizontalTranslationEnabled))
        add(PropertyCategory.COMMON, DimensionIntRangeProperty(BottomNavigationView::getItemIconSize, BottomNavigationView::setItemIconSize, max = dp(128)))
        add(PropertyCategory.COMMON, ColorStateListProperty(BottomNavigationView::getItemIconTintList, BottomNavigationView::setItemIconTintList))
        add(PropertyCategory.COMMON, ColorStateListProperty(BottomNavigationView::getItemRippleColor, BottomNavigationView::setItemRippleColor))
        // setItemTextAppearanceActive // ToDo
        // setItemTextAppearanceInactive // ToDo
        add(PropertyCategory.COMMON, ColorStateListProperty(BottomNavigationView::getItemTextColor, BottomNavigationView::setItemTextColor))
        add(PropertyCategory.COMMON, LabelVisibilityModeProperty())
        add(PropertyCategory.COMMON, IdProperty(BottomNavigationView::getSelectedItemId, BottomNavigationView::setSelectedItemId))
    }

    private class LabelVisibilityModeProperty :
            SingleChoiceProperty<BottomNavigationView, Int>(BottomNavigationView::getLabelVisibilityMode, BottomNavigationView::setLabelVisibilityMode)
    {
        override fun defineKeyValues(map: ArrayMap<Int, String>)
        {
            map.put(LabelVisibilityMode.LABEL_VISIBILITY_AUTO, "AUTO")
            map.put(LabelVisibilityMode.LABEL_VISIBILITY_SELECTED, "SELECTED")
            map.put(LabelVisibilityMode.LABEL_VISIBILITY_LABELED, "LABELED")
            map.put(LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED, "UNLABELED")
        }
    }
}