package com.toolsfordevs.fast.plugins.networkexplorer.core.model

import android.net.Uri
import com.toolsfordevs.fast.core.annotation.Keep

@Keep
class Request
{
    var headers = hashMapOf<String, String>()
    var url:String? = null
    var method = "GET"
    var time = 0L
    var endTime = 0L
    var protocol:String = ""
    var body:String? = null
    var isCancelled:Boolean = false

    var response: Response? = null

    val endpoint:String
        get() {
            url?.let {
                val uri = Uri.parse(url)
                return "${uri.path}${uri.query?.let { "?$it" } ?: ""}"
            }

            return "?"
        }

    val host:String
        get() {
            url?.let {
                val uri = Uri.parse(url)
                return uri.host.toString()
            }

            return "?"
        }

    val ssl:Boolean
        get()
        {
            url?.let {
                val uri = Uri.parse(url)
                return "https" == uri.scheme?.toLowerCase()
            }
            return false
        }
}