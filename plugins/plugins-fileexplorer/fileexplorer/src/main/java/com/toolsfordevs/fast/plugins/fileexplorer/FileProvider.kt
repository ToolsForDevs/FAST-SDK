package com.toolsfordevs.fast.plugins.fileexplorer

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.ParcelFileDescriptor
import java.io.File


class FileProvider : ContentProvider()
{
    override fun openFile(uri: Uri, mode: String): ParcelFileDescriptor?
    {
        return ParcelFileDescriptor.open(File(uri.path ?: ""), ParcelFileDescriptor.MODE_READ_ONLY)
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri?
    {
        return null
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor?
    {
        return null
    }

    override fun onCreate(): Boolean
    {
        return false
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int
    {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int
    {
        return 0
    }

    override fun getType(uri: Uri): String?
    {
        return null
    }
}