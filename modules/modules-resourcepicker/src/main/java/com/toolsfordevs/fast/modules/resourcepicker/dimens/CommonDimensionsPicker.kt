package com.toolsfordevs.fast.modules.resourcepicker.dimens

import android.content.Context
import com.toolsfordevs.fast.modules.resourcepicker.R
import com.toolsfordevs.fast.modules.resourcepicker.ResourcePickerView
import com.toolsfordevs.fast.modules.resources.DimensionResource
import com.toolsfordevs.fast.modules.subheader.Subheader
import com.toolsfordevs.fast.core.ui.ext.hide


internal class CommonDimensionsPicker(context: Context) : ResourcePickerView(context)
{
    private var viewDelegate: DimensViewDelegate
    private var listener: ((dimensionRes: DimensionResource) -> Unit)? = null

    init
    {
        buttonBar.hide()

        viewDelegate = DimensViewDelegate(recyclerView) { dimensionRes -> listener?.invoke(dimensionRes) }

        val data = arrayListOf<Any>()

        data.add(Subheader("Common dp values"))

        data.add(DimensionResource("fast_resource_picker_common_dp_value_1", R.dimen.fast_resource_picker_common_dp_value_1))
        data.add(DimensionResource("fast_resource_picker_common_dp_value_2", R.dimen.fast_resource_picker_common_dp_value_2))
        data.add(DimensionResource("fast_resource_picker_common_dp_value_3", R.dimen.fast_resource_picker_common_dp_value_3))
        data.add(DimensionResource("fast_resource_picker_common_dp_value_4", R.dimen.fast_resource_picker_common_dp_value_4))
        data.add(DimensionResource("fast_resource_picker_common_dp_value_8", R.dimen.fast_resource_picker_common_dp_value_8))
        data.add(DimensionResource("fast_resource_picker_common_dp_value_12", R.dimen.fast_resource_picker_common_dp_value_12))
        data.add(DimensionResource("fast_resource_picker_common_dp_value_16", R.dimen.fast_resource_picker_common_dp_value_16))
        data.add(DimensionResource("fast_resource_picker_common_dp_value_20", R.dimen.fast_resource_picker_common_dp_value_20))
        data.add(DimensionResource("fast_resource_picker_common_dp_value_24", R.dimen.fast_resource_picker_common_dp_value_24))
        data.add(DimensionResource("fast_resource_picker_common_dp_value_32", R.dimen.fast_resource_picker_common_dp_value_32))
        data.add(DimensionResource("fast_resource_picker_common_dp_value_36", R.dimen.fast_resource_picker_common_dp_value_36))
        data.add(DimensionResource("fast_resource_picker_common_dp_value_40", R.dimen.fast_resource_picker_common_dp_value_40))
        data.add(DimensionResource("fast_resource_picker_common_dp_value_48", R.dimen.fast_resource_picker_common_dp_value_48))
        data.add(DimensionResource("fast_resource_picker_common_dp_value_56", R.dimen.fast_resource_picker_common_dp_value_56))
        data.add(DimensionResource("fast_resource_picker_common_dp_value_72", R.dimen.fast_resource_picker_common_dp_value_72))
        data.add(DimensionResource("fast_resource_picker_common_dp_value_96", R.dimen.fast_resource_picker_common_dp_value_96))
        data.add(DimensionResource("fast_resource_picker_common_dp_value_112", R.dimen.fast_resource_picker_common_dp_value_112))
        data.add(DimensionResource("fast_resource_picker_common_dp_value_144", R.dimen.fast_resource_picker_common_dp_value_144))
        data.add(DimensionResource("fast_resource_picker_common_dp_value_192", R.dimen.fast_resource_picker_common_dp_value_192))
        data.add(DimensionResource("fast_resource_picker_common_dp_value_256", R.dimen.fast_resource_picker_common_dp_value_256))
        data.add(DimensionResource("fast_resource_picker_common_dp_value_384", R.dimen.fast_resource_picker_common_dp_value_384))
        data.add(DimensionResource("fast_resource_picker_common_dp_value_512", R.dimen.fast_resource_picker_common_dp_value_512))

        data.add(Subheader("Common sp values"))
        data.add(DimensionResource("fast_resource_picker_common_sp_value_10", R.dimen.fast_resource_picker_common_sp_value_10))
        data.add(DimensionResource("fast_resource_picker_common_sp_value_11", R.dimen.fast_resource_picker_common_sp_value_11))
        data.add(DimensionResource("fast_resource_picker_common_sp_value_12", R.dimen.fast_resource_picker_common_sp_value_12))
        data.add(DimensionResource("fast_resource_picker_common_sp_value_14", R.dimen.fast_resource_picker_common_sp_value_14))
        data.add(DimensionResource("fast_resource_picker_common_sp_value_16", R.dimen.fast_resource_picker_common_sp_value_16))
        data.add(DimensionResource("fast_resource_picker_common_sp_value_18", R.dimen.fast_resource_picker_common_sp_value_18))
        data.add(DimensionResource("fast_resource_picker_common_sp_value_20", R.dimen.fast_resource_picker_common_sp_value_20))
        data.add(DimensionResource("fast_resource_picker_common_sp_value_24", R.dimen.fast_resource_picker_common_sp_value_24))
        data.add(DimensionResource("fast_resource_picker_common_sp_value_34", R.dimen.fast_resource_picker_common_sp_value_34))
        data.add(DimensionResource("fast_resource_picker_common_sp_value_48", R.dimen.fast_resource_picker_common_sp_value_48))
        data.add(DimensionResource("fast_resource_picker_common_sp_value_60", R.dimen.fast_resource_picker_common_sp_value_60))
        data.add(DimensionResource("fast_resource_picker_common_sp_value_96", R.dimen.fast_resource_picker_common_sp_value_96))


        viewDelegate.setData(data)
    }

    fun setOnDimensionSelectedListener(callback: (dimensionRes: DimensionResource) -> Unit)
    {
        listener = callback
    }
}