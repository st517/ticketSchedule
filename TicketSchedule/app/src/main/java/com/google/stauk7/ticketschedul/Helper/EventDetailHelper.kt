package com.google.stauk7.ticketschedul.Helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.google.stauk7.ticketschedul.Data.EventDetailData


class EventDetailHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, VERSION) {

    companion object {
        const val DB_NAME = "ticket.db"
        const val VERSION = 3
        const val SQL_CREATE_EVENT_BASE =
            "CREATE TABLE mst_event_base (event_id Integer PRIMARY KEY, name TEXT, memo TEXT, delete_flg INTEGER)"
        const val SQL_CREATE_EVENT_DETAIL =
            "CREATE TABLE mst_event_detail (event_id Integer, detail_id Integer, text TEXT, date_start TEXT, date_end TEXT, memo TEXT, delete_flg INTEGER, check_flg INTEGER, PRIMARY KEY(event_id, detail_id))"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_EVENT_DETAIL)
        Log.e("SQL_LOG", "onCreate")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS mst_event_detail")
        onCreate(db)
        Log.e("SQL_LOG", "update")
    }

    fun insertDetailData(eventDetailData: EventDetailData) {
        val db = writableDatabase

        val values = ContentValues()
        values.put("event_id", eventDetailData.eventId)
        values.put("detail_id", eventDetailData.detailId)
        values.put("text", eventDetailData.title)
        values.put("date_start", eventDetailData.dateStart)
        values.put("date_end", eventDetailData.dateEnd)
        values.put("memo", eventDetailData.memo)

        db.insert("mst_event_detail", null, values)
        Log.e("SQL_LOG", "save detail data")
    }

    fun selectDetailAll(eventId: Int): MutableList<EventDetailData> {
        val events: MutableList<EventDetailData> = mutableListOf()
        val db = readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery("select * from mst_event_detail where event_id = $eventId", null)

            if (cursor!!.moveToFirst()) {
                while (!cursor.isAfterLast) {
                    val eventId = cursor.getInt((cursor.getColumnIndex("event_id")))
                    val detailId = cursor.getInt((cursor.getColumnIndex("detail_id")))
                    val title = cursor.getString(cursor.getColumnIndex("text"))
                    val dateStart = cursor.getString(cursor.getColumnIndex("date_start"))
                    val dateEnd = cursor.getString(cursor.getColumnIndex("date_end"))
                    val memo = cursor.getString(cursor.getColumnIndex("memo"))

                    events.add(EventDetailData(eventId, detailId, title, dateStart, dateEnd, memo))
                    cursor.moveToNext()
                }
                Log.e("SQL_LOG", "select sql ")
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
