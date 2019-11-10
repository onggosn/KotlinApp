package com.example.footballapp.presenter

import com.example.footballapp.model.matchlist.NextMatch
import com.example.footballapp.model.matchlist.NextMatchResponse
import com.example.footballapp.netservices.ApiRepository
import com.example.footballapp.utils.TestContextProvider
import com.example.footballapp.view.matchlist.MatchView
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

class NextMatchPresenterTest {
    @Mock
    private lateinit var view: MatchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepo: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenterNext: NextMatchPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenterNext = NextMatchPresenter(view, apiRepo, gson, TestContextProvider())
    }

    //unit test menggunakan mockito untuk test hasil request next match
    @Test
    fun getNextMatch() {
        val match: MutableList<NextMatch> = mutableListOf()
        val response = NextMatchResponse(match)
        val id = "4328"

        runBlocking {
            Mockito.`when`(apiRepo.doRequest(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", NextMatchResponse::class.java)).thenReturn(response)
        }

        presenterNext.getNextMatch(id)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showNextMatch(match)
        Mockito.verify(view).hideLoading()
    }
}