package com.toolsfordevs.fast.plugins.actions.ui

import com.toolsfordevs.fast.plugins.actions.base.Action


internal data class ActionWrapper<T:Any>(val name:String, val action:Action<T>, val callback:(T?)->Unit)