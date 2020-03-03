package com.toolsfordevs.fast.core.activity

import java.util.*


internal class OnBackPressedDispatcher
{
    private val callbacks = ArrayDeque<OnBackPressedCallback>()

    fun addCallback(callback: OnBackPressedCallback)
    {
        addCancellableCallback(callback)
    }

    /**
     * Convenience method.
     * You should use callback.remove() instead as this is what this method does
     */
    fun removeCallback(callback: OnBackPressedCallback)
    {
        callback.remove()
    }

    /**
     * Internal implementation of [.addCallback] that gives
     * access to the [Cancellable] that specifically removes this callback from
     * the dispatcher without relying on [OnBackPressedCallback.remove] which
     * is what external developers should be using.
     *
     * @param onBackPressedCallback The callback to add
     * @return a [Cancellable] which can be used to [cancel][Cancellable.cancel]
     * the callback and remove it from the set of OnBackPressedCallbacks.
     */
    private fun addCancellableCallback(onBackPressedCallback: OnBackPressedCallback): Cancellable
    {
        callbacks.add(onBackPressedCallback)
        val cancellable = OnBackPressedCancellable(onBackPressedCallback)
        onBackPressedCallback.addCancellable(cancellable)
        return cancellable
    }

    /**
     * Trigger a call to the currently added {@link OnBackPressedCallback callbacks} in reverse
     * order in which they were added. Only if the most recently added callback is not
     * {@link OnBackPressedCallback#isEnabled() enabled}
     * will any previously added callback be called.
     * <p>
     * It is strongly recommended to call {@link #hasEnabledCallbacks()} prior to calling
     * this method to determine if there are any enabled callbacks that will be triggered
     * by this method as calling this method.
     */
    internal fun onBackPressed(): Boolean
    {
        // Log.d("AppInstance dispatcher", "onBackpressed")
        val iterator = callbacks.descendingIterator()
        while (iterator.hasNext())
        {
            val callback = iterator.next()
            if (callback.enabled)
            {
                callback.handleOnBackPressed()
                // Log.d("AppInstance dispatcher", "onBackpressed return true")
                return true
            }
        }

        // Log.d("AppInstance dispatcher", "onBackpressed return false")
        return false
    }

    private inner class OnBackPressedCancellable internal constructor(private val mOnBackPressedCallback: OnBackPressedCallback) :
        Cancellable
    {
        override fun cancel()
        {
            callbacks.remove(mOnBackPressedCallback)
            mOnBackPressedCallback.removeCancellable(this)
        }
    }

}