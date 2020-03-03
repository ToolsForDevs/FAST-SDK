package com.toolsfordevs.fast.plugins.networkexplorer.core.util

import com.toolsfordevs.fast.core.annotation.Keep

@Keep
class ContentType(contentType:String)
{
    companion object
    {
        const val IMAGE_GIF = "image/gif"
        const val IMAGE_JPG = "image/jpeg"
        const val IMAGE_PNG = "image/png"
        const val IMAGE_SVG = "image/svg+xml"

        const val TEXT_JSON = "text/json"
        const val TEXT_HTML = "text/html"
        const val TEXT_XML = "text/xml"

        const val APPLICATION_JAVASCRIPT = "application/javascript"
        const val APPLICATION_JSON = "application/json"
        const val APPLICATION_LD_JSON = "application/ld+json"
        const val APPLICATION_XHTML = "application/html+xml"
        const val APPLICATION_XML = "application/xml"
    }

    private val type:String

    init
    {
        val index = contentType.indexOf(";")
        type = if (index != -1) contentType.substring(0, index) else contentType
    }

    fun getContentType():String
    {
        return type
    }

    fun isText(): Boolean
    {
        return type.startsWith("text") || type in listOf(APPLICATION_JSON,
                                                         APPLICATION_LD_JSON,
                                                         APPLICATION_JAVASCRIPT,
                                                         APPLICATION_XML,
                                                         APPLICATION_XHTML,
                                                         TEXT_XML)
    }

    fun isJson(): Boolean
    {
        return type in listOf(TEXT_JSON, APPLICATION_JSON, APPLICATION_LD_JSON)
    }

    fun isXml(): Boolean
    {
        return type in listOf(APPLICATION_XML, TEXT_XML)
    }

    fun isHtml(): Boolean
    {
        return type in listOf(TEXT_HTML, APPLICATION_XHTML)
    }

    fun isSupportedImage(): Boolean
    {
        return type in listOf(IMAGE_JPG, IMAGE_PNG)
    }

    fun isImage():Boolean
    {
        return type in listOf(IMAGE_JPG, IMAGE_PNG, IMAGE_GIF, IMAGE_SVG)
    }

    fun getCharset():String
    {
        var index = type.indexOf("charset=")

        if (index != -1)
        {
            index += 8
            val end = type.indexOf(";", index + 1)

            return if (end != -1)
                type.substring(index, end)
            else
                type.substring(index)
        }

        return "UTF-8"
    }


}