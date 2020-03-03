package com.toolsfordevs.fast.plugins.viewinspector.modules.cardview.widgets

import androidx.cardview.widget.CardView
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.*


internal class CardViewProperties: ViewPropertyHolder(CardView::class.java)
{
    init
    {
        add(PropertyCategory.COMMON,
            ColorStateListProperty(CardView::getCardBackgroundColor,
                CardView::setCardBackgroundColor))
        add(PropertyCategory.COMMON,
            DimensionFloatRangeProperty(CardView::getCardElevation,
                CardView::setCardElevation,
                max = dp(50f)))
        add(PropertyCategory.COMMON,
            DimensionFloatRangeProperty(CardView::getMaxCardElevation,
                CardView::setMaxCardElevation,
                max = dp(50f)))
        add(PropertyCategory.COMMON,
            DimensionIntRangeProperty(CardView::getMinimumHeight,
                CardView::setMinimumHeight,
                max = screenHeight))
        add(PropertyCategory.COMMON,
            DimensionIntRangeProperty(CardView::getMinimumWidth,
                CardView::setMinimumWidth,
                max = screenWidth))
        add(PropertyCategory.COMMON,
            BooleanProperty(CardView::getPreventCornerOverlap,
                CardView::setPreventCornerOverlap))
        add(PropertyCategory.COMMON,
            DimensionFloatRangeProperty(CardView::getRadius,
                CardView::setRadius,
                max = dp(50f)))
        add(PropertyCategory.COMMON,
            BooleanProperty(CardView::getUseCompatPadding,
                CardView::setUseCompatPadding))

    }
}