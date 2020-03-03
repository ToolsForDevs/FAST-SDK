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
import com.toolsfordevs.fast.plugins.networkexplorer.core.model.Response
import com.toolsfordevs.fast.plugins.networkexplorer.core.util.ContentType
import com.toolsfordevs.fast.plugins.networkexplorer.formatter.JsonFormatter
import com.toolsfordevs.fast.plugins.networkexplorer.formatter.XmlFormatter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("ViewConstructor")
internal class ResponseView(context: Context, private val response: Response) : RecyclerView(context), BaseRepository.RepositoryListener<Request>
{
    init
    {
        adapter = RendererAdapter()
        layoutManager = LinearLayoutManager(context)
        setHasFixedSize(true)

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
        response.url?.let {
            adapter.add(RequestHeader("URL", it))
        }

        if (response.responseCode > 0)
            adapter.add(RequestHeader("Response code", response.responseCode.toString()))

        response.message?.let {
            adapter.add(RequestHeader("Response message", it))
        }

        response.time?.let {
            adapter.add(RequestHeader("Response time", SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(Date(it))))
            adapter.add(RequestHeader("Total duration", "${response.duration} ms"))
        }

        adapter.add(RequestHeader("Status", response.status))

        response.error?.let {
            adapter.add(RequestHeader("Error", it))
        }

        if (response.headers.isNotEmpty())
        {
            adapter.add(Subheader("HEADERS"))
            for (header in response.headers.toSortedMap())
                adapter.add(RequestHeader(header.key, header.value))
        }

        response.stringBody?.let {
            adapter.add(Subheader("BODY"))

            val contentType = ContentType(response.headers["Content-Type"] ?: "")

            when
            {
                contentType.isJson() -> adapter.add(JsonFormatter().format(it))
                contentType.isXml()  -> adapter.add(XmlFormatter().format(it))
                contentType.isHtml() -> adapter.add(XmlFormatter().format(it))
                else                 -> adapter.add(it)
            }
        }
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
        if (item.response == response)
            refresh()
    }

    override fun onItemRemoved(item: Request)
    {
        // Do nothing
    }
}