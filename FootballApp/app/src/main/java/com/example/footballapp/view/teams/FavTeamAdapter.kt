package com.example.footballapp.view.teams

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.footballapp.R
import com.example.footballapp.db.FavoriteTeam
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content_favteam.view.*

class FavTeamAdapter (private val favorite: List<FavoriteTeam>, private val listener: (FavoriteTeam) -> Unit)
    : RecyclerView.Adapter<FavTeamAdapter.FavoriteMatchHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): FavTeamAdapter.FavoriteMatchHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.content_favteam, parent, false)
        return FavoriteMatchHolder(view)
    }

    override fun getItemCount(): Int = favorite.size

    override fun onBindViewHolder(holder: FavTeamAdapter.FavoriteMatchHolder, position: Int) {
        holder.bindItem(favorite[position], listener)
    }

    class FavoriteMatchHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bindItem(fav: FavoriteTeam, listener: (FavoriteTeam) -> Unit) {
            itemView.favTeamName.text = fav.strTeam
            Picasso.get().load(fav.strTeamBadge).into(itemView.favTeamBadge)

            itemView.setOnClickListener { listener(fav) }
        }
    }
}