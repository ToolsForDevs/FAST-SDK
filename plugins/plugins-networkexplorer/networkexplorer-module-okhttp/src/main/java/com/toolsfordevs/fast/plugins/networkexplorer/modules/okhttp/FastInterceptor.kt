package com.toolsfordevs.fast.plugins.networkexplorer.modules.okhttp

import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.plugins.networkexplorer.core.RequestRepository
import com.toolsfordevs.fast.plugins.networkexplorer.core.model.Request as FastRequest
import com.toolsfordevs.fast.plugins.networkexplorer.core.model.Response as FastResponse
import com.toolsfordevs.fast.plugins.networkexplorer.core.util.ContentType
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import okio.BufferedSource
import okio.GzipSource
import okio.Okio
import java.io.EOFException
import java.io.IOException
import java.nio.charset.Charset
import kotlin.math.min

@Keep
class FastInterceptor : Interceptor
{
    override fun intercept(chain: Interceptor.Chain): Response
    {
        val req = chain.request()

        val request = FastRequest()
        request.method = req.method()
        request.url = req.url().toString()

        for (i in 0 until req.headers().size())
        {
            request.headers.put(req.headers().name(i), req.headers().value(i))
        }
        request.time = System.currentTimeMillis()

        req.body()?.let {
            val source = getNativeSource(Buffer(), isContentEncodingGzip(req.headers()))
            val buffer = source.buffer()

            it.writeTo(buffer)

            val contentType = getContentType(req.headers())
            val charset = contentType.getCharset()

            if (contentType.isText())
            {
                request.body = readBuffer(buffer, Charset.forName(charset))
            }
        }

        RequestRepository.add(request)

        val response = FastResponse()
        request.response = response

        val res: Response
        try
        {
            res = chain.proceed(req)
        }
        catch (e: IOException)
        {
            response.error = e.toString()
            RequestRepository.onItemUpdated(request)
            throw e
        }

        response.time = System.currentTimeMillis()
        response.duration = response.time!! - request.time
        response.url = res.request().url().toString()
        request.protocol = res.protocol().toString()

        response.responseCode = res.code()

        for (i in 0 until res.headers().size())
            response.headers.put(res.headers().name(i), res.headers().value(i))

        response.message = res.message()
        response.protocol = res.protocol().name

        // try
        // {
        //     response.byteBody = res.body()?.bytes()
        // }
        // catch (e: Exception)
        // {
        //     flog("bytes body error $e")
        // }

        try
        {
            response.stringBody = parseResponseBody(res)
            //flog("response body ${response.stringBody}")
        }
        catch (e: Exception)
        {
            
        }

        request.endTime = System.currentTimeMillis()

        RequestRepository.onItemUpdated(request)

        return res
    }

    private val maxContentLength = 250000L

    private fun parseResponseBody(response: Response):String?
    {
        val contentType = getContentType(response.headers())

        if (contentType.isText() && response.body() != null)
        {
            val source = getNativeSource(response)
            source.request(Long.MAX_VALUE)
            val buffer = source.buffer()
            val charset = Charset.forName(contentType.getCharset())

            return readBuffer(buffer.clone(), charset)
        }

        return null
    }

    private fun getNativeSource(response: Response): BufferedSource
    {
        if (isContentEncodingGzip(response.headers()))
        {
            val source = response.peekBody(maxContentLength).source()

            if (source.buffer().size() < maxContentLength)
                return getNativeSource(source, true)
        }

        return response.body()!!.source()
    }

    private fun getNativeSource(input:BufferedSource, gzip:Boolean):BufferedSource
    {
        return if (gzip) Okio.buffer(GzipSource(input)) else input
    }

    private fun readBuffer(buffer: Buffer, charset:Charset):String
    {
        val bufferSize = buffer.size()
        val maxBytes = min(bufferSize, maxContentLength)
        var body = ""
        try
        {
            body = buffer.readString(maxBytes, charset)
        }
        catch (e: EOFException)
        {
            body += "FAST SDK : Unexpected End Of File"
        }

        if (bufferSize > maxContentLength)
            body += "FAST SDK :  Content truncated"

        return body
    }

    private fun getContentType(headers:Headers): ContentType
    {
        return ContentType(headers.get("Content-Type") ?: "")
    }

    private fun isContentEncodingGzip(headers:Headers):Boolean
    {
        return "gzip".equals(headers.get("Content-Encoding"), true)
    }
}