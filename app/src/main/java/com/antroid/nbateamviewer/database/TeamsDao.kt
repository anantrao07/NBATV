package com.antroid.nbateamviewer.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.antroid.nbateamviewer.databaseconstants.QUERY_GET_TEAMS
import com.antroid.nbateamviewer.databaseconstants.QUERY_GET_TEAM_BY_ID

@Dao
interface TeamsDao {
    @Insert(onConflict = REPLACE)
    fun insertTeams(team: List<TeamsListTable>)

    @Query(QUERY_GET_TEAMS)
    fun queryTeams(): List<TeamsListTable>

    @Query(QUERY_GET_TEAM_BY_ID)
    fun queryTeamsByID(teamID: Int): TeamsListTable

}