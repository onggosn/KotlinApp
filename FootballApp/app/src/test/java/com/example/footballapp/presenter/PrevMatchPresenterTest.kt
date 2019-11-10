package com.example.footballapp.presenter

import com.example.footballapp.model.matchlist.PrevMatch
import com.example.footballapp.model.matchlist.PrevMatchResponse
import com.example.footballapp.netservices.ApiRepository
import com.example.footballapp.utils.TestContextProvider
import com.example.footballapp.view.matchlist.MatchView
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Before
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PrevMatchPresenterTest {
    @Mock
    private lateinit var view: MatchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepo: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenterPrev: PrevMatchPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenterPrev = PrevMatchPresenter(view, apiRepo, gson, TestContextProvider())
    }

    //unit test menggunakan mockito untuk test hasil request previous match
    @Test
    fun testGetPrevMatch() {
        val match: MutableList<PrevMatch> = mutableListOf()
        val response = PrevMatchResponse(match, match)
        val id = "4328"

        runBlocking {
            Mockito.`when`(apiRepo.doRequest(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", PrevMatchResponse::class.java)).thenReturn(response)
        }

        presenterPrev.getPrevMatch(id)
        Mockito.verify(view).showLoading()
        Mockito.verify(view).showPrevMatch(match)
        Mockito.verify(view).hideLoading()
    }

    //unit test menggunakan mockito untuk test hasil request pencarian match
    @Test
    fun testGetEvent() {
        val match: MutableList<PrevMatch> = mutableListOf()
        val response = PrevMatchResponse(match, match)
        val eventName = "Arsenal_vs_Chelsea"

        runBlocking {
            Mockito.`when`(apiRepo.doRequest(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", PrevMatchResponse::class.java)).thenReturn(response)
        }

        presenterPrev.getEvent(eventName)
        Mockito.verify(view).showLoading()
        Mockito.verify(view).showPrevMatch(match)
        Mockito.verify(view).hideLoading()
    }
}