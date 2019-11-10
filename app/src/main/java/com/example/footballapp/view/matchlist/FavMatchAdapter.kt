package com.example.footballapp.view.matchlist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.footballapp.R
import com.example.footballapp.db.FavoriteMatch
import kotlinx.android.synthetic.main.content_favmatch.view.*

class FavMatchAdapter (private val favorite: List<FavoriteMatch>, private val listener: (FavoriteMatch) -> Unit)
    : RecyclerView.Adapter<FavMatchAdapter.FavoriteMatchHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMatchHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.content_favmatch, parent, false)
        return FavoriteMatchHolder(view)
    }

    override fun getItemCount(): Int = favorite.size

    override fun onBindViewHolder(holder: FavoriteMatchHolder, position: Int) {
        holder.bindItem(favorite[position], listener)
    }


    class FavoriteMatchHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bindItem(favorite: FavoriteMatch, listener: (FavoriteMatch) -> Unit){
            itemView.tvFavDate.text = favorite.dateMatch?:"--:--"
            itemView.tvFavHome.text = favorite.homeTeam?:"-"
            itemView.tvFavAway.text = favorite.awayTeam?:"-"
            itemView.tvFavHomeScore.text = favorite.homeScore?:"0"
            itemView.tvFavAwayScore.text = favorite.awayScore?:"0"
            itemView.setOnClickListener { listener(favorite) }
        }
    }
}

