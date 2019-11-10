package com.example.footballapp.presenter

import com.example.footballapp.model.standing.Standing
import com.example.footballapp.model.standing.StandingResponse
import com.example.footballapp.netservices.ApiRepository
import com.example.footballapp.utils.TestContextProvider
import com.example.footballapp.view.standing.StandingView
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Before
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class StandingPresenterTest {
    @Mock
    private lateinit var view: StandingView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepo: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenter: StandingPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = StandingPresenter(view, apiRepo, gson, TestContextProvider())
    }

    @Test
    fun getStandingList() {
        val standing: MutableList<Standing> = mutableListOf()
        val response = StandingResponse(standing)
        val id = "4346"

        runBlocking {
            Mockito.`when`(apiRepo.doRequest(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", StandingResponse::class.java)).thenReturn(response)
        }

        presenter.getStandingList(id)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showStanding(standing)
        Mockito.verify(view).hideLoading()
    }
}