package com.example.footballapp.view.matchlist

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.footballapp.R
import com.example.footballapp.db.FavoriteMatch
import com.example.footballapp.db.database
import com.example.footballapp.utils.invisible
import com.example.footballapp.utils.visible
import com.example.footballapp.view.FavView
import com.example.footballapp.view.matchdetail.MatchDetail
import kotlinx.android.synthetic.main.frag_favmatch.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh

class FragFavorite : Fragment(), FavView{

    private var favorites: MutableList<FavoriteMatch> = mutableListOf()
    private lateinit var adapter: FavMatchAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_favmatch, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = FavMatchAdapter(favorites){
            context?.startActivity<MatchDetail>("MATCHID" to "${it.matchId}")
        }

        frag_favmatch.adapter = adapter
        frag_favmatch.layoutManager = LinearLayoutManager(this.context)
        swipeRefreshFav?.onRefresh {
            showFav()
        }
    }

    override fun onResume() {
        super.onResume ()
        showLoading()
        showFav()
        hideLoading()
    }

    private fun showFav(){
        showLoading()
        favorites.clear()
        context?.database?.use{
            swipeRefreshFav.isRefreshing = false
            val result = select(FavoriteMatch.TABLE_FAVMATCH)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
        hideLoading()
    }

    override fun showLoading() {
        favProgressBar.visible()
    }

    override fun hideLoading() {
        favProgressBar.invisible()
    }
}
