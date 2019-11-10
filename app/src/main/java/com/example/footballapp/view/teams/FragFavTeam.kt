package com.example.footballapp.view.teams

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.footballapp.R
import com.example.footballapp.db.FavoriteMatch
import com.example.footballapp.db.FavoriteTeam
import com.example.footballapp.db.database
import com.example.footballapp.model.teamdetail.Team
import com.example.footballapp.utils.invisible
import com.example.footballapp.utils.visible
import kotlinx.android.synthetic.main.frag_favteam.*
import kotlinx.coroutines.selects.select
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh

class FragFavTeam : Fragment(), TeamView {

    private var favorites: MutableList<FavoriteTeam> = mutableListOf()
    private lateinit var adapter: FavTeamAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_favteam, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = FavTeamAdapter(favorites){
            context?.startActivity<TeamDetail>("teamId" to "${it.idTeam}")
        }

        rvFavTeam.adapter = adapter
        rvFavTeam.layoutManager = LinearLayoutManager(this.context)

        swipeRefresh?.onRefresh {
            showLoading()
            showTeam()
            hideLoading()
        }
    }
    override fun showLoading() {
        viewprogressBar.visible()
    }

    override fun hideLoading() {
        viewprogressBar.invisible()
    }

    override fun onResume() {
        super.onResume ()
        showLoading()
        showTeam()
        hideLoading()
    }

    private fun showTeam(){
        favorites.clear()
        context?.database?.use{
            swipeRefresh.isRefreshing = false
            val result = select(FavoriteTeam.TABLE_FAVTEAM)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
        hideLoading()
    }

    override fun showTeam(data: List<Team>) {}
    override fun notFound() {}

}