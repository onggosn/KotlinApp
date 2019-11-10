package com.example.footballapp.presenter

import com.example.footballapp.model.League.LeagueDesc
import com.example.footballapp.model.League.LeagueDescResponse
import com.example.footballapp.netservices.ApiRepository
import com.example.footballapp.netservices.TheSportDBApi
import com.example.footballapp.utils.TestContextProvider
import com.example.footballapp.view.leaguedescription.LeagueDescView
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Before
import org.mockito.*
import org.mockito.Mockito.`when`

class LeagueDescPresenterTest {
    @Mock
    private lateinit var view: LeagueDescView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepo: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenter: LeagueDescPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = LeagueDescPresenter(view, apiRepo, gson, TestContextProvider())
    }

    //unit test untuk testing result hasil request deskripsi liga
    @Test
    fun testGetLeagueDesc() {
        val leagues: MutableList<LeagueDesc> = mutableListOf()
        val response = LeagueDescResponse(leagues)
        val id = "4346"

        runBlocking {
            `when`(apiRepo.doRequest(ArgumentMatchers.anyString())).thenReturn(apiResponse)

            `when`(apiResponse.await()).thenReturn("")

            `when`(gson.fromJson("", LeagueDescResponse::class.java)).thenReturn(response)
        }

        presenter.getLeagueDesc(id)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showLeagueDesc(leagues)
        Mockito.verify(view).hideLoading()
        }
    }
