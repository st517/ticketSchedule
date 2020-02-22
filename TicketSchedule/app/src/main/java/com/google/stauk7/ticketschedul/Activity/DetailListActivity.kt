package com.google.stauk7.ticketschedul.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.stauk7.ticketschedul.Data.EventData
import com.google.stauk7.ticketschedul.Data.EventDetailData
import com.google.stauk7.ticketschedul.Helper.EventBaseHelper
import com.google.stauk7.ticketschedul.R
import com.google.stauk7.ticketschedul.Ui.CardAdapter
import com.google.stauk7.ticketschedul.Helper.EventDetailHelper

class DetailListActivity : AppCompatActivity() {
    val EVENT_ID = "event id"
    val DETAIL_ID = "detail id"
    private var eventDetailList: MutableList<EventDetailData> = mutableListOf()
    var eventId: Int? = null
    val dbBaseHelper = EventBaseHelper(this)
    val dbDetailHelper = EventDetailHelper(this)
    var eventData: EventData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_list)
        eventId = intent.getIntExtra(EVENT_ID, 0)
        loadData()
        initView()
        findViewById<ImageView>(R.id.detail_add).setOnClickListener { addFunction() }
    }

    private fun loadData() {
        if (eventId != null) {
            eventData = dbBaseHelper.selectEvent(eventId!!)
            eventDetailList = dbDetailHelper.selectDetailAll(eventId!!)
        }
    }

    private fun initView() {
        findViewById<TextView>(R.id.title).text = eventData?.title
        findViewById<TextView>(R.id.memo).text = eventData?.memo
        val listView = findViewById<ListView>(R.id.detail_list)
        listView.adapter = CardAdapter(this, eventDetailList)
    }

    private fun addFunction() {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(EVENT_ID, eventId)
        startActivity(intent)
    }
}