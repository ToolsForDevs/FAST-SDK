package com.toolsfordevs.fast.plugins.overlay.ui

import android.view.View
import android.widget.PopupWindow


/**
 * Helper delegate to handle showing and dismissing a popup associated with a button
 *
 * Handles dismiss on reclick on button if popup is displayed
 *
 * @param singleInstance false to create a new instance of the popup on every click on the button, true to keep a single instance of the popup
 */
// ToDo Useful? Move to core ui ?
internal class ButtonPopupDelegate(button: View, private val createPopup:()->PopupWindow, private val singleInstance:Boolean = true)
{
    private var popup:PopupWindow? = null

    private var dismissTime = 0L

    init
    {
        button.setOnClickListener { button ->

            if (popup == null || !singleInstance)
            {
                if (popup != null)
                    popup!!.setOnDismissListener(null)

                popup = createPopup()
                popup!!.setOnDismissListener {
                    dismissTime = System.currentTimeMillis()

                }
            }

            if (popup!!.isShowing)
                popup!!.dismiss()
            // If we click a second time on the button to dismiss the popup
            // the popup will first dismiss (because we touched outside the popup
            // then the button click listener will fire
            // so we put a timing safeguard to prevent re displaying the popup when
            // we in fact want to hide the popup on the second button click
            else if (System.currentTimeMillis() - dismissTime > 100L)
                popup!!.showAsDropDown(button)
        }
    }
}