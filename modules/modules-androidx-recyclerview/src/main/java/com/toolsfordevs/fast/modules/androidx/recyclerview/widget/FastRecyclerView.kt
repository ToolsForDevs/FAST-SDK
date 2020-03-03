package com.toolsfordevs.fast.modules.androidx.recyclerview.widget

import android.content.Context
import android.view.ContextThemeWrapper
import com.toolsfordevs.fast.modules.androidx.recyclerview.R

open class FastRecyclerView(context: Context) : RecyclerView(ContextThemeWrapper(context, R.style.Fast_ScrollbarRecyclerView))