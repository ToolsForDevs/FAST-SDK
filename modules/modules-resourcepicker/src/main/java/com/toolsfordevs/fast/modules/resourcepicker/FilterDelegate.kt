package com.toolsfordevs.fast.modules.resourcepicker

import android.app.AlertDialog
import android.util.ArrayMap
import android.view.View
import com.toolsfordevs.fast.modules.resources.TypedResource
import com.toolsfordevs.fast.modules.subheader.Subheader


internal class FilterDelegate(val button:View, val callback:(data:List<Any>) -> Unit)
{
    private val resMap = ArrayMap<String, ArrayList<TypedResource>>()
    private val filterMap = LinkedHashMap<String, Boolean>()

    var sortEntries:Boolean = true

    companion object
    {
        const val NO_PREFIX = "No prefix"
    }

    init {
        button.setOnClickListener {
            val indices = BooleanArray(filterMap.size)
            val keys = filterMap.keys.toTypedArray()

            for (i in 0 until indices.size)
                indices[i] = filterMap.get(keys[i])!!

            AlertDialog.Builder(button.context)
                .setMultiChoiceItems(keys, indices
                ) { dialog, which, isChecked -> indices[which] = isChecked }
                .setPositiveButton("OK") { dialog, which ->
                    for (i in 0 until indices.size)
                        filterMap[keys[i]] = indices[i]

                    callback(getFilteredResources())
                }
                .setCancelable(true)
                .show()
        }
    }

    fun updateResources(resources: List<TypedResource>, excludeFastResource:Boolean = true)
    {
        makeResourceList(resources, excludeFastResource)
        callback(getFilteredResources())
    }

    private fun makeResourceList(resources: List<TypedResource>, excludeFastResource:Boolean)
    {
        resMap.clear()
        resMap.put(NO_PREFIX, arrayListOf())

        var name = ""
        var list = arrayListOf<TypedResource>()

        // following algorithm expect resources to be ordered, so order
        // resources because if they come from the preferences, they
        // won't be ordered naturally

        val res = if (sortEntries) resources.sortedWith(naturalOrder()) else resources

        for (resource in res)
        {
            var prefix:String? = null
            val index = resource.name.indexOf("_")

            if (index != -1)
            {
                prefix = resource.name.substring(0 until index)

                if (excludeFastResource && prefix =="fast_")
                    continue

                if (prefix != name)
                {
                    name = prefix
                    list = arrayListOf()
                    resMap.put(name, list)

                    if (!filterMap.containsKey(name))
                        filterMap.put(name, true)
                }

                list.add(resource)
            }
            else
            {
                resMap.get(NO_PREFIX)?.add(resource)
            }
        }

        // Clean up resMap in case of an updated resource list
        for (entry in filterMap)
        {
            if (!resMap.contains(entry.key))
                filterMap.remove(entry.key)
        }

        if (resMap.get(NO_PREFIX)!!.size > 0)
            filterMap.put(NO_PREFIX, true)
    }

    private fun getFilteredResources():List<Any>
    {
        val list = arrayListOf<Any>()

        val entries = if (sortEntries) filterMap.entries.sortedBy { it.key.toLowerCase() } else filterMap.entries

        for (entry in entries)
        {
            if (entry.value)
            {
                val resources = resMap[entry.key]!!

                if (resources.isNotEmpty())
                {
                    list.add(Subheader(entry.key))
                    list.addAll(resources)
                }
            }
        }

        return list
    }
}