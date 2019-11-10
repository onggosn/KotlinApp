package com.example.footballapp.view.matchlist

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class MatchAdapter(fm: FragmentManager, val idLeague: String?) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? = when(position) {
        0 -> FragPrevMatch.newInstance(idLeague?:"Id Null")
        1 -> FragNextMatch.newInstance(idLeague?:"Id Null")
        2 -> FragFavorite()
        else -> null
    }

    override fun getPageTitle(position: Int): CharSequence = when(position) {
        0 -> "Previous Match"
        1 -> "Next Match"
        2 -> "Favorite"
        else -> ""
    }

    override fun getCount(): Int = 3

}