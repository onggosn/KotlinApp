package com.example.footballapp.view.matchlist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.footballapp.R
import com.example.footballapp.model.matchlist.PrevMatch
import com.example.footballapp.utils.dateFormat
import kotlinx.android.synthetic.main.content_prevmatch.view.*

class PrevMatchAdapter (private val prevMatch: List<PrevMatch>, private val listener: (PrevMatch) -> Unit):
    RecyclerView.Adapter<PrevMatchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.content_prevmatch, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = prevMatch.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(prevMatch[position], listener)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bindItem(items: PrevMatch, listener: (PrevMatch) -> Unit) {

            when {
                items.eventTime?.count() == 13 -> items.eventTime?.replace("+00:00","")
                items.eventTime?.count() == 7 -> items.eventTime
                items.eventTime          == null -> items.eventTime = "00:00:00"
            }

            itemView.tvDate.text = dateFormat("${items.eventDate} ${items.eventTime}")
            itemView.tvPrevHome.text = items.homeTeam
            itemView.tvPrevAway.text = items.awayTeam

            if (items.awayScore == null){
                itemView.tvAwayScore.text = ""
            } else {
                itemView.tvAwayScore.text = items.awayScore.toString()
            }

            if (items.homeScore == null){
                itemView.tvHomeScore.text = ""
            } else {
                itemView.tvHomeScore.text = items.homeScore.toString()
            }

            itemView.setOnClickListener { listener(items) }
        }
    }
}


