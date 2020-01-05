package com.google.stauk7.ticketschedul.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.stauk7.ticketschedul.Data.EventData
import com.google.stauk7.ticketschedul.R
import com.google.stauk7.ticketscheseledul.Helper.EventBaseHelper

class MainActivity : AppCompatActivity() {
    val EVENT_ID = "event id"
    val TITLE = "title"
    val MEMO = "memo"
    lateinit var listView: ListView

    var mainList: MutableList<Map<String, String>> = mutableListOf()
    var eventDataList: MutableList<EventData> = mutableListOf()
    val dbHelper = EventBaseHelper(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()
        initView()
    }

    private fun initData() {
        eventDataList = dbHelper.selectAll()
    }

    private fun initView() {
        listView = findViewById(R.id.main_list)
        if (eventDataList.isNullOrEmpty()) {
            listView.visibility = View.GONE
            findViewById<TextView>(R.id.no_data).visibility = View.VISIBLE
        } else {
            for (event in eventDataList) {
                val eventMap = mapOf(TITLE to event.title, MEMO to event.memo)
                mainList.add(eventMap)
            }
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


        findViewById<ImageView>(R.id.main_add).setOnClickListener {
            val title = findViewById<EditText>(R.id.input_title).text.toString()
            val memo = findViewById<EditText>(R.id.input_memo).text.toString()
            dbHelper.saveData(EventData(title, memo))

            val intent = Intent(this, DetailActivity::class.java)
//            intent.putExtra(EVENT_ID, position)
            startActivity(intent)
        }

    }
}
