package com.example.footballapp.view.teams

import com.example.footballapp.model.teamdetail.Team

interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun showTeam(data: List<Team>)
    fun notFound()
}