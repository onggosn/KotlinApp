package com.example.footballapp.model.standing

import com.google.gson.annotations.SerializedName

data class Standing(
    @SerializedName("name")
    var name: String? = null,

    @SerializedName("teamId")
    var teamId: String? = null,

    @SerializedName("played")
    var played: String? = null,

    @SerializedName("goalsfor")
    var goalsfor: String? = null,

    @SerializedName("goalsagainst")
    var goalsagainst: String? = null,

    @SerializedName("goalsdifference")
    var goalsdifference: String? = null,

    @SerializedName("win")
    var win: String? = null,

    @SerializedName("draw")
    var draw: String? = null,

    @SerializedName("loss")
    var loss: String? = null,

    @SerializedName("total")
    var total: String? = null
)