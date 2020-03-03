package com.toolsfordevs.fast.core.processor

import com.toolsfordevs.fast.core.annotation.FastIncludeModule


class ModuleProcessor : BaseProcessor<FastIncludeModule>(FastIncludeModule::class.java, "com.toolsfordevs.fast.core.FastModule")
