package com.toolsfordevs.fast.plugins.logger.ui.filter

import com.toolsfordevs.fast.plugins.logger.ui.prefs.FilterProfile


internal interface IFilterView
{
    fun loadProfile(profile:FilterProfile)
}