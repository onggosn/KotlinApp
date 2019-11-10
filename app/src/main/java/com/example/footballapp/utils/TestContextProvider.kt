package com.example.footballapp.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.coroutines.CoroutineContext

class TestContextProvider : CoroutineContextProvider(){
    @ExperimentalCoroutinesApi
    override val main: CoroutineContext = Dispatchers.Unconfined
    //Unconfined = coroutines dan kode lainnya dijalankan pada thread yang sama
}