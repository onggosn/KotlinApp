package com.example.footballapp.view.matchlist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.footballapp.R
import com.example.footballapp.model.matchlist.NextMatch
import com.example.footballapp.utils.dateFormat
import kotlinx.android.synthetic.main.content_nextmatch.view.*

class NextMatchAdapter (private val nextMatch: List<NextMatch>, private val listener: (NextMatch) -> Unit):
        RecyclerView.Adapter<NextMatchAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.content_nextmatch, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = nextMatch.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(nextMatch[position], listener)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bindItem(items: NextMatch, listener: (NextMatch) -> Unit){
            itemView.tvDate.text = dateFormat("${items.eventDate} ${items.eventTime}")
            itemView.tvNextHome.text = items.homeTeam
            itemView.tvNextAway.text = items.awayTeam
            itemView.setOnClickListener { listener(items) }
        }
    }

}