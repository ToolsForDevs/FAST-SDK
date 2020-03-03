package com.toolsfordevs.fast.core.processor

import com.toolsfordevs.fast.core.annotation.FastIncludePlugin


class PluginProcessor : BaseProcessor<FastIncludePlugin>(FastIncludePlugin::class.java, "com.toolsfordevs.fast.core.FastPlugin")
