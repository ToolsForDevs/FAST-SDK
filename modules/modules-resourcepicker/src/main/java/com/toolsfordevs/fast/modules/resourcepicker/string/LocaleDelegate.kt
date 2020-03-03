package com.toolsfordevs.fast.modules.resourcepicker.string

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.View
import java.util.*


internal class LocaleDelegate(val button: View, callback:(locale:Locale) -> Unit)
{
    init
    {
        button.setOnClickListener {

            val locales = Locale.getAvailableLocales()

            val labels = locales.map { it.displayName }.toTypedArray()

            var currentLocale = Locale.getDefault()

            AlertDialog.Builder(button.context)
                .setSingleChoiceItems(labels, 0, object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int)
                    {
                        currentLocale = Locale.getAvailableLocales()[which]
                    }

                })
                .setPositiveButton("OK") { dialog, which ->
                    callback(currentLocale)
                }
                .setCancelable(true)
                .show()
        }
    }
}