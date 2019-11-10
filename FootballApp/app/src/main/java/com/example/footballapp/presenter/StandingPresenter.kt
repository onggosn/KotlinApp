package com.example.footballapp.presenter

import com.example.footballapp.model.standing.StandingResponse
import com.example.footballapp.netservices.ApiRepository
import com.example.footballapp.netservices.TheSportDBApi
import com.example.footballapp.utils.CoroutineContextProvider
import com.example.footballapp.view.standing.StandingView
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StandingPresenter (
    private val view: StandingView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
){
    fun getStandingList(leagueId: String?){
        view.showLoading()
        GlobalScope.launch(context.main) {
            val dataTeam = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getStanding(leagueId)).await(),
                StandingResponse::class.java)

            view.showStanding(dataTeam.table)
            view.hideLoading()
        }
    }
}