package com.example.footballapp.model.League

object LeagueData {

    private var data =
    arrayOf(
        arrayOf(
            "4328",
            "English Premier League",
            "english_premier_league"
        ),
        arrayOf(
            "4334",
            "French League",
            "french_ligue_1"
        ),
        arrayOf(
            "4331",
            "German Bundesliga",
            "german_bundesliga"
        ),
        arrayOf(
            "4332",
            "Italian Serie A",
            "italian_serie_a"
        ),
        arrayOf(
            "4335",
            "La Liga",
            "spanish_la_liga"
        ),
        arrayOf(
            "4346",
            "American Major League",
            "american_mayor_league"
        ),
        arrayOf(
            "4344",
            "Protugeuese Premiera Ligae",
            "portugeuese_premiera_liga"
        ),
        arrayOf(
            "4356",
            "Australian A League",
            "australian_a_league"
        ),
        arrayOf(
            "4330",
            "Scotish Premiere League",
            "scotish_premier_league"
    )
    )

    val listData: ArrayList<League>
        get() {
            val list = ArrayList<League>()
            for (aData in data) {
                val oLeague = League()
                oLeague.leagueID = aData[0]
                oLeague.leagueName = aData[1]
                oLeague.badges = aData[2]

                list.add(oLeague)
            }
            return list
        }
}