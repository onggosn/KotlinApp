package com.example.footballapp.presenter

import com.example.footballapp.model.teamdetail.Team
import com.example.footballapp.model.teamdetail.TeamResponse
import com.example.footballapp.netservices.ApiRepository
import com.example.footballapp.utils.TestContextProvider
import com.example.footballapp.view.teams.TeamView
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TeamInfoPresenterTest {
    @Mock
    private lateinit var view: TeamView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepo: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenter: TeamInfoPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = TeamInfoPresenter(view, apiRepo, gson, TestContextProvider())
    }

    @Test
    fun getListTeam() {
        val team: MutableList<Team> = mutableListOf()
        val response = TeamResponse(team)
        val id = "133604"

        runBlocking {
            Mockito.`when`(apiRepo.doRequest(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", TeamResponse::class.java)).thenReturn(response)
        }

        presenter.getListTeam(id)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showTeam(team)
        Mockito.verify(view).hideLoading()
    }

    @Test
    fun getInfoTeam() {
        val team: MutableList<Team> = mutableListOf()
        val response = TeamResponse(team)
        val id = "133604"

        runBlocking {
            Mockito.`when`(apiRepo.doRequest(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", TeamResponse::class.java)).thenReturn(response)
        }

        presenter.getInfoTeam(id)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showTeam(team)
        Mockito.verify(view).hideLoading()
    }

    @Test
    fun searchTeam() {
        val team: MutableList<Team> = mutableListOf()
        val response = TeamResponse(team)
        val id = "133604"

        runBlocking {
            Mockito.`when`(apiRepo.doRequest(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", TeamResponse::class.java)).thenReturn(response)
        }

        presenter.searchTeam(id)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showTeam(team)
        Mockito.verify(view).hideLoading()
    }
}