package com.antroid.nbateamviewer.network

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.antroid.nbateamviewer.database.TeamsListTable
import com.antroid.nbateamviewer.mock
import com.antroid.nbateamviewer.model.Player
import com.antroid.nbateamviewer.repository.TeamsRepository
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

@RunWith(MockitoJUnitRunner::class)
class TeamServiceTest {
    private val mockWebServer = MockWebServer()
    private lateinit var teamService: TeamService
    private lateinit var teamsRepository: TeamsRepository
    private var teamObserverForever: Observer<List<TeamsListTable>> = mock()
    @Mock lateinit var application: Application
    @Mock lateinit var context: Context

    @Rule @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        `when`(application.applicationContext).thenReturn(context)
        teamsRepository = TeamsRepository(application)
        mockWebServer.start()
        teamsRepository.teamListLiveData.observeForever(teamObserverForever)

        teamService = Retrofit.Builder()
                .baseUrl("http://${mockWebServer.hostName}:${mockWebServer.port}")
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient.Builder().build())
                .build().create(TeamService::class.java)
    }

    @After
    fun tearDown(){
        mockWebServer.shutdown()
        teamsRepository.teamListLiveData.removeObserver { teamObserverForever }
    }

    @Test
    fun verifyValidResponseFromServer() {
        mockWebServer.enqueue(MockResponse().apply {
            this.setResponseCode(HttpURLConnection.HTTP_OK)
            this.setBody(mockResponseBody(listOf(teamRaptors)))
        })
        teamsRepository.retrieveTeamListCall(teamService)
        teamsRepository.teamListLiveData.observeForever{

            assert(it.isNotEmpty())
            assertEquals(
                    teamRaptors
                    ,teamsRepository.teamListLiveData.value)
        }
    }


    private fun mockResponseBody(teams: List<TeamsListTable> = emptyList()): String {
        return Gson().toJson(teams)
    }

    private val raptorPlayers = Player(
            id = 46480,
            first_name = "OG",
            last_name = "Anunoby",
            position = "F",
            number = 3
    )
    private val teamRaptors = TeamsListTable(
            id = 5,
            fullName = "Toronto Raptors",
            losses = 17,
            players = listOf(raptorPlayers),
            wins = 45
    )
}