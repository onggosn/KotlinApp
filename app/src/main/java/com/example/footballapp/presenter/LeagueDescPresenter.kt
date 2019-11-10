package com.example.footballapp.presenter

import com.example.footballapp.model.League.LeagueDescResponse
import com.example.footballapp.netservices.ApiRepository
import com.example.footballapp.netservices.TheSportDBApi
import com.example.footballapp.utils.CoroutineContextProvider
import com.example.footballapp.view.leaguedescription.LeagueDescView
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LeagueDescPresenter(
    private val view: LeagueDescView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getLeagueDesc(leagueId: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getLeagueDesc(leagueId)).await(),
                LeagueDescResponse::class.java)

                view.showLeagueDesc(data.leagues)
                view.hideLoading()
        }
    }
}