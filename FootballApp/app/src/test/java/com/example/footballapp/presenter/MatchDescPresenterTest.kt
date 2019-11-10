package com.example.footballapp.presenter

import com.example.footballapp.model.matchdetail.MatchDesc
import com.example.footballapp.model.matchdetail.MatchDetailResponse
import com.example.footballapp.model.teamdetail.Team
import com.example.footballapp.model.teamdetail.TeamResponse
import com.example.footballapp.netservices.ApiRepository
import com.example.footballapp.utils.TestContextProvider
import com.example.footballapp.view.matchdetail.MatchDetailView
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Before
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class MatchDescPresenterTest {
    @Mock
    private lateinit var view: MatchDetailView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepo: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenterMatch: MatchDetailPresenter
    private lateinit var presenterTeam: TeamPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenterMatch = MatchDetailPresenter(view, apiRepo, gson, TestContextProvider())
        presenterTeam = TeamPresenter(view, apiRepo, gson, TestContextProvider())
    }

    //unit test untuk testing result request match detail
    @Test
    fun testGetMatchDetail() {
        val match: MutableList<MatchDesc> = mutableListOf()
        val leagueResponse = MatchDetailResponse(match)
        val matchId = "441613"

        val teams: MutableList<Team> = mutableListOf()
        val teamResponse = TeamResponse(teams)
        val homeTeam = "133604"
        val awayTeam = "134301"

        runBlocking {
            `when`(apiRepo.doRequest(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            `when`(apiResponse.await()).thenReturn("")
            `when`(gson.fromJson("", MatchDetailResponse::class.java)).thenReturn(leagueResponse)
            `when`(gson.fromJson("", TeamResponse::class.java)).thenReturn(teamResponse)
        }

        presenterMatch.getMatchDetail(matchId)
        presenterTeam.getBadge(homeTeam, awayTeam)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showMatchDetail(match)
        Mockito.verify(view).hideLoading()
    }
}