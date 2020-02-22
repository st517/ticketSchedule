package com.google.stauk7.ticketschedul.Activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.stauk7.ticketschedul.Data.EventDetailData
import com.google.stauk7.ticketschedul.Helper.EventDetailHelper
import com.google.stauk7.ticketschedul.R

class DetailActivity : AppCompatActivity() {

    val EVENT_ID = "event id"
    val DETAIL_ID = "detail id"

    var editId: Int? = null
    var detailId: Int? = null
    private lateinit var etTitle: EditText
    private lateinit var etDateStart: EditText
    private lateinit var etDateEnd: EditText
    private lateinit var etMemo: EditText

    val dbHelper = EventDetailHelper(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        editId = intent.getIntExtra(EVENT_ID, -1)
        detailId = intent.getIntExtra(DETAIL_ID, -1)
        initView()
        findViewById<Button>(R.id.save).setOnClickListener { saveDetail() }
    }

    private fun initView() {
        etTitle = findViewById(R.id.detail_title)
        etDateStart = findViewById(R.id.detail_date_start)
        etDateEnd = findViewById(R.id.detail_date_end)
        etMemo = findViewById(R.id.detail_memo)
    }

    fun saveDetail() {
        if (detailId == -1) {
            val data = EventDetailData(
                editId!!,
                 -1,
                etTitle.text.toString(),
                etDateStart.text.toString(),
                etDateEnd.text.toString(),
                etMemo.text.toString()
            )
            dbHelper.insertDetailData(data)
            finish()
        }

    }
}
