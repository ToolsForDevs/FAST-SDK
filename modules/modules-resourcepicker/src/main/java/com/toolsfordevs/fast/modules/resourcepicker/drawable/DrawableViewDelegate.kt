package com.toolsfordevs.fast.modules.resourcepicker.drawable

import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.RecyclerView
import com.toolsfordevs.fast.modules.recyclerview.Renderer
import com.toolsfordevs.fast.modules.resources.DrawableResource
import com.toolsfordevs.fast.core.util.ResUtils
import com.toolsfordevs.fast.modules.resourcepicker.FavoriteManager
import com.toolsfordevs.fast.modules.resourcepicker.R
import com.toolsfordevs.fast.modules.resourcepicker.SwitchableViewDelegate
import com.toolsfordevs.fast.core.ui.AspectRatio
import com.toolsfordevs.fast.core.ui.ext.hide
import com.toolsfordevs.fast.core.ui.ext.setTint
import com.toolsfordevs.fast.core.ui.ext.show
import com.toolsfordevs.fast.core.ui.image.SuperImageView
import kotlin.reflect.KMutableProperty0


internal class DrawableViewDelegate(button: ImageButton,
                           recyclerView: RecyclerView,
                           selectionPref: KMutableProperty0<Boolean>,
                           private val callback: (drawableRes: DrawableResource) -> Unit) :
        SwitchableViewDelegate(button, recyclerView, selectionPref, DrawableResource::class)
{
    override fun buildListRenderer(): (parent: ViewGroup) -> Renderer<*>
    {
        return ::DrawableListRenderer
    }

    override fun buildGridRenderer(): (parent: ViewGroup) -> Renderer<*>
    {
        return ::DrawableGridRenderer
    }

    private inner class DrawableGridRenderer(parent: ViewGroup) : Renderer<DrawableResource>(parent, R.layout.fast_resourcepicker_item_drawable_grid),
                                                                  View.OnClickListener
    {
        private val delegate = RendererDelegate(itemView)

        init
        {
            itemView.setOnClickListener(this)
        }

        override fun bind(data: DrawableResource, position: Int)
        {
            delegate.bind(data)
        }

        override fun onClick(v: View?)
        {
            val drawableRes: DrawableResource = getItem(adapterPosition) as DrawableResource
            callback(drawableRes)
        }
    }

    private inner class DrawableListRenderer(parent: ViewGroup) : Renderer<DrawableResource>(parent, R.layout.fast_resourcepicker_item_drawable_list),
                                                                  View.OnClickListener
    {
        private val delegate = RendererDelegate(itemView)

        init
        {
            itemView.setOnClickListener(this)
        }

        override fun bind(data: DrawableResource, position: Int)
        {
            delegate.bind(data)
        }

        override fun onClick(v: View?)
        {
            val drawableRes: DrawableResource = getItem(adapterPosition) as DrawableResource
            callback(drawableRes)
        }
    }

    inner class RendererDelegate(val itemView: View)
    {
        private val name: TextView = itemView.findViewById(R.id.name)
        private val image: SuperImageView = itemView.findViewById(R.id.image_view)
        private val stateListIcon: ImageView = itemView.findViewById(R.id.icon_state_list)
        private val icon: ImageButton = itemView.findViewById(R.id.icon)

        init
        {
            image.ratio = AspectRatio.SQUARE
        }

        fun bind(data: DrawableResource)
        {
            name.text = data.name
            name.setTextColor(itemTextColor)
            itemView.setBackgroundColor(itemBackgroundColor)

            icon.isSelected = FavoriteManager.isFavorite(data)

            icon.setOnClickListener {
                icon.isSelected = !icon.isSelected
                FavoriteManager.toggleResource(data)
            }

            stateListIcon.hide()

            try
            {
                val drawable = ResUtils.getDrawable(itemView.context, data.resId)
                image.setImageDrawable(drawable)
                itemDrawableState?.let { image.setImageState(it, false) }

                drawable?.let {
                    if (it.isStateful)
                    {
                        stateListIcon.setTint(itemTextColor)
                        stateListIcon.show()
                    }
                }
            }
            catch (e: Exception)
            {
                //Log.d("Hey", "couldn't find drawable ${data.name}, ${e.message}")
            }
        }
    }
}