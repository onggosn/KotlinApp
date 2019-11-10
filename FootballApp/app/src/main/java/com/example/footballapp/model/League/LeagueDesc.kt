package com.example.footballapp.model.League

import com.google.gson.annotations.SerializedName

data class LeagueDesc(
    @SerializedName("idLeague")
    var leagueID: String? = null,

    @SerializedName("strLeague")
    var leagueName: String? = null,

    @SerializedName("strCountry")
    var leagueCountry: String? = null,

    @SerializedName("strDescriptionEN")
    var leagueDesc: String? = null,

    @SerializedName("strBadge")
    var leagueBadge: String? = null,

    @SerializedName("strWebsite")
    var leagueWeb: String? = null
)