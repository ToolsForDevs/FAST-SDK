package com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.widgets

import android.view.View
import android.widget.RelativeLayout
import com.toolsfordevs.fast.core.util.AndroidVersion
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.BooleanProperty
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.PropertyCategory
import com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.properties.GravityProperty
import com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.properties.IdProperty

internal class RelativeLayoutProperties() : ViewPropertyHolder(RelativeLayout::class.java)
{
    init
    {
        add(PropertyCategory.COMMON, GravityProperty(RelativeLayout::getGravity, RelativeLayout::setGravity))


        add(PropertyCategory.LAYOUT_PARAMS, AlignWithParentIfMissingProperty())
        add(PropertyCategory.LAYOUT_PARAMS, RelativeLayoutParamsProperty(RelativeLayout.ABOVE, "above"))
        add(PropertyCategory.LAYOUT_PARAMS, RelativeLayoutParamsProperty(RelativeLayout.ALIGN_BASELINE, "alignBaseline"))
        add(PropertyCategory.LAYOUT_PARAMS, RelativeLayoutParamsProperty(RelativeLayout.ALIGN_BOTTOM, "alignBottom"))
        add(PropertyCategory.LAYOUT_PARAMS, RelativeLayoutParamsProperty(RelativeLayout.ALIGN_LEFT, "alignLeft"))
        add(PropertyCategory.LAYOUT_PARAMS, BooleanRelativeLayoutParamsProperty(RelativeLayout.ALIGN_PARENT_BOTTOM, "alignParentBottom"))
        add(PropertyCategory.LAYOUT_PARAMS, BooleanRelativeLayoutParamsProperty(RelativeLayout.ALIGN_PARENT_LEFT, "alignParentLeft"))
        add(PropertyCategory.LAYOUT_PARAMS, BooleanRelativeLayoutParamsProperty(RelativeLayout.ALIGN_PARENT_RIGHT, "alignParentRight"))
        add(PropertyCategory.LAYOUT_PARAMS, BooleanRelativeLayoutParamsProperty(RelativeLayout.ALIGN_PARENT_TOP, "alignParentTop"))
        add(PropertyCategory.LAYOUT_PARAMS, RelativeLayoutParamsProperty(RelativeLayout.ALIGN_RIGHT, "alignRight"))
        add(PropertyCategory.LAYOUT_PARAMS, RelativeLayoutParamsProperty(RelativeLayout.ALIGN_TOP, "alignTop"))
        add(PropertyCategory.LAYOUT_PARAMS, RelativeLayoutParamsProperty(RelativeLayout.BELOW, "below"))
        add(PropertyCategory.LAYOUT_PARAMS, BooleanRelativeLayoutParamsProperty(RelativeLayout.CENTER_HORIZONTAL, "centerHorizontal"))
        add(PropertyCategory.LAYOUT_PARAMS, BooleanRelativeLayoutParamsProperty(RelativeLayout.CENTER_IN_PARENT, "centerInParent"))
        add(PropertyCategory.LAYOUT_PARAMS, BooleanRelativeLayoutParamsProperty(RelativeLayout.CENTER_VERTICAL, "centerVertical"))
        add(PropertyCategory.LAYOUT_PARAMS, RelativeLayoutParamsProperty(RelativeLayout.LEFT_OF, "leftOf"))
        add(PropertyCategory.LAYOUT_PARAMS, RelativeLayoutParamsProperty(RelativeLayout.RIGHT_OF, "rightOf"))
    }

    private class AlignWithParentIfMissingProperty() : BooleanProperty<View>()
    {
        init
        {
            name = "alignWithParent"
            subsituteId = "RelativeLayout_alignWithParent"
        }
        override fun getValue(): Boolean
        {
            val params = view.layoutParams as RelativeLayout.LayoutParams
            return params.alignWithParent
        }

        override fun setValue(value: Boolean?): Boolean
        {
            val params = view.layoutParams as RelativeLayout.LayoutParams
            params.alignWithParent = value!!
            view.layoutParams = params

            return true
        }
    }

    class RelativeLayoutParamsProperty(val verb:Int, name:String) : IdProperty<View>()
    {
        init
        {
            this.name = name
            this.subsituteId = "RelativeLayout_$name"
        }

        override fun getValue(): Int
        {
            return getRule(verb, view)
        }

        override fun setValue(value: Int?): Boolean
        {
            setRule(verb, value!!, view)
            return true
        }
    }

    class BooleanRelativeLayoutParamsProperty(val verb:Int, name:String) : BooleanProperty<View>()
    {
        init
        {
            this.name = name
            this.subsituteId = "RelativeLayout_$name"
        }

        // true : RelativeLayout.TRUE => -1
        // false : 0
        override fun getValue(): Boolean
        {
            return getRule(verb, view) == RelativeLayout.TRUE
        }

        override fun setValue(value: Boolean?): Boolean
        {
            setRule(verb, if (value!!) RelativeLayout.TRUE else 0, view)
            return true
        }
    }

    companion object
    {
        fun getRule(verb:Int, view:View):Int
        {
            val params = view.layoutParams as RelativeLayout.LayoutParams

            if (AndroidVersion.isMinMarshmallow())
                return params.getRule(verb)

            return params.rules[verb]
        }

        fun setRule(verb:Int, value:Int, view:View)
        {
            val params = view.layoutParams as RelativeLayout.LayoutParams
            params.addRule(verb, value)
            view.layoutParams = params
        }
    }
}