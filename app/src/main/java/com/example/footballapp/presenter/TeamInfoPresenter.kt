package com.example.footballapp.presenter

import com.example.footballapp.model.teamdetail.TeamResponse
import com.example.footballapp.netservices.ApiRepository
import com.example.footballapp.netservices.TheSportDBApi
import com.example.footballapp.utils.CoroutineContextProvider
import com.example.footballapp.view.teams.TeamView
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamInfoPresenter (
    private val view: TeamView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
){
    fun getListTeam(leagueId: String?){
        view.showLoading()
        GlobalScope.launch(context.main) {
            val dataTeam = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getListTeam(leagueId)).await(),
                TeamResponse::class.java)

            view.showTeam(dataTeam.teams)
            view.hideLoading()
        }
    }

    fun getInfoTeam(teamId: String?){
        view.showLoading()
        GlobalScope.launch(context.main) {
            val dataTeam = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getInfoTeam(teamId)).await(),
                TeamResponse::class.java)

            view.showTeam(dataTeam.teams)
            view.hideLoading()
        }
    }

    fun searchTeam(teamId: String?){
        view.showLoading()
        GlobalScope.launch(context.main) {
            val dataTeam = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeam(teamId)).await(),
                TeamResponse::class.java)

            if (dataTeam.teams != null) {
                view.showTeam(dataTeam.teams)
            }else{
                view.notFound()
            }
            view.hideLoading()
        }
    }

}