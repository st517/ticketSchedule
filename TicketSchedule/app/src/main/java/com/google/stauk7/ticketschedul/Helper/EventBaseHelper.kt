package com.google.stauk7.ticketscheseledul.Helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.google.stauk7.ticketschedul.Data.EventData


class EventBaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, VERSION) {

    companion object {
        const val DB_NAME = "ticket.db"
        const val VERSION = 2
        const val SQL_CREATE_ENTRIES =
            "CREATE TABLE mst_event_base (event_id Integer PRIMARY KEY, name TEXT, memo TEXT, delete_flg INTEGER)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
        Log.e("SQL_LOG", "onCreate")
        db.execSQL(
            "INSERT INTO mst_event_base (event_id, name, memo) VALUES (1, 'title', 'memo')"
        )
        Log.e("SQL_LOG", "insert")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS mst_event_base")
        onCreate(db)
        Log.e("SQL_LOG", "update")
    }

    fun saveData(eventData: EventData) {
        val db = writableDatabase

        val values = ContentValues()
        values.put("name", eventData.title)
        values.put("memo", eventData.memo)

        db.insert("mst_event_base", null, values)
        Log.e("SQL_LOG", "save")

    }
    fun selectAll(): MutableList<EventData> {
        val events: MutableList<EventData> = mutableListOf()
        val db = readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery("select * from mst_event_base", null)
//        var id: Int
            var title: String
            var memo: String
            if (cursor!!.moveToFirst()) {
                while (!cursor.isAfterLast) {
                    title = cursor.getString(cursor.getColumnIndex("name"))
                    memo = cursor.getString(cursor.getColumnIndex("memo"))

                    events.add(EventData(title, memo))
                    cursor.moveToNext()
                }
                Log.e("SQL_LOG", "select sql")
            }
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            Log.e("SQL_LOG", "sql error")
        } finally {
            cursor?.close()
        }
        return events
    }
}
