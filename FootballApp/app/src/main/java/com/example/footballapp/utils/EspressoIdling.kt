package com.example.footballapp.utils

import android.support.test.espresso.IdlingResource
import android.support.test.espresso.idling.CountingIdlingResource

object EspressoIdling {
    private val RESOURCE = "GLOBAL"
    private val countingIdlingResource = CountingIdlingResource(RESOURCE)

    val idlingResource: IdlingResource
    get() = countingIdlingResource

    fun increment(){
        countingIdlingResource.increment()
    }

    fun decrement(){
        countingIdlingResource.decrement()
    }
}