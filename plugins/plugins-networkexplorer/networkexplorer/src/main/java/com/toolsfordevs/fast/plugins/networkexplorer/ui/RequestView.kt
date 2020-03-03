package com.toolsfordevs.fast.plugins.networkexplorer.ui

import android.annotation.SuppressLint
import android.content.Context
import com.toolsfordevs.fast.core.data.BaseRepository
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.LinearLayoutManager
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.RecyclerView
import com.toolsfordevs.fast.modules.recyclerview.RendererAdapter
import com.toolsfordevs.fast.modules.subheader.Subheader
import com.toolsfordevs.fast.modules.subheader.SubheaderRenderer
import com.toolsfordevs.fast.plugins.networkexplorer.core.RequestRepository
import com.toolsfordevs.fast.plugins.networkexplorer.core.model.Request
import com.toolsfordevs.fast.plugins.networkexplorer.core.util.ContentType
import com.toolsfordevs.fast.plugins.networkexplorer.formatter.JsonFormatter
import com.toolsfordevs.fast.plugins.networkexplorer.formatter.XmlFormatter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("ViewConstructor")
internal class RequestView(context: Context, private val request: Request) : RecyclerView(context), BaseRepository.RepositoryListener<Request>
{
    init
    {
        layoutManager = LinearLayoutManager(context)
        setHasFixedSize(true)

        adapter = RendererAdapter()

        val adapter = adapter as RendererAdapter
        adapter.addRenderer(RequestHeader::class, ::RequestHeaderRenderer)
        adapter.addRenderer(Subheader::class, ::SubheaderRenderer)
        adapter.addRenderer(String::class, ::StringRenderer)
    }

    private fun refresh()
    {
        val adapter = adapter as RendererAdapter
        adapter.clear()

        adapter.add(Subheader("DETAILS"))
        adapter.add(RequestHeader("URL", request.url!!))
        adapter.add(RequestHeader("Method", request.method))
        adapter.add(RequestHeader("Protocol", request.protocol))
        adapter.add(RequestHeader("SSL", if (request.ssl) "Yes" else "No"))
        adapter.add(RequestHeader("Start time", SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(Date(request.time))))

        if (request.headers.isNotEmpty())
        {
            adapter.add(Subheader("HEADERS"))

            for (header in request.headers.toSortedMap())
                adapter.add(RequestHeader(header.key, header.value))
        }

        request.body?.let {
            adapter.add(Subheader("BODY"))

            val contentType = ContentType(request.headers["Content-Type"] ?: "")

            when
            {
                contentType.isJson() -> adapter.add(JsonFormatter().format(it))
                contentType.isXml()  -> adapter.add(XmlFormatter().format(it))
                contentType.isHtml() -> adapter.add(XmlFormatter().format(it))
                else                 -> adapter.add(it)
            }
        }

        this.adapter = adapter
    }

    override fun onAttachedToWindow()
    {
        super.onAttachedToWindow()
        RequestRepository.addListener(this)
        refresh()
    }

    override fun onDetachedFromWindow()
    {
        super.onDetachedFromWindow()
        RequestRepository.removeListener(this)
    }

    override fun onItemAdded(item: Request)
    {
        // Do nothing
    }

    override fun onItemUpdated(item: Request)
    {
        if (item == request)
            refresh()
    }

    override fun onItemRemoved(item: Request)
    {
        // Do nothing
    }
}


