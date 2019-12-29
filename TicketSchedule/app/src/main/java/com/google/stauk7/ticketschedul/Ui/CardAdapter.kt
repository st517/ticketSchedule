package com.google.stauk7.ticketschedul.Ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.google.stauk7.ticketschedul.Data.EventData
import com.google.stauk7.ticketschedul.R

class CardAdapter(context: Context, cardInfoList: MutableList<EventData>) :
    ArrayAdapter<EventData>(context, 0, cardInfoList) {
    class ViewHolder {
        var title: TextView? = null
        var date: TextView? = null
        var memo: TextView? = null
    }

    private val inflater = LayoutInflater.from(context)


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val holder = ViewHolder()
        var view: View = null
        if (convertView == null) {
            view = inflater.inflate(R.layout.card_item, parent, false)
        } else {
            view = convertView
        }
        holder.title = view.findViewById(R.id.title)
        holder.date = view.findViewById(R.id.date)
        holder.memo = view.findViewById(R.id.memo)

        val eventData = getItem(position)
        holder.title?.text = eventData?.title
        holder.memo?.text = eventData?.memo


        return view
    }
}