package com.toolsfordevs.fast.plugins.networkexplorer.ui

import android.content.Context
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.toolsfordevs.fast.core.FastManager
import com.toolsfordevs.fast.core.data.BaseRepository
import com.toolsfordevs.fast.core.ext.*
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.core.ui.ext.setPadding
import com.toolsfordevs.fast.core.ui.ext.setPaddingStart
import com.toolsfordevs.fast.core.ui.widget.BottomSheetLayout
import com.toolsfordevs.fast.core.ui.widget.FastPanelToolbar
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.modules.androidx.recyclerview.ext.vRecyclerView
import com.toolsfordevs.fast.modules.recyclerview.RendererAdapter
import com.toolsfordevs.fast.modules.recyclerview.ViewRenderer
import com.toolsfordevs.fast.plugins.networkexplorer.R
import com.toolsfordevs.fast.plugins.networkexplorer.core.RequestRepository
import com.toolsfordevs.fast.plugins.networkexplorer.core.model.Request

internal class ListPanel(context: Context) : BottomSheetLayout(context), BaseRepository.RepositoryChangeListener
{
    private val adapter: RendererAdapter = RendererAdapter()

    init
    {
        val layout = vLinearLayout(context)

        val toolbar = FastPanelToolbar(context)
        val title = TextView(context).apply {
            setTextColor(MaterialColor.WHITE_87)
            text = "Network"
            textSize = 18f
            setPaddingStart(16.dp)
        }

        toolbar.buttonLayout.addView(title, 0, linearLayoutParamsWeW(1f).apply { gravity = Gravity.CENTER_VERTICAL })

        val clearButton = toolbar.createButton(R.drawable.fast_networkexplorer_ic_clear)
        clearButton.setOnClickListener {
            adapter.clear()
            RequestRepository.clear()
        }

        layout.addView(toolbar, layoutParamsMW())

        adapter.addRenderer(Request::class, ::RequestRenderer)

        val recyclerView = vRecyclerView(context)
        recyclerView.adapter = adapter

        layout.addView(recyclerView, layoutParamsMM())
        addView(layout, layoutParamsMM())

        setCollapsedHeight(400.dp)
    }

    override fun onChange()
    {
        adapter.clear()
        adapter.addAll(RequestRepository.getData().reversed())
    }

    override fun onAttachedToWindow()
    {
        super.onAttachedToWindow()
        RequestRepository.addListener(this)
        onChange()
    }

    override fun onDetachedFromWindow()
    {
        super.onDetachedFromWindow()
        RequestRepository.removeListener(this)
    }

    private class RequestRenderer(parent: ViewGroup) : ViewRenderer<Request, RequestView>(RequestView(parent.context)), OnClickListener
    {
        override fun bind(data: Request, position: Int)
        {
            view.method.text = data.method
            view.endpoint.text = data.endpoint
            view.host.text = data.host

            view.method.setTextColor(MaterialColor.BLACK_87)

            view.ssl.setImageResource(if (data.ssl) R.drawable.fast_networkexplorer_ic_lock else R.drawable.fast_networkexplorer_ic_lock_open)

            if (data.response != null)
            {
                with(data.response!!) {
                    if (error != null)
                    {
                        view.responseCode.text = "Error"
                        view.responseCode.setTextColor(MaterialColor.RED_500)
                    }
                    else if (responseCode > 0)
                    {
                        view.responseCode.text = responseCode.toString()

                        val color = when (responseCode)
                        {
                            in 1..299 -> MaterialColor.GREEN_500
                            in 300..399 -> MaterialColor.BLUE_500
                            in 400..499 -> MaterialColor.ORANGE_500
                            in 500..999 -> MaterialColor.RED_500
                            else     -> MaterialColor.BLACK_87
                        }
                        view.responseCode.setTextColor(color)
                        view.method.setTextColor(color)
                    }
                }
            }
            else
            {
                view.responseCode.text = ""
            }

            view.setOnClickListener(this)
        }

        override fun onClick(v: View)
        {
            FastManager.addView(RequestPanel(v.context, getItem(adapterPosition) as Request))
        }
    }

    private class RequestView(context: Context) : LinearLayout(context)
    {
        val method = TextView(context).apply {
            setTextColor(MaterialColor.BLACK_87)
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            textSize = 14f
        }
        val endpoint = TextView(context).apply {
            setTextColor(MaterialColor.BLACK_87)
            textSize = 14f
        }
        val host = TextView(context).apply {
            setTextColor(MaterialColor.BLACK_54)
            textSize = 12f
        }
        val responseCode = TextView(context).apply {
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            textSize = 14f
        }
        val ssl = ImageView(context)

        private val left = vLinearLayout(context)
        private val right = vLinearLayout(context)

        init
        {
            orientation = HORIZONTAL
            setPadding(Dimens.dp(16))
            setBackgroundResource(R.drawable.fast_selectable_item_background)

            left.addView(method, layoutParamsMW())
            left.addView(responseCode, layoutParamsMW())

            right.addView(endpoint, layoutParamsMW())

            val layout = hLinearLayout(context)
            layout.addView(ssl, linearLayoutParamsVV(12.dp, 12.dp).apply { gravity = Gravity.CENTER_VERTICAL })
            layout.addView(host, linearLayoutParamsMW().apply {
                gravity = Gravity.CENTER_VERTICAL
                marginStart = 4.dp
            })
            right.addView(layout, layoutParamsMW())

            addView(left, linearLayoutParamsWeW(0.25f))
            addView(right, linearLayoutParamsWeW(0.75f))

            layoutParams = layoutParamsMW()
        }
    }
}