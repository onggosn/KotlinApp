package com.example.footballapp.presenter

import com.example.footballapp.model.matchdetail.MatchDetailResponse
import com.example.footballapp.netservices.ApiRepository
import com.example.footballapp.netservices.TheSportDBApi
import com.example.footballapp.utils.CoroutineContextProvider
import com.example.footballapp.view.matchdetail.MatchDetailView
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MatchDetailPresenter (
    private val view: MatchDetailView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
){

    fun getMatchDetail(eventId: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getMatchDetail(eventId)).await(),
                MatchDetailResponse::class.java)

                view.showMatchDetail(data.events)
                view.hideLoading()
        }
    }
}