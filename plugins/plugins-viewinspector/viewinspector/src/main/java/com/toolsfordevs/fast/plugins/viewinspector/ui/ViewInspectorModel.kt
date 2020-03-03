package com.toolsfordevs.fast.plugins.viewinspector.ui

import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.toolsfordevs.fast.modules.recyclerview.RendererAdapter
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewInspectorModule
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewProperty
import com.toolsfordevs.fast.plugins.viewinspector.ext.ViewPropertyHolder
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.*
import com.toolsfordevs.fast.plugins.viewinspector.ui.renderer.*


internal object ViewInspectorModel
{
    val holders:List<ViewPropertyHolder> by lazy { ViewInspectorModule.getHolders() }

    //ToDo cache properties -> Map<KClass<T:View>, List<ViewProperty>>

    fun checkIds()
    {
        for (holder in holders)
        {
            holder.getProperties().values
            .flatMap { it }
            .filter { !it.hasId }
            .forEach { Log.d("ViewInspector", "property $it has no id in $holder") }
        }
               /*
            .flatMap { it.getProperties().values }
            .flatMap { it }
            .filter { !it.hasId }
            .forEach { Log.d("ViewInspector", "property $it has no id") }*/
    }

    fun checkNames()
    {
        for (holder in holders)
        {
            holder.getProperties().values
                .flatMap { it }
                .filter { !it.hasName }
                .forEach { Log.d("ViewInspector", "property $it has no name in $holder") }
        }
    }

    fun getLayoutProperties(view: View): List<ViewProperty<*, *>>
    {
        return holders
            .filter { it.viewClass.isInstance(view.parent) }
            .flatMap { it.getProperties().get(PropertyCategory.LAYOUT_PARAMS) ?: listOf() }
            .plus(
                    holders
                .filter { it.viewClass.isInstance(view) }
                .flatMap { it.getProperties().get(PropertyCategory.LAYOUT) ?: listOf() })
    }

    fun getProperties(view: View): List<ViewPropertyHolder>
    {
        return holders
            .filter { it.viewClass.isInstance(view) }
    }

    fun getFavoriteProperties(view: View): List<ViewProperty<*, *>>
    {
        return holders
            .flatMap { it.getProperties().values }
            .flatMap { it }
            .filter { FavoriteManager.isFavorite(view, it.id) }
    }

    fun setupRenderersForAdapter(adapter: RendererAdapter)
    {
        adapter.addRenderer(DimensionRangeProperty::class) { parent: ViewGroup -> ViewPropertyRendererWrapper(parent, DimensionRangeRenderer(parent)) }
        adapter.addRenderer(IntRangeProperty::class) { parent: ViewGroup -> ViewPropertyRendererWrapper(parent, IntRangeRenderer(parent)) }
        adapter.addRenderer(FloatRangeProperty::class) { parent: ViewGroup -> ViewPropertyRendererWrapper(parent, FloatRangeRenderer(parent)) }
        adapter.addRenderer(LongRangeProperty::class) { parent: ViewGroup -> ViewPropertyRendererWrapper(parent, LongRangeRenderer(parent)) }
        adapter.addRenderer(StringProperty::class) { parent: ViewGroup -> ViewPropertyRendererWrapper(parent, StringRenderer(parent)) }
        adapter.addRenderer(BooleanProperty::class) { parent: ViewGroup -> ViewPropertyRendererWrapper(parent, BooleanRenderer(parent)) }
        adapter.addRenderer(ColorProperty::class) { parent: ViewGroup -> ViewPropertyRendererWrapper(parent, ColorRenderer(parent)) }
        adapter.addRenderer(ColorStateListProperty::class) { parent: ViewGroup -> ViewPropertyRendererWrapper(parent, ColorStateListRenderer(parent)) }
        adapter.addRenderer(ColorDrawableProperty::class) { parent: ViewGroup -> ViewPropertyRendererWrapper(parent, ColorDrawableRenderer(parent)) }
        adapter.addRenderer(DrawableProperty::class) { parent: ViewGroup -> ViewPropertyRendererWrapper(parent, DrawableRenderer(parent)) }
        adapter.addRenderer(SingleChoiceProperty::class) { parent: ViewGroup -> ViewPropertyRendererWrapper(parent, SingleChoiceRenderer(parent)) }
        adapter.addRenderer(MultiChoiceProperty::class) { parent: ViewGroup -> ViewPropertyRendererWrapper(parent, MultiChoiceRenderer(parent)) }
        adapter.addRenderer(LayoutParamWHProperty::class) { parent: ViewGroup -> ViewPropertyRendererWrapper(parent, LayoutParamsRenderer(parent)) }
    }
}