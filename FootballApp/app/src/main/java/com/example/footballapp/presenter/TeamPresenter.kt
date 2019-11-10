package com.example.footballapp.presenter

import com.example.footballapp.model.teamdetail.TeamResponse
import com.example.footballapp.netservices.ApiRepository
import com.example.footballapp.netservices.TheSportDBApi
import com.example.footballapp.utils.CoroutineContextProvider
import com.example.footballapp.view.matchdetail.MatchDetailView
import com.example.footballapp.view.teams.TeamView
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamPresenter (
    private val view: MatchDetailView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
){

    fun getBadge(homeId: String?, awayId: String?) {
        GlobalScope.launch(context.main){
            val dataHome = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getBadge(homeId)).await(),
                TeamResponse::class.java)
            val dataAway= gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getBadge(awayId)).await(),
                TeamResponse::class.java)

                view.showBadge(dataHome.teams, dataAway.teams)
        }
    }

}