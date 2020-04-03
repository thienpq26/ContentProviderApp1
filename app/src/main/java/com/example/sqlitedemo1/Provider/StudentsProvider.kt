package com.example.sqlitedemo1.Provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.sqlitedemo1.data.DBManager

class StudentsProvider : ContentProvider() {

    lateinit var uriMatcher: UriMatcher
    lateinit var dbManager: DBManager
    lateinit var cursor: Cursor

    override fun onCreate(): Boolean {
        uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        uriMatcher.addURI(AUTHORITY, TABLE_NAME, 1)
        dbManager = DBManager(context!!)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        when (uriMatcher.match(uri)) {
            1 -> {
                cursor = dbManager.getAllStudentsProvider(
                    projection,
                    selection,
                    selectionArgs,
                    sortOrder
                )
            }
        }
        return cursor
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    companion object {
        val AUTHORITY = "com.example.sqlitedemo1.Provider.StudentsProvider"
        val TABLE_NAME = "students"
        val CONTENT_URI = Uri.parse("content://$AUTHORITY/$TABLE_NAME")
    }
}
