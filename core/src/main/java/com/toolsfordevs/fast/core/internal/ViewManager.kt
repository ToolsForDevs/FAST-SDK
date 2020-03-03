package com.toolsfordevs.fast.core.internal

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import com.toolsfordevs.fast.core.FastManager
import com.toolsfordevs.fast.core.R
import com.toolsfordevs.fast.core.activity.OnBackPressedCallback
import com.toolsfordevs.fast.core.ext.dp
import com.toolsfordevs.fast.core.ext.frameLayoutParamsMM
import com.toolsfordevs.fast.core.ext.layoutParamsMM
import com.toolsfordevs.fast.core.ext.layoutParamsVV
import com.toolsfordevs.fast.core.widget.FastPanel
import java.lang.ref.WeakReference
import java.util.*


internal class ViewManager(activity: Activity)
{
    private val ref = WeakReference(activity)

    private val activity: Activity?
        get() = ref.get()

    private val defaultEnterAnimation: ValueAnimator = ObjectAnimator.ofArgb(0)

    private val stack = Stack<View>()

    private val backPressedCallback = object : OnBackPressedCallback(false)
    {
        override fun handleOnBackPressed()
        {
            if (!isAnimationRunning)
                pop()
        }
    }

    private var isAnimationRunning = false

    private val listener = object : View.OnAttachStateChangeListener
    {
        override fun onViewDetachedFromWindow(v: View?)
        {
            v?.let {
                it.removeOnAttachStateChangeListener(this)

                if (stack.peek() == it)
                {
                    stack.pop()

                    if (stack.size > 0)
                        add(stack.peek())
                }
            }
        }

        override fun onViewAttachedToWindow(v: View?)
        {
            // Do nothing
        }
    }

    internal fun setup()
    {
        FastManager.addCallback(backPressedCallback)
    }

    //#########################################################################
    //
    // View management
    //
    //#########################################################################F

    fun addView(view: View)
    {
        if (stack.size > 0)
            replace(stack.peek(), stack.push(view))
        else
            add(stack.push(view))
    }

    private fun add(view: View, callback: (() -> Unit)? = null)
    {
        backPressedCallback.enabled = true

        view.addOnAttachStateChangeListener(listener)

        view.visibility = View.INVISIBLE

        val container = getViewContainer()

        container.post {
            if (view.parent == null)
            {
                container.addView(view)
            }

            container.post {
                var animator: ValueAnimator? = null

                if (view is FastPanel)
                {
                    animator = view.getEnterTransition() ?: defaultEnterAnimation

                    if (view.mode == FastPanel.MODE_BELOW) animator.addUpdateListener { wholeContentView.translationY = -it.animatedFraction * view.height }

                    view.setOnModeChangeCallback { mode ->
                        val startValue = if (mode == FastPanel.MODE_ON_TOP_OF) wholeContentView.translationY else 0f
                        val endValue = if (mode == FastPanel.MODE_ON_TOP_OF) 0f else -view.height.toFloat()

                        ObjectAnimator.ofFloat(wholeContentView, "translationY", startValue, endValue).apply {
                            interpolator = DecelerateInterpolator(2f)
                        }.start()
                    }
                }

                if (animator == null) animator = defaultEnterAnimation

                animator.addListener(object : AnimatorListenerAdapter()
                                     {

                                         override fun onAnimationEnd(animation: Animator?)
                                         {
                                             isAnimationRunning = false
                                             animation?.removeAllListeners()
                                             callback?.invoke()
                                         }
                                     })

                isAnimationRunning = true
                animator.start()
                view.visibility = View.VISIBLE

                focusView.rootView.findFocus()?.clearFocus()
                focusView.requestFocus()

                // I think it might produce an exception in some cases, so just to be sure...
                try
                {
                    val imm = focusView.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(focusView.rootView.windowToken, 0)
                }
                catch (e: Exception)
                {

                }
            }
        }
    }

    private fun remove(view: View, callback: (() -> Unit)? = null)
    {
        view.removeOnAttachStateChangeListener(listener)

        view.post {
            var animator: ValueAnimator? = null

            if (view is FastPanel)
            {
                animator = view.getExitTransition() ?: getDefaultExitAnimation(view)

                if (view.mode == FastPanel.MODE_BELOW) animator.addUpdateListener {
                    wholeContentView.translationY = -(view.height - it.animatedFraction * view.height)
                }

                view.setOnModeChangeCallback(null)
            }

            if (animator == null) animator = getDefaultExitAnimation(view)

            animator.addListener(object : AnimatorListenerAdapter()
                                 {
                                     override fun onAnimationEnd(animation: Animator?)
                                     {
                                         isAnimationRunning = false
                                         animation?.removeAllListeners()

                                         if (view.parent != null)
                                         {
                                            (view.parent as ViewGroup).removeView(view)
                                         }

                                         callback?.invoke()
                                     }
                                 })

            isAnimationRunning = true
            animator.start()

            if (stack.isEmpty())
                focusView.clearFocus()
        }
    }

    private fun replace(exitingView: View, enteringView: View)
    {
        if (exitingView.parent != null)
        {
            val parent = exitingView.parent as ViewGroup
            // If calls are too fast, remove animation do not have time to finish
            // meaning that top view in stack has not yet been added
            // thus we add directly the entering view
            // while the previous entering view will be added once the remove animation is over
            remove(exitingView) {
                parent.post {
                    // Check that we still needs to display the enteringView
                    // E.g don't display it if user is typing keyboard shortcuts
                    // like crazy to open or close panels quickly
                    if (enteringView == stack.peek())
                        add(enteringView)
                }
            }
        }
        else
        {
            add(enteringView)
        }
    }

    private fun pop()
    {
        if (stack.size > 1)
            replace(stack.pop(), stack.peek())
        else
            remove(stack.pop())

        backPressedCallback.enabled = stack.isNotEmpty()
    }

    internal fun closeAll()
    {
        if (stack.isNotEmpty())
        {
            val item = stack.pop()
            stack.clear()

            if (item.parent != null)
                remove(item)
        }
    }

    private fun getDefaultExitAnimation(view: View): ValueAnimator
    {
        return ObjectAnimator.ofFloat(view, "translationY", view.translationY, view.height.toFloat()).apply {
            interpolator = DecelerateInterpolator(2f)
        }
    }

    internal fun onKeyStroke(key: Int)
    {
        if (stack.isNotEmpty())
        {
            val view = stack.lastElement()

            if (view is FastPanel) view.onKeyStroke(key)
        }
    }


    /*#########################################################################
    *
    * Container management
    *
    * We use PhoneWindow as a reference.
    * The activity root content is in the android.R.id.content view in the the phone
    * window's decor view
    *
    * To display our tools, we use two additional layers on top of the activity's content
    *
    * androidx.appcompat.app.AppCompatActivity goes like this
    * DecorView (from PhoneWindow, the window attached to the activity)
    *   + LinearLayout
    *      + ViewStub for ActionBarMode (not used anymore nowadays...)
    *      + FrameLayout
    *        + ActionBarOverlayLayout
    *            + android.R.id.content : child 0, the activity content view
    *   + Overlay container : a FrameLayout that will contains our overlay views
    *   + View Container : a FrameLayout that will contains our plugin views.
    *   + android.R.id.navigationBarBackground : the bottom navbar background
    *   + android.R.id.statusBarBackground : the top status bar background
    *
    * Basic android.app.Activity goes like this
    * DecorView (from PhoneWindow, the window attached to the activity)
    *   + LinearLayout
    *      + ViewStub for ActionBarMode (not used anymore nowadays...)
    *      + android.R.id.content : child 0, the activity content view
    *   + Overlay container : a FrameLayout that will contains our overlay views
    *   + View Container : a FrameLayout that will contains our plugin views.
    *   + android.R.id.navigationBarBackground : the bottom navbar background
    *   + android.R.id.statusBarBackground : the top status bar background
    *
    #########################################################################*/

    private val decorView: ViewGroup
        get() = activity?.window?.decorView as ViewGroup

    private val wholeContentView: ViewGroup
        get() = decorView.getChildAt(0) as ViewGroup

    private val contentView: ViewGroup
        get() = decorView.findViewById(android.R.id.content) as ViewGroup

    private val focusView: View
        get() = decorView.findViewById<ViewGroup>(fastContainerId).findViewById(focusViewId)

    private val fastContainerId = R.id.fast_core_fast_container
    private val viewContainerId = R.id.fast_core_view_container
    private val overlayContainerId = R.id.fast_core_overlay_container
    private val focusViewId = R.id.fast_core_focus_view


    private fun createContainersIfNecessary()
    {
        var container = decorView.findViewById<ViewGroup>(fastContainerId)

        if (container == null)
        {
            container = FrameLayout(activity!!)
            container.setId(fastContainerId)


            val params = frameLayoutParamsMM()

            //            container.setPadding(0, statusBar?.height ?: 0, 0, navigationBar?.height ?: 0)

            val overlayContainer = FrameLayout(activity!!)
            overlayContainer.id = overlayContainerId
            container.addView(overlayContainer, params)

            val viewContainer = FrameLayout(activity!!)
            viewContainer.id = viewContainerId
            container.addView(viewContainer, params)

            val focusView = View(activity).apply {
                id = focusViewId
                isFocusable = true
                isFocusableInTouchMode = true
            }
            container.addView(focusView, layoutParamsVV(1, 1)) // has to have a size, otherwise can't take focus

            decorView.addView(container, 1, layoutParamsMM())

            decorView.post {
                val statusBar = decorView.findViewById<View>(android.R.id.statusBarBackground)
                val navigationBar = decorView.findViewById<View?>(android.R.id.navigationBarBackground)
                
                val statusBarHeight:Int = if (statusBar != null)
                {
                    statusBar.height
                }
                else
                {
                    val resourceId: Int = container.context.resources.getIdentifier("status_bar_height", "dimen", "android")
                    if (resourceId > 0)
                        container.context.resources.getDimensionPixelSize(resourceId)
                    else
                        24.dp // Default value
                }

                params.topMargin = statusBarHeight
                params.bottomMargin = navigationBar?.height ?: 0

                //overlayContainer.layoutParams = params
                viewContainer.layoutParams = params
            }
        }
    }

    private fun getOverlayContainer(): ViewGroup
    {
        createContainersIfNecessary()
        return decorView.findViewById(overlayContainerId)
    }

    private fun getViewContainer(): ViewGroup
    {
        createContainersIfNecessary()
        return decorView.findViewById(viewContainerId)
    }

    fun offsetActivityContent(offset: Float, animate: Boolean = true)
    {
        if (animate) contentView.animate().translationYBy(offset).start()
        else contentView.translationY = offset
    }

    fun showOverlay(overlay: View)
    {
        // Post to make sure view hierarchy is set up
        decorView.post {
            getOverlayContainer().addView(overlay)
        }
    }

    fun removeOverlay(overlay: View)
    {
        getOverlayContainer().removeView(overlay)
    }
}