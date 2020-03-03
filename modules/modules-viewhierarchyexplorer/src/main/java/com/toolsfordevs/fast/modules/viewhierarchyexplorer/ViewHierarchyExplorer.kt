package com.toolsfordevs.fast.modules.viewhierarchyexplorer

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import com.toolsfordevs.fast.core.annotation.Keep

@Keep
class ViewHierarchyExplorer()
{
    private val views = arrayListOf<View>()

    fun getViewAt(container:View, x:Int, y:Int):View?
    {
        views.clear()

        traverse(container)
        return getHitView(x, y)
    }

    // Recursive method
    private fun traverse(view: View)
    {
        // Avoid our views that are on top of the activity content view
        if (view.id == R.id.fast_core_fast_container)
            return

        views.add(view)

        if (view is ViewGroup)
        {
            for (i in 0 until view.childCount)
            {
                val child = view.getChildAt(i)
                traverse(child)
            }
        }
    }

    private fun getHitView(x:Int, y:Int):View?
    {
        for (view in views.reversed())
        {
            val coords = IntArray(2)

            view.getLocationOnScreen(coords)

            val rect = Rect(coords[0], coords[1], coords[0] + view.width, coords[1] + view.height)

            if (rect.contains(x, y))
                return view
        }

        return null
    }

    fun getHierarchy(container: View):List<View>
    {
        views.clear()
        traverse(container)
        return views
    }
}