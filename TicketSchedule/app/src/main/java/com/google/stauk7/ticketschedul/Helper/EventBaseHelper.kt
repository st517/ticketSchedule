package com.google.stauk7.ticketschedul.Helper

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
        const val VERSION = 3
        const val SQL_CREATE_EVENT_BASE =
            "CREATE TABLE mst_event_base (event_id Integer PRIMARY KEY, name TEXT, memo TEXT, delete_flg INTEGER)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_EVENT_BASE)
        Log.e("SQL_LOG", "onCreate")
        db.execSQL(
            "INSERT INTO mst_event_base (event_id, name, memo) VALUES (1, 'title', 'memo')"
        )
        Log.e("SQL_LOG", "insert")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS mst_event_base")
        db.execSQL("DROP TABLE IF EXISTS mst_event_detail")
        onCreate(db)
        Log.e("SQL_LOG", "update")
    }

    fun saveBaseData(eventData: EventData) {
        val db = writableDatabase

        val values = ContentValues()
        values.put("name", eventData.title)
        values.put("memo", eventData.memo)

        db.insert("mst_event_base", null, values)
        Log.e("SQL_LOG", "save base data")
    }

    fun selectEvent(eventId: Int): EventData? {
        var event: EventData? = null
        val db = readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from mst_event_base where event_id = $eventId", null)

            if (cursor!!.moveToFirst()) {
                event = EventData(
                    cursor.getInt(cursor.getColumnIndex("event_id")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("memo"))
                )
            }
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_EVENT_BASE)
            Log.e("SQL_LOG", "sql error")
        } finally {
            cursor?.close()
        }
        return event
    }

    fun selectBaseAll(): MutableList<EventData> {
        val events: MutableList<EventData> = mutableListOf()
        val db = readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery("select * from mst_event_base", null)

            if (cursor!!.moveToFirst()) {
                while (!cursor.isAfterLast) {
                    val id = cursor.getInt(cursor.getColumnIndex("event_id"))
                    val title = cursor.getString(cursor.getColumnIndex("name"))
                    val memo = cursor.getString(cursor.getColumnIndex("memo"))

                    events.add(EventData(id, title, memo))
                    cursor.moveToNext()
                }
                Log.e("SQL_LOG", "select sql")
            }
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_EVENT_BASE)
            Log.e("SQL_LOG", "sql error")
        } finally {
            cursor?.close()
        }
        return events
    }
}
