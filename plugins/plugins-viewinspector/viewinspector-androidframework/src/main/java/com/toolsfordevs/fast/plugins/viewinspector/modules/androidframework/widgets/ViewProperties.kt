package com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.widgets

import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.ArrayMap
import android.view.View
import android.view.ViewGroup
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.*
import com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.properties.AlphaProperty
import com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.properties.AngleProperty
import com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.properties.GravityProperty
import com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.properties.MarginProperty


internal class ViewProperties : ViewPropertyHolder(View::class.java)
{
    // Don't need to override superclass getter/setter
    init
    {
        add(PropertyCategory.COMMON, AlphaProperty(View::getAlpha, View::setAlpha))

        add(PropertyCategory.COMMON, ColorDrawableProperty(View::getBackground, View::setBackground))
        add(PropertyCategory.COMMON, ColorStateListProperty(View::getBackgroundTintList, View::setBackgroundTintList))
        add(PropertyCategory.COMMON, ColorTintModeProperty(View::getBackgroundTintMode, View::setBackgroundTintMode))

        // Android Q
        // add(PropertyCategory.COMMON, MultiValueViewProperty("Background Tint Mode", BlendModeDelegate(view, View::getBackgroundTintMode, View::setBackgroundTintMode), "29"))

        add(PropertyCategory.COMMON, BooleanProperty(View::isEnabled, View::setEnabled))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            add(PropertyCategory.COMMON, ColorDrawableProperty(View::getForeground, View::setForeground).apply { newApi = "23" })
            add(PropertyCategory.COMMON, ColorStateListProperty(View::getForegroundTintList, View::setForegroundTintList).apply { newApi = "23" })
            add(PropertyCategory.COMMON,
                ColorTintModeProperty(View::getForegroundTintMode, View::setForegroundTintMode).apply { newApi = "23"; deprecatedApi = "29" })


            // Android Q
            //add(PropertyCategory.COMMON, MultiValueViewProperty("Foreground Tint mode", ColorBlendModeDelegate(view, View::getForegroundTintMode, View::setForegroundTintMode), "29"))
            add(PropertyCategory.COMMON, GravityProperty<View>(View::getForegroundGravity, View::setForegroundGravity).apply { newApi = "23" })
        }

        add(PropertyCategory.COMMON, DimensionFloatRangeProperty(View::getElevation, View::setElevation, max = sp(24f)))
        add(PropertyCategory.COMMON, BooleanProperty(View::getFitsSystemWindows, View::setFitsSystemWindows))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) add(PropertyCategory.COMMON,
            BooleanProperty(View::isAccessibilityHeading, View::setAccessibilityHeading).apply { newApi = "28" })

        // setAccessibilityLiveRegion (list)
        // setAccessibilityPaneTitle String 28
        // setAccessibilityTraversalAfter id 22
        // setAccessibilityTraversalBefore id 22

        add(PropertyCategory.COMMON, BooleanProperty(View::isActivated, View::setActivated))

        // CameraDistance (size)
        add(PropertyCategory.COMMON, BooleanProperty(View::isClickable, View::setClickable))
        add(PropertyCategory.COMMON, BooleanProperty(View::getClipToOutline, View::setClipToOutline))
        add(PropertyCategory.COMMON, StringProperty(View::getContentDescription, View::setContentDescription))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) add(PropertyCategory.COMMON,
            BooleanProperty(View::isContextClickable, View::setContextClickable).apply { newApi = "23" })

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) add(PropertyCategory.COMMON,
            BooleanProperty(View::getDefaultFocusHighlightEnabled, View::setDefaultFocusHighlightEnabled).apply { newApi = "26" })

        add(PropertyCategory.COMMON, ColorProperty(View::getDrawingCacheBackgroundColor, View::setDrawingCacheBackgroundColor).apply { deprecatedApi = "28" })
        add(PropertyCategory.COMMON, BooleanProperty(View::isDrawingCacheEnabled, View::setDrawingCacheEnabled).apply { deprecatedApi = "28" })
        add(PropertyCategory.COMMON, DrawingCacheQualityProperty().apply { deprecatedApi = "28" })
        add(PropertyCategory.COMMON, BooleanProperty(View::isDuplicateParentStateEnabled, View::setDuplicateParentStateEnabled))
        // (elevation)
        // (enabled)
        // FadingEdgeLength (size)
        add(PropertyCategory.COMMON, BooleanProperty(View::getFilterTouchesWhenObscured, View::setFilterTouchesWhenObscured))
        // (fitSystemWindows)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) add(PropertyCategory.COMMON,
            BooleanProperty(View::isFocusable, View::setFocusable).apply { newApi = "26" })

        add(PropertyCategory.COMMON, BooleanProperty(View::isFocusableInTouchMode, View::setFocusableInTouchMode))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) add(PropertyCategory.COMMON,
            BooleanProperty(View::isFocusedByDefault, View::setFocusedByDefault).apply { newApi = "26" })

        // Android Q
        // add(PropertyCategory.COMMON, BooleanProperty(View::isForceDarkAllowed, View::setForceDarkAllowed)))
        // (foreground)
        add(PropertyCategory.COMMON, BooleanProperty(View::isHapticFeedbackEnabled, View::setHapticFeedbackEnabled))
        add(PropertyCategory.COMMON, BooleanProperty(View::hasTransientState, View::setHasTransientState))
        add(PropertyCategory.COMMON, BooleanProperty(View::isHorizontalFadingEdgeEnabled, View::setHorizontalFadingEdgeEnabled))
        add(PropertyCategory.COMMON, BooleanProperty(View::isHorizontalScrollBarEnabled, View::setHorizontalScrollBarEnabled))

        //Android Q
        //add(PropertyCategory.COMMON, DrawableProperty("HorizontalScrollbarThumbDrawable", View::getHorizontalScrollbarThumbDrawable, View::setHorizontalScrollbarThumbDrawable), "29))
        //add(PropertyCategory.COMMON, DrawableProperty("HorizontalScrollbarTrackDrawable", View::getHorizontalScrollbarTrackDrawable, View::setHorizontalScrollbarTrackDrawable), "29))

        add(PropertyCategory.COMMON, BooleanProperty(View::isHovered, View::setHovered))
        add(PropertyCategory.COMMON, ImportantForAccessibilityProperty())
        add(PropertyCategory.COMMON, BooleanProperty(View::getKeepScreenOn, View::setKeepScreenOn))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) add(PropertyCategory.COMMON,
            BooleanProperty(View::isKeyboardNavigationCluster, View::setKeyboardNavigationCluster).apply { newApi = "26" })
        // LabelFor (id, not sure there's a getter)
        // (layerPaint)
        // (layerType)
        add(PropertyCategory.COMMON, LayoutDirectionProperty())
        add(PropertyCategory.COMMON, BooleanProperty(View::isLongClickable, View::setLongClickable))
        // (minWidth / Height)
        add(PropertyCategory.COMMON, BooleanProperty(View::isNestedScrollingEnabled, View::setNestedScrollingEnabled))
        // NextClusterForwardId (id, O)
        // NextFocusDownId (id)
        // NextFocusForwardId (id) 26
        // NextFocusLeftId (id)
        // NextFocusRightId (id)
        // NextFocusUpId (id)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
        {
            add(PropertyCategory.COMMON, ColorProperty(View::getOutlineAmbientShadowColor, View::setOutlineAmbientShadowColor).apply { newApi = "28" })
            add(PropertyCategory.COMMON, ColorProperty(View::getOutlineSpotShadowColor, View::setOutlineSpotShadowColor).apply { newApi = "28" })
        }
        add(PropertyCategory.COMMON, OverScrollModeProperty())
        // (padding)
        // (pivot)
        // PointerIcon Icon 24
        add(PropertyCategory.COMMON, BooleanProperty(View::isPressed, View::setPressed))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) add(PropertyCategory.COMMON,
            BooleanProperty(View::getRevealOnFocusHint, View::setRevealOnFocusHint).apply { newApi = "26" })
        // (rotation)
        add(PropertyCategory.COMMON, BooleanProperty(View::isSaveEnabled, View::setSaveEnabled))
        add(PropertyCategory.COMMON, BooleanProperty(View::isSaveFromParentEnabled, View::setSaveFromParentEnabled))
        // (scale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) add(PropertyCategory.COMMON,
            BooleanProperty(View::isScreenReaderFocusable, View::setScreenReaderFocusable).apply { newApi = "28" })
        // ScrollBarDefaultDelayBeforeFade (time in ms)
        // ScrollBarFadeDuration (time in ms)
        // ScrollBarSize (size)
        add(PropertyCategory.COMMON, ScrollBarStyleProperty())
        add(PropertyCategory.COMMON, BooleanProperty(View::isScrollContainer, View::setScrollContainer))
        // ScrollIndicators (list, M)
        add(PropertyCategory.COMMON, DimensionIntRangeProperty(View::getScrollX, View::setScrollX, max = screenWidth))
        add(PropertyCategory.COMMON, DimensionIntRangeProperty(View::getScrollY, View::setScrollY, max = screenHeight))
        add(PropertyCategory.COMMON, BooleanProperty(View::isScrollbarFadingEnabled, View::setScrollbarFadingEnabled))
        add(PropertyCategory.COMMON, BooleanProperty(View::isSelected, View::setSelected))
        add(PropertyCategory.COMMON, BooleanProperty(View::isSoundEffectsEnabled, View::setSoundEffectsEnabled))
        //add(PropertyCategory.COMMON, SystemUiVisibilityProperty(PropertyCategory.COMMON))
        add(PropertyCategory.COMMON, TextAlignmentProperty())
        add(PropertyCategory.COMMON, TextDirectionProperty())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) add(PropertyCategory.COMMON,
            StringProperty(View::getTooltipText, View::setTooltipText).apply { newApi = "26" })


        // Android Q
        // add(PropertyCategory.COMMON, FloatRangeProperty("TransitionAlpha", View::isTransitionAlpha, View::setTransitionAlpha), "29))

        add(PropertyCategory.COMMON, StringProperty(View::getTransitionName, { transitionName = it.toString() }))
        // TransitionVisibility (list, Q)
        // (translation)
        add(PropertyCategory.COMMON, BooleanProperty(View::isVerticalFadingEdgeEnabled, View::setVerticalFadingEdgeEnabled))
        add(PropertyCategory.COMMON, BooleanProperty(View::isVerticalScrollBarEnabled, View::setVerticalScrollBarEnabled))
        add(PropertyCategory.COMMON, VerticalScrollBarPositionProperty())

        //Android Q
        //add(PropertyCategory.COMMON, DrawableProperty("VerticalScrollbarThumbDrawable", View::getVerticalScrollbarThumbDrawable, View::setVerticalScrollbarThumbDrawable), "29"))
        //add(PropertyCategory.COMMON, DrawableProperty("VerticalScrollbarTrackDrawable", View::getVerticalScrollbarTrackDrawable, View::setVerticalScrollbarTrackDrawable), "29"))
        // (visibility)
        add(PropertyCategory.COMMON, BooleanProperty(View::willNotCacheDrawing, View::setWillNotCacheDrawing).apply { deprecatedApi = "28" })
        add(PropertyCategory.COMMON, BooleanProperty(View::willNotDraw, View::setWillNotDraw))

        // Add layout properties at the end so that they are sorted the way I want and not alphabetically
        add(PropertyCategory.LAYOUT, VisibilityProperty())

        // All layouts are using MarginLayoutParams as superclass, so rather than defining margin properties somewhere else, I do it here
        add(PropertyCategory.LAYOUT, MarginProperty(ViewGroup.MarginLayoutParams::leftMargin, dp(64)).apply { name = "marginLeft" })
        add(PropertyCategory.LAYOUT, MarginProperty(ViewGroup.MarginLayoutParams::topMargin, dp(64)).apply { name = "marginTop" })
        add(PropertyCategory.LAYOUT, MarginProperty(ViewGroup.MarginLayoutParams::rightMargin, dp(64)).apply { name = "marginRight" })
        add(PropertyCategory.LAYOUT, MarginProperty(ViewGroup.MarginLayoutParams::bottomMargin, dp(64)).apply { name = "marginBottom" })

        add(PropertyCategory.LAYOUT, DimensionIntRangeProperty(View::getPaddingLeft, View::setPaddingLeft, max = screenWidth))
        add(PropertyCategory.LAYOUT, DimensionIntRangeProperty(View::getPaddingRight, View::setPaddingRight, max = screenWidth))
        add(PropertyCategory.LAYOUT, DimensionIntRangeProperty(View::getPaddingRight, View::setPaddingTop, max = screenHeight))
        add(PropertyCategory.LAYOUT, DimensionIntRangeProperty(View::getPaddingBottom, View::setPaddingBottom, max = screenHeight))

        add(PropertyCategory.LAYOUT, DimensionIntRangeProperty(View::getMinimumWidth, View::setMinimumWidth, max = screenWidth))
        add(PropertyCategory.LAYOUT, DimensionIntRangeProperty(View::getMinimumHeight, View::setMinimumHeight, max = screenHeight))

        add(PropertyCategory.LAYOUT,
            DimensionFloatRangeProperty(View::getTranslationX, View::setTranslationX, min = -screenWidth.toFloat(), max = screenWidth.toFloat()))
        add(PropertyCategory.LAYOUT,
            DimensionFloatRangeProperty(View::getTranslationY, View::setTranslationY, min = -screenWidth.toFloat(), max = screenWidth.toFloat()))
        add(PropertyCategory.LAYOUT,
            DimensionFloatRangeProperty(View::getTranslationZ, View::setTranslationZ, min = -screenWidth.toFloat(), max = screenWidth.toFloat()))

        add(PropertyCategory.LAYOUT, DimensionFloatRangeProperty(View::getX, View::setX, min = -screenWidth.toFloat(), max = screenWidth.toFloat()))
        add(PropertyCategory.LAYOUT, DimensionFloatRangeProperty(View::getY, View::setY, min = -screenHeight.toFloat(), max = screenHeight.toFloat()))
        add(PropertyCategory.LAYOUT, DimensionFloatRangeProperty(View::getZ, View::setZ, min = -1000f, max = 1000f))

        add(PropertyCategory.LAYOUT, AngleProperty(View::getRotation, View::setRotation))
        add(PropertyCategory.LAYOUT, AngleProperty(View::getRotationX, View::setRotationX))
        add(PropertyCategory.LAYOUT, AngleProperty(View::getRotationY, View::setRotationY))

        add(PropertyCategory.LAYOUT, DimensionFloatRangeProperty(View::getPivotX, View::setPivotX, max = screenWidth.toFloat()))
        add(PropertyCategory.LAYOUT, DimensionFloatRangeProperty(View::getPivotY, View::setPivotY, max = screenHeight.toFloat()))
    }


    private class BackgroundColorProperty<T : View>() : ColorProperty<T>(View::getSolidColor, View::setBackgroundColor)
    {
        override fun getValue(): Int
        {
            view.background?.let {
                if (it is ColorDrawable) return it.color
            }

            return -1
        }

        override fun hasValue(): Boolean
        {
            return view.background != null && view.background is ColorDrawable
        }
    }

    private class VisibilityProperty : SingleChoiceProperty<View, Int>(View::getVisibility, View::setVisibility)
    {
        override fun defineKeyValues(map: ArrayMap<Int, String>)
        {
            map.put(View.VISIBLE, "VISIBLE")
            map.put(View.INVISIBLE, "INVISIBLE")
            map.put(View.GONE, "GONE")
        }
    }

    private class DrawingCacheQualityProperty : SingleChoiceProperty<View, Int>(View::getDrawingCacheQuality, View::setDrawingCacheQuality)
    {
        override fun defineKeyValues(map: ArrayMap<Int, String>)
        {
            map.put(View.DRAWING_CACHE_QUALITY_AUTO, "AUTO")
            map.put(View.DRAWING_CACHE_QUALITY_LOW, "LOW")
            map.put(View.DRAWING_CACHE_QUALITY_HIGH, "HIGH")
        }
    }

    private class ImportantForAccessibilityProperty : SingleChoiceProperty<View, Int>(View::getImportantForAccessibility, View::setImportantForAccessibility)
    {
        override fun defineKeyValues(map: ArrayMap<Int, String>)
        {
            map.put(View.IMPORTANT_FOR_ACCESSIBILITY_AUTO, "AUTO")
            map.put(View.IMPORTANT_FOR_ACCESSIBILITY_YES, "YES")
            map.put(View.IMPORTANT_FOR_ACCESSIBILITY_NO, "NO")
            map.put(View.IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS, "NO HIDE DESCENDANTS")
        }
    }

    private class OverScrollModeProperty : SingleChoiceProperty<View, Int>(View::getOverScrollMode, View::setOverScrollMode)
    {
        override fun defineKeyValues(map: ArrayMap<Int, String>)
        {
            map.put(View.OVER_SCROLL_ALWAYS, "ALWAYS")
            map.put(View.OVER_SCROLL_IF_CONTENT_SCROLLS, "IF CONTENT SCROLLS")
            map.put(View.OVER_SCROLL_NEVER, "NEVER")
        }
    }

    private class ScrollBarStyleProperty : SingleChoiceProperty<View, Int>(View::getScrollBarStyle, View::setScrollBarStyle)
    {
        override fun defineKeyValues(map: ArrayMap<Int, String>)
        {
            map.put(View.SCROLLBARS_INSIDE_INSET, "INSIDE INSET")
            map.put(View.SCROLLBARS_INSIDE_OVERLAY, "INSIDE OVERLAY")
            map.put(View.SCROLLBARS_OUTSIDE_INSET, "OUTSIDE INSET")
            map.put(View.SCROLLBARS_OUTSIDE_OVERLAY, "OUTSIDE OVERLAY")
        }
    }

    private class LayoutDirectionProperty : SingleChoiceProperty<View, Int>(View::getLayoutDirection, View::setLayoutDirection)
    {
        override fun defineKeyValues(map: ArrayMap<Int, String>)
        {
            map.put(View.LAYOUT_DIRECTION_INHERIT, "INHERIT")
            map.put(View.LAYOUT_DIRECTION_LOCALE, "LOCALE")
            map.put(View.LAYOUT_DIRECTION_LTR, "RTL")
            map.put(View.LAYOUT_DIRECTION_RTL, "LTR")
        }
    }

    private class TextAlignmentProperty : SingleChoiceProperty<View, Int>(View::getTextAlignment, View::setTextAlignment)
    {
        override fun defineKeyValues(map: ArrayMap<Int, String>)
        {
            map.put(View.TEXT_ALIGNMENT_INHERIT, "INHERIT")
            map.put(View.TEXT_ALIGNMENT_GRAVITY, "GRAVITY")
            map.put(View.TEXT_ALIGNMENT_TEXT_START, "TEXT START")
            map.put(View.TEXT_ALIGNMENT_TEXT_END, "TEXT END")
            map.put(View.TEXT_ALIGNMENT_CENTER, "CENTER")
            map.put(View.TEXT_ALIGNMENT_VIEW_START, "VIEW START")
            map.put(View.TEXT_ALIGNMENT_VIEW_END, "VIEW END")
        }
    }

    private class TextDirectionProperty : SingleChoiceProperty<View, Int>(View::getTextDirection, View::setTextDirection)
    {
        override fun defineKeyValues(map: ArrayMap<Int, String>)
        {
            map.put(View.TEXT_DIRECTION_INHERIT, "INHERIT")
            map.put(View.TEXT_DIRECTION_ANY_RTL, "ANY RTL")
            map.put(View.TEXT_DIRECTION_FIRST_STRONG, "FIRST STRONG")
            map.put(View.TEXT_DIRECTION_FIRST_STRONG_LTR, "FIRST STRONG LTR")
            map.put(View.TEXT_DIRECTION_FIRST_STRONG_RTL, "FIRST STRING RTL")
            map.put(View.TEXT_DIRECTION_LOCALE, "LOCALE")
            map.put(View.TEXT_DIRECTION_LTR, "LTR")
            map.put(View.TEXT_DIRECTION_RTL, "RTL")
        }
    }

    /*private class SystemUiVisibilityDelegate(view: View) : MultiSelectableValueDelegate<View>(view) {
        companion object {
            private val labels = arrayListOf(
                "VISIBLE",
                "FULLSCREEN",
                "HIDE NAVIGATION",
                "IMMERSIVE",
                "IMMERSIVE STICKY",
                "LAYOUT HIDE NAVIGATION",
                "LAYOUT STABLE",
                "LIGHT NAVIGATION BAR",
                "LIGHT STATUS BAR",
                "LOW PROFILE"
            )

            private val values = arrayListOf(
                View.SYSTEM_UI_FLAG_VISIBLE,
                View.SYSTEM_UI_FLAG_FULLSCREEN,
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION,
                View.SYSTEM_UI_FLAG_IMMERSIVE,
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY,
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION,
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE,
                View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR,
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR,
                View.SYSTEM_UI_FLAG_LOW_PROFILE
            )
        }

        override fun getLabel(): String {
            if (getSelectedValues().isEmpty())
                return "VISIBLE"

            var label = ""

            for (flag in getSelectedValues()) {
                if (label.isNotEmpty())
                    label += "|"

                label += labels[values.indexOf(flag)]
            }

            return label
        }

        override fun getLabels(): List<String> {
            return labels
        }

        override fun getValues(): List<Int> {
            return values
        }

        override fun getSelectedValues(): List<Int> {
            val flags = view.systemUiVisibility

            val list = arrayListOf<Int>()

            if (flags == View.SYSTEM_UI_FLAG_VISIBLE)
                add(PropertyCategory.COMMON, View.SYSTEM_UI_FLAG_VISIBLE)
            else {
                for (flag in values.drop(1)) {
                    if (flag and flags == flag)
                        add(PropertyCategory.COMMON, flag)
                }
            }

            return list
        }

        override fun setSelectedValues(values: List<Int>) {
            var flags = 0

            for (flag in values)
                flags = flags or flag

            view.systemUiVisibility = flags
        }
    }*/


    private class VerticalScrollBarPositionProperty() : SingleChoiceProperty<View, Int>(View::getVerticalScrollbarPosition, View::setVerticalScrollbarPosition)
    {
        override fun defineKeyValues(map: ArrayMap<Int, String>)
        {
            map.put(View.SCROLLBAR_POSITION_DEFAULT, "DEFAULT")
            map.put(View.SCROLLBAR_POSITION_LEFT, "LEFT")
            map.put(View.SCROLLBAR_POSITION_RIGHT, "RIGHT")
        }
    }
}

private fun View.setPaddingLeft(value: Int) = setPadding(value, paddingTop, paddingRight, paddingBottom)
private fun View.setPaddingRight(value: Int) = setPadding(paddingLeft, paddingTop, value, paddingBottom)
private fun View.setPaddingTop(value: Int) = setPaddingRelative(paddingStart, value, paddingEnd, paddingBottom)
private fun View.setPaddingBottom(value: Int) = setPaddingRelative(paddingStart, paddingTop, paddingEnd, value)