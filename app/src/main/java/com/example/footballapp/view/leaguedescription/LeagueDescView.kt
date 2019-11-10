package com.example.footballapp.view.leaguedescription

import com.example.footballapp.model.League.LeagueDesc

interface LeagueDescView {
    fun showLoading()
    fun hideLoading()
    fun showLeagueDesc(data: List<LeagueDesc>)
}