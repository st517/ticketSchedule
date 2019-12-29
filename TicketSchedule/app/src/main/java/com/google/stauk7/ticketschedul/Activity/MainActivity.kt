package com.google.stauk7.ticketschedul.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.stauk7.ticketschedul.Data.EventData
import com.google.stauk7.ticketschedul.R

class MainActivity : AppCompatActivity() {

    lateinit var title: TextView

    var mainCardsList: MutableList<EventData>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()
        initView()
    }

    private fun initData() {
        // TODO DBからデータ取得
        mainCardsList = listOf(EventData("title1", "memo1", 0), EventData("title2", "memo2", 0))
    }

    private fun initView() {
        if (!mainCardsList.isNullOrEmpty()) {
            for (cardInfo in mainCardsList!!) {

            }
        }
    }
}
