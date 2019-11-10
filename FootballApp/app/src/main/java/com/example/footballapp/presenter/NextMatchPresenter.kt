package com.example.footballapp.presenter

import com.example.footballapp.model.matchlist.NextMatchResponse
import com.example.footballapp.netservices.ApiRepository
import com.example.footballapp.netservices.TheSportDBApi
import com.example.footballapp.utils.CoroutineContextProvider
import com.example.footballapp.view.matchlist.MatchView
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NextMatchPresenter (
    private val view: MatchView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider() ){

    fun getNextMatch(leagueId: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getNextMatch(leagueId)).await(),
                NextMatchResponse::class.java)

                view.showNextMatch(data.events)
                view.hideLoading()
        }
    }
}