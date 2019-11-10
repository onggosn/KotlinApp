package com.example.footballapp.view.teams

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.footballapp.R
import com.example.footballapp.model.player.PlayerList
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content_listplayer.view.*

class PlayerListAdapter (private val playerList: List<PlayerList>, private val listener: (PlayerList) -> Unit) :
        RecyclerView.Adapter<PlayerListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.content_listplayer, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = playerList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(playerList[position], listener)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)  {
        fun bindItem(items: PlayerList, listener: (PlayerList) -> Unit) {
            itemView.playerName.text = items.strPlayer
            Picasso.get().load(items.strCutout).into(itemView.playerImg)

            itemView.setOnClickListener { listener(items) }
        }
    }


}