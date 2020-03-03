package com.toolsfordevs.fast.modules.resourcepicker.color

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.toolsfordevs.fast.core.AppInstance
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.RecyclerView
import com.toolsfordevs.fast.modules.recyclerview.Renderer
import com.toolsfordevs.fast.modules.resources.ColorResource
import com.toolsfordevs.fast.core.util.ResUtils
import com.toolsfordevs.fast.modules.resourcepicker.FavoriteManager
import com.toolsfordevs.fast.modules.resourcepicker.R
import com.toolsfordevs.fast.modules.resourcepicker.SwitchableViewDelegate
import com.toolsfordevs.fast.core.ui.AspectRatio
import com.toolsfordevs.fast.core.ui.ext.hide
import com.toolsfordevs.fast.core.ui.ext.setTint
import com.toolsfordevs.fast.core.ui.ext.show
import com.toolsfordevs.fast.core.ui.image.MaskOptions
import com.toolsfordevs.fast.core.ui.image.PathHelper
import com.toolsfordevs.fast.core.ui.image.SuperImageView
import kotlin.reflect.KMutableProperty0


internal class ColorViewDelegate(button: ImageButton,
                        recyclerView: RecyclerView,
                        selectionPref: KMutableProperty0<Boolean>,
                        private val callback: (color: ColorResource) -> Unit) :
        SwitchableViewDelegate(button, recyclerView, selectionPref, ColorResource::class)
{
    override fun buildGridRenderer(): (parent: ViewGroup) -> Renderer<*>
    {
        return ::ColorGridRenderer
    }

    override fun buildListRenderer(): (parent: ViewGroup) -> Renderer<*>
    {
        return ::ColorListRenderer
    }

    private inner class ColorGridRenderer(parent: ViewGroup) :
            Renderer<ColorResource>(parent, R.layout.fast_resourcepicker_item_color_grid),
            View.OnClickListener
    {
        private val delegate = RendererDelegate(itemView)

        init
        {
            itemView.setOnClickListener(this)
        }

        override fun bind(data: ColorResource, position: Int)
        {
            delegate.bind(data)
        }

        override fun onClick(v: View?)
        {
            val colorRes: ColorResource = getItem(adapterPosition) as ColorResource

            callback(colorRes)
            //                if (colorRes.isColorStateList) colorRes.colorStateList!!
            //                else ColorStateList.valueOf(ResUtils.get_color(colorRes.resId) )
            //            )
        }
    }

    private inner class ColorListRenderer(parent: ViewGroup) :
            Renderer<ColorResource>(parent, R.layout.fast_resourcepicker_item_color_list),
            View.OnClickListener
    {
        private val delegate = RendererDelegate(itemView)

        init
        {
            itemView.setOnClickListener(this)
        }

        override fun bind(data: ColorResource, position: Int)
        {
            delegate.bind(data)
        }

        override fun onClick(v: View?)
        {
            val colorRes: ColorResource = getItem(adapterPosition) as ColorResource
            callback(colorRes)
            //                if (colorRes.isColorStateList) colorRes.colorStateList!!
            //                else ColorStateList.valueOf(ResUtils.get_color(colorRes.resId)))
        }
    }

    inner class RendererDelegate(val itemView: View)
    {
        private val name: TextView = itemView.findViewById(R.id.name)
        private val hexCode: TextView = itemView.findViewById(R.id.color_hexcode)
        private val image: SuperImageView = itemView.findViewById(R.id.image_view)
        private val stateListIcon: ImageView = itemView.findViewById(R.id.icon_state_list)
        private val icon: ImageButton = itemView.findViewById(R.id.icon)

        private val maskOptions = MaskOptions.Builder(PathHelper.circle(50f)).build()

        init
        {
            image.ratio = AspectRatio.SQUARE
            image.setMaskOptions(maskOptions)
        }

        fun bind(data: ColorResource)
        {
            // Resource might not be available on older versions of Android
            // For example, a resource in -v23 folder trying to get accessed from api 21
            val color = data.value ?: try
            {
                ResUtils.getColor(data.resId)
            }
            catch (e: Exception)
            {
                null
            }

            name.text = data.name
            name.setTextColor(itemTextColor)
            hexCode.setTextColor(itemTextColor)
            itemView.setBackgroundColor(itemBackgroundColor)

            icon.isSelected = FavoriteManager.isFavorite(data)

            icon.setOnClickListener {
                icon.isSelected = !icon.isSelected
                FavoriteManager.toggleResource(data)
            }

            stateListIcon.hide()

            if (color != null)
            {
                if (!image.hasMask())
                    image.setMaskOptions(maskOptions)

                hexCode.text = String.format("#%08X", 0xFFFFFFFF.toInt() and color)

                if (!data.isColorStateList)
                {
                    try
                    {
                        if (data.value != null) image.setImageDrawable(ColorDrawable(data.value!!))
                        else image.setImageDrawable(ResUtils.getDrawable(itemView.context, data.resId))
                    }
                    catch (e: Exception)
                    {
                        if (data.value == null) data.colorStateList =
                                ResUtils.getColorStateList(getColorByName(itemView.context, data.name))
                    }
                }

                data.colorStateList?.let {
                    if (itemDrawableState != null) image.setImageDrawable(ColorDrawable(it.getColorForState(itemDrawableState,
                                                                                                            it.defaultColor)))
                    else image.setImageDrawable(ColorDrawable(it.defaultColor))

                    stateListIcon.setTint(itemTextColor)
                    stateListIcon.show()
                }
            }
            else
            {
                hexCode.text = "Not available"
                image.setMaskOptions(null)
                image.setImageResource(R.drawable.fast_resourcepicker_ic_unavailable)
            }

        }

        fun getColorByName(context: Context, name: String): Int
        {
            return context.resources.getIdentifier(name, "color", context.packageName)
        }
    }
}