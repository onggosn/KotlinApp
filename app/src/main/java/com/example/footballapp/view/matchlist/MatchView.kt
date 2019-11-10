package com.example.footballapp.view.matchlist

import com.example.footballapp.model.matchlist.NextMatch
import com.example.footballapp.model.matchlist.PrevMatch

interface MatchView {
    fun showLoading()
    fun hideLoading()
    fun showPrevMatch(data: List<PrevMatch>)
    fun showNextMatch(data: List<NextMatch>)
    fun notFound()
}