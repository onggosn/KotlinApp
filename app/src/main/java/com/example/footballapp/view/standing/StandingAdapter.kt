package com.example.footballapp.view.standing

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.footballapp.R
import com.example.footballapp.model.standing.Standing
import kotlinx.android.synthetic.main.content_standing.view.*

class StandingAdapter (private val standing: List<Standing>)
    : RecyclerView.Adapter<StandingAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.content_standing, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = standing.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(standing[position])
    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bindItem(item: Standing){
            itemView.team.text = item.name
            itemView.play.text = item.played
            itemView.win.text = item.win
            itemView.draw.text = item.draw
            itemView.loss.text = item.loss
            itemView.goals.text = item.goalsfor
            itemView.goalsAgaints.text = item.goalsagainst
            itemView.goalsDif.text = item.goalsdifference
            itemView.points.text = item.total
        }
    }

}