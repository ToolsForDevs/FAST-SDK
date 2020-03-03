package com.toolsfordevs.fast.plugins.logger.formatter

import com.toolsfordevs.fast.modules.formatters.FastFormatter
import java.lang.reflect.Modifier


internal class IntrospectionFormatter : FastFormatter
{
    override val name: String = "Introspect"

    override fun canFormatData(data: Any?): Boolean
    {
        return true // This formatter can format anything
    }

    override fun formatToString(data: Any?): String
    {
        if (data == null)
            return "null"

        val clazz = data::class.java

        var s = "Object ${clazz.name} ${System.identityHashCode(data)}"

        if (clazz.declaredFields.isNotEmpty())
            s += "\n\nPrivate properties\n"

        for (declaredField in clazz.declaredFields)
        {
            if (Modifier.isStatic(declaredField.modifiers))
                continue

            // Access private fields !!
            declaredField.isAccessible = true

            s += "\n${declaredField.name} : ${declaredField.type.name} = ${declaredField.get(data)}"
        }

        if (clazz.fields.isNotEmpty())
            s += "\n\nPublic properties\n"

        for (declaredField in clazz.fields)
        {
            if (Modifier.isStatic(declaredField.modifiers))
                continue

            s += "\n${declaredField.name} : ${declaredField.type.name} = ${declaredField.get(data)}"
        }

        return s
    }
}