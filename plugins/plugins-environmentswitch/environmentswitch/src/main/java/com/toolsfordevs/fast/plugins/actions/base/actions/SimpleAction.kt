package com.toolsfordevs.fast.plugins.actions.base.actions

class SimpleAction(name: String, callback: () -> Unit) : Action<Any>(name, { callback() })