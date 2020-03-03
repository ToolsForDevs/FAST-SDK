package com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.widgets

import android.util.ArrayMap
import android.widget.ImageView
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.*
import com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.properties.AlphaIntProperty


internal class ImageViewProperties: ViewPropertyHolder(ImageView::class.java) {
    init {

        add(PropertyCategory.COMMON, BooleanProperty(ImageView::getAdjustViewBounds, ImageView::setAdjustViewBounds))
        // Alpha deprecated 16, is in View
        add(PropertyCategory.COMMON, IntRangeProperty(ImageView::getBaseline, ImageView::setBaseline, -1, 30))
        add(PropertyCategory.COMMON, BooleanProperty(ImageView::getBaselineAlignBottom, ImageView::setBaselineAlignBottom))
        // ColorFilter
        // ColorFilter
        // ColorFilter
        add(PropertyCategory.COMMON, BooleanProperty(ImageView::getCropToPadding, ImageView::setCropToPadding))
        add(PropertyCategory.COMMON, AlphaIntProperty(ImageView::getImageAlpha, ImageView::setImageAlpha))
        // ImageBitmap, same as ImageDrawable
        add(PropertyCategory.COMMON, ColorDrawableProperty(ImageView::getDrawable, ImageView::setImageDrawable))
        // ImageIcon 23, same as ImageDrawable
        add(PropertyCategory.COMMON, ImageLevelProperty())
        // ImageMatrix
        // ImageResource, same as ImageDrawable
        // ImageState
        add(PropertyCategory.COMMON, ColorStateListProperty(ImageView::getImageTintList, ImageView::setImageTintList))
        add(PropertyCategory.COMMON, ColorTintModeProperty(ImageView::getImageTintMode, ImageView::setImageTintMode))
        // ImageURI, same as ImageDrawable
        add(PropertyCategory.COMMON, DimensionIntRangeProperty(ImageView::getMaxHeight, ImageView::setMaxHeight, max = screenHeight))
        add(PropertyCategory.COMMON, DimensionIntRangeProperty(ImageView::getMaxWidth, ImageView::setMaxWidth, max = screenWidth))
        add(PropertyCategory.COMMON, ScaleTypeProperty())
        // Selected is in View
        // Visibility is in View
    }

    private class ScaleTypeProperty() : SingleChoiceProperty<ImageView, ImageView.ScaleType>(ImageView::getScaleType, ImageView::setScaleType) {
        override fun defineKeyValues(map: ArrayMap<ImageView.ScaleType, String>) {
            map.put(ImageView.ScaleType.CENTER_CROP, "CENTER CROP")
            map.put(ImageView.ScaleType.CENTER, "CENTER")
            map.put(ImageView.ScaleType.CENTER_INSIDE, "CEDNTER INSIDE")
            map.put(ImageView.ScaleType.FIT_CENTER, "FIT CENTER")
            map.put(ImageView.ScaleType.FIT_START, "FIT START")
            map.put(ImageView.ScaleType.FIT_END, "FIT END")
            map.put(ImageView.ScaleType.FIT_XY, "FIT XY")
            map.put(ImageView.ScaleType.MATRIX, "MATRIX")
        }
    }

    private class ImageLevelProperty : IntRangeProperty<ImageView>(null, ImageView::setImageLevel, 0, 100)
    {
        init
        {
            name = "imageLevel"
        }

        override fun getValue(): Int?
        {
            if (view.drawable != null) // No need to check if drawable is LevelListDrawable
                return view.drawable.level

            return 0
        }
    }
}