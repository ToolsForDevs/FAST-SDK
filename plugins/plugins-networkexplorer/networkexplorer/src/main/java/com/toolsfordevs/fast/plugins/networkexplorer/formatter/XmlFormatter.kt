package com.toolsfordevs.fast.plugins.networkexplorer.formatter

import org.xml.sax.InputSource
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.xml.transform.OutputKeys
import javax.xml.transform.sax.SAXSource
import javax.xml.transform.sax.SAXTransformerFactory
import javax.xml.transform.stream.StreamResult

class XmlFormatter
{
    fun format(xml: String): String
    {
        return try
        {
            val serializer = SAXTransformerFactory.newInstance().newTransformer()
            serializer.setOutputProperty(OutputKeys.INDENT, "yes")
            serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
            val xmlSource = SAXSource(InputSource(ByteArrayInputStream(xml.toByteArray())))
            val res = StreamResult(ByteArrayOutputStream())
            serializer.transform(xmlSource, res)
            String((res.outputStream as ByteArrayOutputStream).toByteArray())
        }
        catch (e: Exception)
        {
            xml
        }
    }
}