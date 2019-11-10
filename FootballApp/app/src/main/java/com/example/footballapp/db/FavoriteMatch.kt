package com.example.footballapp.db

data class FavoriteMatch(
    val id: Long?,
    val matchId: String?,
    val dateMatch: String?,
    val homeTeam: String?,
    val awayTeam: String?,
    val homeScore: String?,
    val awayScore: String?){

    companion object{
        const val TABLE_FAVMATCH: String = "TABLE_FAVMATCH"
        const val ID: String = "ID_"
        const val MATCH_ID: String = "MATCH_ID"
        const val DATE_MATCH: String = "DATE_MATCH"
        const val HOME_TEAM: String = "HOME_TEAM"
        const val AWAY_TEAM: String = "AWAY_TEAM"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val AWAY_SCORE: String = "AWAY_SCORE"

    }
}