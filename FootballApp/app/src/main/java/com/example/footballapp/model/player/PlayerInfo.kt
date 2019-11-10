package com.example.footballapp.model.player

import com.google.gson.annotations.SerializedName

data class PlayerInfo(
    @SerializedName("idPlayer")
    var idPlayer: String? = null,

    @SerializedName("strNationality")
    var strNationality: String? = null,

    @SerializedName("strPlayer")
    var strPlayer: String? = null,

    @SerializedName("strTeam")
    var strTeam: String? = null,

    @SerializedName("dateBorn")
    var dateBorn: String? = null,

    @SerializedName("strBirthLocation")
    var strBirthLocation: String? = null,

    @SerializedName("strDescriptionEN")
    var strDescriptionEN: String? = null,

    @SerializedName("strPosition")
    var strPosition: String? = null,

    @SerializedName("strHeight")
    var strHeight: String? = null,

    @SerializedName("strWeight")
    var strWeight: String? = null,

    @SerializedName("strCutout")
    var strCutout: String? = null,

    @SerializedName("strFanart1")
    var strFanart1: String? = null
)