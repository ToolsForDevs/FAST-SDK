package com.toolsfordevs.fast.core.activity


/*
 * Copyright 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Class for handling {@link OnBackPressedDispatcher#onBackPressed()} callbacks without
 * strongly coupling that implementation to a subclass of {@link ComponentActivity}.
 * <p>
 * This class maintains its own {@link #isEnabled() enabled state}. Only when this callback
 * is enabled will it receive callbacks to {@link #handleOnBackPressed()}.
 * <p>
 * Note that the enabled state is an additional layer on top of the
 * {@link androidx.lifecycle.LifecycleOwner} passed to
 * {@link OnBackPressedDispatcher#addCallback(LifecycleOwner, OnBackPressedCallback)}
 * which controls when the callback is added and removed to the dispatcher.
 * <p>
 * By calling {@link #remove()}, this callback will be removed from any
 * {@link OnBackPressedDispatcher} it has been added to. It is strongly recommended
 * to instead disable this callback to handle temporary changes in state.
 *
 * @see ComponentActivity#getOnBackPressedDispatcher()
 */
abstract class OnBackPressedCallback(var enabled: Boolean = true)
{
    private val mCancellables = CopyOnWriteArrayList<Cancellable>();

    /**
     * Removes this callback from any {@link OnBackPressedDispatcher} it is currently
     * added to.
     */
    fun remove()
    {
        for (cancellable in mCancellables)
        {
            cancellable.cancel();
        }
    }

    /**
     * Callback for handling the {@link OnBackPressedDispatcher#onBackPressed()} event.
     */
    abstract fun handleOnBackPressed()

    internal fun addCancellable(cancellable: Cancellable)
    {
        mCancellables.add(cancellable);
    }

    internal fun removeCancellable(cancellable: Cancellable)
    {
        mCancellables.remove(cancellable);
    }
}
