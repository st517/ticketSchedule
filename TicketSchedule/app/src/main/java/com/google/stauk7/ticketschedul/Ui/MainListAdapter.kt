package com.google.stauk7.ticketschedul.Ui

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.TextView
import com.google.stauk7.ticketschedul.Data.EventDetailData

class MainListAdapter(context: Context, cardInfoList: MutableList<EventDetailData>) :
    ArrayAdapter<EventDetailData>(context, 0, cardInfoList) {
    class ViewHolder {
        var title: TextView? = null
        var date: TextView? = null
        var memo: TextView? = null
    }
}