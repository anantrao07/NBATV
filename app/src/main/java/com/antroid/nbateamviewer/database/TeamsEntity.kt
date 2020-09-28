package com.antroid.nbateamviewer.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.antroid.nbateamviewer.database.converter.PlayersTypeConverter
import com.antroid.nbateamviewer.databaseconstants.*
import com.antroid.nbateamviewer.model.Player
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize
import org.jetbrains.annotations.NotNull

@Entity(tableName = TEAMS_TABLE_NAME)
@Parcelize
class TeamsListTable(): Parcelable {
    @PrimaryKey
    @Expose
    @NotNull
    @ColumnInfo(name = COLUMN_NAME_TEAM_ID)
    var id: Int = 0

    @Expose
    @NotNull
    @ColumnInfo(name = COLUMN_NAME_TEAM_WINS)
    var wins: Int = 0

    @Expose
    @NotNull
    @ColumnInfo(name = COLUMN_NAME_TEAM_LOSSES)
    var losses: Int = 0

    @Expose
    @NotNull
    @ColumnInfo(name = COLUMN_NAME_TEAM_FULL_NAME)
    var fullName: String = ""

    @Expose
    @NotNull
    @TypeConverters(PlayersTypeConverter::class)
    @ColumnInfo(name = COLUMN_NAME_TEAM_PLAYERS)
    var players: List<Player> = listOf()

    constructor(id: Int, wins: Int, losses: Int, fullName: String, players: List<Player>) : this() {
        this.id = id
        this.wins = wins
        this.losses = losses
        this.fullName = fullName
        this.players = players
    }
}