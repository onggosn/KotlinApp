package com.example.footballapp.view.matchdetail

import com.example.footballapp.model.matchdetail.MatchDesc
import com.example.footballapp.model.teamdetail.Team

interface MatchDetailView {
    fun showLoading()
    fun hideLoading()
    fun showMatchDetail(data: List<MatchDesc>)
    fun showBadge(dataHome: List<Team>, dataAway: List<Team>)
}