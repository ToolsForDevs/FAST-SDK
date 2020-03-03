package com.toolsfordevs.fast.modules.resourcepicker

import com.toolsfordevs.fast.core.prefs.BooleanPref
import com.toolsfordevs.fast.core.prefs.IntPref
import com.toolsfordevs.fast.modules.resourcepicker.prefs.TypedResourceArrayPref
import com.toolsfordevs.fast.modules.resourcepicker.prefs.ValueResourceArrayPref
import com.toolsfordevs.fast.modules.resources.ColorResource
import com.toolsfordevs.fast.modules.resources.DimensionResource
import com.toolsfordevs.fast.modules.resources.DrawableResource
import com.toolsfordevs.fast.modules.resources.StringResource


internal object Prefs
{
    var explorerPickerSelectedTab:Int by IntPref("EXPLORER_PICKER_SELECTED_TAB", 0)
    var colorPickerSelectedTab:Int by IntPref("COLOR_PICKER_SELECTED_TAB", 0)
    var drawablePickerSelectedTab:Int by IntPref("DRAWABLE_PICKER_SELECTED_TAB", 0)
    var colorDrawablePickerSelectedTab:Int by IntPref("COLOR_DRAWABLE_PICKER_SELECTED_TAB", 0)
    var stringPickerSelectedTab:Int by IntPref("STRING_PICKER_SELECTED_TAB", 0)
    var dimenPickerSelectedTab:Int by IntPref("DIMEN_PICKER_SELECTED_TAB", 0)

    var favoriteColors:ArrayList<ColorResource> by ValueResourceArrayPref("RESOURCE_PICKER_FAVORITE_COLORS")
    var favoriteDimensions:ArrayList<DimensionResource> by ValueResourceArrayPref("RESOURCE_PICKER_FAVORITE_DIMENSIONS")
    var favoriteDrawables:ArrayList<DrawableResource> by TypedResourceArrayPref("RESOURCE_PICKER_FAVORITE_DRAWABLES")
    var favoriteStrings:ArrayList<StringResource> by ValueResourceArrayPref("RESOURCE_PICKER_FAVORITE_STRINGS")

    var dimensionPickerSelectedUnit:Int by IntPref("DIMEN_PICKER_SELECTED_UNIT", -1)

    var materialColorPickerMode:Boolean by BooleanPref("COLOR_PICKER_MATERIAL_MODE", false)
    var favoriteColorPickerMode:Boolean by BooleanPref("COLOR_PICKER_FAVORITE_MODE", false)
    var resourceColorPickerMode:Boolean by BooleanPref("COLOR_PICKER_RESOURCE_MODE", false)

    var favoriteDrawablePickerMode:Boolean by BooleanPref("DRAWABLE_PICKER_FAVORITE_MODE", true)
    var resourceDrawablePickerMode:Boolean by BooleanPref("DRAWABLE_PICKER_RESOURCE_MODE", true)


    var customColorPickerMode:Int by IntPref("CUSTOM_COLOR_PICKER_MODE", 0)
    var customColorPickerBackground:Int by IntPref("CUSTOM_COLOR_PICKER_BACKGROUND", 3)

}