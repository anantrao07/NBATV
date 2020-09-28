package com.antroid.nbateamviewer.ui.team

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.antroid.nbateamviewer.database.TeamsListTable
import com.antroid.nbateamviewer.repository.TeamsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamDetailsViewModel(application: Application) : ViewModel() {

    val teamsRepository: TeamsRepository

    var teamInfoLiveData: MutableLiveData<TeamsListTable>? = MutableLiveData()
    init {
        teamsRepository = TeamsRepository(application)
    }

    class Factory constructor(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return TeamDetailsViewModel(application) as T
        }
    }

    fun getTeam(teamID: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            teamsRepository.getTeam(teamID)
            teamInfoLiveData?.postValue(teamsRepository.teamLiveData.value)
        }
    }
    fun getTeamInfo(): MutableLiveData<TeamsListTable>{
        return teamsRepository.teamLiveData
    }
}