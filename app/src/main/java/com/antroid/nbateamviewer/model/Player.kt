package com.antroid.nbateamviewer.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Player(
        var id: Int = 0,
        var first_name: String = "",
        var last_name: String = "",
        var position: String = "",
        var number: Int = 0
): Parcelable