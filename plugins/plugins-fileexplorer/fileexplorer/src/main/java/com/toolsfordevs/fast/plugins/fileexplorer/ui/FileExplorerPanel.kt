package com.toolsfordevs.fast.plugins.fileexplorer.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.*
import com.toolsfordevs.fast.core.AppInstance
import com.toolsfordevs.fast.core.FastManager
import com.toolsfordevs.fast.core.ext.*
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.core.ui.ext.hide
import com.toolsfordevs.fast.core.ui.ext.setPadding
import com.toolsfordevs.fast.core.ui.ext.setPaddingHorizontal
import com.toolsfordevs.fast.core.ui.ext.show
import com.toolsfordevs.fast.core.ui.widget.BottomSheetLayout
import com.toolsfordevs.fast.core.ui.widget.FastPanelToolbar
import com.toolsfordevs.fast.core.ui.widget.FastSpinner
import com.toolsfordevs.fast.core.util.AndroidVersion
import com.toolsfordevs.fast.core.util.FastSort
import com.toolsfordevs.fast.modules.androidx.recyclerview.ext.vRecyclerView
import com.toolsfordevs.fast.modules.recyclerview.RendererAdapter
import com.toolsfordevs.fast.modules.recyclerview.ViewRenderer
import com.toolsfordevs.fast.plugins.fileexplorer.R
import java.io.File
import java.text.DateFormat
import java.util.*
import kotlin.Comparator


@SuppressLint("NewApi")
internal class FileExplorerPanel(context: Context) : BottomSheetLayout(context)
{
    private val adapter = RendererAdapter()
    private lateinit var currentFolder: File

    private val folder = TextView(context).apply {
        setTextColor(MaterialColor.WHITE_87)
        textSize = 12f
        setPadding(8.dp)
    }

    init
    {
        Prefs.lastFolder?.let {
            val file = File(it)
            if (file.exists() && file.isDirectory)
                currentFolder = file
        }

        // Fallback to default
        if (!::currentFolder.isInitialized)
            currentFolder = context.applicationContext.filesDir

        val layout = vLinearLayout(context)

        val toolbar = FastPanelToolbar(context)

        val spinner = FastSpinner(context)
        spinner.setLightTheme()

        val folders = arrayListOf(
                Pair("context.cacheDir", context.cacheDir),
                Pair("context.codeCacheDir", context.codeCacheDir),
                Pair("context.filesDir", context.filesDir)
        )

        context.externalCacheDir?.let { folders.add(Pair("context.externalCacheDir", it)) }
        context.noBackupFilesDir?.let { folders.add(Pair("context.noBackupFilesDir", it)) }
        context.obbDir?.let { folders.add(Pair("context.obbDir", it)) }

        if (AndroidVersion.isMinNougat())
            folders.add(Pair("context.dataDir", context.dataDir))

        folders.add(Pair("Environment.getDataDirectory()", Environment.getDataDirectory()))
        folders.add(Pair("Environment.getDownloadCacheDirectory()", Environment.getDownloadCacheDirectory()))
        folders.add(Pair("Environment.getRootDirectory()", Environment.getRootDirectory()))

        context.getExternalFilesDir(Environment.DIRECTORY_ALARMS)?.let { folders.add(Pair("Environment.DIRECTORY_ALARMS", it)) }

        if (AndroidVersion.isMinApi(29))
            context.getExternalFilesDir(Environment.DIRECTORY_AUDIOBOOKS)?.let { folders.add(Pair("Environment.DIRECTORY_AUDIOBOOKS", it)) }

        context.getExternalFilesDir(null)?.let { folders.add(Pair("context.getExternalFilesDir(null)", it)) }
        context.getExternalFilesDir(Environment.DIRECTORY_DCIM)?.let { folders.add(Pair("Environment.DIRECTORY_DCIM", it)) }
        context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.let { folders.add(Pair("Environment.DIRECTORY_DOCUMENTS", it)) }
        context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.let { folders.add(Pair("Environment.DIRECTORY_DOWNLOADS", it)) }
        context.getExternalFilesDir(Environment.DIRECTORY_MOVIES)?.let { folders.add(Pair("Environment.DIRECTORY_MOVIES", it)) }
        context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)?.let { folders.add(Pair("Environment.DIRECTORY_MUSIC", it)) }
        context.getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS)?.let { folders.add(Pair("Environment.DIRECTORY_NOTIFICATIONS", it)) }
        context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.let { folders.add(Pair("Environment.DIRECTORY_PICTURES", it)) }
        context.getExternalFilesDir(Environment.DIRECTORY_PODCASTS)?.let { folders.add(Pair("Environment.DIRECTORY_PODCASTS", it)) }
        context.getExternalFilesDir(Environment.DIRECTORY_RINGTONES)?.let { folders.add(Pair("Environment.DIRECTORY_RINGTONES", it)) }

        if (AndroidVersion.isMinApi(29))
            context.getExternalFilesDir(Environment.DIRECTORY_SCREENSHOTS)?.let { folders.add(Pair("Environment.DIRECTORY_SCREENSHOTS", it)) }


        folders.sortBy { it.first.toLowerCase(Locale.getDefault()) }

        val lastSelectedFolder = Prefs.lastSelectedFolder ?: ""
        val index = folders.indexOfFirst { pair -> pair.second.absolutePath == lastSelectedFolder }

        spinner.setAdapter(folders.map { it.first }, if (index > -1) index else 0) { index ->
            Prefs.lastSelectedFolder = folders[index].second.absolutePath
            showFolder(folders[index].second)
        }

        toolbar.buttonLayout.addView(spinner, 0, linearLayoutParamsWeM(1f))
        toolbar.addView(folder, layoutParamsMW())

        val sortButton = toolbar.createButton(R.drawable.fast_plugin_fileexplorer_ic_sort)
        sortButton.makePopupMenu(listOf("Sort A->Z", "Sort Z->A", "Most recent", "Older first", "Largest first", "Smallest first")) { selectedIndex ->
            Prefs.sortType = when (selectedIndex) {
                0 -> FastSort.ALPHA_ASC
                1 -> FastSort.ALPHA_DESC
                3 -> FastSort.DATE_ASC
                4 -> FastSort.DATE_DESC
                5 -> FastSort.SIZE_ASC
                6 -> FastSort.SIZE_DESC
                else -> FastSort.ALPHA_ASC
            }
            showFolder(currentFolder)
        }

        val overflow = toolbar.createButton(R.drawable.fast_plugin_fileexplorer_ic_overflow_white)
        overflow.makePopupMenu(listOf("Refresh", "New folder", "New file")) { index ->

            if (index == 0)
            {
                showFolder(currentFolder)
            }
            else
            {
                val editText = EditText(overflow.context)

                AlertDialog.Builder(overflow.context)
                    .setTitle(if (index == 1) "New folder" else "New file")
                    .setView(editText)
                    .setPositiveButton("Create") { dialog, which ->
                        val newFile = File(currentFolder, editText.text.toString())

                        try
                        {
                            if (index == 1)
                            {
                                val result = newFile.mkdir()

                                if (!result)
                                    Toast.makeText(overflow.context, "Can’t create folder", Toast.LENGTH_SHORT).show()
                                else
                                    showFolder(currentFolder)
                            }
                            else
                            {
                                val result = newFile.createNewFile()

                                if (!result)
                                    Toast.makeText(overflow.context, "File already exist", Toast.LENGTH_SHORT).show()
                                else
                                    showFolder(currentFolder)
                            }
                        }
                        catch (e: Exception)
                        {
                            Toast.makeText(
                                    overflow.context,
                                    "Error while trying to create " + (if (index == 1) "folder" else "file") + ":  ${e.message}",
                                    Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    .setNegativeButton("Cancel") { dialog, which -> }
                    .show()

                editText.layoutParams = frameLayoutParamsMW().apply { setMargins(16.dp, 8.dp, 16.dp, 8.dp) }
            }
        }

        val recyclerView = vRecyclerView(context)
        recyclerView.isVerticalScrollBarEnabled = true

        adapter.addRenderer(File::class, ::FileRenderer)
        recyclerView.adapter = adapter

        layout.addView(toolbar, layoutParamsMW())
        layout.addView(recyclerView, layoutParamsMM())

        addView(layout, layoutParamsMM())
        setCollapsedHeight(500.dp)

        showFolder(currentFolder)
    }

    private fun showFolder(folder: File)
    {
        this.currentFolder = folder
        this.folder.text = folder.absolutePath

        Prefs.lastFolder = folder.absolutePath

        adapter.clear()

        val files = listFiles(folder).sortedWith(Comparator { o1, o2 ->
            if (o1.isDirectory && !o2.isDirectory)
                -1
            else if (!o1.isDirectory && o2.isDirectory)
                1
            else
            {
                val sort = Prefs.sortType!!

                if (sort == FastSort.ALPHA_ASC || sort == FastSort.ALPHA_DESC)
                    FastSort.compare(o1.name.toLowerCase(Locale.getDefault()), o2.name.toLowerCase(Locale.getDefault()), sort)
                else if (sort == FastSort.SIZE_ASC || sort == FastSort.SIZE_DESC)
                    FastSort.compare(o1.length(), o2.length(), sort)
                else
                    FastSort.compare(o1.lastModified(), o2.lastModified(), sort)
            }
        })

        adapter.addAll(arrayListOf<File>().also {
            it.addAll(files)

            folder.parentFile?.let { parent ->
                if (parent.canRead())
                    it.add(0, parent)
            }
        })
    }

    private fun listFiles(folder: File): List<File>
    {
        return if (folder.exists()) folder.listFiles()?.asList() ?: listOf() else listOf()
    }

    private inner class FileRenderer(parent: ViewGroup) : ViewRenderer<File, LinearLayout>(hLinearLayout(parent.context)), OnClickListener
    {
        val icon = ImageView(parent.context)
        val name = TextView(parent.context).apply {
            setTextColor(MaterialColor.BLACK_87)
        }
        val size = TextView(parent.context).apply {
            setTextColor(MaterialColor.BLACK_54)
            textSize = 10f
            typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL)
        }
        val date = TextView(parent.context).apply {
            setTextColor(MaterialColor.BLACK_54)
            textSize = 10f
            typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL)
        }
        val overflow = ImageView(parent.context).apply {
            setImageResource(R.drawable.fast_plugin_fileexplorer_ic_overflow)
            setBackgroundResource(R.drawable.fast_selectable_item_background_borderless)
        }

        init
        {
            view.setBackgroundResource(R.drawable.fast_selectable_item_background)
            view.setPaddingHorizontal(16.dp)
            view.minimumHeight = 56.dp

            view.addView(icon, linearLayoutParamsVV(24.dp, 24.dp).apply {
                topMargin = 16.dp
            })

            val layout = vLinearLayout(parent.context)

            layout.addView(name, layoutParamsMW())

            val hLayout = hLinearLayout(context)
            hLayout.addView(size, linearLayoutParamsWeW(1f))
            hLayout.addView(date, layoutParamsWW())

            layout.addView(hLayout, layoutParamsMW())
            view.addView(layout, linearLayoutParamsWeW(1f).apply {
                gravity = Gravity.CENTER_VERTICAL
                marginStart = 32.dp
                marginEnd = 16.dp
            })

            view.addView(overflow, linearLayoutParamsVV(24.dp, 24.dp).apply {
                topMargin = 16.dp
            })

            view.setOnClickListener(this)
            view.layoutParams = layoutParamsMW()
        }

        override fun bind(data: File, position: Int)
        {
            if (position == 0 && data == currentFolder.parentFile)
            {
                icon.setImageResource(R.drawable.fast_plugin_fileexplorer_ic_back)

                name.text = ".."
                name.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                (size.parent as View).hide()

                overflow.hide()
            }
            else
            {
                icon.setImageResource(if (data.isDirectory) R.drawable.fast_plugin_fileexplorer_ic_folder else R.drawable.fast_plugin_fileexplorer_ic_file)

                name.text = data.name
                name.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)

                if (data.isDirectory)
                {
                    val count = listFiles(data).size
                    val label = if (count > 1) "files" else "file"
                    size.text = "$count $label"
                }
                else
                {
                    try
                    {
                        size.text = FileSizeFormatter.formatSize(data.length())
                    }
                    catch (e: Exception)
                    {
                        size.text = "???"
                    }
                }

                (size.parent as View).show()

                overflow.show()
                overflow.makePopupMenu(listOf("Rename", "Delete")) { index ->
                    if (index == 0)
                    {
                        val editText = EditText(overflow.context)
                        editText.setText(data.name)

                        AlertDialog.Builder(overflow.context)
                            .setTitle("Rename ${data.name}")
                            .setView(editText)
                            .setPositiveButton("Rename") { dialog, which ->
                                val newFile = File(currentFolder, editText.text.toString())

                                try
                                {
                                    val result = data.renameTo(newFile)

                                    if (!result)
                                        Toast.makeText(overflow.context, "Could not rename", Toast.LENGTH_SHORT).show()
                                    else
                                        showFolder(currentFolder)
                                }
                                catch (e: Exception)
                                {
                                    Toast.makeText(overflow.context, "Error while trying to rename :  ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                            .setNegativeButton("Cancel") { dialog, which -> }
                            .show()

                        editText.layoutParams = frameLayoutParamsMW().apply { setMargins(16.dp, 8.dp, 16.dp, 8.dp) }
                    }
                    else
                    {
                        val parent = data.parentFile
                        if (data.deleteRecursively())
                            showFolder(parent!!)
                        else
                            Toast.makeText(overflow.context, "Could not delete", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            date.text = DateFormatter.formatDate(data.lastModified())
        }

        override fun onClick(v: View?)
        {
            val file = getItem(adapterPosition) as File

            if (file.isDirectory)
                showFolder(file)
            else
            {
                val intent = Intent(Intent.ACTION_VIEW)
                val absoluteFilePath = file.absolutePath
                // com.toolsfordevs.fast = FileProvider authority defined in Manifest
                // <provider
                //  android:name=".FileProvider"
                //  android:authorities="com.toolsfordevs.fast.plugins.fileexplorer"
                //  android:exported="true" />
                val fileUri: Uri = Uri.parse("content://com.toolsfordevs.fast.plugins.fileexplorer.${AppInstance.getApplicationId()}$absoluteFilePath")

                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                val mimeType: String = getMimeType(file.path)

                // Log.d("FAST", "XXXXXXXXXX Mime type $mimeType")
                intent.setDataAndType(fileUri, mimeType)

                try
                {
                    view.context.startActivity(intent)
                }
                catch (e: Exception)
                {
                    Toast.makeText(context, "No activity found to open this file", Toast.LENGTH_SHORT).show()
                    // ToDo Use (to create) FAST Text Editor
                    //  Features
                    //  Possibility to format in various formats like JSON, XML, HTML, ...
                    //  Possibility to change open in a different encoding Charset
                    //  Possibility to edit and save
                }
            }
        }

        private fun getMimeType(path: String?): String
        {
            // ToDo MimeTypeMap doesn’t know json... Use ContentType class from NetworkExplorer core module
            //  to make a first pass, then use MimeTypeMap if ContentType do not handle the extension
            val extension = MimeTypeMap.getFileExtensionFromUrl(path)
            return extension?.run { MimeTypeMap.getSingleton().getMimeTypeFromExtension(this) } ?: "*/*"
        }


    }

    object DateFormatter
    {
        private val df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM)

        fun formatDate(date:Long):String = df.format(Date(date))
    }

    object FileSizeFormatter
    {
        private val units = listOf("B", "KB", "MB", "GB", "TB", "PB") //Don’t need to go further than this I guess
        private const val DENOMINATOR:Double = 1024.0

        fun formatSize(bytes:Long):String
        {
            if (bytes == 0L)
                return "0 B"

            var size = bytes.toDouble()
            var i = 0
            do
            {
                if (size < DENOMINATOR)
                    return String.format("%.2f ", size) + units[i]

                size /= DENOMINATOR
                i++

                if (i > 5)
                    return "Unknown"
            }
            while (true)
        }
    }
}