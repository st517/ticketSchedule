package com.google.stauk7.ticketschedul.Activity

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.stauk7.ticketschedul.Data.EventDetailData
import com.google.stauk7.ticketschedul.R
import com.google.stauk7.ticketschedul.Ui.CardAdapter

class DetailActivity : AppCompatActivity() {
    val EVENT_ID = "event id"
    private val eventDetailList: MutableList<EventDetailData> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val id = intent.getIntExtra(EVENT_ID, 0)
        loadData(id)
        initView()
    }

    private fun loadData(id: Int){
        // TODO EventIDに一致するdetailをDBから取得
        eventDetailList.add(EventDetailData("title1", "yyyy/mm/dd", "yyyy/mm/dd", "memo1", 0, 0))
        eventDetailList.add(EventDetailData("title2", "yyyy/mm/dd", "yyyy/mm/dd", "memo2", 0, 0))
    }

    private fun initView(){
        val listView = findViewById<ListView>(R.id.detail_list)
        listView.adapter = CardAdapter(this, eventDetailList)
    }

}