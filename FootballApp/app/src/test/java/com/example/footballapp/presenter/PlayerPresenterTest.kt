package com.example.footballapp.presenter

import com.example.footballapp.model.player.PlayerList
import com.example.footballapp.model.player.PlayerListResponse
import com.example.footballapp.netservices.ApiRepository
import com.example.footballapp.utils.TestContextProvider
import com.example.footballapp.view.teams.PlayerView
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Before
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PlayerPresenterTest {
    @Mock
    private lateinit var view: PlayerView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepo: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenterNext: PlayerPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenterNext = PlayerPresenter(view, apiRepo, gson, TestContextProvider())
    }

    //unit test menggunakan mockito untuk test hasil request list player team
    @Test
    fun getPlayer() {
        val players: MutableList<PlayerList> = mutableListOf()
        val response = PlayerListResponse(players)
        val id = "133604"

        runBlocking {
            Mockito.`when`(apiRepo.doRequest(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", PlayerListResponse::class.java)).thenReturn(response)
        }

        presenterNext.getPlayer(id)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showPlayerList(players)
        Mockito.verify(view).hideLoading()
    }
}