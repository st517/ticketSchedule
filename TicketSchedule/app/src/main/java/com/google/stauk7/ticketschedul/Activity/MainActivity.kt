package com.google.stauk7.ticketschedul.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.stauk7.ticketschedul.Data.EventData
import com.google.stauk7.ticketschedul.R

class MainActivity : AppCompatActivity() {
    val EVENT_ID = "event id"
    lateinit var listView: ListView

    var mainList: MutableList<Map<String, String>> = mutableListOf()
    var eventDataList: MutableList<EventData> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()
        initView()
    }

    private fun initData() {
        // TODO DBからデータ取得
        eventDataList.add(EventData("title1", "memo1", 0))
        eventDataList.add(EventData("title2", "memo2", 0))
        for (eventData in eventDataList) {
            val map = mapOf("title" to eventData.title, "memo" to eventData.memo)
            mainList.add(map)
        }

    }

    private fun initView() {
        listView = findViewById(R.id.main_list)
        if (eventDataList.isNullOrEmpty()) {
            listView.visibility = View.GONE
            findViewById<TextView>(R.id.no_data).visibility = View.VISIBLE
        } else {
            val adapter = SimpleAdapter(
                this,
                mainList,
                R.layout.list_item,
                arrayOf("title", "memo"),
                intArrayOf(R.id.list_title, R.id.list_memo)
            )
            listView.adapter = adapter
        }
        listView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, DetailActivity::class.java)
//            intent.putExtra(EVENT_ID, position)
            startActivity(intent)
        }

        findViewById<ImageView>(R.id.main_add).setOnClickListener{
            val i = Intent(this, DetailActivity::class.java)
            startActivity(i)
        }
    }
}
