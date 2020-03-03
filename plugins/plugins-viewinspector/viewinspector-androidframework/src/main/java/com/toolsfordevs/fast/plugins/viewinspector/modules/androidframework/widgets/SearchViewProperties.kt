package com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.widgets

import android.widget.SearchView
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.BooleanProperty
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.DimensionIntRangeProperty
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.PropertyCategory
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.StringProperty


internal class SearchViewProperties : ViewPropertyHolder(SearchView::class.java)
{
    init
    {
        add(PropertyCategory.COMMON, BooleanProperty(SearchView::isIconified, SearchView::setIconified))
        add(PropertyCategory.COMMON, BooleanProperty(SearchView::isIconfiedByDefault, SearchView::setIconifiedByDefault))
        add(PropertyCategory.COMMON, TextViewProperties.ImeOptionsProperty(SearchView::getImeOptions, SearchView::setImeOptions))
        add(PropertyCategory.COMMON, TextViewProperties.InputTypeProperty())
        add(PropertyCategory.COMMON, DimensionIntRangeProperty(SearchView::getMaxWidth, SearchView::setMaxWidth, max = screenWidth))
        add(PropertyCategory.COMMON, StringProperty(SearchView::getQueryHint, SearchView::setQueryHint))
        add(PropertyCategory.COMMON, BooleanProperty(SearchView::isQueryRefinementEnabled, SearchView::setQueryRefinementEnabled))
        add(PropertyCategory.COMMON, BooleanProperty(SearchView::isSubmitButtonEnabled, SearchView::setSubmitButtonEnabled))

    }
}