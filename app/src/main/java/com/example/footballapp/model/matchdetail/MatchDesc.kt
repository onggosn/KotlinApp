package com.example.footballapp.model.matchdetail

import com.google.gson.annotations.SerializedName

class MatchDesc (
    @SerializedName("idEvent")
    var eventId: String? = null,

    @SerializedName("idHomeTeam")
    var idHome: String? = null,

    @SerializedName("idAwayTeam")
    var idAway: String? = null,

    @SerializedName("strEvent")
    var eventName: String? = null,

    @SerializedName("strLeague")
    var leagueName: String? = null,

    @SerializedName("strHomeTeam")
    var homeName: String? = null,

    @SerializedName("strAwayTeam")
    var awayName: String? = null,

    @SerializedName("intHomeScore")
    var homeScore: Int? = null,

    @SerializedName("intAwayScore")
    var awayScore: Int? = null,

    @SerializedName("strHomeGoalDetails")
    var homeGoalScorer: String? = null,

    @SerializedName("strHomeRedCards")
    var homeRedCards: String? = null,

    @SerializedName("strHomeYellowCards")
    var homeYellowCards: String? = null,

    @SerializedName("strHomeLineupGoalkeeper")
    var homeGoalKeeper: String? = null,

    @SerializedName("strHomeLineupDefense")
    var homeDefense: String? = null,

    @SerializedName("strHomeLineupMidfield")
    var homeMidfield: String? = null,

    @SerializedName("strHomeLineupForward")
    var homeForward: String? = null,

    @SerializedName("strHomeLineupSubstitutes")
    var homeSubtitute: String? = null,

    @SerializedName("strHomeformation")
    var homeFormation: String? = null,

    @SerializedName("strAwayGoalDetails")
    var awayGoalScorer: String? = null,

    @SerializedName("strAwayRedCards")
    var awayRedCards: String? = null,

    @SerializedName("strAwayYellowCards")
    var awayYellowCards: String? = null,

    @SerializedName("strAwayLineupGoalkeeper")
    var awayGoalKeeper: String? = null,

    @SerializedName("strAwayLineupDefense")
    var awayDefense: String? = null,

    @SerializedName("strAwayLineupMidfield")
    var awayMidfield: String? = null,

    @SerializedName("strAwayLineupForward")
    var awayForward: String? = null,

    @SerializedName("strAwayLineupSubstitutes")
    var awaySubtitute: String? = null,

    @SerializedName("strAwayFormation")
    var awayFormation: String? = null,

    @SerializedName("intHomeShots")
    var homeShots: Int? = null,

    @SerializedName("intAwayShots")
    var awayShots: Int? = null,

    @SerializedName("dateEvent")
    var dateMatch: String? = null,

    @SerializedName("strDate")
    var stringDate: String? = null,

    @SerializedName("strTime")
    var stringTime: String? = null
)