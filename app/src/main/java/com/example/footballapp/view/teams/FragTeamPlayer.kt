package com.example.footballapp.view.teams

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.footballapp.R
import com.example.footballapp.model.player.PlayerInfo
import com.example.footballapp.model.player.PlayerList
import com.example.footballapp.netservices.ApiRepository
import com.example.footballapp.presenter.PlayerPresenter
import com.example.footballapp.utils.invisible
import com.example.footballapp.utils.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.content_listplayer.*
import kotlinx.android.synthetic.main.frag_listplayer.*
import org.jetbrains.anko.support.v4.onRefresh

class FragTeamPlayer : Fragment(), PlayerView {
    private var playerList: MutableList<PlayerList> = mutableListOf()
    private lateinit var teamId: String
    private lateinit var presenter: PlayerPresenter
    private lateinit var adapter: PlayerListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val idTeam = arguments?.getString("id")
        this.teamId = idTeam.toString()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frag_listplayer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val request = ApiRepository()
        val gson = Gson()

        presenter = PlayerPresenter(this, request, gson)
        presenter.getPlayer(teamId)

        adapter = PlayerListAdapter(playerList){
             partItem: PlayerList -> sendTeamId(partItem)
        }

        rvListPlayer.adapter = adapter
        rvListPlayer.layoutManager = LinearLayoutManager(this.context)

        swipeRefresh?.onRefresh {
            presenter.getPlayer(teamId)
        }
    }

    companion object {
        fun newInstance(teamId: String): FragTeamPlayer {
            val args = Bundle()
            args.putString("id", teamId)
            val fragment = FragTeamPlayer()
            fragment.arguments = args
            return fragment
        }
    }

    override fun showLoading() {
        viewprogressBar.visible()
    }

    override fun hideLoading() {
        viewprogressBar.invisible()
    }

    override fun showPlayerList(data: List<PlayerList>) {
        swipeRefresh?.isRefreshing = false
        playerList.clear()
        playerList.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showPlayerDetail(data: List<PlayerInfo>) {}

    private fun sendTeamId(data: PlayerList){
        val intent = Intent(this.context, PlayerDetail::class.java)
        intent.putExtra("playerId", data.idPlayer.toString())
        startActivity(intent)
    }
}