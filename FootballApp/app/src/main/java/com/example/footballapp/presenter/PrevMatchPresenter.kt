package com.example.footballapp.presenter

import com.example.footballapp.model.matchlist.PrevMatchResponse
import com.example.footballapp.netservices.ApiRepository
import com.example.footballapp.netservices.TheSportDBApi
import com.example.footballapp.utils.CoroutineContextProvider
import com.example.footballapp.view.matchlist.MatchView
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PrevMatchPresenter (
    private val view: MatchView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider() ){

    fun getPrevMatch(leagueId: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getPrevMatch(leagueId)).await(),
                PrevMatchResponse::class.java)

                view.showPrevMatch(data.events)
                view.hideLoading()
        }
    }

    fun getEvent(eventName: CharSequence?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getEvent(eventName)).await(),
                PrevMatchResponse::class.java)

                if (data.event != null) {
                    view.showPrevMatch(data.event)
                }else{
                    view.notFound()
                }
            view.hideLoading()
        }
    }
}