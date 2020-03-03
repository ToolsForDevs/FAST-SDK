package com.toolsfordevs.fast.plugins.viewinspector.modules.materialcomponents.widgets

import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.*
import com.google.android.material.card.MaterialCardView


internal class MaterialCardViewProperties: ViewPropertyHolder(MaterialCardView::class.java)
{
    init
    {
        // setBackground is in View
        // setBackgroundTintList is in View
        // setBackgroundTintMode is in View
        // setCardBackgroundColor is in View
        // setCardElevation is in CardView
        add(PropertyCategory.COMMON,
            BooleanProperty(MaterialCardView::isCheckable,
                MaterialCardView::setCheckable))
        add(PropertyCategory.COMMON,
            BooleanProperty(MaterialCardView::isChecked,
                MaterialCardView::setChecked))
        add(PropertyCategory.COMMON,
            DrawableProperty(MaterialCardView::getCheckedIcon,
                MaterialCardView::setCheckedIcon))
        add(PropertyCategory.COMMON,
            ColorStateListProperty(MaterialCardView::getCheckedIconTint,
                MaterialCardView::setCheckedIconTint))
        add(PropertyCategory.COMMON,
            BooleanProperty(MaterialCardView::isDragged,
                MaterialCardView::setDragged))
        // setMaxCardElevation is in CardView
        // setPreventCornerOverlap is in CardView
        // setRadius is in CardView
        add(PropertyCategory.COMMON,
            ColorStateListProperty(MaterialCardView::getRippleColor,
                MaterialCardView::setRippleColor))
        add(PropertyCategory.COMMON,
            ColorStateListProperty(MaterialCardView::getStrokeColorStateList,
                MaterialCardView::setStrokeColor))
        add(PropertyCategory.COMMON,
            DimensionIntRangeProperty(MaterialCardView::getStrokeWidth,
                MaterialCardView::setStrokeWidth,
                max = dp(20)))
        // setUseCompatPadding is in CardView
    }
}