package com.antroid.nbateamviewer.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.antroid.nbateamviewer.database.NBADatabase
import com.antroid.nbateamviewer.database.TeamsListTable
import com.antroid.nbateamviewer.model.Team
import com.antroid.nbateamviewer.network.RetrofitManager
import com.antroid.nbateamviewer.network.TeamService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamsRepository(application: Application) {

    val teamListLiveData: MutableLiveData<List<TeamsListTable>> = MutableLiveData()
    val teamLiveData: MutableLiveData<TeamsListTable> = MutableLiveData()
    val teamListLVData: MutableLiveData<List<Team>> = MutableLiveData()
    private val db = NBADatabase.getInstance(application)
    private val getTeamService: TeamService = RetrofitManager
            .getInstance()
            .getTeamsService
            .create(TeamService::class.java)

    fun performTeamFetch() {
        val teamsData = db.teamsDao().queryTeams()
        if (teamsData.isNullOrEmpty()) {
            retrieveTeamListCall(getTeamService)
        } else {
            teamListLiveData.postValue(teamsData)
        }
    }

    fun getTeam(teamID: Int) {
        val team = db.teamsDao().queryTeamsByID(teamID)
        teamLiveData.postValue(team)
    }

    fun retrieveTeamListCall(teamService: TeamService) {
        val teamsList: ArrayList<TeamsListTable> = ArrayList()
        teamService.getTeamsListUrl().enqueue(
                object : Callback<List<Team>> {
                    override fun onFailure(call: Call<List<Team>>, t: Throwable) {
                        //TODO Handle Error case during network call

                    }

                    override fun onResponse(call: Call<List<Team>>, response: Response<List<Team>>) {
                        response.body()?.let {
                            teamListLVData.postValue(it)
                            it.forEach { eachTeam ->
                                val team = TeamsListTable(
                                        eachTeam.id,
                                        eachTeam.wins,
                                        eachTeam.losses,
                                        eachTeam.fullName,
                                        eachTeam.players)
                                teamsList.add(team)
                            }
                                teamListLiveData.postValue(teamsList)
                                handleRetrievedList(db, teamsList )
                        }
                    }
                }
        )
    }

    fun handleRetrievedList(database: NBADatabase, teamsEntityList: List<TeamsListTable>) {
        CoroutineScope(Dispatchers.IO).launch {
            database.teamsDao().insertTeams(teamsEntityList)
        }
    }
}