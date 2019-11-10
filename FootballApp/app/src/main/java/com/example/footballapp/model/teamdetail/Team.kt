package com.example.footballapp.model.teamdetail

import com.google.gson.annotations.SerializedName

data class Team(
    @SerializedName("idTeam")
    var idTeam: String? = null,

    @SerializedName("strTeamBadge")
    var strTeamBadge: String? = null,

    @SerializedName("strTeam")
    var strTeam: String? = null,

    @SerializedName("strWebsite")
    var strWebsite: String? = null,

    @SerializedName("strDescriptionEN")
    var strDescriptionEN: String? = null,

    @SerializedName("strTeamJersey")
    var strTeamJersey: String? = null,

    @SerializedName("strStadiumThumb")
    var strStadiumThumb: String? = null,

    @SerializedName("strStadium")
    var strStadium: String? = null,

    @SerializedName("strStadiumDescription")
    var strStadiumDescription: String? = null,

    @SerializedName("strStadiumLocation")
    var strStadiumLocation: String? = null,

    @SerializedName("intStadiumCapacity")
    var intStadiumCapacity: String? = null,

    @SerializedName("intFormedYear")
    var intFormedYear: String? = null
)