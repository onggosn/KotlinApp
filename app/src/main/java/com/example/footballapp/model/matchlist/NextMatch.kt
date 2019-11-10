package com.example.footballapp.model.matchlist

import com.google.gson.annotations.SerializedName

data class NextMatch(
    @SerializedName("idEvent")
    var eventId: String? = null,

    @SerializedName("dateEvent")
    var eventDate: String? = null,

    @SerializedName("strEvent")
    var eventName: String? = null,

    @SerializedName("strTime")
    var eventTime: String? = null,

    @SerializedName("strHomeTeam")
    var homeTeam: String? = null,

    @SerializedName("strAwayTeam")
    var awayTeam: String? = null
)