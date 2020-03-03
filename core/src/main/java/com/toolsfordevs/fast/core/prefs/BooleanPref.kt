package com.toolsfordevs.fast.core.prefs

import com.toolsfordevs.fast.core.annotation.Keep

@Keep
class BooleanPref(key:String, defaultValue:Boolean) : FastPref<Boolean>(key, defaultValue)