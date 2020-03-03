package com.toolsfordevs.fast.plugins.networkexplorer.core

import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.core.data.BaseRepository
import com.toolsfordevs.fast.plugins.networkexplorer.core.model.Request

@Keep
object RequestRepository : BaseRepository<Request>()