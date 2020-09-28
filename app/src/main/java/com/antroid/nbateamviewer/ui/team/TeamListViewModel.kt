package com.antroid.nbateamviewer.ui.team

import android.app.Application
import androidx.lifecycle.*
import com.antroid.nbateamviewer.database.TeamsListTable
import com.antroid.nbateamviewer.repository.TeamsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamListViewModel(application: Application) : ViewModel() {
    val teamRepository: TeamsRepository
    init {
        teamRepository = TeamsRepository(application)
        fetchTeamsList()
    }
    private val teamListLiveData: MutableLiveData<List<TeamsListTable>> = MutableLiveData()
    private var teamFetchedList: List<TeamsListTable>? = emptyList()

    class Factory constructor(private val application: Application): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return TeamListViewModel(application) as T
        }
    }
    private fun fetchTeamsList() {
        CoroutineScope(Dispatchers.IO).launch {
            teamRepository.performTeamFetch()
            teamFetchedList = teamRepository.teamListLiveData.value
            teamListLiveData.postValue(teamFetchedList)
        }
    }

    fun getTeamList(): MutableLiveData<List<TeamsListTable>> {
        return teamRepository.teamListLiveData
    }

}