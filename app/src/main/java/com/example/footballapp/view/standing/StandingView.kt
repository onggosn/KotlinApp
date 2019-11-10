package com.example.footballapp.view.standing

import com.example.footballapp.model.standing.Standing

interface StandingView {
    fun showLoading()
    fun hideLoading()
    fun showStanding(data: List<Standing>)
}