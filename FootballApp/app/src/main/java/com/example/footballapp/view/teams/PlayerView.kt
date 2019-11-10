package com.example.footballapp.view.teams

import com.example.footballapp.model.player.PlayerInfo
import com.example.footballapp.model.player.PlayerList

interface PlayerView {
    fun showLoading()
    fun hideLoading()
    fun showPlayerList(data: List<PlayerList>)
    fun showPlayerDetail(data: List<PlayerInfo>)
}