package com.toolsfordevs.fast.plugins.actions.ui

import com.toolsfordevs.fast.plugins.actions.base.*
import com.toolsfordevs.fast.plugins.actions.ui.delegates.*


internal object DelegateManager
{
    fun <T : Any> getDelegateForAction(action: Action<T>): ActionDelegate<T, Action<T>>
    {
        return when (action)
        {
            is BooleanAction        -> BooleanDelegate()
            is ColorAction          -> ColorDelegate()
            is IntAction            -> IntDelegate()
            is FloatAction          -> FloatDelegate()
            is LongAction           -> LongDelegate()
            is StringAction         -> StringDelegate()
            is SingleChoiceAction   -> SingleChoiceDelegate<T>()
            is MultiChoiceAction<*> -> MultiChoiceDelegate<T>()
            is ComboAction          -> MultiDelegate()
            else                    -> throw Exception("Allo")
        } as ActionDelegate<T, Action<T>>
    }
}