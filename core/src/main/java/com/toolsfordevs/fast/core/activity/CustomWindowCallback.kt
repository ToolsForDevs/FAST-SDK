package com.toolsfordevs.fast.core.activity

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.view.accessibility.AccessibilityEvent
import com.toolsfordevs.fast.core.AppInstance
import com.toolsfordevs.fast.core.FastManager
import com.toolsfordevs.fast.core.internal.FastPluginProvider


internal class CustomWindowCallback(val callback: Window.Callback) : Window.Callback
{
    override fun onActionModeFinished(mode: ActionMode?)
    {
        callback.onActionModeFinished(mode)
    }

    override fun onCreatePanelView(featureId: Int): View?
    {
        return callback.onCreatePanelView(featureId)
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean
    {
        return callback.dispatchTouchEvent(event)
    }

    override fun onCreatePanelMenu(featureId: Int, menu: Menu?): Boolean
    {
        return callback.onCreatePanelMenu(featureId, menu)
    }

    override fun onWindowStartingActionMode(callback: ActionMode.Callback?): ActionMode?
    {
        return this.callback.onWindowStartingActionMode(callback)
    }

    override fun onWindowStartingActionMode(callback: ActionMode.Callback?, type: Int): ActionMode?
    {
        if (Build.VERSION.SDK_INT >= 23)
            return this.callback.onWindowStartingActionMode(callback, type)
        else
            return this.callback.onWindowStartingActionMode(callback)
    }

    override fun onAttachedToWindow()
    {
        callback.onAttachedToWindow()
    }

    override fun dispatchGenericMotionEvent(event: MotionEvent?): Boolean
    {
        return callback.dispatchGenericMotionEvent(event)
    }

    override fun dispatchPopulateAccessibilityEvent(event: AccessibilityEvent?): Boolean
    {
        return callback.dispatchPopulateAccessibilityEvent(event)
    }

    override fun dispatchTrackballEvent(event: MotionEvent?): Boolean
    {
        return callback.dispatchTrackballEvent(event)
    }

    override fun dispatchKeyShortcutEvent(event: KeyEvent?): Boolean
    {
        return callback.dispatchKeyShortcutEvent(event)
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean
    {
        event?.let {

            if (event.action == KeyEvent.ACTION_DOWN)
            {
                if (event.keyCode == KeyEvent.KEYCODE_BACK && FastManager.onBackPressed()) return true
//                    .also { Log.i("AppInstance", "dispatchKeyEvent back button down") }

                //if (event.keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)
                //  return true
                //}

                //if (event.action == KeyEvent.ACTION_UP)
                //{
                if (event.keyCode in listOf(KeyEvent.KEYCODE_SHIFT_LEFT, KeyEvent.KEYCODE_SHIFT_RIGHT)) return false

                val now = System.currentTimeMillis()

                if (now - lastKeyStroke < 50) runnable?.cancel()

                lastKeyStroke = now
                runnable = KeyStrokeRunnable(event.keyCode, event.isShiftPressed)
                handler.postDelayed(runnable, 50)
            }
        }

        //Log.d("AppInstance", "dispatchKeyEvent let super handle the stuff")
        return callback.dispatchKeyEvent(event)
    }

    private var lastKeyStroke: Long = 0L
    private var runnable: KeyStrokeRunnable? = null
    private val handler = Handler(Looper.getMainLooper())

    private class KeyStrokeRunnable(val key: Int, val isUppercase: Boolean) : Runnable
    {
        private var cancelled = false

        override fun run()
        {
            if (!cancelled)
            {
//                Log.d("AppInstance", "run key stroke $key")

                // Space => close FAST
                if (key == KeyEvent.KEYCODE_SPACE)
                {
                    FastManager.closeAllViews()
                }
                // Backspace => Close current panel
                else if (key == KeyEvent.KEYCODE_DEL)
                {
                    FastManager.onBackPressed()
                }
                else if (isUppercase)
                {
                    // Prevent any other plugin that may have set "F" as hotkey to run
                    // instead of the plugin launcher
                    if (key == KeyEvent.KEYCODE_F)
                    {
                        FastManager.open()
                    }
                    else
                    {
//                        Log.d("AppInstance", "run uppercase key stroke $key")
                        for (plugin in FastPluginProvider.plugins)
                        {
//                            Log.d("AppInstance", "plugin key ${plugin.getHotkey()}")
                            if (plugin.getHotkey() == key)
                            {
                                // Fallback to app context if activity is not available
                                plugin.launch(AppInstance.activity ?: AppInstance.get())
                                break
                            }
                        }
                    }
                }
                else
                {
//                    Log.d("AppInstance", "run lowercase key stroke $key")
                    FastManager.dispatchKeyStrokeToCurrentView(key)
                }
            }
        }

        fun cancel()
        {
            cancelled = true
        }
    }

    override fun onMenuOpened(featureId: Int, menu: Menu?): Boolean
    {
        return callback.onMenuOpened(featureId, menu)
    }

    override fun onPanelClosed(featureId: Int, menu: Menu?)
    {
        callback.onPanelClosed(featureId, menu)
    }

    override fun onMenuItemSelected(featureId: Int, item: MenuItem?): Boolean
    {
        return callback.onMenuItemSelected(featureId, item)
    }

    override fun onDetachedFromWindow()
    {
        callback.onDetachedFromWindow()
    }

    override fun onPreparePanel(featureId: Int, view: View?, menu: Menu?): Boolean
    {
        return callback.onPreparePanel(featureId, view, menu)
    }

    override fun onWindowAttributesChanged(attrs: WindowManager.LayoutParams?)
    {
        callback.onWindowAttributesChanged(attrs)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean)
    {
        callback.onWindowFocusChanged(hasFocus)
    }

    override fun onContentChanged()
    {
        callback.onContentChanged()
    }

    override fun onSearchRequested(): Boolean
    {
        return callback.onSearchRequested()
    }

    override fun onSearchRequested(searchEvent: SearchEvent?): Boolean
    {
        if (Build.VERSION.SDK_INT >= 23) return callback.onSearchRequested(searchEvent)

        return callback.onSearchRequested()
    }

    override fun onActionModeStarted(mode: ActionMode?)
    {
        callback.onActionModeStarted(mode)
    }

}