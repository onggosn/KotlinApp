package com.example.footballapp.view.teams

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class TeamAdapter (fm: FragmentManager, val idLeague: String?) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? = when(position) {
        0 -> FragTeamList.newInstance(idLeague?:"Id Null")
        1 -> FragFavTeam()
        else -> null
    }

    override fun getPageTitle(position: Int): CharSequence = when(position) {
        0 -> "Teams"
        1 -> "Favorite"
        else -> ""
    }

    override fun getCount(): Int = 2

}