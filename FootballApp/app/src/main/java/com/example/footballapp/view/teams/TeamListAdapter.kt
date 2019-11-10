package com.example.footballapp.view.teams

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.footballapp.R
import com.example.footballapp.model.teamdetail.Team
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content_listteam.view.*

class TeamListAdapter (private val teamList: List<Team>, private val listener: (Team) -> Unit):
        RecyclerView.Adapter<TeamListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.content_listteam, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = teamList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(teamList[position], listener)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bindItem(items: Team, listener: (Team) -> Unit) {
            itemView.teamName.text = items.strTeam
            Picasso.get().load(items.strTeamBadge).into(itemView.teamBadge)

            itemView.setOnClickListener { listener(items) }
        }
    }

}