package com.antroid.nbateamviewer.network

import com.antroid.nbateamviewer.model.Team
import retrofit2.Call
import retrofit2.http.GET

interface TeamService {
    @GET("scoremedia/nba-team-viewer/master/input.json")
    fun getTeamsListUrl(): Call<List<Team>>
}