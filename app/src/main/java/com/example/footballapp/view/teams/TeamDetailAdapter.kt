package com.example.footballapp.view.teams

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class TeamDetailAdapter (fm: FragmentManager, val teamId: String?) : FragmentPagerAdapter(fm){

    override fun getItem(position: Int): Fragment? = when(position) {
        0 -> FragTeamInfo.newInstance(teamId?:"null")
        1 -> FragTeamPlayer.newInstance(teamId?:"null")
        else -> null
    }

    override fun getPageTitle(position: Int): CharSequence = when(position) {
        0 -> "Team Info"
        1 -> "Player"
        else -> ""
    }

    override fun getCount(): Int = 2
}