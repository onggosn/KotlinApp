package com.example.footballapp.view.leaguedescription

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.footballapp.R
import com.example.footballapp.model.League.League
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.gridlayout.v7.gridLayout
import kotlin.collections.ArrayList


class RecyclerViewAdapter(private val context: Context, private val listLeague: ArrayList<League>, private val listener: (League) -> Unit)
    : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = ViewHolder(RvUI().createView(AnkoContext.create(context, parent))
    )

    class RvUI : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
            gridLayout {
                cardView {
                    radius = dip(0).toFloat()

                    linearLayout{
                        orientation = LinearLayout.VERTICAL
                        padding = dip(10)
                        transitionName = "tNameHolder"
                        id = R.id.idLinier

                        imageView {
                            id = R.id.badgesId
                            transitionName = "tImage"
                        }.lparams(width = wrapContent, height = dip(200))

                        view {
                            backgroundColor = Color.LTGRAY
                        }.lparams(width = matchParent, height = dip(1)) {
                            horizontalMargin = dip(2)
                        }

                        textView {
                            id = R.id.tvLeagueName
                            textColor = Color.DKGRAY
                        }.lparams(width = wrapContent, height = wrapContent) {
                            gravity = Gravity.CENTER_HORIZONTAL
                            topMargin = dip(4)
                        }
                    }
                }.lparams {
                    width = matchParent
                    height = wrapContent
                    verticalMargin = dip(4)
                    horizontalMargin = dip(4)
                }
            }
        }
    }

    override fun getItemCount(): Int = listLeague.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bindItem(listLeague[position], listener)
    }

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val badgesView: ImageView = itemView.findViewById(R.id.badgesId)
        val leagueName: TextView = itemView.findViewById(R.id.tvLeagueName)

        fun bindItem(items: League, listener: (League) -> Unit){
            val url = "android.resource://com.example.submissionone/drawable/${items.badges}"
            leagueName.text = items.leagueName
            items.badges?.let { Picasso.get().load(url).into(badgesView) }
            itemView.setOnClickListener { listener(items) }
        }
    }

}