package com.toolsfordevs.fast.plugins.networkexplorer.core.model

import com.toolsfordevs.fast.core.annotation.Keep
import java.util.*

@Keep
class Response
{
    var url:String? = null
    var headers = TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER)
    var responseCode:Int = 0
    var stringBody:String? = null
    var byteBody:ByteArray? = null
    var message:String? = null
    val isFromCache:Boolean = false
    var protocol:String = ""
    var error:String? = null
    var isCancelled = false
    var time:Long? = null
    var duration:Long? = null

    val status:String
        get()
        {
            if (error != null)
            {
                return "Error"
            }
            else if (isCancelled)
            {
                return "Cancelled"
            }
            else
            {
                return if (responseCode > 0) "Complete" else "In Progress"
            }
        }
}