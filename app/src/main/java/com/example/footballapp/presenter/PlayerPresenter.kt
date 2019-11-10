package com.example.footballapp.presenter

import com.example.footballapp.model.player.PlayerInfoResponse
import com.example.footballapp.model.player.PlayerListResponse
import com.example.footballapp.netservices.ApiRepository
import com.example.footballapp.netservices.TheSportDBApi
import com.example.footballapp.utils.CoroutineContextProvider
import com.example.footballapp.view.teams.PlayerView
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlayerPresenter (
    private val view: PlayerView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
){
    fun getPlayer(teamId: String?){
        view.showLoading()
        GlobalScope.launch(context.main) {
            val dataTeam = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getPlayerList(teamId)).await(),
                PlayerListResponse::class.java)

            view.showPlayerList(dataTeam.player)
            view.hideLoading()
        }
    }

    fun getDetailPlayer(playerId: String?){
        view.showLoading()
        GlobalScope.launch(context.main) {
            val dataTeam = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getPlayerDetail(playerId)).await(),
                PlayerInfoResponse::class.java)

            view.showPlayerDetail(dataTeam.players)
            view.hideLoading()
        }
    }
}