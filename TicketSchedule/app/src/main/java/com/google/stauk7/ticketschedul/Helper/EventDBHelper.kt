package com.google.stauk7.ticketschedul.Helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.google.stauk7.ticketschedul.Data.EventData
import com.google.stauk7.ticketschedul.Data.EventDetailData


class EventDBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, VERSION) {

    companion object {
        const val DB_NAME = "ticket.db"
        const val VERSION = 5
        const val SQL_CREATE_EVENT_BASE =
            "CREATE TABLE mst_event_base (event_id Integer PRIMARY KEY, name TEXT, memo TEXT, delete_flg INTEGER)"
        const val SQL_CREATE_EVENT_DETAIL =
            "CREATE TABLE mst_event_detail (event_id Integer, detail_id Integer, title TEXT, date_start TEXT, date_end TEXT, memo TEXT, delete_flg INTEGER, check_flg INTEGER, PRIMARY KEY(event_id, detail_id))"

    }

    private val EVENT_BASE = "mst_event_base"
    private val EVENT_DETAIL = "mst_event_detail"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_EVENT_BASE)
        db.execSQL(SQL_CREATE_EVENT_DETAIL)

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

    fun saveDetailData(eventDetailData: EventDetailData) {
        val db = writableDatabase
        if (eventDetailData.detailId == -1) {
            eventDetailData.detailId = getNewDetailId(eventDetailData)
        }

        val values = ContentValues()
        values.put("event_id", eventDetailData.eventId)
        values.put("detail_id", eventDetailData.detailId)
        values.put("title", eventDetailData.title)
        values.put("date_start", eventDetailData.dateStart)
        values.put("date_end", eventDetailData.dateEnd)
        values.put("memo", eventDetailData.memo)

        db.insert("mst_event_detail", null, values)
        Log.e("SQL_LOG", "save detail data")
    }

    fun selectEvent(eventId: Int): EventData? {
        var event: EventData? = null
        val db = readableDatabase
        var cursor: Cursor? = null
        val selection = "event_id = ?"
        val selectionArgs = arrayOf(eventId.toString())

        try {
            cursor = db.query(
                EVENT_BASE,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null,
                null
            )

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

    fun selectEventAll(): MutableList<EventData> {
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

    fun selectDetailAll(eventId: Int): MutableList<EventDetailData> {
        val events: MutableList<EventDetailData> = mutableListOf()
        val db = readableDatabase
        val selection = "event_id = ?"
        val selectionArgs = arrayOf(eventId.toString())
        var cursor: Cursor? = null

        try {
            cursor = db.query(
                EVENT_DETAIL,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null,
                null
            )
            if (cursor!!.moveToFirst()) {
                while (!cursor.isAfterLast) {
                    val eventId = cursor.getInt((cursor.getColumnIndex("event_id")))
                    val detailId = cursor.getInt((cursor.getColumnIndex("detail_id")))
                    val title = cursor.getString(cursor.getColumnIndex("title"))
                    val dateStart = cursor.getString(cursor.getColumnIndex("date_start"))
                    val dateEnd = cursor.getString(cursor.getColumnIndex("date_end"))
                    val memo = cursor.getString(cursor.getColumnIndex("memo"))

                    events.add(EventDetailData(eventId, detailId, title, dateStart, dateEnd, memo))
                    cursor.moveToNext()
                }
                Log.e("SQL_LOG", "select sql ")
            }
        } catch (e: SQLiteException) {
            Log.e("SQL_LOG", "sql error")
        } finally {
            cursor?.close()
        }
        return events
    }

    private fun getNewDetailId(eventDetailData: EventDetailData): Int {
        val db = readableDatabase
        val projection = arrayOf("detail_id")
        val selection = "event_id = ?"
        val selectionArgs = arrayOf(eventDetailData.eventId.toString())
        val sortOrder = "detail_id DESC"
        val limit = "1"
        var cursor: Cursor? = null
        val nextId: Int
        try {
            cursor = db.query(
                EVENT_DETAIL,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder,
                limit
            )
            if (cursor.moveToFirst()) {
                nextId = cursor.getInt(cursor.getColumnIndex("detail_id")) + 1
            } else {
                nextId = 1
            }
        } finally {
            cursor?.close()
        }
        return nextId
    }
}