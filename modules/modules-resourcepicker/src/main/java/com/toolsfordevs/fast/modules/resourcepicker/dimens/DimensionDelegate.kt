package com.toolsfordevs.fast.modules.resourcepicker.dimens

import android.util.TypedValue
import android.view.Menu
import android.widget.Button
import android.widget.PopupMenu
import com.toolsfordevs.fast.modules.resourcepicker.Prefs
import com.toolsfordevs.fast.modules.resourcepicker.R


internal class DimensionDelegate(val button: Button, val callback: (dimensionType: Int) -> Unit)
{
    companion object
    {
        const val DEFAULT_UNIT: Int = -1
    }

    var dimensionType = Prefs.dimensionPickerSelectedUnit
        set(value)
        {
            field = value
            Prefs.dimensionPickerSelectedUnit = value

            button.text = when (field)
            {
                DEFAULT_UNIT                -> "DEF"
                TypedValue.COMPLEX_UNIT_MM  -> "MM"
                TypedValue.COMPLEX_UNIT_IN  -> "IN"
                TypedValue.COMPLEX_UNIT_PT  -> "PT"
                TypedValue.COMPLEX_UNIT_PX  -> "PX"
                TypedValue.COMPLEX_UNIT_SP  -> "SP"
                TypedValue.COMPLEX_UNIT_DIP -> "DP"
                else                        -> "DP"
            }

            callback(value)
        }

    init
    {
        dimensionType = dimensionType

        button.setOnClickListener {

            val popup = PopupMenu(button.context, button)

            popup.menu.add(Menu.NONE, R.id.fast_resourcepicker_menu_dd, Menu.NONE, "Default dimension (DEF)")
            popup.menu.add(Menu.NONE, R.id.fast_resourcepicker_menu_mm, Menu.NONE, "Millimeters (MM)")
            popup.menu.add(Menu.NONE, R.id.fast_resourcepicker_menu_in, Menu.NONE, "Inches (IN)")
            popup.menu.add(Menu.NONE, R.id.fast_resourcepicker_menu_pt, Menu.NONE, "Points (PT)")
            popup.menu.add(Menu.NONE, R.id.fast_resourcepicker_menu_px, Menu.NONE, "Pixels (PX)")
            popup.menu.add(Menu.NONE, R.id.fast_resourcepicker_menu_sp, Menu.NONE, "Scaled-independent Pixels (SP)")
            popup.menu.add(Menu.NONE, R.id.fast_resourcepicker_menu_dp, Menu.NONE, "Density-independent Pixels (DP/DIP)")

            popup.setOnMenuItemClickListener { menuItem ->

                dimensionType = when (menuItem.itemId)
                {
                    R.id.fast_resourcepicker_menu_dd -> DEFAULT_UNIT
                    R.id.fast_resourcepicker_menu_mm -> TypedValue.COMPLEX_UNIT_MM
                    R.id.fast_resourcepicker_menu_in -> TypedValue.COMPLEX_UNIT_IN
                    R.id.fast_resourcepicker_menu_pt -> TypedValue.COMPLEX_UNIT_PT
                    R.id.fast_resourcepicker_menu_px -> TypedValue.COMPLEX_UNIT_PX
                    R.id.fast_resourcepicker_menu_sp -> TypedValue.COMPLEX_UNIT_SP
                    R.id.fast_resourcepicker_menu_dp -> TypedValue.COMPLEX_UNIT_DIP
                    else                             -> DEFAULT_UNIT
                }
                true
            }

            popup.show()
        }
    }
}