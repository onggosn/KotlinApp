package com.example.footballapp.db

data class FavoriteTeam(
    val id: Long?,
    val idTeam: String?,
    val strTeam: String?,
    val strTeamBadge: String?
){
    companion object {
        const val TABLE_FAVTEAM: String = "TABLE_FAVTEAM"
        const val ID: String = "ID_"
        const val TEAM_ID: String = "TEAM_ID"
        const val TEAM_NAME: String = "TEAM_NAME"
        const val TEAM_BADGE: String = "TEAM_BADGE"
    }
}