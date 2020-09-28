package com.antroid.nbateamviewer.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Team(
        var id: Int = 0,
        @SerializedName("full_name")
        var fullName: String = "",
        var wins: Int = 0,
        var losses: Int = 0,
        var players:List<Player> = emptyList()
): Parcelable