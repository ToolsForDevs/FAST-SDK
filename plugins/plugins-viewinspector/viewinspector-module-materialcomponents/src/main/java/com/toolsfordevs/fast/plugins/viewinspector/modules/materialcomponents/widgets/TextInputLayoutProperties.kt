package com.toolsfordevs.fast.plugins.viewinspector.modules.materialcomponents.widgets

import android.util.ArrayMap
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.*
import com.google.android.material.textfield.TextInputLayout


internal class TextInputLayoutProperties: ViewPropertyHolder(TextInputLayout::class.java)
{
    init
    {
        add(PropertyCategory.COMMON,
            ColorProperty(TextInputLayout::getBoxBackgroundColor,
                TextInputLayout::setBoxBackgroundColor))
        add(PropertyCategory.COMMON, BoxBackgroundModeProperty())
        add(PropertyCategory.COMMON,
            DimensionFloatRangeProperty(TextInputLayout::getBoxCornerRadiusBottomEnd,
                TextInputLayout::setBoxCornerRadiiBottomEnd,
                max = dp(50f)))
        add(PropertyCategory.COMMON,
            DimensionFloatRangeProperty(TextInputLayout::getBoxCornerRadiusBottomStart,
                TextInputLayout::setBoxCornerRadiiBottomStart,
                max = dp(50f)))
        add(PropertyCategory.COMMON,
            DimensionFloatRangeProperty(TextInputLayout::getBoxCornerRadiusTopEnd,
                TextInputLayout::setBoxCornerRadiiTopEnd,
                max = dp(50f)))
        add(PropertyCategory.COMMON,
            DimensionFloatRangeProperty(TextInputLayout::getBoxCornerRadiusTopStart,
                TextInputLayout::setBoxCornerRadiiTopStart,
                max = dp(50f)))
        add(PropertyCategory.COMMON,
            ColorProperty(TextInputLayout::getBoxStrokeColor,
                TextInputLayout::setBoxStrokeColor))
        add(PropertyCategory.COMMON,
            BooleanProperty(TextInputLayout::isCounterEnabled,
                TextInputLayout::setCounterEnabled))
        add(PropertyCategory.COMMON,
            IntRangeProperty(TextInputLayout::getCounterMaxLength,
                TextInputLayout::setCounterMaxLength,
                max = 200))
        // setCounterOverflowTextAppearance ToDo
        add(PropertyCategory.COMMON,
            ColorStateListProperty(TextInputLayout::getCounterOverflowTextColor,
                TextInputLayout::setCounterOverflowTextColor))
        // setCounterTextAppearance ToDo
        add(PropertyCategory.COMMON,
            ColorStateListProperty(TextInputLayout::getCounterTextColor,
                TextInputLayout::setCounterTextColor))
        add(PropertyCategory.COMMON,
            ColorStateListProperty(TextInputLayout::getDefaultHintTextColor,
                TextInputLayout::setDefaultHintTextColor))
        // setEnabled is in SuperClass
        // add(PropertyCategory.COMMON, BooleanProperty(TextInputLayout::endIisECounterEnabled, TextInputLayout::setEndIconActivated)) no getter :(
        add(PropertyCategory.COMMON,
            StringProperty(TextInputLayout::getEndIconContentDescription,
                TextInputLayout::setEndIconContentDescription))
        add(PropertyCategory.COMMON,
            DrawableProperty(TextInputLayout::getEndIconDrawable,
                TextInputLayout::setEndIconDrawable))
        add(PropertyCategory.COMMON, EndIconModeProperty())
        // add(PropertyCategory.COMMON, ColorStateListProperty(TextInputLayout::getEndIconDrawable, TextInputLayout::setEndIconTintList)) no getter :(
        // add(PropertyCategory.COMMON, ColorTintModeProperty(TextInputLayout::getEndIconDrawable, TextInputLayout::setEndIconTintMode)) no getter :(
        add(PropertyCategory.COMMON,
            BooleanProperty(TextInputLayout::isEndIconVisible,
                TextInputLayout::setEndIconVisible))
        add(PropertyCategory.COMMON,
            StringProperty(TextInputLayout::getError, TextInputLayout::setError))
        add(PropertyCategory.COMMON,
            BooleanProperty(TextInputLayout::isErrorEnabled,
                TextInputLayout::setErrorEnabled))
        // setErrorTextAppearance ToDo
        // add(PropertyCategory.COMMON, ColorStateListProperty(TextInputLayout::getErrorCurrentTextColors, TextInputLayout::setErrorTextColor)) no getter :(
        add(PropertyCategory.COMMON,
            StringProperty(TextInputLayout::getHelperText,
                TextInputLayout::setHelperText))
        // add(PropertyCategory.COMMON, ColorStateListProperty(TextInputLayout::getHelperTextCurrentTextColor, TextInputLayout::setHelperTextColor)) no getter :(
        add(PropertyCategory.COMMON,
            BooleanProperty(TextInputLayout::isHelperTextEnabled,
                TextInputLayout::setHelperTextEnabled))
        // setHelperTextTextAppearance ToDo
        add(PropertyCategory.COMMON,
            StringProperty(TextInputLayout::getHint, TextInputLayout::setHint))
        add(PropertyCategory.COMMON,
            BooleanProperty(TextInputLayout::isHintAnimationEnabled,
                TextInputLayout::setHintAnimationEnabled))
        add(PropertyCategory.COMMON,
            BooleanProperty(TextInputLayout::isHintEnabled,
                TextInputLayout::setHintEnabled))
        // setHintTextAppearance ToDo
        add(PropertyCategory.COMMON,
            ColorStateListProperty(TextInputLayout::getHintTextColor,
                TextInputLayout::setHintTextColor))
        add(PropertyCategory.COMMON, StringProperty(
            TextInputLayout::getPasswordVisibilityToggleContentDescription,
            TextInputLayout::setPasswordVisibilityToggleContentDescription).apply { deprecatedApi = "D" })
        add(PropertyCategory.COMMON, DrawableProperty(
            TextInputLayout::getPasswordVisibilityToggleDrawable,
            TextInputLayout::setPasswordVisibilityToggleDrawable).apply { deprecatedApi = "D" })
        add(PropertyCategory.COMMON, BooleanProperty(
            TextInputLayout::isPasswordVisibilityToggleEnabled,
            TextInputLayout::setPasswordVisibilityToggleEnabled).apply { deprecatedApi = "D" })
        // add(PropertyCategory.COMMON, ColorStateListProperty(TextInputLayout::getPVHintTextColor, TextInputLayout::setPasswordVisibilityToggleTintList)) no getter :(
        // add(PropertyCategory.COMMON, ColorTintModeProperty(TextInputLayout::getPVHintTextColor, TextInputLayout::setPasswordVisibilityToggleTintMode)) no getter :(
        add(PropertyCategory.COMMON,
            StringProperty(TextInputLayout::getStartIconContentDescription,
                TextInputLayout::setStartIconContentDescription))
        add(PropertyCategory.COMMON,
            DrawableProperty(TextInputLayout::getStartIconDrawable,
                TextInputLayout::setStartIconDrawable))
        // add(PropertyCategory.COMMON, ColorStateList(TextInputLayout::getStartIconDrawable, TextInputLayout::setStartIconTintList)) no getter :(
        // add(PropertyCategory.COMMON, ColorTintModeProperty(TextInputLayout::getStartIconDrawable, TextInputLayout::setStartIconTintMode)) no getter :(
        add(PropertyCategory.COMMON,
            BooleanProperty(TextInputLayout::isStartIconVisible,
                TextInputLayout::setStartIconVisible))
        // setTypeface ToDo
    }

    private class BoxBackgroundModeProperty : SingleChoiceProperty<TextInputLayout, Int>(TextInputLayout::getBoxBackgroundMode, TextInputLayout::setBoxBackgroundMode)
    {
        override fun defineKeyValues(map: ArrayMap<Int, String>)
        {
            map.put(TextInputLayout.BOX_BACKGROUND_NONE, "NONE")
            map.put(TextInputLayout.BOX_BACKGROUND_FILLED, "FILLED")
            map.put(TextInputLayout.BOX_BACKGROUND_OUTLINE, "OUTLINE")
        }
    }

    private class EndIconModeProperty : SingleChoiceProperty<TextInputLayout, Int>(TextInputLayout::getEndIconMode, TextInputLayout::setEndIconMode)
    {
        override fun defineKeyValues(map: ArrayMap<Int, String>)
        {
            map.put(TextInputLayout.END_ICON_NONE, "NONE")
            map.put(TextInputLayout.END_ICON_CLEAR_TEXT, "CLEAR TEXT")
            map.put(TextInputLayout.END_ICON_CUSTOM, "CUSTOM")
            map.put(TextInputLayout.END_ICON_DROPDOWN_MENU, "DROPDOWN MENU")
            map.put(TextInputLayout.END_ICON_PASSWORD_TOGGLE, "PASSWORD TOGGLE")
        }
    }
}

internal fun TextInputLayout.setBoxCornerRadiiTopStart(radii:Float) {
    setBoxCornerRadii(radii, boxCornerRadiusTopEnd, boxCornerRadiusBottomStart, boxCornerRadiusBottomEnd)
}

internal fun TextInputLayout.setBoxCornerRadiiTopEnd(radii:Float) {
    setBoxCornerRadii(boxCornerRadiusTopStart, radii, boxCornerRadiusBottomStart, boxCornerRadiusBottomEnd)
}

internal fun TextInputLayout.setBoxCornerRadiiBottomStart(radii:Float) {
    setBoxCornerRadii(boxCornerRadiusTopStart, boxCornerRadiusTopEnd, radii, boxCornerRadiusBottomEnd)
}

internal fun TextInputLayout.setBoxCornerRadiiBottomEnd(radii:Float) {
    setBoxCornerRadii(boxCornerRadiusTopStart, boxCornerRadiusTopEnd, boxCornerRadiusBottomStart, radii)
}