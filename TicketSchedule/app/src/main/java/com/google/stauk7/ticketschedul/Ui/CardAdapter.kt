package com.google.stauk7.ticketschedul.Ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.google.stauk7.ticketschedul.Data.EventDetailData
import com.google.stauk7.ticketschedul.R
import java.lang.String.format

class CardAdapter(context: Context, cardInfoList: MutableList<EventDetailData>) :
    ArrayAdapter<EventDetailData>(context, 0, cardInfoList) {
    class ViewHolder {
        var title: TextView? = null
        var date: TextView? = null
        var memo: TextView? = null
    }

    private val inflater = LayoutInflater.from(context)


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val holder = ViewHolder()
        val view: View
        if (convertView == null) {
            view = inflater.inflate(R.layout.card_item, parent, false)
        } else {
            view = convertView
        }
        holder.title = view.findViewById(R.id.card_title)
        holder.date = view.findViewById(R.id.card_date)
        holder.memo = view.findViewById(R.id.card_memo)

        val eventData = getItem(position)
        holder.title?.text = eventData?.title
        holder.memo?.text = eventData?.memo
        holder.date?.text = format("%s ~ %s", eventData?.dateStart, eventData?.dateEnd)


        return view
    }
}